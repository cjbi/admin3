import {createRouter, createWebHashHistory, RouteRecordRaw} from 'vue-router';
import Home from '../views/home.vue';
import {useBasicStore} from "../store/basic";

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    redirect: '/dashboard',
  },
  {
    path: '/',
    name: 'Home',
    component: Home,
    children: [
      {
        path: '/dashboard',
        name: 'dashboard',
        meta: {
          title: '系统首页',
        },
        component: () => import(/* webpackChunkName: "dashboard" */ '../views/dashboard.vue'),
      },
      {
        path: '/users',
        name: 'users',
        meta: {
          title: '用户列表',
        },
        component: () => import(/* webpackChunkName: "dashboard" */ '../views/user-list.vue'),
      }, {
        path: '/roles',
        name: 'roles',
        meta: {
          title: '角色管理',
        },
        component: () => import(/* webpackChunkName: "dashboard" */ '../views/role-list.vue'),
      }, {
        path: '/resources',
        name: 'resources',
        meta: {
          title: '权限资源',
        },
        component: () => import(/* webpackChunkName: "dashboard" */ '../views/resource-list.vue'),
      },
      {
        path: '/logs',
        name: 'logs',
        meta: {
          title: '系统日志',
        },
        component: () => import(/* webpackChunkName: "dashboard" */ '../views/log-list.vue'),
      },
      {
        path: '/storage',
        name: 'storage',
        meta: {
          title: '对象存储',
        },
        component: () => import(/* webpackChunkName: "dashboard" */ '../views/storage-list.vue'),
      },
      {
        path: '/tabs',
        name: 'tabs',
        meta: {
          title: 'tab标签',
          permiss: '3',
        },
        component: () => import(/* webpackChunkName: "tabs" */ '../views/tabs.vue'),
      },
      {
        path: '/user',
        name: 'user',
        meta: {
          title: '个人中心',
        },
        component: () => import(/* webpackChunkName: "user" */ '../views/user.vue'),
      },
    ],
  },
  {
    path: '/login',
    name: 'Login',
    meta: {
      title: '登录',
    },
    component: () => import(/* webpackChunkName: "login" */ '../views/login.vue'),
  },
  {
    path: '/403',
    name: '403',
    meta: {
      title: '没有权限',
    },
    component: () => import(/* webpackChunkName: "403" */ '../views/403.vue'),
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

router.beforeEach(async (to, from, next) => {
  document.title = `${to.meta.title} | Admin3后台管理系统`;
  const token = localStorage.getItem('token');
  const basicStore = useBasicStore();
  if (!token && to.path !== '/login') {
    next('/login');
  } /*else if (to.meta.permiss && !permiss.key.includes(to.meta.permiss)) {
        // 如果没有权限，则进入403
        next('/403');
    }*/ else {
    if (token) {
      await basicStore.fetchUserinfo();
    }
    next();
  }
});
export default router;
