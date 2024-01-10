<template>
    <div :style="`background: url(${require('../assets/register.jpg')}) no-repeat center center; background-size: cover; height: 130vh; padding: 5% 0;`">
        <!-- 添加了一个半透明背景层，确保文字清晰可见 -->
        <div style="background-color: rgba(255, 255, 255, 0.8); border-radius: 10px; padding: 20px;">
          <div>
              <h1 class="header">社团注册</h1>
          </div>
          <!-- <el-text class="header" size="large">账号ID:{{ sto_ID }}</el-text> -->
          <br>
          <el-form :model="form" 
                  @submit.prevent="registerStore"
                  label-width="100px" 
                  style="max-width: 460px;position: relative;margin: auto;"
                  label-position="right">
            <el-form-item label="社团号" prop="username" required >
              <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="user_password" required>
          <el-input type="password" v-model="form.user_password"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirm_password" :validate-status="confirmStatus" :error="confirmError">
          <el-input type="password" v-model="form.confirm_password" @blur="validateConfirm"></el-input>
        </el-form-item>
            <el-form-item label="邮箱" prop="user_email" :validate-status="emailStatus" :error="emailError" required >
          <el-input v-model="form.user_email" @blur="validateEmail"></el-input>
        </el-form-item>
        <el-form-item label="电话号码" prop="user_phone" :validate-status="phoneStatus" :error="phoneError" required >
          <el-input v-model="form.user_phone" @blur="validatePhone"></el-input>
        </el-form-item>
        <el-form-item label="校区" prop="user_campus">
          <el-select v-model="form.user_campus" placeholder="请选择校区">
            <!-- 遍历校区选项，options 是一个数组，包含每个校区的信息 -->
            <el-option v-for="option in campusOptions" :key="option.value" :label="option.label" :value="option.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="支付密码" prop="pay_password" required>
          <el-input type="password" v-model="form.pay_password"></el-input>
        </el-form-item>
        <el-form-item label="社团名称" prop="soc_name" required >
          <el-input v-model="form.soc_name"></el-input>
        </el-form-item>   
        <el-form-item label="社团介绍" prop="soc_introduction">
          <el-input v-model="form.soc_introduction"></el-input>
        </el-form-item>
        <el-form-item label="社团logo">
          <el-upload
            v-model:file-list='fileList_logo'
            class="upload-demo"
            action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
            :auto-upload="false"
            :multiple="false"
            :limit="1"
            :before-upload="beforeImageUpload"
            :on-exceed="handleExceed">
            <template #trigger>
              <el-button type="primary">select file</el-button>
              </template>
          </el-upload>
        </el-form-item>
        <el-form-item label="社团类型" prop="soc_type">
          <el-select v-model="form.soc_type" placeholder="请选择社团类型">
            <!-- 遍历社团类型选项，options 是一个数组，包含每个社团类型的信息 -->
            <el-option v-for="option in socTypeOptions" :key="option.value" :label="option.label" :value="option.value"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="社团图片">
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
        <el-form-item>
          <p>请选择社团关键词</p>
          <el-select v-model="soc_keyword"
                     multiple
                     placeholder="Select"
                     style="width: 100%; position: relative; margin: auto;">
                     <el-option v-for="item in keywords" :key="item" :label="item" :value="item" />

          </el-select>
        </el-form-item>

        <!-- 点击按钮触发添加管理员输入框事件 -->
        <!-- <button @click="addAdminInput">添加管理员</button>

        管理员信息输入框列表 
        <div v-for="(admin, index) in adminInputs" :key="index">
          <input v-model="admin.socAdminNo" placeholder="管理员学号" />
          <input v-model="admin.socAdminName" placeholder="管理员姓名" />
          <input v-model="admin.socAdminPhone" placeholder="管理员手机号" />
          <input v-model="admin.socAdminEmail" placeholder="管理员邮箱" />
        </div> -->

        <el-form-item>
            <!-- 点击按钮打开弹窗 -->
            <el-button @click="showDialog">添加管理员</el-button>

              <!-- 管理员信息弹窗 -->
              <el-dialog title="添加管理员" v-model="dialogVisible" width="60%">
                <el-table :data="adminData" v-show="dialogVisible">
                  <!-- 表格标题 -->
                  <el-table-column
                    v-for="column in columns"
                    :key="column.prop"
                    :prop="column.prop"
                    :label="column.label"
                   required>
                  <template v-slot="{ row }">
                    <el-input v-model="row[column.prop]"/>
                  </template>
                  </el-table-column>
                   <!-- 操作列 -->
                  <el-table-column label="操作">
                    <template v-slot="{ row }">
                      <el-button type="danger" @click="deleteRow(row)">删除</el-button>
                    </template>
                  </el-table-column>
                </el-table>

                <!-- 添加管理员数据按钮 -->
                <div>
                  <el-button @click="addRow">添加行</el-button>
                  <el-button type="primary" @click="saveAdmins">保存</el-button>
                </div>
              </el-dialog>

              <!-- 展示注册表单中的管理员信息 -->
              <el-table :data="registeredAdmins" v-show="registeredAdmins.length > 0">
                <!-- 表格标题 -->
                <el-table-column
                  v-for="column in columns"
                  :key="column.prop"
                  :prop="column.prop"
                  :label="column.label"
                ></el-table-column>
              </el-table>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" native-type="submit">确认注册</el-button>
        </el-form-item>
          </el-form>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import axios from 'axios';
import { useRouter, useRoute } from 'vue-router';
import { ElMessage } from 'element-plus';

const router = useRouter();
const route = useRoute();
const form = reactive({
  username: '',
  user_password: '',
  confirm_password: '',
  user_email: '',       // Add user_email
  user_phone: '',       // Add user_phone
  user_campus: '',      // Add user_campus
  pay_password: '',     // Add pay_password
  soc_name: '',         // Add stu_name
  soc_keyword: [],  
  soc_introduction: '',
  soc_type: '',
  soc_logo: null,  //商家执照
  soc_picture: null, // 商家图片
});
const confirmStatus = ref('');
const confirmError = ref('');
const emailStatus = ref('');
const emailError = ref('');
const phoneStatus = ref('');
const phoneError = ref('');
const emailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
const phoneReg = /^1[3-9]\d{9}$/;   //正则表达式，暂时先检测中国号码
const soc_keyword = ref([]);
const keywords = ref([]);
const adminInputs = ref([]);
//const sto_ID=ref(sessionStorage.getItem('id') as string)
const fileList_logo = ref([]);  // 用于保存社团标志的文件列表
const fileList_pic = ref([]); // 用于保存社团图片的文件列表

const dialogVisible = ref(false);
const adminData = ref([
  { socAdminNo: '', socAdminName: '', socAdminPhone: '', socAdminEmail: '' },
]);
const columns = [
  { label: '管理员学号', prop: 'socAdminNo' },
  { label: '管理员姓名', prop: 'socAdminName' },
  { label: '管理员手机号', prop: 'socAdminPhone' },
  { label: '管理员邮箱', prop: 'socAdminEmail' },
];
const registeredAdmins = ref<Array<{ socAdminNo: string; socAdminName: string; socAdminPhone: string; socAdminEmail: string; }>>([]);

const showDialog = () => {
  dialogVisible.value = true;
      adminData.value = [...registeredAdmins.value]; // 在弹窗打开时显示之前的管理员信息
};

const addRow = () => {
  adminData.value.push({ socAdminNo: '', socAdminName: '', socAdminPhone: '', socAdminEmail: '' });
};

const saveAdmins = () => {
  const nonEmptyRows = adminData.value.filter(row => Object.values(row).some(val => val.trim() !== ''));
  registeredAdmins.value = [...nonEmptyRows]; // 保存非空行的数据
  dialogVisible.value = false;
};
const deleteRow = (row) => {
      const index = adminData.value.indexOf(row);
      if (index !== -1) {
        adminData.value.splice(index, 1);
      }
};

  // 校区选项数组，每个选项包含 label（显示文本） 和 value（对应值）
  const campusOptions = ref([
  { label: '嘉定校区', value: '嘉定校区' },
    { label: '四平路校区', value: '四平路校区' },
    { label: '沪西校区', value: '沪西校区' },
    { label: '沪北校区', value: '沪北校区' },
    { label: '其它校区', value: '其它校区' },
    // 添加更多校区选项...
  ]);
  // 社团类型选项数组，每个选项包含 label（显示文本） 和 value（对应值）
  const socTypeOptions = ref([
    { label: '科技类社团', value: '科技类社团' },
    { label: '艺术类社团', value: '艺术类社团' },
    { label: '体育类社团', value: '体育类社团' },
    // 添加更多社团类型选项...
  ]);
  //确认密码验证
  const validateConfirm = () => {
    if (form.user_password !== form.confirm_password) {
      confirmStatus.value = 'error';
      confirmError.value = '两次输入的密码不一致';
    } else {
      confirmStatus.value = 'success';
      confirmError.value = '';
    }
  };
  //邮箱输入验证
  const validateEmail = () => {
    if (!emailReg.test(form.user_email)) {
      emailStatus.value = 'error';
      emailError.value = '邮箱格式不正确';
    } else {
      emailStatus.value = 'success';
      emailError.value = '';
    }
  };
  //手机号输入验证
  const validatePhone = () => {
    if (!phoneReg.test(form.user_phone)) {
      phoneStatus.value = 'error';
      phoneError.value = '电话号码格式不正确';
    } else {
      phoneStatus.value = 'success';
      phoneError.value = '';
    }
  };
  
  const get_keywords = () => {
      axios.get('/api/keywords/getkeywords'
      ,{
        headers: {
              //'Content-Type': 'application/json', 
              "Access-Control-Allow-Origin": "*",
          }
          }).then(response => {
            console.log(response.data)
            keywords.value=response.data.keywords
            console.log(keywords.value)
          })
          .catch((error) => {
              console.log('An error occurred:', error);
          });
    }
    onMounted(()=>{
        get_keywords()
  })

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

const registerStore = async () => {
  console.log(sessionStorage.getItem('id') as string)
  try {
    // 创建一个对象来存储注册信息
    let registerData = {
      username: form.username,
      password: form.user_password,
      email: form.user_email,
      phone: form.user_phone,
      campus: form.user_campus,
      payPassword: form.pay_password,
      socName: form.soc_name,
      socIntro: form.soc_introduction,
      socType: form.soc_type,
      socKeywords: soc_keyword.value,
      socAdminRegs: registeredAdmins.value, //包含管理员信息
      socLogoFile: '',
      socImageFiles: []
    };
    // 确保 fileList 中的第一个元素存在且有 raw 属性
    if (fileList_logo.value.length > 0 && fileList_logo.value[0].raw) {
      registerData.socLogoFile = await toBase64(fileList_logo.value[0]);
    }
    // 遍历 fileList1 以获取其他图片
    for (let fileObj of fileList_pic.value) {
      if (fileObj.raw) {
        registerData.socImageFiles.push(await toBase64(fileObj));
      }
    }
    

    // 发送请求
    const response = await axios.post('/api/user/register/society', registerData, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    console.log(response);
    if (response.status === 201) {
      ElMessage({
          message: '社团注册信息已提交，正在跳转...',
          type: 'success',
        });
        // 这里跳转到登录首页
        router.push('/login');
    } else {
      console.error(`Error: HTTP status code ${response.status}`);
    }
  } catch (error) {
    console.error(error);
  }
}
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

<style scoped>
.form-layout{
  display: flex;
  flex-direction:column;
  width:300px;
  margin-left: 620px;
  justify-content: center;
}
.header{
    display: flex;
    justify-content: center;
    align-items: center;
    
}
</style>
