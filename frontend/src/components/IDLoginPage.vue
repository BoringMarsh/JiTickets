<template>
    <div>
      <!-- <h1 class="header">通过您的ID登录</h1>
      <div id="hiddenContainer1" style="display:none;"></div>  -->
    <div>
    <el-form label-width="100px" style="max-width: 460px;position: relative;margin: auto;">
      <el-form-item label="账号ID" >
        <el-input type="tel" v-model="loginUsername" placeholder="请输入账号" required/>
      </el-form-item>
      <el-form-item label="密码">
        <el-input type="password" show-password v-model="loginPassword" placeholder="请输入密码" required/>
      </el-form-item>
      <div class="submit-button" style="max-width: 120px;margin: auto;">
        <el-button type="default" round="true" :icon="Pointer" @click="login_id">提交</el-button>
      </div>
    </el-form>
    </div>
  </div>
</template>

<script lang="ts" setup>
import axios from 'axios';
import {ref} from 'vue';
//import { useRouter } from 'vue-router';
import { Pointer } from '@element-plus/icons-vue'
import { ElMessage,ElMessageBox } from 'element-plus';
import type {Action} from 'element-plus';
import { useRouter } from 'vue-router';
  const loginUsername=ref('')
  const loginPassword=ref('')
  const router = useRouter();
  //const router=useRouter()
  const user_id = ref();


  const login_id = async ()=> {
          axios.post('/api/user/login',  JSON.stringify({ 
          username:loginUsername.value,
          password:loginPassword.value,
          //login_type:"0" 
        }), {
          headers: {
              'Content-Type': 'application/json'
          },
          // baseURL: 'http://127.0.0.1:4523/m1/3653807-0-default'
          })
          .then(response => {
            // console.log('Request URL:', response.config.baseURL + response.config.url);
            console.log("username",loginUsername.value);
            console.log(response.data);
            if(response.data.message==='该用户已经被封禁') {
                console.log('blocked.');
                sessionStorage.setItem("user_id",response.data.user_ID );
                router.push({
                      path:'/BlockUserPage',
                  });
              } 
            else if(response.data.message==='success'){
                /*------------------------*/
                /*登录成功后编辑此处跳转界面*/
                /*------------------------*/
              
                if (response.data.role=='1'){
                  //sessionStorage.removeItem("soc_id");
                  sessionStorage.removeItem("username");
                  sessionStorage.removeItem("user_role");
                  sessionStorage.setItem("username", loginUsername.value);
                  sessionStorage.setItem("user_role", response.data.role);
                    router.push({
                      path:'/store'
                  });
                }
                else if (response.data.role=='0'){

                  //sessionStorage.removeItem("sto_id");
                  sessionStorage.removeItem("username");
                  sessionStorage.removeItem("user_role");
                  sessionStorage.setItem("username", loginUsername.value);
                  sessionStorage.setItem("user_role", response.data.role);
                  console.log("loginUsername.value:"+loginUsername.value)
                  /*获取用户账号*/
                  user_id.value = response.data.user_ID;
                  router.push({
                      path:'/home'
                  });
                  

                  // 确保 fetchLocationData 完成后再进行页面跳转
                  // fetchLocationData()
                  //   .then(() => {
                  //     router.push({
                  //       path: '/home',
                  //     });
                  //   })
                  //   .catch((error) => {
                  //     console.error('Fetch location data failed:', error);
                  //   });
                }

              }
              else {
                ElMessageBox.alert(response.data.message, '登录失败', {
                  confirmButtonText: 'OK',
                  callback: (action: Action) => {
                    ElMessage({
                      type: 'info',
                      message: `action: ${action}`,
                    })
                  },
                })
              }
          })
          .catch((error) => {
              console.log('An error occurred:', error);
          });
      };
</script>

<style scoped>
.total-layout{
    display: flex;
    flex-direction:column;
    
}

.form-layout{
  display: flex;
  justify-content: center;

}
.header{
    display: flex;
    justify-content: center;
    align-items: center;
    
}
.submit-button{
    display: flex;

    /*width: 90px;
    /*justify-content: center;*/
}

#hiddenContainer1 {
    display: none;
}
</style>