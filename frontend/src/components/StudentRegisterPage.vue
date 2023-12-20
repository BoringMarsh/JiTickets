<template>
    <div :style="`background: url(${require('../assets/register.jpg')}) no-repeat center center; background-size: cover; height: 100vh; padding: 5% 0;`">
  
  
    <!-- 添加了一个半透明背景层，确保文字清晰可见 -->
    <div style="background-color: rgba(255, 255, 255, 0.8); border-radius: 10px; padding: 20px;">
      <h1 class="header" style="text-align: center; color: #333;">用户注册</h1>
  
      <el-form :model="form" 
               @submit.prevent="register"
               label-width="100px" 
               style="max-width: 460px; position: relative; margin: auto;"
               label-position="right">
  
        <!-- ...其他表单项内容保持不变... -->
        <el-form-item label="学号" prop="username">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="user_password">
          <el-input type="password" v-model="form.user_password"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirm_password" :validate-status="confirmStatus" :error="confirmError">
          <el-input type="password" v-model="form.confirm_password" @blur="validateConfirm"></el-input>
        </el-form-item>
        <el-form-item label="电子邮箱" prop="user_email">
          <el-input v-model="form.user_email"></el-input>
        </el-form-item>
        <el-form-item label="电话号码" prop="user_phone" :validate-status="phoneStatus" :error="phoneError">
        <el-input v-model="form.user_phone" @blur="validatePhone"></el-input>
        </el-form-item>
        <el-form-item label="校区" prop="user_campus">
          <el-select v-model="form.user_campus" placeholder="请选择校区">
            <!-- 遍历校区选项，options 是一个数组，包含每个校区的信息 -->
            <el-option v-for="option in campusOptions" :key="option.value" :label="option.label" :value="option.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="支付密码" prop="pay_password">
          <el-input type="password" v-model="form.pay_password"></el-input>
        </el-form-item>
        <el-form-item label="真实姓名" prop="stu_name">
          <el-input v-model="form.stu_name"></el-input>
        </el-form-item>   
        <el-form-item label="所在年级" prop="stu_year">
          <el-input v-model="form.stu_year"></el-input>
        </el-form-item>
        <el-form-item label="所在年级" prop="stu_year">
          <el-select v-model="form.stu_year" placeholder="请选择年级">
            <!-- 遍历年级选项，options 是一个数组，包含每个年级的信息 -->
            <el-option v-for="option in yearOptions" :key="option.value" :label="option.label" :value="option.value"></el-option>
          </el-select>
        </el-form-item>   
        <!-- 学院选择 -->
        <el-form-item label="所在学院" prop="stu_school">
          <el-select v-model="form.stu_school" placeholder="请选择所在学院" @change="loadMajors">
            <el-option
              v-for="school in schoolOptions"
              :key="school.value"
              :label="school.label"
              :value="school.value"
            ></el-option>
          </el-select>
        </el-form-item>

        <!-- 专业选择 -->
        <el-form-item label="所在专业" prop="stu_major">
          <el-select v-model="form.stu_major" placeholder="请选择所在专业">
            <el-option
              v-for="major in majorOptions"
              :key="major"
              :label="major"
              :value="major"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="个人备注" prop="stu_notes">
          <el-input type="textarea" v-model="form.stu_notes" style="width: 100%;" rows="2"></el-input>
        </el-form-item>
        <el-form-item>
          <p>请选择个人喜好关键词</p>
          <el-select v-model="stu_keyword"
                     multiple
                     placeholder="Select"
                     style="width: 100%; position: relative; margin: auto;">
                     <el-option v-for="item in keywords" :key="item" :label="item" :value="item" />

          </el-select>
        </el-form-item>
        <el-form-item>
          <!-- 增加了按钮的样式，使其更为吸引眼球 -->
          <el-button type="primary" native-type="submit" style="width: 100%; border-radius: 25px; box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);">确认注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
  </template>
  
  
  <script lang="ts" setup>
  import { reactive, ref, onMounted } from 'vue';
  import axios from 'axios';
  import { useRouter } from 'vue-router';
  import { ElMessage } from 'element-plus';

  
  const router = useRouter();
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

  const confirmStatus = ref('');
  const confirmError = ref('');
  const phoneStatus = ref('');
  const phoneError = ref('');
  const phoneReg = /^1[3-9]\d{9}$/;   //正则表达式，暂时先检测中国号码。。。。
  const stu_keyword = ref([]);
  const keywords = ref([]);

  // 校区选项数组，每个选项包含 label（显示文本） 和 value（对应值）
  const campusOptions = ref([
    { label: '嘉定校区', value: 'campus1' },
    { label: '四平路校区', value: 'campus2' },
    { label: '沪西校区', value: 'campus3' },
    { label: '沪北校区', value: 'campus4' },
    { label: '彰武路校区', value: 'campus5' },
    { label: '张江校区', value: 'campus6' },
    { label: '临港校区', value: 'campus7' },
    // 添加更多校区选项...
  ]);

    // 年级选项数组，每个选项包含 label（显示文本） 和 value（对应值）
    const yearOptions = ref([
    { label: '本科2023级', value: 'year1' },
    { label: '本科2022级', value: 'year2' },
    { label: '本科2021级', value: 'year3' },
    { label: '本科2020级', value: 'year4' },
    { label: '本科2019级', value: 'year5' },
    { label: '硕士研究生', value: 'year6' },
    { label: '博士研究生', value: 'year7' },
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
  const selectedSchool = form.stu_school;
  const selectedSchoolData = schoolAndMajorData.find(item => item.school === selectedSchool);
  majorOptions.value = selectedSchoolData ? selectedSchoolData.majors : [];
};



  const validateConfirm = () => {
    if (form.user_password !== form.confirm_password) {
      confirmStatus.value = 'error';
      confirmError.value = '两次输入的密码不一致';
    } else {
      confirmStatus.value = 'success';
      confirmError.value = '';
    }
  };

  const validatePhone = () => {
    if (!phoneReg.test(form.user_phone)) {
      phoneStatus.value = 'error';
      phoneError.value = '电话号码格式不正确';
    } else {
      phoneStatus.value = 'success';
      phoneError.value = '';
    }
  };
  
  const get_keywords = () => {
      axios.get('/api/keywords/getkeywords'
      ,{
        headers: {
              //'Content-Type': 'application/json', 
              "Access-Control-Allow-Origin": "*",
          }
          }).then(response => {
            console.log(response.data)
            keywords.value=response.data
            console.log(keywords.value)
          })
          .catch((error) => {
              console.log('An error occurred:', error);
          });
    }
    onMounted(()=>{
        get_keywords()
  })

  const register = async () => {
    validateConfirm();
    validatePhone();
    if (phoneStatus.value === 'error' || confirmStatus.value === 'error') {
      return;
    }
    try {
        const registerForm = {
            username: form.username,               // Update from user_phone to username
            password: form.user_password,               // Update from user_password to password
            email: form.user_email,                     // Add user_email
            phone: form.user_phone,                     // Add user_phone
            campus: form.user_campus,                   // Add user_campus
            payPassword: form.pay_password,         // Add pay_password
            stuName: form.stu_name,                 // Add stu_name
            stuYear: form.stu_year,                 // Add stu_year
            stuSchool: form.stu_school,             // Add stu_school
            stuMajor: form.stu_major,               // Add stu_major
            stuNotes: form.stu_notes,               // Add stu_notes
            keywords: stu_keyword.value,               // Add stu_keyword as an array
        };

      console.log(registerForm);

      const response = await axios.post('/api/user/register/student', registerForm);

      console.log(response.data);

      if (response.status === 200) {
        const data = response.data;
        if (data.message === 'success') {
            localStorage.setItem('user_ID', data.user_ID); // 保存 user_ID 到 localStorage
            ElMessage({  
              message: '注册成功，正在跳转登录页面...',
              type: 'success',
            });
            sessionStorage.removeItem('id');
            sessionStorage.setItem('id',data.user_ID)
            router.push({name:'login'});
        } else {
          // 如果不是 'success'，那么这是一个错误消息
          ElMessage({  
            message: data.message,
            type: 'error',
          });
        }
      } else {
        console.error(`Error: HTTP status code ${response.status}`);
      }
    } catch (error) {
      console.error(error);
    }
  };
  </script>
  
  <style>
  .header{
    display: flex;
    justify-content: center;
    align-items: center;
    
  }
  </style>