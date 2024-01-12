<template>
  <el-container>
    <el-header  style="background-color: #eeebe4;">
      <el-row justify="space-between">
        <el-col :span="4" style="display: flex; align-items: center;">
          <img src="../../public/Logo.png" alt="Logo" style="height: 100%; max-height: 50px; margin-right: 10px;"> <!-- 添加商标图片 -->
          <h3 style="  
              color:#9f4833;
              text-overflow:ellipsis;
              white-space:nowrap;
              overflow:hidden;">
            个人信息修改
          </h3>
        </el-col>
        <el-col :span="20" style="display: flex; justify-content: flex-end; align-items: center;">
          <el-link icon="el-icon-arrow-left" @click="goBack" style="color: #9f4833; font-size: large; margin-left: 15px;">返回主页</el-link>
        </el-col>
      </el-row>
    </el-header>
    <el-main style="background-color: #fffbf7; min-height: 100vh;">
      <el-form :model="userInfo" ref="form" @submit.prevent="modifyUser" style="max-width: 1100px; position: relative; margin: auto;">
        <div style="border-bottom: 1px solid gray; padding: 10px;">
          <div style="display: flex; align-items: center;">
            <img src="../assets/icons8-account-64.png" alt="关于账户">
            <h3 style="margin-left: 10px;">账号信息</h3>
          </div>
          <div style="clear: both;"></div>
        </div>
        <br><br>
        <el-row :gutter="25">
          <el-col :span="3">
            <span class="label">
              <img :src="require('../assets/icons8-id-64.png')" alt="用户ID图标" style="height: 1.1em; margin-right: 1px;">
              学号：
            </span>
          </el-col>
          <el-col :span="9">
            <span class="value">{{ userInfo.username }}</span>
          </el-col>
        </el-row>
        <br>
        <el-row :gutter="25">
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
              <img :src="require('../assets/icons8-password-64.png')" alt="密码图标" style="height: 1.1em; margin-right: 1px;">
              新密码：
            </span>
          </el-col>
          <el-col :span="9">
            <el-input v-model="userInfo.password" type="password" :disabled="flag!=null"></el-input>
          </el-col>
        </el-row>
        <br>
        <el-row :gutter="25">
          <el-col :span="3">
            <span class="label">
              <img :src="require('../assets/icons8-edit-user-64.png')" alt="昵称图标" style="height: 1.1em; margin-right: 1px;">
              新昵称：
            </span>
          </el-col>
          <el-col :span="9">
            <el-input v-model="userInfo.stuName" :disabled="flag!=null"></el-input>
          </el-col>
        </el-row>
        <br>
        <el-row :gutter="25">
          <el-col :span="3">
            <span class="label">
              <img :src="require('../assets/icons8-edit-user-64.png')" alt="昵称图标" style="height: 1.1em; margin-right: 1px;">
              年级：
            </span>
          </el-col>
          <el-col :span="9">
            <el-select v-model="userInfo.stuYear" placeholder="请选择年级">
            <!-- 遍历年级选项，options 是一个数组，包含每个年级的信息 -->
            <el-option v-for="option in yearOptions" :key="option.value" :label="option.label" :value="option.value"></el-option>
          </el-select>
          </el-col>
        </el-row>
        <br>
        <el-row :gutter="25">
          <el-col :span="3">
            <span class="label">
              <img :src="require('../assets/icons8-edit-user-64.png')" alt="昵称图标" style="height: 1.1em; margin-right: 1px;">
              校区：
            </span>
          </el-col>
          <el-col :span="9">
            <el-select v-model="userInfo.campus" placeholder="请选择校区">
            <!-- 遍历校区选项，options 是一个数组，包含每个校区的信息 -->
            <el-option v-for="option in campusOptions" :key="option.value" :label="option.label" :value="option.value"></el-option>
          </el-select>
          </el-col>
        </el-row>
        <br>
        <el-row :gutter="25">
          <el-col :span="3">
            <span class="label">
              <img :src="require('../assets/icons8-edit-user-64.png')" alt="昵称图标" style="height: 1.1em; margin-right: 1px;">
              学院：
            </span>
          </el-col>
          <el-col :span="9">
            <el-select v-model="userInfo.stuSchool" placeholder="请选择所在学院" @change="loadMajors">
            <el-option
              v-for="school in schoolOptions"
              :key="school.value"
              :label="school.label"
              :value="school.value"
            ></el-option>
          </el-select>
          </el-col>
        </el-row>
        <br>
        <el-row :gutter="25">
          <el-col :span="3">
            <span class="label">
              <img :src="require('../assets/icons8-edit-user-64.png')" alt="昵称图标" style="height: 1.1em; margin-right: 1px;">
              专业：
            </span>
          </el-col>
          <el-col :span="9">
            <el-select v-model="userInfo.stuMajor" placeholder="请选择所在专业">
            <el-option
              v-for="major in majorOptions"
              :key="major"
              :label="major"
              :value="major"
            ></el-option>
          </el-select>
          </el-col>
        </el-row>
        <br>
        <el-row :gutter="25">
          <el-col :span="3">
            <span class="label">
              <img :src="require('../assets/icons8-like-50.png')" alt="喜好图标" style="height: 1.1em; margin-right: 1px;">
              兴趣爱好：
            </span>
          </el-col>
          <el-col :span="9">
            <el-select v-model="userInfo.stuKeyword" multiple clearable placeholder="Select" filterable allow-create :disabled="flag!=null">
                <el-option v-for="item in keywords" :key="item" :label="item" :value="item" :disabled="flag!=null"></el-option>
              </el-select>
          </el-col>
        </el-row>
        <br>
        <!-- <el-row :gutter="25">
          <el-col :span="3">
            <span class="label">
              <img :src="require('../assets/icons8-address-50.png')" alt="地址图标" style="height: 1.1em; margin-right: 1px;">
              新地址：
            </span>
          </el-col>
          <el-col :span="21">
            <el-input v-model="addressInput" @keyup.enter="searchLocation" :disabled="flag!=null"></el-input>
          </el-col>
        </el-row>
        <div id="baiduMap" style="width:100%; height: 300px; margin-top:10px;"></div>
        <br> -->
        <el-row :gutter="25">
          <el-col :span="3">
            <span class="label">
              <img :src="require('../assets/icons8-edit-user-64.png')" alt="昵称图标" style="height: 1.1em; margin-right: 1px;">
              新邮箱：
            </span>
          </el-col>
          <el-col :span="9">
            <el-input v-model="userInfo.email" :disabled="flag!=null"></el-input>
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
            <el-input v-model="userInfo.stuNotes"></el-input>
          </el-col>
        </el-row>
        <br>
        <el-button style="background-color: #aa1514; color: white;" native-type="submit">提交修改</el-button>
      </el-form>
    </el-main>
  </el-container>
</template>
  
  <script lang="ts" setup>
  import { reactive,ref, onMounted } from 'vue';
  import axios from 'axios';
  import { useRoute, useRouter } from 'vue-router';
  import { ElMessage } from 'element-plus';
  
  
  const form = reactive({
    username: '',
    user_password: '',
    confirm_password: '',
    user_email: '',       // Add user_email
    user_phone: '',       // Add user_phone
    user_campus: '',      // Add user_campus
    pay_password: '',     // Add pay_password
    stu_name: '',         // Add stu_name
    stu_year: '',         // Add stu_year
    stu_school: '',       // Add stu_school
    stu_major: '',        // Add stu_major
    stu_notes: '',        // Add stu_notes
    stu_keyword: [],      // Add stu_keyword as an array
  });
  const keywords = ref([]);

  // 校区选项数组，每个选项包含 label（显示文本） 和 value（对应值）
  const campusOptions = ref([
    { label: '嘉定校区', value: '嘉定校区' },
    { label: '四平路校区', value: '四平路校区' },
    { label: '沪西校区', value: '沪西校区' },
    { label: '沪北校区', value: '沪北校区' },
    { label: '其它校区', value: '其它校区' },
    // 添加更多校区选项...
    ]);

  // 年级选项数组，每个选项包含 label（显示文本） 和 value（对应值）
  const yearOptions = ref([
    { label: '2023本', value: '2023本' },
    { label: '2022本', value: '2022本' },
    { label: '2021本', value: '2021本' },
    { label: '2020本', value: '2020本' },
    { label: '硕士研究生', value: '硕士研究生' },
    { label: '博士研究生', value: '博士研究生' },
     // 添加更多选项...
     ]);

  // 学院和专业数据
  const schoolAndMajorData = [
    { school: '新生院', majors: ['人文科学试验班', '社会科学试验班', '经济管理试验班'
    , '工科试验班（建筑城规景观与设计类）', '工科试验班（土木与环境类）', '工科试验班（智能交通与车辆类）'
    , '工科试验班（智能化制造类）', '工科试验班（信息类）', '理科试验班'
    , '医学试验班', '机械类（中外合作办学）', '设计学类', '其它'] },
    { school: '艺术与传媒学院', majors: ['表演', '动画', '广播电视编导', '音乐表演', '广播电视学', '广告学'] },
    { school: '外国语学院', majors: ['日语', '英语', '德语'] },
    { school: '人文学院', majors: ['汉语言文学', '文化产业管理', '哲学'] },
    { school: '法学院', majors: ['法学'] },
    { school: '政治与国际关系学院', majors: ['政治学与行政学', '社会学'] },
    { school: '经济与管理学院', majors: ['金融学', '国际经济与贸易', '会计学', '市场营销', '物流管理', '工程管理', '信息管理与信息系统'] },
    { school: '建筑与城市规划学院', majors: ['风景园林', '城乡规划', '历史建筑保护工程', '建筑学', '城市设计'] },
    { school: '设计创意学院', majors: ['工业设计', '产品设计', '环境设计', '视觉传达设计'] },
    { school: '土木工程学院', majors: ['港口航道与海岸工程', '智能建造', '土木工程', '地质工程'] },
    { school: '环境科学与工程学院', majors: ['环境科学', '环境工程', '给排水科学与工程'] },
    { school: '机械与能源工程学院', majors: ['建筑环境与能源应用工程', '能源与动力工程', '工业工程', '机械设计制造及其自动化', '智能制造工程'] },
    { school: '交通运输工程学院', majors: ['交通工程', '交通运输'] },
    { school: '汽车学院', majors: ['车辆工程（汽车）'] },
    { school: '铁道与城市轨道交通研究院', majors: ['车辆工程'] },
    { school: '材料科学与工程学院', majors: ['新能源材料与器件', '材料科学与工程'] },
    { school: '航空航天与力学学院', majors: ['工程力学', '飞行器制造工程'] },
    { school: '测绘与地理信息学院', majors: ['测绘工程'] },
    { school: '电子与信息工程学院', majors: ['电气工程及其自动化', '自动化', '通信工程', '微电子科学与工程'
    , '数据科学与大数据技术', '电子信息工程', '计算机科学与技术', '信息安全', '人工智能'] },
    { school: '软件学院', majors: ['软件工程'] },
    { school: '物理科学与工程学院', majors: ['光电信息科学与工程', '应用物理学'] },
    { school: '海洋与地球科学学院', majors: ['海洋技术', '地球物理学', '海洋科学'] },
    { school: '数学科学学院', majors: ['统计学', '数学与应用数学'] },
    { school: '化学科学与工程学院', majors: ['应用化学', '化学工程与工艺'] },
    { school: '生命科学与技术学院', majors: ['生物技术', '生物信息学'] },
    { school: '口腔医学院', majors: ['口腔医学'] },
    { school: '医学院', majors: ['临床医学', '护理学', '康复物理治疗'] },
    { school: '中德工程学院', majors: ['机械电子工程', '汽车服务工程', '建筑电气与智能化'] },
    { school: '马克思主义学院', majors: ['马克思主义理论'] },
    { school: '国际足球学院', majors: ['运动训练'] },
    { school: '其它学院', majors: ['其它专业'] },
    // 添加更多学院和专业...
  ];

  // 学院选项
  const schoolOptions = ref(schoolAndMajorData.map(item => ({ value: item.school, label: item.school })));

  // 专业选项
  const majorOptions = ref<string[]>([]);
  // 加载专业选项
  const loadMajors = () => {
  const selectedSchool = userInfo.value.stuSchool;
  const selectedSchoolData = schoolAndMajorData.find(item => item.school === selectedSchool);
  majorOptions.value = selectedSchoolData ? selectedSchoolData.majors : [];
};

  const route = useRoute();
  const router = useRouter();
  const userInfo = ref({});
  const categories = ref([]);  

  const flag=route.query.flag;
  const get_keywords = () => {
      axios.get('/api/keywords/getkeywords')
      .then(response => {
            console.log(response.data)
            keywords.value=response.data.keywords
            console.log('keywords',keywords.value)
          })
          .catch((error) => {
              console.log('An error occurred:', error);
          });
    }

  onMounted(async () => {
    get_keywords();
    const username = flag==null? sessionStorage.getItem('username') as string:route.query.id;
    // const cus_ID=user_ID;
    console.log(sessionStorage.getItem('username'))
    const response = await axios.get('/api/user/profile/student/get', { params: { username:username } });
    if (response.status === 200) {
      userInfo.value = response.data;
    } else {
      console.error(`Error: HTTP status code ${response.status}`);
    }
  });
  
  // const searchLocation = () => { // 搜索地点
  //   const local = new BMapGL.LocalSearch(map, {
  //     renderOptions: { map: map, autoViewport: true, selectFirstResult: false },
  //     pageCapacity: 8,
  //   });
  //   // 搜索回调
  //   local.setSearchCompleteCallback(results => {
  //     if (local.getStatus() === BMAP_STATUS_SUCCESS) {
  //       const pt = results.getPoi(0).point;
  //       //form.sto_locationPoint = `${pt.lng},${pt.lat}`;
  //       // 更新输入框地址
  //       geoc.getLocation(pt, rs => {
  //         const addComp = rs.addressComponents;
  //         addressInput.value = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;
  //         userInfo.value.user_address = addressInput.value;
  //         //form.sto_lng = pt.lng.toString();
  //         //form.sto_lat = pt.lat.toString();
  //       });

  //     } else {
  //       ElMessage.error('未找到相关地址！');
  //     }
  //   });

  //   local.search(addressInput.value);
  // };

  const goBack = () => {
    if(flag==null)
      router.push({ path: '/home' });
    else
      router.push('/administrator');
  };

  const modifyUser = async () => {
    try {
      const userResponse = await axios.post('/api/user/profile/student/modify', {
        userId: userInfo.value.id,
        email: userInfo.value.email,
        phone: userInfo.value.phone,
        campus: userInfo.value.campus,
        stuName: userInfo.value.stuName,
        stuYear: userInfo.value.stuYear,
        stuSchool: userInfo.value.stuSchool,
        stuMajor: userInfo.value.stuMajor,
        stuNotes: userInfo.value.stuNotes,
        stuKeywords: userInfo.value.stuKeywords,
      });
  
      // const customerResponse = await axios.post('/api/modify/customer', {
      //   cus_ID: cusInfo.value.cus_ID,
      //   cus_nickname: cusInfo.value.cus_nickname,
      //   cus_notes: cusInfo.value.cus_notes,
      //   cus_payPassword: cusInfo.value.cus_payPassword,
      //   cus_category: cusInfo.value.cus_loves,
      // });
      console.log(userResponse);
      if (userResponse.status === 200/* && customerResponse.data.message === 'success'*/) {
        ElMessage.success('修改成功');
        if(flag==null)
          router.push({ name: 'UserInfoPage'});
        else
        router.push({ name: 'UserInfoPage',query:{id:userInfo.value.username,flag:1}});
      } else {
        ElMessage.error('修改失败');
      }
    } catch (error) {
      console.error(error);
    }
  };

  const editing = ref(false);

    const startEditing = () => {
    userInfo.value.cus_category = [];  // Clear the categories
    editing.value = true;
    };
  </script>
  