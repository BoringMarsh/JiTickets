<template>
  <el-container>
    <el-header style="background-color: #eeebe4;">
      <el-row justify="space-between">
        <el-col :span="4" style="display: flex; align-items: center;">
          <img src="../../public/Logo.png" alt="Logo" style="height: 100%; max-height: 50px; margin-right: 10px;"> <!-- 添加商标图片 -->
          <h3 style="  
              color:#9f4833;
              text-overflow:ellipsis;
              white-space:nowrap;
              overflow:hidden;">
            用户信息修改（社团）
          </h3>
        </el-col>
        <el-col :span="20" style="display: flex; justify-content: flex-end; align-items: center;">
          <el-link icon="el-icon-arrow-left" @click="goBack" style="color: #9f4833; font-size: large; margin-left: 15px;">返回主页</el-link>
        </el-col>
      </el-row>
    </el-header>
    <el-main style="background-color: #fffbf7; min-height: 100vh;">
      <el-form :model="userInfo" ref="form" @submit.prevent="modifyUser" style="max-width: 1100px; position: relative; margin: auto;">
        <div style="border-bottom: 1px solid gray; padding: 10px;">
          <div style="display: flex; align-items: center;">
            <img src="../assets/icons8-account-64.png" alt="关于账户">
            <h3 style="margin-left: 10px;">账号信息</h3>
          </div>
          <div style="clear: both;"></div>
        </div>
        <br><br>
        <el-row :gutter="25">
          <el-col :span="3">
            <span class="label">
              <img :src="require('../assets/icons8-id-64.png')" alt="用户ID图标" style="height: 1.1em; margin-right: 1px;">
              社团号：
            </span>
          </el-col>
          <el-col :span="9">
            <span class="value">{{ basicInfo.username }}</span>
          </el-col>
        </el-row>
        <br>
        <el-row :gutter="25">
          <el-col :span="3">
            <span class="label">
              <img :src="require('../assets/icons8-telephone-64.png')" alt="电话号码图标" style="height: 1.1em; margin-right: 1px;">
              电话号码：
            </span>
          </el-col>
          <el-col :span="9">
            <span class="value">{{ basicInfo.phone }}</span>
          </el-col>
        </el-row>
        <br>
        <!--
        <el-row :gutter="25">
          <el-col :span="3">
            <span class="label">
              <img :src="require('../assets/icons8-password-64.png')" alt="密码图标" style="height: 1.1em; margin-right: 1px;" :disabled="flag!=null">
              新密码：
            </span>
          </el-col>
          <el-col :span="9">
            <el-input v-model="basicInfo.password" type="password" :disabled="flag!=null"></el-input>
          </el-col>
        </el-row>
        <br> -->
        <div style="border-bottom: 1px solid gray; padding: 10px;">
          <div style="display: flex; align-items: center;">
            <img src="../assets/icons8-store-64.png" alt="商户信息">
            <h3 style="margin-left: 10px;">社团信息</h3>
          </div>
        </div>
        <div style="clear: both;"></div>
        <br><br>
        <el-row :gutter="25">
          <el-col :span="3">
            <span class="label">
              <img :src="require('../assets/icons8-edit-user-64.png')" alt="商户名图标" style="height: 1.1em; margin-right: 1px;">
              新社团名：
            </span>
          </el-col>
          <el-col :span="9">
            <el-input v-model="basicInfo.socName" :disabled="flag!=null"></el-input>
          </el-col>
        </el-row>
        <br>
        <el-row :gutter="25">
          <el-col :span="3">
            <span class="label">
              <img :src="require('../assets/icons8-edit-user-64.png')" alt="昵称图标" style="height: 1.1em; margin-right: 1px;">
              新邮箱：
            </span>
          </el-col>
          <el-col :span="9">
            <el-input v-model="basicInfo.email" :disabled="flag!=null"></el-input>
          </el-col>
        </el-row>
        <br>

        <el-row :gutter="25">
          <el-col :span="3">
            <span class="label">
              <img :src="require('../assets/icons8-edit-user-64.png')" alt="昵称图标" style="height: 1.1em; margin-right: 1px;">
              校区：
            </span>
          </el-col>
          <el-col :span="9">
            <el-select v-model="basicInfo.campus" placeholder="请选择校区">
            <!-- 遍历校区选项，options 是一个数组，包含每个校区的信息 -->
            <el-option v-for="option in campusOptions" :key="option.value" :label="option.label" :value="option.value"></el-option>
          </el-select>
          </el-col>
        </el-row>
        <br>
        <el-row :gutter="25">
          <el-col :span="3">
            <span class="label">
              <img :src="require('../assets/icons8-goal-64.png')" alt="主营类型图标" style="height: 1.1em; margin-right: 1px;">
              社团类型：
            </span>
          </el-col>
          <el-col :span="9">
            <el-select v-model="basicInfo.socType" placeholder="请选择社团类型">
            <!-- 遍历社团类型选项，options 是一个数组，包含每个社团类型的信息 -->
            <el-option v-for="option in socTypeOptions" :key="option.value" :label="option.label" :value="option.value"></el-option>
          </el-select>
          </el-col>
        </el-row>
        <br>
        <el-row :gutter="25">
          <el-col :span="3">
            <span class="label">
              <img :src="require('../assets/icons8-goal-64.png')" alt="主营类型图标" style="height: 1.1em; margin-right: 1px;">
              社团关键词：
            </span>
          </el-col>
          <el-col :span="9">
            <el-select v-model="soc_keyword"
                     multiple
                     placeholder="Select"
                     style="width: 100%; position: relative; margin: auto;">
                     <el-option v-for="item in keywords" :key="item" :label="item" :value="item" />
            </el-select>
          </el-col>
        </el-row>
        <br>
        <!-- <el-row :gutter="25">
          <el-col :span="3">
            <span class="label">
              <img :src="require('../assets/icons8-address-50.png')" alt="地址图标" style="height: 1.1em; margin-right: 1px;" >
              新地址：
            </span>
          </el-col>
          <el-col :span="18">
            <el-input v-model="addressInput" :disabled="flag!=null"></el-input>
          </el-col>
          <el-col :span="3">
            <el-button style="background-color: white; color: #aa1514;" @click="searchLocation" :disabled="flag!=null">搜索地址</el-button>
          </el-col>
        </el-row>
        <div id="baiduMap" style="width:100%; height: 300px; margin-top:10px;"></div>
        <br> -->
        <el-row :gutter="25">
          <el-col :span="3">
            <span class="label">
              <img :src="require('../assets/icons8-note-64.png')" alt="备注图标" style="height: 1.1em; margin-right: 1px;">
              社团简介：
            </span>
          </el-col>
          <el-col :span="21">
            <el-input v-model="basicInfo.socIntro"></el-input>
          </el-col>
        </el-row>
        <br>
        <!--
        <el-row :gutter="25">
          <el-col :span="3">
            <span class="label">
              <img :src="require('../assets/icons8-license-64.png')" alt="营业执照图标" style="height: 1.1em; margin-right: 1px;">
              社团Logo：
            </span>
          </el-col>
          <el-col :span="21">
             <el-row :gutter="10">
              <el-col :xs="4" :sm="4" :md="4" :lg="4">
                <div class="image-container">
                  <img :src="logoImage" alt="Logo Image" width="100" height="100" />
                  <el-button type="danger" @click="deleteLogoImage()" size="mini" :disabled="flag!=null">删除</el-button>
                </div>
              </el-col>
            </el-row> 
            <el-upload v-model:file-list='fileList_logo' class="upload-demo"
              action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15" :auto-upload="false" :limit="1" :on-exceed="handleExceed" :disabled="flag!=null">
              <template #trigger>
                <el-button style="background-color: #aa1514; color: white;" :disabled="flag!=null">选择文件</el-button>
              </template>
            </el-upload>
          </el-col>
        </el-row>
        <br> -->
        <!--
        <el-row :gutter="25">
          <el-col :span="3">
            <span class="label">
              <img :src="require('../assets/icons8-image-64.png')" alt="商家图片图标" style="height: 1.1em; margin-right: 1px;">
              社团图片：
            </span>
          </el-col>
          <el-col :span="21">
            <el-row :gutter="10">
              <el-col :xs="4" :sm="4" :md="4" :lg="4" v-for="(pic, index) in storedImages" :key="index">
                <div class="image-container">
                  <img :src="pic.fullUrl" alt="Stored Image" width="100" height="100" />
                  <el-button type="danger" @click="deleteStoredImage(index)" size="mini" :disabled="flag!=null">删除</el-button>
                </div>
              </el-col>
            </el-row>
            <el-upload v-model:file-list='fileList_pic' class="upload-demo"
              action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15" :auto-upload="false" :disabled="flag!=null">
              <template #trigger>
                <el-button style="background-color: #aa1514; color: white;" :disabled="flag!=null">选择文件</el-button>
              </template>
            </el-upload>
          </el-col>
        </el-row>
        <br> -->
        <!-- 管理员信息 -->
        <el-row>
        <el-table :data="adminData" v-show="adminData.length > 0">
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
                      <el-button type="danger" @click="deleteAdmin(row)">删除</el-button>
                    </template>
                  </el-table-column>
              </el-table>
              <div>
                <el-button @click="addAdminRow">添加管理员</el-button>
              </div>
            </el-row>
            <br>
        <el-button style="background-color: #aa1514; color: white;" native-type="submit">提交修改</el-button>
      </el-form>
    </el-main>
  </el-container>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';

const route = useRoute();
const router = useRouter();
const basicInfo = ref({});
// const userInfo = ref({});
// const categories = ref(["体育类","文化类","艺术类"]);

const soc_keyword = ref([]);
const keywords = ref([]);
const storedImages = ref([]);
const logoImage = ref([]);
const fileList_logo = ref([]);  // 用于保存社团标志的文件列表
const fileList_pic = ref([]); // 用于保存社团图片的文件列表
const adminData = ref([
  { socAdminNo: '', socAdminName: '', socAdminPhone: '', socAdminEmail: '' },
]);//管理员列表
const columns = [
  { label: '管理员学号', prop: 'socAdminNo' },
  { label: '管理员姓名', prop: 'socAdminName' },
  { label: '管理员手机号', prop: 'socAdminPhone' },
  { label: '管理员邮箱', prop: 'socAdminEmail' },
];
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
  const get_keywords = () => {
    axios.get('/api/keywords/getkeywords')
      .then(response => {
        console.log('get_keywords_response:',response.data)
        keywords.value=response.data.keywords
        console.log('get_keywords:',keywords.value)
      })
      .catch((error) => {
        console.log('An error occurred:', error);
      });
  }


const flag=route.query.flag;

onMounted(async () => {
  //const sto_ID = route.query.id;
  get_keywords();
  const username = flag==null? sessionStorage.getItem('username'):route.query.id;
  const basic_response = await axios.get('/api/user/profile/society/info/get', { params: { username:username } });
  if (basic_response.status === 200) {
    basicInfo.value = basic_response.data;
    soc_keyword.value = basicInfo.value.socKeywords;
    console.log('basicInfo',basicInfo.value);
    adminData.value = basicInfo.value.socAdmins;
    console.log('adminData',adminData.value);
    } else {
    console.error(`Error: HTTP status code ${basic_response.status}`);
  }
  
});


const addAdminRow = () => {
  adminData.value.push({ socAdminNo: '', socAdminName: '', socAdminPhone: '', socAdminEmail: '' });
};
const deleteAdmin = (row) => {
      const index = adminData.value.indexOf(row);
      if (index !== -1) {
        adminData.value.splice(index, 1);
      }
};
const goBack = () => {
    if(flag==null)
      router.push({ path: '/society' });
    else
      router.push('/administrator');
  };


const modifyUser = async () => {
  try {
    let requestData = {
      userId: basicInfo.value.userId,
      username: basicInfo.value.username,
      email: basicInfo.value.email,
      phone: basicInfo.value.phone,
      campus: basicInfo.value.campus,
      socName: basicInfo.value.socName,
      socIntro: basicInfo.value.socIntro,
      socType: basicInfo.value.socType,
      socKeywords: soc_keyword.value,
      socAdmins: adminData.value, // 包含管理员信息
    };

    console.log('requestData',requestData);
    // 发送 JSON 格式的请求数据到后端
    const societyResponse = await axios.post('/api/user/profile/society/modify', requestData, {
      headers: {
        'Content-Type': 'application/json' // 指定请求头为 JSON 格式
      }
    });
    console.log('postResponese',societyResponse);
    if (societyResponse.status === 200) {
      ElMessage.success('修改成功');
      if(flag==null)
          router.push({ name: 'UserInfoPage'});
        else
        router.push({ name: 'UserInfoPage',query:{id:basicInfo.value.username,flag:1}});
    } else {
      ElMessage.error('修改失败');
    }
  } catch (error) {
    console.error(error);
  }
};

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
.form-item {
  display: flex;
  align-items: center;
}
.label {
  color: grey;
  margin-right: 10px;
}
.value {
  color: black;
  font-size: larger;
}
.image-container {
    text-align: center;
    margin-bottom: 15px;
  }
</style>