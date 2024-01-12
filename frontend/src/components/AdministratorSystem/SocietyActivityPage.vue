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
        <el-button type="primary" @click="noticeUpload">
          确认
        </el-button>
      </span>
    </template>
  </el-dialog>
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
        <el-button type="primary" icon="search" @click="getGoods"/>
        <el-col :span="10"></el-col>
      </el-row>
  
      <el-row>
    <el-table
      v-loading="loading"
     :data="activityList"
      style="width: 100%" max-height="1000" 
      border 
      @sort-change="changeSort"
      :default-sort="{ prop: 'actId', order: 'ascending' }"
      :row-style="changeStyle"
      >
      <el-table-column label="活动图片" width="200">
        <template #default="scope">
          <div style="display: flex; align-items: center">
            <el-image
              v-for="(item, index) in scope.row.images" :key="index"
              style="width: 180px; height: 90px"
              :src="'data:image/png;base64,' + item"
              :zoom-rate="1.2"
              :initial-index="0"
              fit="fill"
            />
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="actName" label="活动名字"> </el-table-column>
      <el-table-column prop="actId" sortable="custom" label="活动ID"></el-table-column>
      <el-table-column prop="ticketPrice" label="当前票价"></el-table-column>
      <el-table-column
      prop="actLeft"
      label="活动票余量"
      ></el-table-column>
      <el-table-column
      prop="keywords"
      label="活动类别"
      width="225"
      >
      <template #header>
        <div class="categoryStyle">
        {{'活动类别'}}
        <el-select
        v-model="category"
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
    <el-table-column prop="uploadTime" label="上传日期" width="160">
      <template #header>
        <div class="categoryStyle">
        {{'上传日期'}}
            <el-date-picker
              v-model="uploadDate"
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
    <el-table-column prop="regStartTime" label="开始报名日期" width="160">
      <template #header>
        <div class="categoryStyle">
        {{'过期日期'}}
            <el-date-picker
              v-model="expirationDate"
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
    <el-table-column prop="regEndTime" label="报名结束日期" width="160">
      <template #header>
        <div class="categoryStyle">
        {{'过期日期'}}
            <el-date-picker
              v-model="expirationDate"
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
    <el-table-column prop="actTime" label="活动日期" width="160">
      <template #header>
        <div class="categoryStyle">
        {{'过期日期'}}
            <el-date-picker
              v-model="expirationDate"
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
            @click="viewDetail(scope.$index)"
            ></el-button>
          <el-button
            type="primary"
            icon="edit"
            size="medium"
            :disabled = "isOverDate[scope.$index]"
            @click="viewUpdate(scope.$index)"
            v-if="!parseInt(admi)"
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
            layout="total, sizes, prev, pager, next, jumper" :total="activitycount">
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
    import { ArrowLeft } from '@element-plus/icons-vue'
    import { ElMessage, ElMessageBox,ElNotification, dayjs } from 'element-plus'
  import { ConsoleLogger } from '@microsoft/signalr/dist/esm/Utils';
    const router=useRouter();
    const pagenum=ref(1);
    const pagesize=ref(5);
    const activitycount=ref(0);
    const query=ref('');
    const haveQuery=ref('');
    const uploadDate=ref('');
    const expirationDate=ref('')
    const loading=ref(false);
    const ableEdit = ref([true]);
    const isEmpty = ref([true]) //是否售空
    const isOverDate = ref([true]) //是否过期
    const soc_id=ref('');
    const route=useRoute();
    const admi=ref('0');
    const dialogVisible = ref(false)
    const text=ref('');
    const idx=ref(1);
    const activityList=ref([
      {
          "actId": 1,
          "actName": "商品1",
          "actLeft": 1,
          "uploadDate":'',
          "regStartTime":'',
          "regEndTime":'',
          "actTime":'',
          "ticketPrice": 0.01,
          "keywords": [
              "苹果"
          ],
          "images": [
              ""
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
      value: '主食',
      label: '主食',
    },
    {
      value: '面包',
      label: '面包',
    },
    {
      value: '苹果',
      label: '苹果',
    }
  ]);
  
  const order=ref(0);
  const category=ref([]);
  const getSocietyActivityList=async()=>{
    loading.value=Boolean(true);
    activityList.value.length=0;
    axios.get('http://localhost:8084/api/order/society-activitylist?SOC_ID='+soc_id.value+'&BEGIN_NUMBER='+(pagesize.value*(pagenum.value-1)+1)+'&END_NUMBER='+(pagesize.value*pagenum.value)) 
      .then(response=>{
        activitycount.value = response.data.count;
        activityList.value=JSON.parse(JSON.stringify(response.data.list));
        isEmpty.value = []
        isOverDate.value=[]
        // for(var i = 0; i < activityList.value.length;i++){
        //   if(status.value==1){
        //     isEmpty.value.push(false);
        //     isOverDate.value.push(false);
        //   }
        //   else if (status.value==0){
        //     isEmpty.value.push(true);
        //     isOverDate.value.push(false);
        //   }
        //   else{ if(status.value==-1)
        //     if(activityList.value[i].com_left !=0)  {isEmpty.value.push(false);}
        //     else {isEmpty.value.push(true);}
        //     isOverDate.value.push(true);
        //   }
        // }
        loading.value=Boolean(false);
      })
  }

  
  const getGoods=async()=>{
    getSocietyActivityList().then(()=>
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
    getSocietyActivityList();
    query.value=str;
  }
  
  
  const srcList =ref( [
    "/commodity_image\\1\\com_image_0.jpg",
    ]);
  const url = 'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg';
  
  const goBack=()=>{
    history.back();
  }
  onMounted(()=>{
      //soc_id.value = route.query.soc_id as string;
      if(route.query.soc_id==null)
        soc_id.value = sessionStorage.getItem('soc_id') as string;
      else
        soc_id.value = route.query.soc_id as string;
  
      if(route.query.admi!=null)
        admi.value='1';
      console.log(soc_id.value);
        getSocietyActivityList();
    });
  
  onBeforeUpdate(()=>{
    if(route.query.soc_id==null)
        soc_id.value = sessionStorage.getItem('soc_id') as string;
      else
        soc_id.value = route.query.soc_id as string;
      console.log(soc_id.value);
        getSocietyActivityList();
  })
    const viewDetail=(index: number)=>{
        // router.push('/view');
        console.log(activityList.value[index].com_id);
        router.push({
          path: '/indDetail',
          query:{
              com_id:activityList.value[index].com_id
          }
      });
    }
  var durationTime=2000;
  const viewUpdate=(index: number)=>{
        // router.push('/view');
        console.log(activityList.value[index].com_id);
        router.push({
          path: '/updateCommodity',
          query:{
              com_id:activityList.value[index].com_id,
              soc_id:soc_id.value
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
        idx.value=index;
        dialogVisible.value=true;
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
      getSocietyActivityList();
    }
  
    const handleCurrentChange=(val:number)=>{
      pagenum.value=val;
      
      getSocietyActivityList();
    }
  
    const tableRowClassName = ({
      rowIndex,
    }: {
      rowIndex: number
    }) => {
      if (rowIndex === 1) {
        return 'warning-row'
      } else if (rowIndex === 3) {
        return 'success-row'
      }
      return ''
    }
  
    const changeStyle=({row,index})=>{
      console.log(dayjs(row.com_expirationdate).add(1,'day').isBefore(dayjs()));
      // console.log(dayjs());
      if(dayjs(row.com_expirationdate).add(1,'day').isBefore(dayjs()))
        return '--el-table-tr-bg-color: var(--el-color-error-light-9);'
      else if(dayjs(row.com_expirationdate).add(1,'day').isBefore(dayjs().add(5,'day')))
        return '--el-table-tr-bg-color: var(--el-color-warning-light-9);'
      else if(dayjs(row.com_expirationdate).add(1,'day').isBefore(dayjs().add(1,'month')))
        return '--el-table-tr-bg-color: var(--el-color-success-light-9);'
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
    axios.post('api/UploadCommodity/deleteCommodity?COM_ID='+activityList.value[idx.value].com_id)
          .then(response=>{
            console.log(response);
            ElNotification.success({
              title: 'Success',
              message: '下架成功',
              duration: durationTime
            })
            var str=query.value;
            query.value=haveQuery.value;
            getSocietyActivityList();
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
  <style>
  .el-table .warning-row {
    --el-table-tr-bg-color: var(--el-color-warning-light-9);
    /* background-color: aquamarine; */
    /* color: aqua; */
  }
  .el-table .success-row {
    --el-table-tr-bg-color: var(--el-color-success-light-9);
  }
  </style>
    