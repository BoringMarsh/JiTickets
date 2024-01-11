<template>
  <el-card>
  <div>
    <v-form-render :form-json="formJson" :form-data="formData" :option-data="optionData" ref="vFormRef">
    </v-form-render>
    <el-row>
    <el-col :span="14" :offset="3">
      <el-form-item label="🧩社团图片">
        <el-upload
          v-model:file-list='fileList_pic'
          class="upload-demo"
          action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
          :auto-upload="false"
          :multiple="true"
          :limit="9"
          :before-upload="beforeImageUpload"
          :on-exceed="handleExceed">
          <template #trigger>
            <el-button type="primary">select file</el-button>
          </template>
        </el-upload>
      </el-form-item>
    </el-col>
  </el-row>
    <el-row>
      <el-col :span="11"/>
    <el-button type="danger" size="large" plain  @click="submitForm">提交信息</el-button>
  </el-row>
    <!-- <el-button type="primary" @click="returnDetailPage">返回商品列表</el-button> -->
  </div>
</el-card>
</template>

<script lang = "ts" setup>
  import { ref, reactive, toRaw } from 'vue'
  import { ElMessage } from 'element-plus'
  import axios from 'axios';
  import vformjson from '../../vform.json'
  import { useRouter,useRoute } from 'vue-router';
  const formJson = reactive(vformjson)
  const fileList_pic = ref([]); // 用于保存活动图片的文件列表
  
  const formData = reactive({})
  const optionData = reactive({})
  const vFormRef = ref(null)
  const router=useRouter();
  const route=useRoute();
  const socId = sessionStorage.getItem('socId') as string;

  const submitForm = () => {
    console.log('vFormRef',vFormRef)
    vFormRef.value.getFormData().then(async formData => {
      console.log('formData',formData);
      formData.socId = socId;
      formData.base64ActImages = [];
      for (let fileObj of fileList_pic.value) {
        if (fileObj.raw) {
          formData.base64ActImages.push(await toBase64(fileObj));
        }
      }
      //console.log("为了传商品基本信息获取IMAGE_KEY"+localStorage.getItem('IMAGE_KEY'));
      
      //delete formData.com_image_upload;
      //formData.IMAGE_KEY = localStorage.getItem('IMAGE_KEY');
      console.log("往后端传活动Json"+JSON.stringify(formData));
      const response = await axios.post("/api/society/activity/upload",JSON.stringify(formData),
      { headers: {'Content-Type': 'application/json'} } );
      console.log("活动上传成功后获得ID",response.data);
      
      ElMessage({
        showClose: true,
        message: '上传成功！您的活动ID：'+response.data.actId,
        type: 'success',
      })
      //location.reload();
    }).catch(error => {
      // Form Validation failed
      ElMessage.error(error)

    })
  }

  const getKeywordsRemote = (query) => {
  return new Promise((resolve, reject) => {
    axios.get('/api/keywords/getkeywords', {
      headers: {
        "Access-Control-Allow-Origin": "*",
      }
    }).then(response => {
      console.log(response.data)
      const keywords = response.data.keywords;
      const formattedKeywords = keywords.map(keyword => {
        return {
          value: keyword,
          label: keyword
        };
      });
      resolve(formattedKeywords);
      console.log(formattedKeywords);
    }).catch((error) => {
      console.log('An error occurred:', error);
      reject(error);
    });
  });
}

  const returnDetailPage = () =>{
    console.log(socId);
  router.push({
      path: '/detail',
      query:{
        userId:socId
      }
  });
}
const beforeImageUpload = (file: File) => {
    if (fileList_pic.value.length >= 9) {
      ElMessage.error('You can only upload up to 9 images.');
      return false;
    }
    return true;
};

const handleExceed = () => {
    ElMessage.warning('You can only upload up to 9 images.');
};
// 修改 toBase64 方法以接收包含文件的对象
function toBase64(fileObj) {
  return new Promise((resolve, reject) => {
    if (fileObj && fileObj.raw instanceof File) {
      const reader = new FileReader();
      reader.readAsDataURL(fileObj.raw);
      reader.onload = () => resolve(reader.result);
      reader.onerror = error => reject(error);
    } else {
      reject(new Error("No file object found"));
    }
  });
}
</script>