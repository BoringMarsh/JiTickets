<template>
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
            <el-col :span="4">
                <el-button type="danger" @click="queryClearReset">显示所有</el-button>
            </el-col>

            <el-col :span="8">
                <el-text>点击筛选：</el-text>
                <el-button  color=" #aa0d0d" @click="noleftCom" >售罄</el-button>
                <el-button  color=" #b1b3b8" @click="expiredCom" >过期</el-button>
            </el-col>
        </el-row>
        <!-- 列表区域 -->
        <el-table :data="favorListOnDisplay" height="80vh">
            <!-- <el-table-column label="活动ID" prop="actId"></el-table-column> -->
            <el-table-column label="活动地点" prop="actLocation"></el-table-column>
            <el-table-column label="活动名称" prop="actName"></el-table-column>
            <el-table-column label="发布时间" prop="uploadTime"></el-table-column>
            <el-table-column label="报名截止时间" prop="regStartTime"></el-table-column>
            <el-table-column label="活动时间" prop="actTime"></el-table-column>
            <el-table-column label="操作" width="250px">
            <template #default="scope">
                <el-button
                type="danger"
                size="medium"
                @click="deleteFavor(scope.row.actId)"
                >删除</el-button>
                </template>
            </el-table-column>
        <!-- 其他列 -->
        </el-table>

        <!-- 分页组件 -->
        <!-- <el-pagination 
        v-model:current-page="queryInfo.pagenum" 
            :page-size="queryInfo.pagesize" 
            layout="total, prev, pager, next, jumper"
            :total="favorListAfterPick.favorListAfterPick.length" 
            @current-change="handleCurrentChange"
        >
        </el-pagination> -->

    </el-card>
    <!-- <el-button @click="getFavorList">test</el-button> -->
</template>
<script setup lang="ts">
import {reactive,onMounted, computed,ref,onActivated} from 'vue';
import {useRouter} from 'vue-router'
import {store}from '../../../../router/store'
import axios from 'axios';
import {
  Star,
  StarFilled
} from '@element-plus/icons-vue'
import { ElMessageBox } from 'element-plus';

const user_id=ref();

//筛选后的收藏


const queryInfo=reactive({
    query:'',//查询参数
    pagenum: 1,//当前页码
    pagesize:10//每页显示条数
})


const favorListOnDisplay=ref([
{
    actId: 1,
    actLocation: "嘉定校区",
    actName: "比亚迪冬季长跑",
    actTime: "2023-11-30 14:30:00",
    regStartTime: "2023-11-25 10:00:00",
    uploadTime: "2023-12-24 21:55:53",
}]);


function getFavorList(){
    user_id.value = sessionStorage.getItem('stuId') as string;
    console.log("userId:"+user_id.value)
    axios.get('http://localhost:8084/api/activity-personal/favour/'+user_id.value)
    .then(res=>{
        favorListOnDisplay.value = res.data;
        console.log(res.data)
        console.log(favorListOnDisplay.value)
    })
}

function deleteFavor(actId){
    user_id.value = sessionStorage.getItem('stuId') as string;
    axios.delete('http://localhost:8084/api/activity-personal/favour?stuId='+user_id.value+'&actId='+actId)
    .then(res=>{
        favorListOnDisplay.value = res.data;
        console.log(res.data)
        console.log(favorListOnDisplay.value)
        getFavorList();
    })
}
//显示的收藏
onMounted(()=>{
    user_id.value=sessionStorage.getItem("stuId")
    console.log("user_id:", user_id)
    getFavorList();
})





// function handleCurrentChange(){
//     console.log('当前页数',queryInfo.pagenum)

// }
// function comClick(com_id:number){
//     router.push({ 
//             path: '/home/commodityDetail', 
//             query: { com_id: com_id ,
//                     cus_id:user_id.value} 
//         })
// }
// function stoClick(sto_id:number){
//     router.push({ 
//             path: '/storeDetail', 
//             query: { sto_id: sto_id,
//                 cus_id:user_id.value } 
//         })
// }








</script>

<style scoped>
.el-table .warning-row {
  --el-table-tr-bg-color: var(--el-color-warning-light-9);
}
.el-table .success-row {
  --el-table-tr-bg-color: var(--el-color-success-light-9);
}
</style>
