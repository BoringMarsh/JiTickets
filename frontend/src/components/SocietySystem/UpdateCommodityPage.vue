<template>
  
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
  </div>

  <el-row>

  <el-col :span="3"/>
  <!-- <el-upload
    ref="uploadImageRef"
    v-model:file-list="fileList"
  
    list-type="picture-card"
    :data = ACT_ID_TYPE     
    :auto-upload="true"
    :on-preview="handlePictureCardPreview"
    :on-exceed = "handleExceed"
    :on-remove="handleRemove"
    :on-progress  ="handleProgress"
    :on-success="handleSuccess"
    :before-remove = "handleBeforeRemove"
    :limit  =3
  >
  <el-icon><Plus /></el-icon>
</el-upload> -->

<el-dialog v-model="dialogVisible">
  <img w-full :src="dialogImageUrl" alt="Preview Image" />
</el-dialog>
</el-row>
<el-row>
    <el-col :span="11"/>
  <el-button type="danger" size="large" plain  @click="submitForm">提交信息</el-button>
</el-row>
</template>

<script lang = 'ts' setup>
import { ref, reactive, toRaw, onMounted, onBeforeMount } from 'vue'
import { useRouter,useRoute } from 'vue-router';
import { ElMessage, UploadFile } from 'element-plus'
import type { UploadProps, UploadUserFile ,UploadInstance } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import axios from 'axios';
import vformjson1 from '../../vform.json'
const formJson = reactive( {})
const formData = reactive({})
const optionData = reactive({})
const vFormRef = ref(null)
const router=useRouter();
const route=useRoute();
const socId = ref('');
const actId = ref('');
actId.value=route.query.actId as string; 

type ACT_ID  = Record<string,number|string>;
const ACT_ID_TYPE:ACT_ID = {
"ACT_ID":parseInt (actId.value)
};

const fileList = ref<UploadUserFile[]>([]);
const dialogImageUrl = ref('')
const dialogVisible = ref(false)
const uploadImageRef = ref<UploadInstance>()

onBeforeMount(
() =>{
  console.log('mount！');
  
  axios.get('/api/activity-personal/activity/'+parseInt(actId.value)) 
      .then(response =>{
          let newFormData = null
          console.log(response.data);
          newFormData = response.data; 
          vFormRef.value.setFormJson(vformjson1);
          localStorage.setItem('isFormDataFirstUpload','1');
          vFormRef.value.setFormData(newFormData);
          setTimeout("console.log('马上改为0！');localStorage.setItem('isFormDataFirstUpload','0');",2000);
      })
  }
)

onMounted(()=>{
//socId.value=route.query.socId as string;
socId.value = sessionStorage.getItem('socId') as string;
console.log(socId.value);
// axios.get('/api/uploadcommodity/image-path?ACT_ID='+actId.value)
//   .then(response =>{
//     response.data.forEach(path => {
//       var urlPath = path.substr(10);
//       console.log(urlPath);
//       fileList.value.push(
//         {
//           name:path,
//           url:urlPath
//         })
//       });
//       //const statusArray: Array<"ready" | "uploading" | "success" | "fail"> = ["success","ready"];
//       //uploadImageRef.value!.clearFiles(statusArray);
//   })
});


const submitForm = () => {
vFormRef.value.getFormData().then(async formData => {
  formData.actId=actId.value;
  console.log(JSON.stringify(formData));
  axios.put("/api/society/activity/update",JSON.stringify(formData),{ headers: {'Content-Type': 'application/json'} } ).then(response =>{
    console.log('submitForm_response',response.data);
    //uploadImageRef.value!.submit()
    ElMessage({
        showClose: true,
        message: "修改成功",
        type: 'success',
      });
    location.reload();
  })
}).catch(error => {
  // Form Validation failed
  ElMessage.error(error)

})
}

const returnDetailPage = () =>{
router.push({
    path: '/detail',
    query:{
        socId:socId.value
    }
});
}
const handleBeforeRemove: UploadProps['beforeRemove'] = (uploadFile, uploadFiles) => {
if(uploadFiles.length==1){
  ElMessage({
    showClose:true,
    message:"至少保留一张图片！",
    type:'warning'
  })
  return false;
}
return true;
}
const handleRemove: UploadProps['onRemove'] = (uploadFile, uploadFiles) => {
//console.log(uploadFile, uploadFiles)
console.log("准备删除"+uploadFile.name);
axios.post('/api/uploadcommodity/deleteImage?ACT_ID='+actId.value+"&COM_IMAGE="+uploadFile.name);

}

const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {
dialogImageUrl.value = uploadFile.url!
dialogVisible.value = true
}

const handleExceed:UploadProps['onExceed'] = (uploadFile, uploadFiles) => {
ElMessage({
    showClose: true,
    message: "最多上传3张！",
    type: 'warning',
    });
}

// const handleProgress:UploadProps['onProgress']=(evt,uploadFile:UploadFile,uploadFiles)=>{

// }
const handleSuccess:UploadProps['onSuccess'] = (response,uploadFile, uploadFiles) => {
ElMessage({
    showClose: true,
    message: "图片上传成功！",
    type: 'success',
    });
  uploadFile.name = response;
  console.log("图片name path为"+uploadFile.name);
}

</script>
