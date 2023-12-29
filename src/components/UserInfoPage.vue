<template>
  <el-container>
    <el-header style="background-color: #aa1514;"> 
      <el-row justify="space-between">
        <el-col :span="4" style="display: flex; align-items: center;"> <!-- 使用flex布局来垂直居中 -->
          <img src="../assets/shibaobao_logo.png" alt="商标" style="height: 100%; max-height: 50px; margin-right: 10px;"> <!-- 添加商标图片 -->
          <h3 style="
            color:aliceblue;
            text-overflow:ellipsis;
            white-space:nowrap;
            overflow:hidden;">
            个人信息
          </h3>
        </el-col>
        <el-col :span="20" style="display: flex; justify-content: flex-end; align-items: center;">
          <el-link icon="el-icon-edit" @click="modifyUserInfo" style="color: aliceblue; font-size: large; margin-left: 15px;">修改信息</el-link>
          <el-link icon="el-icon-plus" @click="rechargeNow" style="color: aliceblue; font-size: large; margin-left: 15px;" v-if="flag==null">充值</el-link>
          <el-link icon="el-icon-minus" @click="withdrawNow" style="color: aliceblue; font-size: large; margin-left: 15px;" v-if="flag==null">提现</el-link>
          <el-link icon="el-icon-arrow-left" @click="goBack" style="color: aliceblue; font-size: large; margin-left: 15px;">返回主页</el-link>
        </el-col>
      </el-row>
    </el-header>
    <el-main style="background-color: #fef0f0; min-height: 100vh;">
      <el-form :model="userInfo" style="max-width: 1100px; position: relative; margin: auto;">
        <el-row :gutter="25" v-if="flag==null">
          <!-- 昵称和用户ID -->
          <div v-if="displayUserType === '顾客'" style="display: flex; align-items: center;">
            <img src="../assets/icons8-user-64.png" alt="顾客图标" style="margin-left: 20px;">
            <div style="font-size: 24px; margin-left: 15px;">
              {{ greeting }}，亲爱的{{ userInfo.stuName }}同学
            </div>
          </div>
          <div v-else-if="displayUserType === '商户'"  style="display: flex; align-items: center;">
            <img src="../assets/icons8-user-64.png" alt="商家图标" style="margin-left: 20px;">
            <div style="font-size: 24px; margin-left: 15px;">
              {{ greeting }}，亲爱的 {{ userInfo.socName }} 社团
            </div>
          </div>
        </el-row>
        <br><br>
        <div style="border-bottom: 1px solid gray; padding: 10px;">
          <div style="display: flex; align-items: center;">
            <img src="../assets/icons8-account-64.png" alt="关于账户">
            <h3 style="margin-left: 10px;">关于账户</h3>
          </div>
          <div style="clear: both;"></div>
        </div>
        <br><br>
        <el-row :gutter="25">
          <el-col :span="3">
            <span class="label">
              <img :src="require('../assets/icons8-id-64.png')" alt="用户ID图标" style="height: 1.1em; margin-right: 1px;">
              用户ID：
            </span>
          </el-col>
          <el-col :span="9">
            <span class="value">{{ userInfo.username }}</span>
          </el-col>
          <el-col :span="3">
            <span class="label">
              <img :src="require('../assets/icons8-telephone-64.png')" alt="电话号码图标" style="height: 1.1em; margin-right: 1px;">
              电话号码：
            </span>
          </el-col>
          <el-col :span="9">
            <span class="value">{{ userInfo.phone }}</span>
          </el-col>
        </el-row>
        <br>
        <el-row :gutter="25">
          <el-col :span="3">
            <span class="label">
              <img :src="require('../assets/icons8-money-50.png')" alt="余额图标" style="height: 1.1em; margin-right: 1px;">
              账户余额：
            </span>
          </el-col>
          <el-col :span="3">
            <span class="value" v-if="showBalance">
              ¥ {{ userInfo.balance }}
            </span>
            <span class="value" v-else>
              ¥ ******
            </span>
          </el-col>
          <el-col :span="3">
            <el-button 
                style="float: left; background-color: #aa1514; color: white;" 
                @click="toggleBalance">
              显示余额
            </el-button>
          </el-col>
        </el-row>
        <br><br>
        <!-- 基本信息或商户信息 -->
        <div style="border-bottom: 1px solid gray; padding: 10px;">
          <div v-if="displayUserType === '学生'" style="display: flex; align-items: center;">
            <img src="../assets/icons8-information-64.png" alt="基本信息">
            <h3 style="margin-left: 10px;">基本信息</h3>
          </div>
          <div v-if="displayUserType === '社团'" style="display: flex; align-items: center;">
            <img src="../assets/icons8-store-64.png" alt="社团信息">
            <h3 style="margin-left: 10px;">社团信息</h3>
          </div>
          <div v-if="displayUserType === '学生'" style="float: right;"><i class="el-icon-user"></i></div>
          <div v-else-if="displayUserType === '社团'" style="float: right;"><i class="el-icon-s-shop"></i></div>
          <div style="clear: both;"></div>
        </div>
        <br><br>
        <!-- 根据 displayUserType 显示相应的字段 -->
        <div v-if="displayUserType === '学生'">
          <!-- 显示顾客相关的表单项 -->
          <el-row :gutter="25">
            <el-col :span="3">
              <span class="label">
                <img :src="require('../assets/icons8-status-48.png')" alt="用户状态图标" style="height: 1.1em; margin-right: 1px;">
                用户状态：
              </span>
            </el-col>
            <el-col :span="9">
              <span class="value">{{ displayUserState }}</span>
            </el-col>
            <el-col :span="3">
              <span class="label">
                <img :src="require('../assets/icons8-time-64.png')" alt="注册时间图标" style="height: 1.1em; margin-right: 1px;">
                注册时间：
              </span>
            </el-col>
            <el-col :span="9">
              <span class="value">{{ userInfo.regTime }}</span>
            </el-col>
          </el-row>
          <br>

          <el-row :gutter="25">
            <el-col :span="3">
              <span class="label">
                <img :src="require('../assets/icons8-status-48.png')" alt="用户状态图标" style="height: 1.1em; margin-right: 1px;">
                学号：
              </span>
            </el-col>
            <el-col :span="9">
              <span class="value">{{ username }}</span>
            </el-col>
            <el-col :span="3">
              <span class="label">
                <img :src="require('../assets/icons8-time-64.png')" alt="注册时间图标" style="height: 1.1em; margin-right: 1px;">
                邮箱：
              </span>
            </el-col>
            <el-col :span="9">
              <span class="value">{{ userInfo.email }}</span>
            </el-col>
          </el-row>
          <br>

          <el-row :gutter="25">
            <el-col :span="3">
              <span class="label">
                <img :src="require('../assets/icons8-status-48.png')" alt="用户状态图标" style="height: 1.1em; margin-right: 1px;">
                校区：
              </span>
            </el-col>
            <el-col :span="9">
              <span class="value">{{ userInfo.campus }}</span>
            </el-col>
            <el-col :span="3">
              <span class="label">
                <img :src="require('../assets/icons8-time-64.png')" alt="注册时间图标" style="height: 1.1em; margin-right: 1px;">
                年级：
              </span>
            </el-col>
            <el-col :span="9">
              <span class="value">{{ userInfo.stuYear }}</span>
            </el-col>
          </el-row>
          <br>

          <el-row :gutter="25">
            <el-col :span="3">
              <span class="label">
                <img :src="require('../assets/icons8-status-48.png')" alt="用户状态图标" style="height: 1.1em; margin-right: 1px;">
                学院：
              </span>
            </el-col>
            <el-col :span="9">
              <span class="value">{{ userInfo.stuSchool }}</span>
            </el-col>
            <el-col :span="3">
              <span class="label">
                <img :src="require('../assets/icons8-time-64.png')" alt="注册时间图标" style="height: 1.1em; margin-right: 1px;">
                专业：
              </span>
            </el-col>
            <el-col :span="9">
              <span class="value">{{ userInfo.stuMajor }}</span>
            </el-col>
          </el-row>
          <br>
          <!-- <el-row :gutter="25">
            <el-col :span="3">
              <span class="label">
                <img :src="require('../assets/icons8-address-50.png')" alt="注册地址图标" style="height: 1.1em; margin-right: 1px;">
                注册地址：
              </span>
            </el-col>
            <el-col :span="21">
              <span class="value">{{ userInfo.user_address }}</span>
            </el-col>
          </el-row>
          <br> -->
          <el-row :gutter="25">
            <el-col :span="3">
              <span class="label">
                <img :src="require('../assets/icons8-like-50.png')" alt="商品喜好图标" style="height: 1.1em; margin-right: 1px;">
                喜好：
              </span>
            </el-col>
            <el-col :span="15">
              <el-select v-model="userInfo.stuKeywords" multiple disabled>
                <el-option
                  v-for="(item, index) in userInfo.stuKeywords"
                  :key="index"
                  :label="item"
                  :value="item"
                ></el-option>
              </el-select>
            </el-col>
          </el-row>
          <br>
          <el-row :gutter="25">
            <el-col :span="3">
              <span class="label">
                <img :src="require('../assets/icons8-note-64.png')" alt="备注图标" style="height: 1.1em; margin-right: 1px;">
                个人简介：
              </span>
            </el-col>
            <el-col :span="21">
              <span class="value">{{ userInfo.stuNotes }}</span>
            </el-col>
          </el-row>
        </div>

        <div v-else-if="displayUserType === '社团'">
          <!-- 显示商户相关的表单项 -->
          <el-row :gutter="25">
            <el-col :span="3">
              <span class="label">
                <img :src="require('../assets/icons8-status-48.png')" alt="商户状态图标" style="height: 1.1em; margin-right: 1px;">
                社团状态：
              </span>
            </el-col>
            <el-col :span="9">
              <span class="value">{{ displayStoreState }}</span>
            </el-col>
            <el-col :span="3">
              <span class="label">
                <img :src="require('../assets/icons8-time-64.png')" alt="商户注册时间图标" style="height: 1.1em; margin-right: 1px;">
                注册时间：
              </span>
            </el-col>
            <el-col :span="9">
              <span class="value">{{ userInfo.regTime }}</span>
            </el-col>
          </el-row>
          <br>
          <!-- <el-row :gutter="25">
            <el-col :span="3">
              <span class="label">
                <img :src="require('../assets/icons8-address-50.png')" alt="商户注册地址图标" style="height: 1.1em; margin-right: 1px;">
                注册地址：
              </span>
            </el-col>
            <el-col :span="21">
              <span class="value">{{ userInfo.user_address }}</span>
            </el-col>
          </el-row> 
          <br>-->
          <el-row :gutter="25">
            <el-col :span="3">
              <span class="label">
                <img :src="require('../assets/icons8-status-48.png')" alt="用户状态图标" style="height: 1.1em; margin-right: 1px;">
                社团号：
              </span>
            </el-col>
            <el-col :span="9">
              <span class="value">{{ username }}</span>
            </el-col>
            <el-col :span="3">
              <span class="label">
                <img :src="require('../assets/icons8-time-64.png')" alt="注册时间图标" style="height: 1.1em; margin-right: 1px;">
                邮箱：
              </span>
            </el-col>
            <el-col :span="9">
              <span class="value">{{ userInfo.email }}</span>
            </el-col>
          </el-row>
          <br>

          <el-row :gutter="25">
            <el-col :span="3">
              <span class="label">
                <img :src="require('../assets/icons8-status-48.png')" alt="用户状态图标" style="height: 1.1em; margin-right: 1px;">
                校区：
              </span>
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
            <el-col :span="15">
              <el-select v-model="userInfo.socType" multiple disabled>
                <el-option
                  v-for="(item, index) in userInfo.socType"
                  :key="index"
                  :label="item"
                  :value="item"
                ></el-option>
              </el-select>
            </el-col>
          </el-row>
          <br>
          <el-row :gutter="25">
            <el-col :span="3">
              <span class="label">
                <img :src="require('../assets/icons8-information-64.png')" alt="商户简介图标" style="height: 1.1em; margin-right: 1px;">
                社团简介：
              </span>
            </el-col>
            <el-col :span="21">
              <span class="value">{{ userInfo.socIntro }}</span>
            </el-col>
          </el-row>
          <br><br>
          <el-row :gutter="25">
            <el-col :span="3">
              <span class="label">
                <img :src="require('../assets/icons8-license-64.png')" alt="营业执照图标" style="height: 1.1em; margin-right: 1px;">
                社团LOGO：
              </span>
            </el-col>
            <el-col :span="21">
              <el-image style="width: 100px; height: 100px" :src="image" />
            </el-col>
          </el-row>
          <br><br>
          <el-row :gutter="25">
            <el-col :span="3">
              <span class="label">
                <img :src="require('../assets/icons8-image-64.png')" alt="商户图片图标" style="height: 1.1em; margin-right: 1px;">
                社团图片：
              </span>
            </el-col>
            <el-col :span="9">
              <div class="grid-container">
                <div v-for="(pic, index) in storePictures" :key="index" class="grid-item">
                  <el-image :src="pic" style="width: 100%; height: 100%; object-fit: cover;"></el-image>
                </div>
              </div>
            </el-col>
          </el-row>
          <br>
        </div> 
      </el-form>
      <!-- 充值弹窗 -->
      <el-dialog title = "充值" v-model = "showRecharge" width = "30%">
        <el-input v-model="recharge_amount" placeholder="请输入充值金额"></el-input>
        <template v-slot:footer>
          <el-button style="background-color: white; color: #aa1514;" @click="showRecharge = false">取消</el-button>
          <el-button style="background-color: #aa1514; color: white;" @click="confirmRecharge">确认支付</el-button>
        </template>
      </el-dialog>

      <!-- 提现弹窗 -->
      <el-dialog title = "提现" v-model = "showWithdraw" width = "30%">
        <el-input v-model="withdraw_amount" placeholder="请输入提现金额"></el-input>
        <template v-slot:footer>
          <el-button style="background-color: white; color: #aa1514;" @click="showWithdraw = false">取消</el-button>
          <el-button style="background-color: #aa1514; color: white;" @click="confirmWithdraw">确认提现</el-button>
        </template>
      </el-dialog>
    </el-main>
  </el-container>
</template>

  

<script lang="ts" setup>
/* eslint-disable */
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';
import { useRouter, useRoute } from 'vue-router';

const route = useRoute();
const userInfo = ref({});
const image=ref('')
const storePictures = ref([]);
const username = ref('');
const user_role = ref('');
const flag=route.query.flag;
//const alipayBase = 'http://localhost:3000';
const alipayBase = 'http://localhost:3000';
// 充值提现相关
function generateOrderId() {
  // 获取当前时间的时间戳，精确到秒
  const timestamp = Math.floor(Date.now() / 1000); 
  // 生成一个1000到9999之间的随机数
  const randomDigits = Math.floor(Math.random() * 9000) + 1000;
  return `${timestamp}${randomDigits}`;
}

const showBalance  = ref(false);  // 在个人信息页面中显示余额
const toggleBalance = () => {
  showBalance.value = !showBalance.value;
}
const showRecharge = ref(false);
const showWithdraw = ref(false)
const recharge_amount = ref('');  // 用户充值的数量
const withdraw_amount = ref('')    //用户提现的数量
const rechargeNow = () => {
  showRecharge.value = true;
}
const withdrawNow = () => {
    showWithdraw.value = true
}
const confirmRecharge = () => {
    console.log('11111');
  // 使用 generateOrderId 函数生成一个唯一的orderId
  const orderId = generateOrderId();

  axios.post(alipayBase + '/api/payment', {
    orderId: orderId,
    price: recharge_amount.value,
    name: '充值',
    cus_id: username.value,
  })
  .then((res) => {
    window.location.href = res.data.data.paymentUrl;
  });
  axios.post('/api/balance/setBalance', {
    cus_id: username.value,
    balance: parseFloat(userInfo.value.user_balance) + parseFloat(recharge_amount.value),
  })
  .then((res) => {    
  });
}
const confirmWithdraw = () => {
  if (parseFloat(withdraw_amount.value) > parseFloat(userInfo.value.user_balance)) {
      alert('余额不足');
      return;  // 提前终止函数的执行
  }

  const outBizNo = generateOrderId(); // 这需要是一个动态生成的唯一值
  const orderTitle = '提现';
  const payeeInfo = { 
    identity: '2088722008198443', // 支付宝用户ID
    identity_type: 'ALIPAY_USER_ID' // 支付宝的会员ID
  };

  axios.post(alipayBase + '/api/withdraw', {
      out_biz_no: outBizNo,
      trans_amount: parseFloat(withdraw_amount.value).toFixed(2),
      biz_scene: 'DIRECT_TRANSFER',
      product_code: 'TRANS_ACCOUNT_NO_PWD',
      order_title: orderTitle,
      payee_info: payeeInfo,
  })
  .then((res) => {
      if (res.data.success) {
        axios.post('/api/balance/setBalance', {
          cus_id: username.value,
          balance: parseFloat(userInfo.value.user_balance) - parseFloat(withdraw_amount.value),
        })
        .then((res) => {    
        });
        alert('提现成功');
        window.location.reload();  // 刷新页面
      } else {
        alert('提现失败: ' + res.data.errorMessage);
      }
  })
  .catch((error) => {
      console.error(error);
      alert('提现请求失败');
  });

  showWithdraw.value = false;
}
  
const greeting = ref('');
const setGreeting = () => {
  const now = new Date();
  const hour = now.getHours();

  if (hour < 12) {
    greeting.value = '早上好';
  } else if (hour >= 12 && hour < 14) {
    greeting.value = '中午好';
  } else if (hour >= 14 && hour < 18) {
    greeting.value = '下午好';
  } else {
    greeting.value = '晚上好';
  }
};
//加入百度地图组件


onMounted(async () => {
  setGreeting();
  console.log('123');
   //user_ID.value = route.query.id as string;
  //  if(sessionStorage.getItem('sto_id')!=null){
  //   username.value = sessionStorage.getItem('sto_id') as string;
  // }
  console.log(sessionStorage)
  if(sessionStorage.getItem('username')!=null){
    username.value = sessionStorage.getItem('username') as string;
  }
  if(sessionStorage.getItem('user_role')!=null){
    user_role.value = sessionStorage.getItem('user_role') as string;
  }
  console.log("这是ID"+username.value)
  if(flag!=null)
    username.value=route.query.id as string;
  console.log("这是ID"+username.value)
  try {
    // console.log(userInfo.value.cus_notes);
    // const response = await axios.get('/api/user/profile/student/get', { params: {username:username.value } });
    // if (response.status === 200) {
    //   userInfo.value = response.data;
    // } else {
    //   console.error(`Error: HTTP status code ${response.status}`);
    // }

    // console.log('##'+userInfo.value.role)
    if (user_role.value === '0') {
      const studentResponse = await axios.get('/api/user/profile/student/get', { params: { username: username.value } });
      if (studentResponse.status === 200) {
          // Object.assign(userInfo.value, studentResponse.data);
          // console.log(userInfo.value.cus_notes);
          userInfo.value = studentResponse.data;
          console.log(userInfo.value);
      } else {
          console.error(`Error: HTTP status code ${studentResponse.status}`);
      }
    } else if (user_role.value === '1') {
      const societyResponse = await axios.get('/api/user/profile/society/info/get', { params: { username: username.value } });
      image.value='http://localhost:5000\\'+societyResponse.data.soc_logo;
      if (societyResponse.status === 200) {
          // Object.assign(userInfo.value, societyResponse.data);
          userInfo.value = societyResponse.data;
          //const socPicResponse = await axios.get('/api/getinformation/storeimg', { params: { username: username.value } });
          //storePictures.value = socPicResponse.data.imageURL.map(pic => 'http://localhost:5000\\' + pic);
          console.log(userInfo.value);
      } else {
          console.error(`Error: HTTP status code ${societyResponse.status}`);
      }
    }
  } catch (error) {
    //console.log(userInfo.value.cus_notes);
    console.error(error);
  }
  //console.log(userInfo.value.cus_notes);
});

  const displayUserType = computed(() => {
  return user_role.value === '0' ? '学生' : '社团';
  });

  const displayUserState = computed(() => {
  return userInfo.value.accountStatus === '0' ? '封禁' : '正常';
  });

  const displayStoreState = computed(() => {
  return userInfo.value.accountStatus === '0' ? '封禁' : '正常';
});

const router = useRouter();

const goBack = () => {
    const userRole = sessionStorage.getItem("user_role");
    if(flag!=null){
      router.push('/administrator')
    }
    else if (userRole === '1') {
      router.push({ path: '/store' });
    } else if (userRole === '0') {
      router.push({ path: '/home' });
    } else {
      console.error("Invalid user type");
    }
  };
const modifyUserInfo = () => {
  
if(flag!=null){
  if (user_role.value === '0') {
    router.push({ name: 'ModifyStudentInfoPage' ,query:{id:username.value,flag:1}});
  } else {
    router.push({ name: 'ModifySocietyInfoPage' ,query:{id:username.value,flag:1}});
  }
}
else{
  if (user_role.value === '0') {
    router.push({ name: 'ModifyStudentInfoPage' });
  } else {
    router.push({ name: 'ModifySocietyInfoPage'});
  }
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

  .grid-container {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 1px;
  }

  .grid-item {
    width: 100px;
    height: 100px;
    margin: 2px;
  }
    /* 改变下拉列表的背景颜色 */
    .el-select-dropdown .el-scrollbar__wrap {
    background-color: #aa1514;
  }
  
  /* 改变下拉列表项的文本颜色 */
  .el-select-dropdown .el-select-dropdown__item {
    color: white; /* 或其他你喜欢的颜色 */
  }
  
  /* 改变已选择选项的背景颜色 */
  .el-tag--info {
    background-color: #aa1514;
  }

  /* 改变已选择选项的文本颜色 */
  .el-tag--info .el-tag__close {
    color: white; /* 或其他你喜欢的颜色 */
  }

  /* 改变已选择选项关闭按钮的颜色 */
  .el-tag--info .el-tag__close.el-icon-close {
    color: white; /* 或其他你喜欢的颜色 */
  }
</style>