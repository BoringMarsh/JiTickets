<template>
    <el-drawer
        v-model="showActivityDetail"
        title="活动详情"
        direction="rtl"
        size="50%"
        
    >
    <el-form :model="activityDetailedInfo" label-width="100px">
        <el-form-item label="活动名称">
          <el-input v-model="activityDetailedInfo.actName"></el-input>
        </el-form-item>
        <el-form-item label="活动介绍">
          <el-input v-model="activityDetailedInfo.actIntro"></el-input>
        </el-form-item>
        <el-form-item label="活动地点">
          <el-input v-model="activityDetailedInfo.actLocation"></el-input>
        </el-form-item>
        <el-form-item label="票价">
          <el-input-number v-model="activityDetailedInfo.ticketPrice" :precision="2" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="上传时间">
          <el-date-picker v-model="activityDetailedInfo.uploadTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
        </el-form-item>
        <el-form-item label="活动开始时间">
          <el-date-picker v-model="activityDetailedInfo.actTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
        </el-form-item>
        <el-form-item label="活动容量">
          <el-input v-model="activityDetailedInfo.actCapability" :step="1"></el-input>
        </el-form-item>
        <el-form-item label="剩余名额">
          <el-input v-model="activityDetailedInfo.actLeft" :step="1"></el-input>
        </el-form-item>
        <el-form-item label="活动评分">
          <el-rate v-model="activityDetailedInfo.actRating" :max="5" :allow-half="true"></el-rate>
        </el-form-item>
        <el-form-item label="评分人数">
          <el-input v-model="activityDetailedInfo.ratingNum" :step="1"></el-input>
        </el-form-item>
        <el-form-item label="社团 ID">
          <el-input v-model="activityDetailedInfo.socId"></el-input>
        </el-form-item>
        <!-- <el-form-item label="活动图片">
        
        </el-form-item> -->
        <el-form-item label="关键词">
          <div>{{ activityDetailedInfo.keywords.join(', ') }}</div>
        </el-form-item>
      
      </el-form>
      <template v-slot:footer>
        <div class="dialog-footer">
          <el-button @click="apply()" type="success">报名</el-button>
          <el-button @click="addFavour()" type="primary">收藏</el-button>
          <el-button @click="closeDrawer()" type="danger">关闭</el-button>
        </div>
      </template>
      
    </el-drawer>
    <el-row>
    <el-col :span="16" :offset="4">
    <!-- <el-card>
        <el-carousel :interval="5000" arrow="hover" trigger="click" v-loading="car_loading">
            <el-carousel-item v-for="item in carousel" :key="item.commdation" @click="carNav(item)" :label="item.commdation">
                <el-row justify="center">
                    <el-image :src="item.image" fit="contain">
                        <template #error>
                            <div class="image-slot">加载错误</div>
                        </template>
                    </el-image>
                </el-row>
            </el-carousel-item>
        </el-carousel>
    </el-card> -->

    
    </el-col>
    </el-row>
    <el-card>
        <!-- 搜索区域 -->
        <el-row :gutter="20">
            <el-col :span="8">
                <el-input 
                    placeholder="请输入活动名称或社团名称" 
                    v-model="queryInfo.query" 
                    clearable
                    @clear="queryClearReset"
                >
                    <template #append>
                    <el-button @click="queryFavor" type="danger"><el-icon><search/></el-icon></el-button>
                    </template>
                </el-input>
            </el-col>

        </el-row>
        <!-- 列表区域 -->
        <el-table :data="activityList" style="width: 100%">
        <!-- <el-table-column prop="actId" label="活动ID"></el-table-column> -->
        <el-table-column prop="actName" label="活动名称"></el-table-column>
        <el-table-column prop="actLocation" label="活动地点"></el-table-column>
        <el-table-column prop="uploadTime" label="上传时间"></el-table-column>
        <el-table-column prop="regStartTime" label="报名开始时间"></el-table-column>
        <el-table-column prop="actTime" label="活动时间"></el-table-column>
        <el-table-column label="操作" width="250px">
            <template #default="scope">
                <el-button
                type="success"
                size="medium"
                @click="getActivityDetail(scope.row.actId)"
                >详情</el-button>
                </template>
            </el-table-column>
        </el-table>
        
        <el-pagination
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 30, 50]"
        :page-size="pageSize"
        :total="totalPage"
        layout="total, sizes, prev, pager, next, jumper"
        ></el-pagination>

    

    </el-card>
    <!-- <el-button @click="getActivityList">test</el-button> -->
</template>
<script setup lang="ts">
interface Commodity_tab {
  com_id: number;
  com_name: string;
  com_introduction: string;
  com_oriPrice: number;
  com_expirationDate: string;
  com_uploadDate: string;
  com_left: number;
  com_rating: number;
  sto_name: string;
  sto_id: number;
  com_categories: string[];
  com_firstImage:string;
  com_price:number;
  favor_state:number;
  com_status:number;
  com_color:string;// 显示的颜色
  dayDiff:number;// 距离过期的时间/天
}

interface carousel_item{
    image:string,
    id,
    type:number,// 0 商品 1 商家
    commdation:string
}

const activityList = ref([])


import { ref, reactive, onMounted,computed, watchEffect,onDeactivated, onActivated} from 'vue';
import { Waterfall } from "vue-waterfall-plugin-next";
import  baseURL  from "../../../../router/baseURL.js";
import {store}from '../../../../router/store'
import "vue-waterfall-plugin-next/dist/style.css";
import axios from 'axios';
import {  useRouter } from 'vue-router'
import {
  Star,
  StarFilled
} from '@element-plus/icons-vue'


const target_actId = ref();
const loading=ref(false);

const user_id=ref();

const display_str=ref("");//用于错误提示
const showActivityDetail=ref(false);

const TagArr=ref<Array<{
    tag:string,
    checked:boolean
}>>([]);//全部标签
const TagSelected=computed(()=>{
    let arr=Array<string>()
    TagArr.value.forEach(item =>{
        if(item.checked){
            arr.push(item.tag)
        }
    })
    return arr;
});//被选中的标签



const List=reactive<Array<Commodity_tab>>([]);//展示的列表

const queryInfo=reactive({
    pagenum: 1,//当前页码
    pagesize:10,//每页显示条数
    total:0,//共计条目数
    type:"商品", //暂时只能查看商品，这里不动
    categories:[] as Array<string>
})


onMounted(()=>{
    user_id.value=sessionStorage.getItem("stu_id")
    console.log("user_id.value:"+user_id.value);

    getActivityList();

})

onActivated(()=>{
    store.activePath='/home/recommendation'
})

onDeactivated(()=>{
    axios.post('/api/recommendation',
    {
        user_id:user_id.value as string
    }).then(res=>{
        console.log('推荐算法启动成功',res)
    }).catch(()=>{
        console.log('推荐算法启动失败')
    })
})
const page=ref(1)

function getActivityList(){
    axios.get(
        baseURL + `/api/activity-personal/activity/page/${page.value}`
    ).then(response => {
        console.log(response.data)
        activityList.value = response.data.activityList
    })
}
let activityDetailedInfo = reactive({
  "actName": "Sample Activity",
  "actIntro": "This is a sample activity.",
  "actLocation": "New York",
  "ticketPrice": 10.5,
  "uploadTime": "2022-01-01 10:00:00",
  "regStartTime": "2022-01-02 12:00:00",
  "regEndTime": "2022-01-03 12:00:00",
  "actTime": "2022-01-10 15:00:00",
  "actCapability": 100,
  "actLeft": 50,
  "actRating": 4.5,
  "ratingNum": 10,
  "socId": 1,
  "images": ["image1.jpg", "image2.jpg"],
  "keywords": ["music", "sports", "outdoor"]
})


const body = ref({
    broTimeStart: "2020-05-31 18:37:49", // 浏览起始时间
    actId: 1,
    browserId: 1, // 浏览者Id
    whetherBuy: false // 购买情况 true购买 false未购买
})
// 查看活动详情
async function getActivityDetail(actId){
    // 把活动详情根据 actId get出来
    await axios.get(`http://localhost:8084/api/activity-personal/activity/${actId}`)
    .then(response=>{
        console.log(response.data)
        activityDetailedInfo = response.data
        console.log(activityDetailedInfo)
        
    })
    // 添加浏览记录
    user_id.value=sessionStorage.getItem("stuId")
    body.value.browserId = user_id.value
    body.value.actId = actId
    axios.post(
        baseURL + `/api/activity-personal/browse`,body
    ).then(response => {
        console.log(response.data)
        
    })
    showActivityDetail.value = true;
    target_actId.value = actId
}
function closeDrawer(){
    showActivityDetail.value = false;
}
const requestBody=ref({
  indPrice: 0,
  indCreateTime: "string",
  indVerifyCode: "string",
  indName: "string",
  indStuNo: "string",
  indNotes: "string",
  actId: 0,
  stuId: 0,
  socId: 0
})
// 报名
function apply(){
    requestBody.value.actId = target_actId.value
    user_id.value = sessionStorage.getItem("stuId")
    requestBody.value.stuId = user_id.value
    requestBody.value.indPrice = activityDetailedInfo.ticketPrice
    // 其他信息从哪里获取？
    axios.post(baseURL + `/api/activity-personal/indent`, requestBody)
    .then(res=>{
        console.log(res.data)
    })
}
// 收藏
function addFavour(){
    user_id.value=sessionStorage.getItem("stuId")
    console.log(user_id.value)
    axios.post(baseURL + `/api/activity-personal/favour?actId=${target_actId.value}&stuId=${user_id.value}`)
    .then(res=>{
        console.log(res.data)
    })
}

function handleCurrentChange(){
    console.log('分页信息：',JSON.stringify({
                com_categories:TagSelected.value,
                begin_pos:queryInfo.pagesize*(queryInfo.pagenum-1),
                end_pos:queryInfo.pagesize*queryInfo.pagenum
              }))
    
    loading.value=true;
    //触发axios
    axios.post(
    baseURL+'/api/search/commodityList',
    JSON.stringify({
        cus_id: user_id.value,
        search_str: "",
        sort_order: 4,
        com_categories:TagSelected.value,
        begin_pos:queryInfo.pagesize*(queryInfo.pagenum-1),
        end_pos:queryInfo.pagesize*queryInfo.pagenum
    }), {
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response=>{
        console.log('列表为',response.data.com_list);
        var len=List.length;
        List.splice(0,len+1, ...response.data.com_list)
        //处理商品颜色
        List.forEach((item)=>{
            var expirationDate = new Date(item.com_expirationDate.replace(/-/g,"/"));
            var dateNow = new Date();
            var dateDiff = expirationDate.getTime() - dateNow.getTime();//时间差的毫秒数
            var dayDiff = Math.ceil(dateDiff / (24 * 3600 * 1000));//计算出相差天数，向下取整
            if(dayDiff<1)
                item.com_color="red";
            else if(dayDiff<3)
                    item.com_color="#e67300";
                else if(dayDiff<7)
                    item.com_color="#b88230";
                else
                    item.com_color="green";
            //设置显示颜色
        
            item.dayDiff=dayDiff
            //设置距离过期相差天数
            item.com_firstImage=baseURL+'/'+item.com_firstImage;
            //设置图片相对路径
        })
    }).catch(error => {
        console.error('分页失败', error);
        display_str.value='分页失败'
    }).finally(()=>{
        loading.value=false;
    });
            
        
}

</script>
<style scoped>

el-image{
  padding: 0 5px;
  max-width: 300px;
  width: 100%;
  height: 200px;  
}

.image-slot {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  background: var(--el-fill-color-light);
  color: var(--el-text-color-secondary);
  font-size: 30px;
}

.cardset{
    background-color: #fef0f0;
    box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
    border: 0.1px solid #81bbc7;
    border-radius: 2%;
    
}

.commodity-img {
    width: 100%;
    height: 200px;
    object-fit: contain;
  }

.comName{
    font-size:x-large;
}
</style>