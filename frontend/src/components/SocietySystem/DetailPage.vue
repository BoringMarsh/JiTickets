<template>
  <el-row>
<el-page-header :icon="ArrowLeft" title="返回" @back="goBack" >
  <template #content>
    <span class="text-large font-600 mr-3"> 活动总览 </span>
  </template>
</el-page-header>

</el-row>
  <el-row>
  <el-col :span="24">
    <el-card>
    <el-row :gutter="20">
      <el-col :span="8">
    <el-input placeholder="请输入内容" clearable v-model="query" />
      </el-col>
      <el-button type="danger" icon="search" @click="getGoods" />
      <el-col :span="10"></el-col>
      <el-col :span="5">
      
        <el-radio-group fill="#F56C6C" v-model="tableLayout">
          <el-radio-button  label="报名进行中" @click="checkLeft"/>
          <el-radio-button label="报名人满" @click="checkLeft1"/>
          <el-radio-button label="报名结束" @click="checkLeft2"/>
        </el-radio-group>
            
      </el-col>
    </el-row>

    <el-row>
  <el-table
    v-loading="loading"
   :data="goodsList"
    style="width: 100%" max-height="1000" 
 
    stripe
    @sort-change="changeSort"
    :default-sort="{ prop: 'act_id', order: 'descending' }"
    >
    <el-table-column label="活动图片" width="200">
      <template #default="scope">
        <div style="display: flex; align-items: center">
          <el-image
            style="width: 180px; height: 90px"
            :src="getUrl(scope.$index)"
            :zoom-rate="1.2"
            :initial-index="0"
            fit="fill"
            @click="getSrcList(scope.$index)"
          />
        </div>
      </template>
    </el-table-column>
    <el-table-column prop="act_name" label="活动名"> </el-table-column>
    <el-table-column prop="act_id" sortable="custom" label="活动ID"></el-table-column>
    <el-table-column prop="ticket_price" label="票价"></el-table-column>
    <el-table-column
    prop="act_left"
    label="活动剩余名额"
    ></el-table-column>
    <el-table-column
    prop="keyword"
    label="活动关键词"
    width="225"
    >
    <template #header>
      <div class="categoryStyle">
      {{'活动关键词'}}
      <el-select
      v-model="keyword"
      multiple
      collapse-tags
      collapse-tags-tooltip
      :max-collapse-tags="2"
      placeholder="Select"
      style="width: 210px"
    >
      <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      />
    </el-select>
    </div>
    </template>
  </el-table-column>
  <el-table-column prop="upload_time" label="上传日期" width="160">
    <template #header>
      <div class="categoryStyle">
      {{'发布时间'}}
          <el-date-picker
            v-model="upload_time"
            type="date"
            placeholder="Pick a day"
            :disabled-date="disabledDate"
            :shortcuts="shortcuts"
            size="default"
            style="width: 150px"
            value-format="YYYY-MM-DD"
          />
        </div>
      </template>
  </el-table-column>
  <el-table-column prop="reg_end_time" label="过期日期" width="160">
    <template #header>
      <div class="categoryStyle">
      {{'报名截至时间'}}
          <el-date-picker
            v-model="reg_end_time"
            type="date"
            placeholder="Pick a day"
            :shortcuts="shortcuts"
            size="default"
            style="width: 150px"
            value-format="YYYY-MM-DD"
          />
        </div>
      </template>
  </el-table-column>
    <el-table-column label="操作" width="200">
      <template #default="scope">
        <el-button
          type="primary"
          icon="search"
          size="medium"
          color="#ff9966"
          style="color: white;"
          @click="viewDetail(scope.$index)"
          ></el-button>
        <el-button
          type="primary"
          icon="edit"
          size="medium"
          color="#ffcb66"
          style="color: white;"
          :disabled = "isOverDate[scope.$index]"
          @click="viewUpdate(scope.$index)"
          ></el-button>
        <el-button
          type="danger"
          icon="delete"
          size="medium"
          :disabled = "isEmpty[scope.$index]||isOverDate[scope.$index]"
          @click="deleteCommodity(scope.$index)"
        ></el-button>
        </template>
    </el-table-column>
  </el-table>
  </el-row>
  <!-- 分页区域 -->
  <el-row>
  <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
          :current-page="pagenum" :page-sizes="[1, 3, 5, 10, 15, 20]" :page-size="pagesize"
          layout="sizes, prev, pager, next, jumper" :total="tot">
  </el-pagination>
</el-row>

</el-card>
</el-col>
</el-row>
  </template>
  
  <script lang="ts" setup>
  import { Options, Vue } from 'vue-class-component';
  import axios from 'axios';
  import {onMounted, ref} from 'vue'
  import { useRouter,useRoute } from 'vue-router';
  import { ArrowLeft } from '@element-plus/icons-vue'
  import { ElMessage, ElMessageBox,ElNotification } from 'element-plus'
  const router=useRouter();
  const pagenum=ref(1);
  const pagesize=ref(5);
  const query=ref('');
  const haveQuery=ref('');
  const tableLayout = ref('在售')
  const upload_time=ref('');
  const reg_end_time=ref('')
  const loading=ref(false);
  const ableEdit = ref([true]);
  const isEmpty = ref([false]) //是否售空
  const isOverDate = ref([false]) //是否过期
  const soc_id=ref('');
  const route=useRoute();
  const tot=ref(800);
  const goodsList=ref([
    {
        "act_id": 1,
        "act_name": "商品1",
        "act_left": 1,
        "upload_time":'',
        "reg_end_time":'',
        "ticket_price": 0.01,
        "keyword": [
            "苹果"
        ],
        "act_image": [
            ".\\wwwroot\\commodity_image\\1\\com_image_0.jpg"
        ]
    }
]);
const shortcuts = [
  {
    text: 'Today',
    value: new Date(),
  },
  {
    text: 'Yesterday',
    value: () => {
      const date = new Date()
      date.setTime(date.getTime() - 3600 * 1000 * 24)
      return date
    },
  },
  {
    text: 'A week ago',
    value: () => {
      const date = new Date()
      date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
      return date
    },
  },
]
const disabledDate = (time: Date) => {
  return time.getTime() > Date.now()
}
const options =ref( [
  {
    value: '体育',
    label: '体育',
  },
  {
    value: '文艺',
    label: '文艺',
  },
  {
    value: '娱乐',
    label: '娱乐',
  }
]);

const status=ref(1);  //1 活动报名中 0 报名人满 -1 报名截止
const order=ref(1);
const keyword=ref([]);
const getGoodsList=async()=>{
  loading.value=Boolean(true);
  goodsList.value.length=0;

  // axios.get('api/searchCommodity/getcommoditytotal?STO_ID='+soc_id.value+'&COM_STATUS='+status.value)
  // .then(response=>{
  //   tot.value=response.data;
  // });
  // 获取某社团活动
  axios.post('/api/society/activity/activities', {
        socId: soc_id.value,
        status: status.value,
        order: order.value,
        keyword: keyword.value,
        query: query.value,
        upload_time: upload_time.value,
        reg_end_time: reg_end_time.value,
        page: pagenum.value,
        pageSize: pagesize.value,
      })
      .then(response => {
        console.log('response',response.data)
        goodsList.value = response.data;
        loading.value=Boolean(false);
        for(var i = 0; i < goodsList.value.length;i++){
          if(status.value==1){
            isEmpty.value.push(false);
            isOverDate.value.push(false);
          }
          else if (status.value==0){
            isEmpty.value.push(true);
            isOverDate.value.push(false);
          }
          else if(status.value==-1)
          {
            if(goodsList.value[i].act_left !=0)  {isEmpty.value.push(false);}
            else {isEmpty.value.push(true);}
            isOverDate.value.push(true);
          }
      }
      })
      .catch(error => {
        console.error(error);
        loading.value=Boolean(false);
      });
  // axios.post('api/searchCommodity?STO_ID='+soc_id.value+'&com_begin_n='+(pagesize.value*(pagenum.value-1)+1)+'&com_end_n='+(pagesize.value*pagenum.value),{
  //   "status":status.value,
  //   "order":order.value,
  //   "category":category.value,
  //   "query":query.value,
  //   'COM_UPLOADDATE':uploadDate.value,
  //   'COM_EXPIRATIONDATE':expirationDate.value
  // }) 
  //   .then(response=>{
  //     console.log(pagesize.value*(pagenum.value-1)+1);
  //     console.log(pagesize.value*pagenum.value);
  //     goodsList.value=JSON.parse(JSON.stringify(response.data));
  //     isEmpty.value = []
  //     isOverDate.value=[]
  //     for(var i = 0; i < goodsList.value.length;i++){
  //       if(status.value==1){
  //         isEmpty.value.push(false);
  //         isOverDate.value.push(false);
  //       }
  //       else if (status.value==0){
  //         isEmpty.value.push(true);
  //         isOverDate.value.push(false);
  //       }
  //       else{ if(status.value==-1)
  //         if(goodsList.value[i].com_left !=0)  {isEmpty.value.push(false);}
  //         else {isEmpty.value.push(true);}
  //         isOverDate.value.push(true);
  //       }
  //     }
  //     loading.value=Boolean(false);
  //   })

}

const checkLeft=()=>{
  status.value=1;
  getGoodsList();
}

const checkLeft1=()=>{
  status.value=0;
  getGoodsList();
}

const checkLeft2=()=>{
  status.value=-1;
  getGoodsList();
}

const getGoods=async()=>{
  getGoodsList().then(()=>
  ElNotification.success({
    title: 'Success',
    message: '搜索成功',
    duration: durationTime
  }))
  haveQuery.value=query.value;
}

const changeSort=(value)=>{
  console.log(value.order);
  if(value.order=='ascending')
    order.value=0;
  else
    order.value=1;
  
  var str='';
  for(var i=0;i<query.value.length;++i)
    str+=query.value[i];
  console.log(str);
  query.value=haveQuery.value;
  getGoodsList();
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
//const getUrl=(index:number)=>{
  //return change(goodsList.value[index].act_image);
//}

const getUrl = (index) => {
  const activity = goodsList.value[index];
  if (activity.act_image && activity.act_image.length > 0) {
    return activity.act_image[0]; // 返回第一张图片的Base64编码
  }
  return ''; // 没有图片时返回空字符串
};

const goBack=()=>{
  history.back();
}
onMounted(()=>{
    //sto_id.value = route.query.sto_id as string;
    
    soc_id.value = sessionStorage.getItem('socId') as string;
    console.log('soc_id_inDetailPage',soc_id.value);
      getGoodsList();
  });
  const viewDetail=(index: number)=>{
      // router.push('/view');
      console.log(goodsList.value[index].act_id);
      router.push({
        path: '/view',
        query:{
            act_id:goodsList.value[index].act_id
        }
    });
  }
var durationTime=2000;
const viewUpdate=(index: number)=>{
      // router.push('/view');
      console.log(goodsList.value[index].act_id);
      router.push({
        path: '/updateCommodity',
        query:{
            act_id:goodsList.value[index].act_id,
            username:soc_id.value
        }
    });
  }

  const deleteCommodity=(index: number)=>{

    ElMessageBox.confirm(
    '此操作将下架该商品，是否继续？',
    'Warning',
    {
      confirmButtonText: 'OK',
      cancelButtonText: 'Cancel',
      type: 'warning',
    }
  )
    .then(() => {

      axios.delete('/api/society/activities', {
        params: {
          act_id: goodsList.value[index].act_id
        },
      })
        .then(response=>{
          console.log(response);
          ElNotification.success({
            title: 'Success',
            message: '下架成功',
            duration: durationTime
          })
          var str=query.value;
          query.value=haveQuery.value;
          getGoodsList();
          query.value=str;
        })
        .catch((error)=>{
          ElNotification({
            title: 'Error',
            message: '下架失败',
            type: 'error',
            duration: durationTime
          })
        })
    })
    .catch(() => {
      ElNotification({
        title: 'Info',
        message: '操作取消',
        type: 'info',
        duration: durationTime
      })
    })

  }


  const handleSizeChange=(val:number)=>{
    pagesize.value=val;
    getGoodsList();
  }

  const handleCurrentChange=(val:number)=>{
    pagenum.value=val;
    
    getGoodsList();
  }

  const getSrcList=(index:number)=>{
    var arr: string[];
    // arr=goodsList.value[index].com_image;
    arr=[];
    for(var i=0;i<goodsList.value[index].act_image.length;i++)
      arr.push(goodsList.value[index].act_image[i]);
    for(i=0;i<arr.length;i++){
      var str=arr[i].split('\\');
      arr[i]='/';
      for(var j=2;j<str.length;j++){
        arr[i]+=str[j];
        if(j!=str.length-1)
          arr[i]+='/'
      }
      console.log(arr[i]);
    }

    srcList.value=arr;
    // url.value=srcList.value[0];
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
  