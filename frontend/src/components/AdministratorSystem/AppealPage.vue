<template>

  <el-card>
    <el-row :gutter="20">
      <el-col :span="3"><el-tag>按申诉时间排序方式：</el-tag></el-col>
      <el-radio-group v-model="tableLayout">
        <el-radio-button label="升序" @click="order=0,getVerificationsList()"/>
        <el-radio-button label="降序" @click="order=1,getVerificationsList()"/>
      </el-radio-group>

      <el-col  v-for="(item,index) in verificationsList" :key="index">
      
        <el-card>
          {{ "申诉ID : "+item.appId }}
          <el-row :gutter="20" >
              <el-col :span="5" v-for="(itemm,idx) in item.images" :key="idx"><el-image style="width: 200px; height: 100px" :src="'data:image/png;base64,' + itemm" fit="fill" /></el-col>
              <el-col :span="19">
                <el-descriptions
            class="margin-top"
            :column="3"
            size="large"
            border
        >
            <template #extra>
            </template>
            <el-descriptions-item>
            <template #label>
                <div class="cell-item">
                <el-icon :style="iconStyle">
                    <location />
                </el-icon>
                申诉时间
                </div>
            </template>
            {{item.appTime}}
            </el-descriptions-item>
            <el-descriptions-item>
            <template #label>
                <div class="cell-item">
                <el-icon :style="iconStyle">
                    <user />
                </el-icon>
                申诉者ID
                </div>
            </template>
            
            <el-tag size="small" @click="router.push({path:'/UserInfoPage',query:{id:item.complainantId,flag:1}})">{{ item.complainantId }}</el-tag>
            </el-descriptions-item>

            <el-descriptions-item>
            <template #label>
                <div class="cell-item">
                <el-icon :style="iconStyle">
                    <tickets />
                </el-icon>
                申诉事项
                </div>
            </template>
            <el-tag size="small" type="danger">{{item.appMatters}}</el-tag>
            </el-descriptions-item>

            <el-descriptions-item>
            <template #label>
                <div class="cell-item">
                <el-icon :style="iconStyle">
                    <iphone />
                </el-icon>
                申诉活动ID
                </div>
            </template>
            <el-tag v-if="item.actId=='无'" >{{'无'}}</el-tag>
            <el-tag v-else size="small" @click="router.push({path: '/indDetail',query:{com_id:item.actId}});">{{item.actId}}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item>
            <template #label>
                <div class="cell-item">
                <el-icon :style="iconStyle">
                    <location />
                </el-icon>
                申诉对象ID
                </div>
            </template>
            <el-tag v-if="item.userId=='无'" >{{'无'}}</el-tag>
            <el-tag v-else size="small" @click="router.push({path:'/UserInfoPage',query:{id:item.userId,flag:1}})">{{item.userId}}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item>
            <template #label>
                <div class="cell-item">
                <el-icon :style="iconStyle">
                    <location />
                </el-icon>
                申诉评论ID
                </div>
            </template>
            {{ item.cmtId }}
              <el-tooltip
                  effect="dark"
                  :content="item.cmtContent"
                  placement="top"
                  v-if="item.cmtId!='无'"
              >
                  <el-icon style="margin-left: 4px" :size="12">
                  <Warning />
                  </el-icon>
              </el-tooltip>
            </el-descriptions-item>


            <el-descriptions-item>
            <template #label>
                <div class="cell-item">
                <el-icon :style="iconStyle">
                    <location />
                </el-icon>
                申诉内容
                </div>
            </template>
            {{item.appContent}}
            </el-descriptions-item>
        </el-descriptions>
              </el-col>
          </el-row>
          

        </el-card>
      </el-col>
    </el-row>

  <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="pagenum" :page-sizes="[2, 4, 6, 10, 16, 20]" :page-size="pagesize"
        layout="total, sizes, prev, pager, next, jumper" :total="appealcount" >
  </el-pagination>


  </el-card>
</template>




<script lang="ts" setup>
import { Options, Vue } from 'vue-class-component';
import axios from 'axios';
import {onMounted, ref,computed} from 'vue'
import { useRouter,useRoute } from 'vue-router';
import { ArrowLeft } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox,ElNotification, buildLocaleContext } from 'element-plus'
// import router from 'router';
import { it } from 'element-plus/es/locale';
const tabPosition = ref('right')
const dialogVisible=ref(false);
const router=useRouter();
const sto_id=sessionStorage.getItem('sto_id') as string;
const query=ref('');
const loading=ref(false);
const tableLayout = ref('升序')

// const appealList=ref([
//   {
//     "appId": 1,
//     "appTime": "yyyy-MM-dd HH:mm:ss",
//     "appMatters": "申诉事项", // 申诉事项
//     "appContent": "申诉内容",
//     "complainantId": 520
//   },
//   {
//     "appId": 2,
//     "appTime": "yyyy-MM-dd HH:mm:ss",
//     "appMatters": "申诉事项2", // 申诉事项
//     "appContent": "申诉内容2",
//     "complainantId": 521
//   },
//   {
//     "appId": 3,
//     "appTime": "yyyy-MM-dd HH:mm:ss",
//     "appMatters": "申诉事项3", // 申诉事项
//     "appContent": "申诉内容3",
//     "complainantId": 522
//   },
// ])

const verificationsList=ref([
  {
    "appId": 1,
    "appTime": "2023-08-25 16:11:24",
    "images": [
      "appeal_image\\1_1.jpg",
      "appeal_image\\1_2.jpg"
    ],
    "userId": "1000001",
    "actId": "261",
    "cmtId": "-1",
    "cmtContent": "helloWorld!",
    "complainantId": "null",
    "appMatters": "1",
    "appContent": "奸商啊啊啊啊"
  }
])

const pagenum=ref(1);
const pagesize=ref(6);
var appealcount=ref(0);


const order=ref(0);
const phone=ref('');
const state=ref(0);

// const getVerificationsList2=async()=>{
//   loading.value=Boolean(true);
//   verificationsList.value.length=0;
//   console.log(sto_id);
//   var Phone=phone.value;
//   if(Phone.length==0)
//     Phone="null";
//   axios.get('api/administrator/appeallist?TIME_ORDER='+order.value+'&BEGIN_NUM='+(pagesize.value*(pagenum.value-1)+1)+'&END_NUM='+(pagesize.value*pagenum.value)) 
//     .then(response=>{
//       console.log(pagesize.value*(pagenum.value-1)+1);
//       console.log(pagesize.value*pagenum.value);
//       console.log(state.value);
//       verificationsList.value=JSON.parse(JSON.stringify(response.data));
//       for(var i=0;i<verificationsList.value.length;++i){
//         if(verificationsList.value[i].CMT_ID=="-1")
//           verificationsList.value[i].CMT_ID='无'
//         if(verificationsList.value[i].COM_ID=='-1')
//           verificationsList.value[i].COM_ID='无'
//         if(verificationsList.value[i].USER_ID=='-1')
//           verificationsList.value[i].USER_ID='无'
//         if(verificationsList.value[i].APP_MATTERS=='1')
//           verificationsList.value[i].APP_MATTERS='商品信息不实'
//         if(verificationsList.value[i].APP_MATTERS=='2')
//           verificationsList.value[i].APP_MATTERS='食品安全问题'
//         if(verificationsList.value[i].APP_MATTERS=='3')
//           verificationsList.value[i].APP_MATTERS='商家资质不全'
//         if(verificationsList.value[i].APP_MATTERS=='4')
//           verificationsList.value[i].APP_MATTERS='恶意评论'
//         if(verificationsList.value[i].APP_MATTERS=='5')
//           verificationsList.value[i].APP_MATTERS='多次退款刷单'
//         if(verificationsList.value[i].APP_MATTERS=='6')
//           verificationsList.value[i].APP_MATTERS='多次恶意评论'
//       }

//       console.log(verificationsList.value);
//       loading.value=Boolean(false);
//     })
// }

const getVerificationsList=async()=>{
  loading.value=Boolean(true);
  verificationsList.value.length=0;
  console.log(sto_id);
  var Phone=phone.value;
  if(Phone.length==0)
    Phone="null";
  axios.get('http://localhost:8084/api/order/appeal/page?TIME_ORDER='+order.value+'&BEGIN_NUM='+(pagesize.value*(pagenum.value-1)+1)+'&END_NUM='+(pagesize.value*pagenum.value)) 
    .then(response=>{
      verificationsList.value=JSON.parse(JSON.stringify(response.data.appealList));
      appealcount = response.data.appealCount;
      for(var i=0;i<verificationsList.value.length;++i){
        if(verificationsList.value[i].cmtId==null)
          verificationsList.value[i].cmtId='无'
        if(verificationsList.value[i].actId==null)
          verificationsList.value[i].actId='无'
        if(verificationsList.value[i].userId==null)
          verificationsList.value[i].userId='无'
        if(verificationsList.value[i].appMatters=='1')
          verificationsList.value[i].appMatters='举报评论'
        if(verificationsList.value[i].appMatters=='2')
          verificationsList.value[i].appMatters='举报活动'
        if(verificationsList.value[i].appMatters=='3')
          verificationsList.value[i].appMatters='举报社团'
        if(verificationsList.value[i].appMatters=='4')
          verificationsList.value[i].appMatters='举报个人用户'
      }
      console.log(verificationsList.value);
      loading.value=Boolean(false);
    })
}


onMounted(()=>{
  //sto_id.value = route.query.sto_id as string;
    getVerificationsList();
});



const handleSizeChange=(val:number)=>{
  pagesize.value=val;
  getVerificationsList();
}

const handleCurrentChange=(val:number)=>{
  pagenum.value=val;
  
  getVerificationsList();
}
const resVerificate=ref({
    "CUS_ID": 1000001,
    "LIST": [
      {
        "IND_ID": 1,
        "COM_ID": 263,
        "COM_NAME": "test1",
        "IND_QUANTITY": 2
      }
    ]
  })
const verificate=()=>{
  axios.post('api/verficate?'+"IND_VERFICATIONCODE="+query.value)
    .then(response=>{
      console.log(response.data);
      if(response.data=='核销失败'){
        ElNotification({
          title: 'Error',
          message: '核销失败',
          type: 'error',
          duration: 2000
        })
      }
      else{
        resVerificate.value=JSON.parse(JSON.stringify(response.data));
        console.log(resVerificate.value);
        ElNotification({
          title: 'success',
          message: '核销成功',
          type: 'success',
          duration: 2000
        })
        dialogVisible.value=true;
      }

      getVerificationsList();
    })
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


const iconStyle = computed(() => {
const marginMap = {
  large: '8px',
  default: '6px',
  small: '4px',
}
return {
  marginRight: marginMap["small"] || marginMap.default,
}
})
</script>



<style>
.demo-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 16px;
  font-weight: 600;
}
.demo-tabs .custom-tabs-label .el-icon {
  vertical-align: middle;
}
.demo-tabs .custom-tabs-label span {
  vertical-align: middle;
  margin-left: 4px;
}
</style>

<!-- 需要后端新增一个接口：返回所有申诉详情 -->