<template>
  <el-dialog v-model="dialogVisible" title="理由发布" width="30%" draggable>
    <el-input
    v-model="text"
    class="w-50 m-2"
    type="textarea"
    placeholder="请输入理由"
    style="width: 450px"
    maxlength="400"
    show-word-limit
    autosize
  />
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="noticeNotUpload">取消</el-button>
        <el-button type="primary" @click="changeState(prohibit.id, prohibit.result); noticeUpload();">
          确认
        </el-button>
      </span>
    </template>
  </el-dialog>
    <el-row>
  
  </el-row>
    <el-row>
    <el-col :span="24">
      <el-card>
      <el-row :gutter="20">
        <el-col :span="8">
      <el-input placeholder="请输入内容" clearable v-model="query" />
        </el-col>
        <el-col :span="7"><el-button type="primary" icon="search" @click="getGoods"/></el-col>
      </el-row>
      <el-row>
    <el-table
      v-loading="loading"
     :data="userList"
      style="width: 100%" max-height="1000" 
      border 
      stripe
      @sort-change="changeSort"
      :default-sort="{ prop: 'userId', order: 'ascending' }"
      >
      <el-table-column label="用户图片" width="200" v-if="type=='1'">
        <!-- <template #default="scope">
          <div style="display: flex; align-items: center">
             <el-image
              style="width: 180px; height: 90px"
              :src="getUrl(scope.$index)"
              :zoom-rate="1.2"
              :initial-index="0"
              fit="fill"
            /> 
          </div>
        </template> -->
      </el-table-column>
      <el-table-column prop="username" label="用户名字"> </el-table-column>
      <el-table-column prop="userId" sortable="custom" label="用户ID">
        <template #default="scope">
        <el-tag @click="router.push({path:'/UserInfoPage',query:{id:userList[scope.$index].userId,flag:1}})">{{ userList[scope.$index].userId }}</el-tag>
        </template>
      </el-table-column>
    <el-table-column prop="regTime" label="用户注册日期" >
    </el-table-column>
      <el-table-column prop="accountStatus"  label="用户状态">
      </el-table-column>

      <el-table-column label="操作" width="250px">
      <template #default="scope">
        <el-button
          type="warning"
          icon="List"
          size="medium"
          color="#ff9966"
          style="color: white;"
          @click="router.push({path:'/UserInfoPage',query:{id:userList[scope.$index].userId,flag:1}})"
          v-if= "parseInt(type)"
          ></el-button>
        <el-button
          type="success"
          icon="Goods"
          size="medium"
          color="#ffcb66"
          style="color: white;"
          @click="viewSocietyActivity(scope.$index)"
          v-if= "parseInt(type)"
          ></el-button>
        <el-button
          type="danger"
          size="medium"
          @click="storeChangeStateInfo(scope.row.userId, true)"
          v-if= "scope.row.accountStatus == '正常'"
        >封禁</el-button>
        <el-button
          type="primary"
          size="medium"
          @click="storeChangeStateInfo(scope.row.userId, false)"
          v-if= "scope.row.accountStatus == '封禁'"
        >解封</el-button>
        <el-button
          type="success"
          size="medium"
          @click="passRegRequest(scope.row.userId)"
          v-if= "scope.row.accountStatus == '待审核'"
        >通过</el-button>
        <el-button
          type="info"
          size="medium"
          @click="refuseRegRequest(scope.row.userId)"
          v-if= "scope.row.accountStatus == '待审核'"
        >拒绝</el-button>
        </template>
    </el-table-column>
    </el-table>
    </el-row>
    <!-- 分页区域 -->
    <el-row>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page="pagenum" :page-sizes="[1, 3, 5, 10, 15, 20]" :page-size="pagesize"
            layout="total, sizes, prev, pager, next, jumper" :total="usercount">
    </el-pagination>
  </el-row>
  
  </el-card>
  </el-col>
  </el-row>
    </template>
    
    <script lang="ts" setup>
    import { Options, Vue } from 'vue-class-component';
    import axios from 'axios';
    import {onMounted, ref,onBeforeUpdate} from 'vue'
    import { useRouter,useRoute } from 'vue-router';
    import { ArrowLeft, User } from '@element-plus/icons-vue'
    import { ElMessage, ElMessageBox,ElNotification } from 'element-plus'
    const router=useRouter();
    const pagenum=ref(1);
    const pagesize=ref(5);
    const query=ref('');
    const haveQuery=ref('');
    const tableLayout = ref('默认')
    const uploadDate=ref('');
    const expirationDate=ref('')
    const loading=ref(false);
    const ableEdit = ref([true]);
    const isEmpty = ref([true]) //是否售空
    const sto_id=ref('');
    const route=useRoute();
    const type=ref('');
    const dialogVisible = ref(false)
    const text=ref('');
    var userList=ref([
        {
            "userId": 1000001,
            "username": "张翔",
            "regTime": "2023-08-01",
            "accountStatus": "正常",
            //"": "store_image\\1000040_picture.jpg"
        }
    ]);
    var prohibit = ref({
      id: 0,
      result: false
    });
    var usercount = ref(0);
  
  const status=ref(1);  //1 在售 0 售罄/下架 -1 过期
  const id_order=ref(1);
  const name_order=ref(-1);
  const category=ref([]);
  const getUserList=async()=>{
    loading.value=Boolean(true);
    userList.value.length=0;
    var Name=query.value;
    if(Name.length==0)
        Name="NULL";

    console.log("开始查询：" + type.value);
    axios.get('http://localhost:8084/api/order/userlist/' + (type.value == '1' ? 'society' : 'student') + '?&BEGIN_NUMBER='+(pagesize.value*(pagenum.value-1)+1)+'&END_NUMBER='+(pagesize.value*pagenum.value)) 
      .then(response=>{
        console.log(response);
        usercount.value = response.data.count;
        for(var i=0;i<response.data.list.length;i++){
          switch (response.data.list[i].accountStatus) {
            case 0:
              response.data.list[i].accountStatus = '封禁';
              break;
            case 1:
              response.data.list[i].accountStatus = '正常';
              break;
            case 2:
              response.data.list[i].accountStatus = '待审核';
              break;
          }
      }

        userList.value=JSON.parse(JSON.stringify(response.data.list));
        // console.log(userList.value[0].USER_REGTIME)
        isEmpty.value = []
        
        loading.value=Boolean(false);
      })
  }
  
  
  const getGoods=async(keyword:string)=>{
    getUserList().then(()=>{
      userList.value = userList.value.filter(user => user.username.includes(keyword));

      ElNotification.success({
        title: 'Success',
        message: '搜索成功',
        duration: durationTime
      });
    });
  }
  
  const changeSort=(value)=>{
    console.log(value.order);
    if(value.order=='ascending')
      id_order.value=1;
    else
      id_order.value=0;
    
    var str='';
    for(var i=0;i<query.value.length;++i)
      str+=query.value[i];
    console.log(str);
    query.value=haveQuery.value;
    getUserList();
    query.value=str;
  }
  
  function change(strr :string[]){
    var arr=[''];
      for(var i=0;i<strr.length;i++)
        arr.push(strr[i]);
      for(i=1;i<arr.length;i++){
        var str=arr[i].split('\\');
        arr[i]='/';
        for(var j=2;j<str.length;j++){
          arr[i]+=str[j];
          if(j!=str.length-1)
            arr[i]+='/'
        }
        // console.log(arr[i]);
      }
  
      // srcList.value=arr;
      // console.log(arr.length)
      return arr[1];
  }
  
  
  const srcList =ref( [
    "/commodity_image\\1\\com_image_0.jpg",
    ]);
  const url = 'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg';
  const getUrl=(index:number)=>{
    // console.log(change([userList.value[index].STO_IMAGE]));
    return userList.value[index].STO_IMAGE;
  }

    onBeforeUpdate(()=>{
      //sto_id.value = route.query.sto_id as string;
    //   sto_id.value = sessionStorage.getItem('sto_id') as string;
        type.value=route.query.type as string;
    //   console.log(sto_id.value);
        // console.log(type);
        getUserList();
    });

    onMounted(()=>{
      //sto_id.value = route.query.sto_id as string;
    //   sto_id.value = sessionStorage.getItem('sto_id') as string;
        type.value=route.query.type as string;
    //   console.log(sto_id.value);
        // console.log(type);
        getUserList();
    });

  var durationTime=2000;
  
  
    const handleSizeChange=(val:number)=>{
      pagesize.value=val;
      getUserList();
    }
  
    const handleCurrentChange=(val:number)=>{
      pagenum.value=val;
      
      getUserList();
    }
    
  const viewSocietyActivity=(index:number)=>{
    // sessionStorage.setItem('sto_id',userList.value[index].USER_ID.toString());
    router.push({
        path:'/society-activity',
        query:{
            soc_id:userList.value[index].userId,
            admi:1
        }
    })
  }

const storeChangeStateInfo=(userId:number, ifProhibited:boolean)=>{
  dialogVisible.value=true;
  prohibit.value.id=userId;
  prohibit.value.result=ifProhibited;
}

const changeState=(userId:number, ifProhibited:boolean)=>{
  axios.put('http://localhost:8084/api/order/prohibit?USER_ID='+userId+'&IF_PROHIBITED='+ifProhibited)
    .then(response=>{
      // getState(index)
    }, error=>{
      console.log(error);
      return;
    });
  
  getUserList();
}

const passRegRequest=(userId:number)=>{
  axios.put('http://localhost:8084/api/order/reg-request?USER_ID='+userId)
  .then(response=>{
    console.log(response.data);
    getUserList();
    noticeNotUpload();
  });
}

const refuseRegRequest=(userId:number)=>{
  axios.delete('http://localhost:8084/api/order/reg-request?USER_ID='+userId)
  .then(response=>{
    console.log(response.data);
    getUserList();
    noticeNotUpload();
  })
}

const noticeNotUpload=()=>{
    dialogVisible.value = false;
    ElNotification({
        title: 'Info',
        message: '操作取消',
        type: 'info',
        duration: 2000
      })
      text.value='';
    
  }
  const noticeUpload=()=>{
    dialogVisible.value = false;
      ElNotification.success({
            title: 'Success',
            message: '成功',
            duration: 2000
          })
          dialogVisible.value=false;
          text.value='';
    }
    </script>
    
    <!-- Add "scoped" attribute to limit CSS to this component only -->
    <style scoped>
    .el-row {
    margin-bottom: 20px;
  }
  .el-row:last-child {
    margin-bottom: 0;
  }
  .el-col {
    border-radius: 4px;
  }
  
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
    h3 {
      margin: 40px 0 0;
    }
    ul {
      list-style-type: none;
      padding: 0;
    }
    li {
      display: inline-block;
      margin: 0 10px;
    }
    a {
      color: #42b983;
    }
    .demo-image__error .image-slot {
    font-size: 30px;
  }
  .demo-image__error .image-slot .el-icon {
    font-size: 30px;
  }
  .demo-image__error .el-image {
    width: 100%;
    height: 200px;
  }
  
  .categoryStyle{
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
    </style>
    