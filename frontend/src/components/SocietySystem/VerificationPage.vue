<template>
  <el-dialog v-model="dialogVisible" title="信息确认" draggable>
    <el-tag>{{'报名者学号: '+ resVerificate.indStuNo }}</el-tag>
    <el-tag>{{'报名者姓名: '+ resVerificate.indName }}</el-tag>
    <el-table :data="resVerificate.LIST" border style="width: 100%" height="150">

        <el-table-column prop="indId" label="订单ID">

        </el-table-column>
        <el-table-column prop="actId" label="活动ID" >
          <template #default="scope">
            <el-tag @click="router.push('/view?com_id='+resVerificate.LIST[scope.$index].actId)">{{ resVerificate.LIST[scope.$index].actId }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="indPrice" label="订单金额" />
        <!-- <el-table-column prop="COM_NAME" label="商品名称" /> -->
    </el-table>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="dialogVisible=false">
          确定
        </el-button>
      </span>
    </template>
  </el-dialog>
<el-card>
<el-row :gutter="20">
<el-col :span="8">
<el-input placeholder="请输入核销码" clearable v-model="indVerifyCode" :disabled="Boolean(state)" />
  </el-col>
  <el-button type="danger" icon="search" @click="verificate" :disabled="Boolean(state)" />
  <el-col :span="2"/>
  <el-col :span="4">
    <el-radio-group v-model="tabPosition" fill="#F56C6C" style="margin-bottom: 30px">
      <el-radio-button label="top" @click="order=1,getVerificationsList()">升序</el-radio-button>
      <el-radio-button label="right" @click="order=0,getVerificationsList()">降序</el-radio-button>
    </el-radio-group>
    </el-col>
  <el-col :span="8">
    <el-input placeholder="请输入报名者学号" clearable v-model="indStuNo" />
    <!-- <el-button type="primary" icon="search" @click="getVerificationsList"/> -->
  </el-col>
  <el-button type="danger" icon="search" @click="getVerificationsList"/>
  
  <el-col :span="10"></el-col>
  <el-col :span="5">

        
  </el-col>
</el-row>

<el-tabs type="border-card" class="demo-tabs"  v-loading="loading">
  <el-tab-pane>
    <template #label>
      <span class="custom-tabs-label"  @click="state=0,getVerificationsList()">
        <el-icon><Warning /></el-icon>
        <span>待核销</span>
      </span>
    </template>
  </el-tab-pane>

  <el-tab-pane>
    <template #label>
      <span class="custom-tabs-label"  @click="state=1,getVerificationsList()">
        <el-icon><CircleCheck /></el-icon>
        <span>已核销</span>
      </span>
    </template>
  </el-tab-pane>

  <el-tab-pane>
    <template #label>
      <span class="custom-tabs-label"  @click="state=2,getVerificationsList()">
        <el-icon><CircleClose /></el-icon>
        <span>已退款</span>
      </span>
    </template>
  </el-tab-pane>


  <el-row :gutter="20">
    <el-col :span="12" v-for="(item,index) in verificationsList" :key="index">
    <el-card>
        <el-descriptions
        class="margin-top"
        title="订单信息"
        :column="3"
        size="small"
        border
    >
        <template #extra>
        </template>
        <el-descriptions-item>
        <template #label>
            <div class="cell-item">
            <el-icon :style="iconStyle">
                <user />
            </el-icon>
            报名者姓名
            </div>
        </template>
        {{ item.indName }}
        </el-descriptions-item>
        <el-descriptions-item>
        <template #label>
            <div class="cell-item">
            <el-icon :style="iconStyle">
                <iindStuNo />
            </el-icon>
            报名者学号
            </div>
        </template>
        {{item.indStuNo}}
        </el-descriptions-item>
        <el-descriptions-item>
        <template #label>
            <div class="cell-item">
            <el-icon :style="iconStyle">
                <location />
            </el-icon>
            报名者ID
            </div>
        </template>
        {{item.stuId}}
        </el-descriptions-item>
        <el-descriptions-item>
        <template #label>
            <div class="cell-item">
            <el-icon :style="iconStyle">
                <tickets />
            </el-icon>
            订单创建时间
            </div>
        </template>
        <el-tag size="small" type="warning">{{item.indCreateTime}}</el-tag>
        </el-descriptions-item>
        
    </el-descriptions>

        <el-table :data="item.BOX" border style="width: 100%" height="150">
          <!-- <el-table-column label="商品图片" width="105">
            <template #default="scope">
              <div style="display: flex; align-items: center">
                <el-image
                  style="width: 180px; height: 90px"
                  :src="change([item.BOX[scope.$index].COM_IMAGE])"
                  :zoom-rate="1.2"
                  :initial-index="0"
                  fit="fill"
                />
              </div>
            </template>
            <template #header="scope">
                    {{ '商品图片' }}
                    <el-tooltip
                        effect="dark"
                        :content="item.BOX[scope.$index].indNotes"
                        placement="top"
                    >
                        <el-icon style="margin-left: 4px" :size="12">
                        <Warning />
                        </el-icon>
                    </el-tooltip>
            </template>
          </el-table-column> -->
            <el-table-column prop="indId" label="订单ID">

            </el-table-column>
            <el-table-column prop="actId" label="活动ID">
              <template #default="scope">
                <el-tag @click="router.push('/view?com_id='+item.BOX[scope.$index].actId)">{{ item.BOX[scope.$index].actId }}</el-tag>
              </template>
            </el-table-column>
            <!-- <el-table-column prop="IND_QUANTITY" label="订单数量" /> -->
            <el-table-column prop="indPrice" label="订单金额" />
            <!-- <el-table-column prop="COM_NAME" label="商品名称" /> -->
            <el-table-column prop="indNotes" label="用户备注" >
                <template #default="scope">
                    {{ item.BOX[scope.$index].indNotes }}
                </template>
            </el-table-column>
            <el-table-column prop="indRating" label="评分" />
        </el-table>
    </el-card>
    </el-col>
  </el-row>
</el-tabs>

<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
      :current-page="pagenum" :page-sizes="[2, 4, 6, 10, 16, 20]" :page-size="pagesize"
      layout="sizes, prev, pager, next, jumper" :total="800" >
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
console.log("sessionStorage",sessionStorage)
const socId=sessionStorage.getItem('socId') as string;
const indVerifyCode=ref('');
const loading=ref(false);
const verificationsList=ref([
{
"stuId": 1000001,
"indCreateTime": "2023-08-11 15:14:56",
"indStuNo": "17879683110",
"indName": "张翔",
"BOX": [
  {
    "indId": 1,
    "actId": 263,
    "IND_QUANTITY": 2,
    "indPrice": 70,
    "COM_NAME": "test1",
    "COM_IMAGE": ".\\wwwroot\\commodity_image\\263\\com_image_0.jpg",
    "indNotes": "",
    "indRating": "-1"
  }
]
},
{
"stuId": 1000001,
"indCreateTime": "2023-08-11 15:14:56",
"indStuNo": "17879683110",
"indName": "张翔",
"BOX": [
  {
    "indId": 1,
    "actId": 263,
    "IND_QUANTITY": 2,
    "indPrice": 70,
    "COM_NAME": "test1",
    "COM_IMAGE": ".\\wwwroot\\commodity_image\\263\\com_image_0.jpg",
    "indNotes": "",
    "indRating": "-1"
  }
]
},
{
"stuId": 1000001,
"indCreateTime": "2023-08-11 15:14:56",
"indStuNo": "17879683110",
"indName": "张翔",
"BOX": [
  {
    "indId": 1,
    "actId": 263,
    "IND_QUANTITY": 2,
    "indPrice": 70,
    "COM_NAME": "test1",
    "COM_IMAGE": ".\\wwwroot\\commodity_image\\263\\com_image_0.jpg",
    "indNotes": "",
    "indRating": "-1"
  }
]
},
{
"stuId": 1000001,
"indCreateTime": "2023-08-11 15:14:56",
"indStuNo": "17879683110",
"indName": "张翔",
"BOX": [
  {
    "indId": 1,
    "actId": 263,
    "IND_QUANTITY": 2,
    "indPrice": 70,
    "COM_NAME": "test1",
    "COM_IMAGE": ".\\wwwroot\\commodity_image\\263\\com_image_0.jpg",
    "indNotes": "",
    "indRating": "-1"
  }
]
},
{
"stuId": 1000001,
"indCreateTime": "2023-08-11 15:14:56",
"indStuNo": "17879683110",
"indName": "张翔",
"BOX": [
  {
    "indId": 1,
    "actId": 263,
    "IND_QUANTITY": 2,
    "indPrice": 70,
    "COM_NAME": "test1",
    "COM_IMAGE": ".\\wwwroot\\commodity_image\\263\\com_image_0.jpg",
    "indNotes": "",
    "indRating": "-1"
  }
]
},
{
"stuId": 1000001,
"indCreateTime": "2023-08-11 15:14:56",
"indStuNo": "17879683110",
"indName": "张翔",
"BOX": [
  {
    "indId": 1,
    "actId": 263,
    "IND_QUANTITY": 2,
    "indPrice": 70,
    "COM_NAME": "test1",
    "COM_IMAGE": ".\\wwwroot\\commodity_image\\263\\com_image_0.jpg",
    "indNotes": "",
    "indRating": "-1"
  }
]
}
])

const pagenum=ref(1);
const pagesize=ref(6);



const order=ref(0);
const indStuNo=ref('');
const state=ref(0);
const getVerificationsList=async()=>{
  loading.value=Boolean(false);
  verificationsList.value.length=0;
  console.log(socId);
  // var indStuNo=indStuNo;
  console.log(indStuNo);
  if(indStuNo.value.length==0)
  indStuNo.value="null";
  axios.get('/api/society/indentlist',{
    params:{
    socId:socId,
    order:order.value,
    boxBegin:(pagesize.value*(pagenum.value-1)+1),
    boxEnd:(pagesize.value*pagenum.value),
    indStuNo:indStuNo.value,
    indState:state.value}
  }) 
  .then(response=>{
    console.log(pagesize.value*(pagenum.value-1)+1);
    console.log(pagesize.value*pagenum.value);
    console.log(state.value);
    verificationsList.value=JSON.parse(JSON.stringify(response.data));
    for(var i=0;i<verificationsList.value.length;++i)
      for(var j=0;j<verificationsList.value[i].BOX.length;++j){
          // console.log(verificationsList.value[i].BOX[j].indRating);
          if(verificationsList.value[i].BOX[j].indRating=='-1')
            verificationsList.value[i].BOX[j].indRating="无评分"
      }
    console.log(verificationsList.value);
    loading.value=Boolean(false);
  })
}


onMounted(()=>{
//socId.value = route.indVerifyCode.socId as string;
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
  "indStuNo": 1000001,
  "indName":"hhl",
  "LIST": [
    {
      "indId": 1,
      "actId": 263,
      //"COM_NAME": "test1",
      "indPrice": 2
    }
  ]
})
const verificate=()=>{
axios.post('/api/society/activity/verficate',{params:{indVerifyCode:indVerifyCode.value}})
  .then(response=>{
    console.log('response',response);
    // if(response.data=='核销失败'){
    if(response.status!=200){
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
font-size: 32px;
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