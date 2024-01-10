<template>
  <el-container>
    <el-header style="background-color: #aa1514;">
      <el-row justify="space-between">
        <el-col :span="4" style="display: flex; align-items: center;">
          <img src="../assets/shibaobao_logo.png" alt="商标" style="height: 100%; max-height: 50px; margin-right: 10px;"> <!-- 添加商标图片 -->
          <h3 style="  
              color:aliceblue;
              text-overflow:ellipsis;
              white-space:nowrap;
              overflow:hidden;">
            用户信息修改（社团）
          </h3>
        </el-col>
        <el-col :span="20" style="display: flex; justify-content: flex-end; align-items: center;">
          <el-link icon="el-icon-arrow-left" @click="goBack" style="color: aliceblue; font-size: large; margin-left: 15px;">返回主页</el-link>
        </el-col>
      </el-row>
    </el-header>
    <el-main style="background-color: #fef0f0; min-height: 100vh;">
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
        <br>
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
            <el-select v-model="basicInfo.soc_type" placeholder="请选择社团类型">
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
            <el-input v-model="basicInfo.soc_introduction"></el-input>
          </el-col>
        </el-row>
        <br>
        <el-row :gutter="25">
          <el-col :span="3">
            <span class="label">
              <img :src="require('../assets/icons8-license-64.png')" alt="营业执照图标" style="height: 1.1em; margin-right: 1px;">
              社团Logo：
            </span>
          </el-col>
          <el-col :span="21">
            <!-- <el-row :gutter="10">
              <el-col :xs="4" :sm="4" :md="4" :lg="4">
                <div class="image-container">
                  <img :src="logoImage" alt="Logo Image" width="100" height="100" />
                  <el-button type="danger" @click="deleteLogoImage()" size="mini" :disabled="flag!=null">删除</el-button>
                </div>
              </el-col>
            </el-row> -->
            <el-upload v-model:file-list='fileList_logo' class="upload-demo"
              action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15" :auto-upload="false" :limit="1" :on-exceed="handleExceed" :disabled="flag!=null">
              <template #trigger>
                <el-button style="background-color: #aa1514; color: white;" :disabled="flag!=null">选择文件</el-button>
              </template>
            </el-upload>
          </el-col>
        </el-row>
        <br>
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
        <br>
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

  /*           */
//加入百度地图组件
/* eslint-disable */
// let map: BMapGL.Map;
// let geoc: BMapGL.Geocoder;
// const addressInput = ref('');     //地址搜索框绑定变量
const flag=route.query.flag;

onMounted(async () => {
  //const sto_ID = route.query.id;
  get_keywords();
  const username = flag==null? sessionStorage.getItem('username'):route.query.id;
  const basic_response = await axios.get('/api/user/profile/society/info/get', { params: { username:username } });
  if (basic_response.status === 200) {
    basicInfo.value = basic_response.data;
    storedImages.value = basicInfo.value.base64SocImages.map(pic => ({
      fullUrl: 'http://localhost:5000\\' + pic,
      relativePath: pic
    }));
    adminData.value = basicInfo.value.socAdmin;
    console.log('adminData',adminData.value);
    console.log('basicInfo',basicInfo.value);
    } else {
    console.error(`Error: HTTP status code ${basic_response.status}`);
  }

    // map = new BMapGL.Map("baiduMap"); 
    // geoc = new BMapGL.Geocoder();
    // //const map = new BMapGL.Map("baiduMap"); 
    // const point = new BMapGL.Point(116.404, 39.915);  // 创建点坐标
    // map.centerAndZoom(point, 15);                     // 初始化地图，设置中心点坐标和地图级别
    // map.enableScrollWheelZoom(true);                  // 开启鼠标滚轮缩放
    // addressInput.value = basicInfo.value.user_address;
    // // 创建地址解析器实例
    // const myGeo = new BMapGL.Geocoder();
    // // 将地址解析结果显示在地图上，并调整地图视野
    // myGeo.getPoint(basicInfo.value.user_address, function(point) {
    //   if (point) {
    //     map.centerAndZoom(point, 16);
    //     map.addOverlay(new BMapGL.Marker(point));
    //   } else {
    //     alert("您选择地址没有解析到结果!");
    //   }
    // });

    // // 添加地图点击事件监听
    // map.addEventListener("click", function (e: any) {
    //   const pt = e.latlng;
    //   //alert('点击的经纬度：' + e.latlng.lng + ', ' + e.latlng.lat);
    //   geoc.getLocation(pt, function (rs: any) {
    //     console.log('Complete geocoding result:', rs);
    //     const addComp = rs.addressComponents;
    //     addressInput.value = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;
    //     //alert(form.user_address);
    //     //form.sto_lng = pt.lng.toString();
    //     //form.sto_lat = pt.lat.toString();
    //     //alert('点击的经纬度：' + pt.lng.toString() + ', ' + pt.lat.toString());
    //     basicInfo.value.user_address = addressInput.value;
    //     var marker = new BMapGL.Marker(pt);  // 创建标注
    //     map.clearOverlays(); // 清除所有覆盖物
    //     map.addOverlay(marker); // 将标注添加到地图中
    //     //marker.enableDragging(); // 可拖拽
    //   });
    // });
  
  // const response = await axios.get('/api/getinformation/store', { params: { sto_ID: sto_ID } });
  // if (response.status === 200) {
  //   userInfo.value = response.data;
  // } else {
  //   console.error(`Error: HTTP status code ${response.status}`);
  // }
    // 获取已有的商家图片
  // const imgResponse = await axios.get('/api/getinformation/storeimg', { params: { sto_ID: sto_ID } });
  // if (imgResponse.status === 200) {
  //   storedImages.value = imgResponse.data.imageURL.map(pic => ({
  //     fullUrl: 'http://localhost:5000\\' + pic,
  //     relativePath: pic
  //   }));
  //   console.log(storedImages.value);
  // } else {
  //   console.error(`Error: HTTP status code ${imgResponse.status}`);
  // }


//   const response_categoty = await axios.get('/api/category/getcategories');
//     if (response_categoty.status === 200) {
//       categories.value = response_categoty.data.categorylist;
//     } else {
//       console.error(`Error: HTTP status code ${response_categoty.status}`);
//     }
});

//删除社团图片
const deleteStoredImage = (imageIndex) => {
  try {
    // 删除图片在storedImages中的引用
    storedImages.value.splice(imageIndex, 1);
    ElMessage.success('图片删除成功');
  } catch (error) {
    console.error(error);
    ElMessage.error('删除失败');
  }
};
//删除社团logo图片
// const deleteLogoImage = () => {
//   try {
//     // 删除图片在storedImages中的引用
//     logoImage.value.splice(0, 1);
//     ElMessage.success('图片删除成功');
//   } catch (error) {
//     console.error(error);
//     ElMessage.error('删除失败');
//   }
// }
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

// const searchLocation = () => { // 搜索地点
//     const local = new BMapGL.LocalSearch(map, {
//       renderOptions: { map: map, autoViewport: true, selectFirstResult: false },
//       pageCapacity: 8,
//     });
//     // 搜索回调
//     local.setSearchCompleteCallback(results => {
//       if (local.getStatus() === BMAP_STATUS_SUCCESS) {
//         const pt = results.getPoi(0).point;
//         //form.sto_locationPoint = `${pt.lng},${pt.lat}`;
//         // 更新输入框地址
//         geoc.getLocation(pt, rs => {
//           const addComp = rs.addressComponents;
//           addressInput.value = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;
//           basicInfo.value.user_address = addressInput.value;
//           //form.sto_lng = pt.lng.toString();
//           //form.sto_lat = pt.lat.toString();
//         });

//       } else {
//         ElMessage.error('未找到相关地址！');
//       }
//     });

//     local.search(addressInput.value);
//   };
const handleExceed = (files, fileList_logo) => {
  ElMessage.warning('只能上传一张图片！');
  };
const modifyUser = async () => {
  try {
    if (fileList_pic.value.length + storedImages.value.length < 1 || fileList_pic.value.length + storedImages.value.length > 9) {
      alert('商家图片数量必须在1-9张之间！');
      return;
    }
  let formData = {
      username: basicInfo.value.username,
      password: basicInfo.value.password,
      email: basicInfo.value.email,
      phone: basicInfo.value.phone,
      campus: basicInfo.value.campus,
      socName: basicInfo.value.socName,
      socIntro: basicInfo.value.soc_introduction,
      socType: basicInfo.value.soc_type,
      socKeywords: soc_keyword.value,
      socAdminRegs: adminData.value, //包含管理员信息
      socLogoFile: '',
      socImageFiles: []
    };

    // 确保 fileList 中的第一个元素存在且有 raw 属性
    if (fileList_logo.value.length > 0 && fileList_logo.value[0].raw) {
      formData.socLogoFile = await toBase64(fileList_logo.value[0]);
    }
    // 遍历 fileList1 以获取其他图片
    for (let fileObj of fileList_pic.value) {
      if (fileObj.raw) {
        formData.socImageFiles.push(await toBase64(fileObj));
      }
    }


    // for (let category of basicInfo.value.categories) {
    //   formData.push('categories', category);
    // }
    // basicInfo.value.categories.forEach((category: string) => {
    //   formData.append('categories', category);
    // });
    console.log('formData',formData);
    const societyResponse = await axios.post('/api/user/profile/society/modify', formData);
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