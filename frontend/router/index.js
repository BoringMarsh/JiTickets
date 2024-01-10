import { createRouter, createWebHistory } from 'vue-router';
import DetailPage from '../src/components/SocietySystem/DetailPage.vue'; // 导入相应的路由组件
import ViewPage from '../src/components/SocietySystem/ViewPage.vue'; // 导入相应的路由组件
import UploadCommodityPage from '../src/components/SocietySystem/UploadCommodityPage.vue'; // 导入相应的路由组件
import SocietyPage from '../src/components/SocietySystem/SocietyPage.vue'
import AdministratorPage from '../src/components/AdministratorSystem/AdministratorPage.vue';
import UpdateCommodityPageVue from '@/components/SocietySystem/UpdateCommodityPage.vue';
import VerificationPage from '@/components/SocietySystem/VerificationPage.vue';
import RefundPage from '@/components/SocietySystem/RefundPage.vue'
import OperatePage from '@/components/AdministratorSystem/OperatePage.vue';
import StoreCommodityPage from '@/components/AdministratorSystem/StoreCommodityPage.vue'
import StoreIndentPage from '@/components/AdministratorSystem/StoreIndentPage.vue'
import IndentDetailPage from '@/components/AdministratorSystem/IndentDetailPage.vue';
import AppealPage from '@/components/AdministratorSystem/AppealPage.vue';
import LoginPage from '../src/components/LoginPage.vue';
import IDLoginPage from '../src/components/IDLoginPage.vue'
import PhoneLoginPage from '../src/components/PhoneLoginPage.vue'
import FindPasswordPage from '../src/components/FindPasswordPage.vue'
import SocietyRegister from '@/components/SocietyRegister.vue';
import UserRegister from '@/components/UserRegister.vue';
import CustomerRegister from '@/components/CustomerRegisterPage.vue';
import StudentRegister from '@/components/StudentRegisterPage.vue';
import UserInfo from '@/components/UserInfoPage.vue'
import ModifyStudentInfo from '@/components/ModifyStudentInfoPage.vue';
import ModifySocietyInfo from '@/components/ModifySocietyInfoPage.vue';
import ChatPage from '@/components/ChatPage.vue';
import MiddleTestPage from '@/components/MiddleTestPage.vue';
import SearchPage from '../src/components/StudentSystem/homepages/SearchPage.vue';
import CommodityDetailPage from '../src/components/StudentSystem/homepages/CommodityDetailPage.vue';
import storeDetaillPage from '../src/components/StudentSystem/storeDetailPage.vue';
import PersonalFavorPage from '../src/components/StudentSystem/homepages/PersonalFavorPage.vue'
import HistoryPage from '../src/components/StudentSystem/homepages/HistoryPage.vue'
import ShoppingCartPage from '../src/components/StudentSystem/homepages/ShoppingCartPage.vue'
import RecommendationPage from '../src/components/StudentSystem/homepages/RecommendationPage.vue'
import IndentPage from '../src/components/StudentSystem/homepages/IndentPage.vue'
import BlockUserPage from '../src/components/BlockUserPage.vue';
import HomePage from '../src/components/StudentSystem/HomePage.vue';


const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            redirect: "/login",
            meta: {
                keepAlive: true
            }
        },
        {
            path: '/home',
            component: HomePage,
            redirect: '/home/recommendation',
            children: [
                {
                    path: '/home/recommendation',
                    name: 'recommendation',
                    component: RecommendationPage,
                    meta: {
                        keepAlive: true //需要被缓存
                    }
                },
                {
                    path: '/home/search',
                    name: 'search',
                    component: SearchPage,
                    meta: {
                        keepAlive: true //需要被缓存
                    }
                },
                {
                    path: '/home/personalFavor',
                    name: 'personalFavor',
                    component: PersonalFavorPage,
                    meta: {
                        keepAlive: true //需要被缓存
                    }
                },
                {
                    path: '/home/commodityDetail',
                    name: 'commodityDetail',
                    component: CommodityDetailPage,
                },
                {
                    path: '/home/history',
                    name: 'history',
                    component: HistoryPage,
                    meta: {
                        keepAlive: true //需要被缓存
                    }
                },
                {
                    path: '/home/cart',
                    name: 'cart',
                    component: ShoppingCartPage,
                    meta: {
                        keepAlive: true //需要被缓存
                    }
                },
                {
                    path: '/home/indent',
                    name: 'indent',
                    component: IndentPage,
                    meta: {
                        keepAlive: true //需要被缓存
                    }
                },
            ]
        },
        {
            path: '/storeDetail',
            name: 'storeDetail',
            component: storeDetaillPage
        },
        {
            path: '/society',
            redirect: '/detail',
            component: SocietyPage,
            children: [
                {
                    path: '/detail',
                    component: DetailPage,
                    meta: {
                        keepAlive: true
                    }
                },
                {
                    path: '/view',
                    component: ViewPage
                },
                {
                    path: '/upload',
                    component: UploadCommodityPage
                },
                {
                    path: '/updateCommodity',
                    component: UpdateCommodityPageVue
                },
                {
                    path: '/verification',
                    component: VerificationPage
                },
                {
                    path: '/refund',
                    component: RefundPage
                }
            ]
        },
        {
            path: '/administrator',
            component: AdministratorPage,
            children: [
                {
                    path: '/operate',
                    component: OperatePage
                },
                {
                    path: '/commodity',
                    component: StoreCommodityPage,
                    meta: {
                        keepAlive: true
                    }
                },
                {
                    path: '/indent',
                    component: StoreIndentPage
                },
                {
                    path: '/indDetail',
                    component: IndentDetailPage
                },
                {
                    path: '/appeal',
                    component: AppealPage
                },

            ]
        },
        {
            path: '/login',
            name: 'login',
            component: LoginPage
        },
        {
            path: '/IDLogin',
            name: 'IDlogin',
            component: IDLoginPage
        },
        {
            path: '/phonelogin',
            name: 'phonelogin',
            component: PhoneLoginPage
        },
        {
            path: '/findpassword',
            name: 'findpassword',
            component: FindPasswordPage
        },
        {
            path: '/societyregister',
            name: 'societyregister',
            component: SocietyRegister
        },
        {
            path: '/userregister',
            name: 'userregister',
            component: UserRegister
        },
        {
            path: '/customerregister',
            name: 'customerregister',
            component: CustomerRegister
        },
        {
            path: '/studentregister',
            name: 'studentregister',
            component: StudentRegister
        },
        {
            path: "/UserInfoPage",
            name: 'UserInfoPage',
            component: UserInfo
        },
        {
            path: '/ModifyStudentInfoPage',
            name: 'ModifyStudentInfoPage',
            component: ModifyStudentInfo
        },
        {
            path: '/ModifySocietyInfoPage',
            name: 'ModifySocietyInfoPage',
            component: ModifySocietyInfo
        },
        {
            path: '/ChatPage',
            name: 'ChatPage',
            component: ChatPage
        },
        {
            path: '/BlockUserPage',
            name: 'BlockUserPage',
            component: BlockUserPage
        },
        {
            path: '/MiddleTest',
            name: 'MiddleTest',
            component: MiddleTestPage
        },
    ]
});

export default router;
