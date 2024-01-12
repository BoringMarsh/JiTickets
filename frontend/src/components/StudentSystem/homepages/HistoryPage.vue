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
                    <el-button @click="queryHistory" type="danger"><el-icon><search/></el-icon></el-button>
                    </template>
                </el-input>
            </el-col>
            <el-col :span="4">
                <el-button type="danger" @click="queryClearReset">显示所有</el-button>
            </el-col>
        </el-row>
        <!-- 列表区域 -->
        <el-table :data="browseData" style="width: 100%">
            <el-table-column prop="actId" label="活动ID" width="120"></el-table-column>
            <el-table-column prop="broTimeStart" label="浏览时间" width="180"></el-table-column>
            <el-table-column prop="browserId" label="浏览者ID" width="120"></el-table-column>
            <el-table-column prop="whetherBuy" label="是否购买" width="120">
                <template v-slot="scope">
                    {{ scope.row.whetherBuy == 0 ? '是' : '否' }}
                </template>
            </el-table-column>
        </el-table>


    </el-card>
</template>
<script setup lang="ts">
import {reactive,onActivated,onMounted, ref} from 'vue';
import {useRouter} from 'vue-router'
import {store}from '../../../../router/store'
import axios from 'axios';
import {
  Star,
  StarFilled
} from '@element-plus/icons-vue'
import { ElMessageBox} from 'element-plus';

const user_id=ref();
const browseData = ref([
    {
        "broTimeStart": "2021-05-18 16:56:46",
        "actId": 1,
        "browserId": 1,
        "whetherBuy": 0
    }
])


async function getData(){
    user_id.value=sessionStorage.getItem("stuId")

    await axios.get('http://localhost:8084/api/activity-personal/browse?userId='+user_id.value)
    .then(response=>{
        console.log(response.data)
        browseData.value = JSON.parse(JSON.stringify(response.data));
    })
}

//显示的记录
onMounted(()=>{
    user_id.value=sessionStorage.getItem("stuId")
    getData()
    //记得改回来
    //user_id.value=1000000
})


</script>