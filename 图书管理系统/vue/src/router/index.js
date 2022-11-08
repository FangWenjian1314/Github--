import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '../views/Layout.vue'
import Cookies from "js-cookie";

Vue.use(VueRouter)

const routes = [
  // ====  登录  ====
  { path: '/login', name: 'Login', component: () => import('@/views/login/Login') },

  // ====  主页  ====
  {
    path: '/', name: 'Layout', component: Layout,
    redirect: '/home', //重定向
    //children:[] 数组，子路由，定义其他路由嵌到在Layout里面
    children: [
      // ====  主页  ====
      { path: 'home', name: 'Home', component: () => import('@/views/home/HomeView') },

      // ====  User  ====
      { path: 'userList', name: 'UserList', component: () => import('@/views/user/List') },
      { path: 'addUser', name: 'AddUser', component: () => import('@/views/user/Add') },
      { path: 'editUser', name: 'EditUser', component: () => import('@/views/user/Edit') },

      // ====  Admin  ====
      { path: 'adminList', name: 'AdminList', component: () => import('@/views/admin/List') },
      { path: 'addAdmin', name: 'AddAdmin', component: () => import('@/views/admin/Add') },
      { path: 'editAdmin', name: 'EditAdmin', component: () => import('@/views/admin/Edit') },
      // ====  Category  ====
      { path: 'categoryList', name: 'CategoryList', component: () => import('@/views/category/List') },
      { path: 'addCategory', name: 'AddCategory', component: () => import('@/views/category/Add') },
      { path: 'editCategory', name: 'EditCategory', component: () => import('@/views/category/Edit') },

      // ====  Book  ====
      { path: 'bookList', name: 'BookList', component: () => import('@/views/book/List') },
      { path: 'addBook', name: 'AddBook', component: () => import('@/views/book/Add') },
      { path: 'editBook', name: 'EditBook', component: () => import('@/views/book/Edit') },

      // ====  Borrow  ====
      { path: 'borrowList', name: 'BorrowList', component: () => import('@/views/borrow/List') },
      { path: 'addBorrow', name: 'AddBorrow', component: () => import('@/views/borrow/Add') },
      { path: 'editBorrow', name: 'EditBorrow', component: () => import('@/views/borrow/Edit') },

      // ====  Return  ====
      { path: 'returnList', name: 'returnList', component: () => import('@/views/return/List') },
    ]
  },
  {
    path: '*',
    component: () => import('@/views/404')
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

//路由守卫
router.beforeEach((to,from,next) => {
  if (to.path === '/login') next()
  const admin = Cookies.get("admin")
  if (!admin && to.path !== '/login') return next("/login") //强制退回到登录页面
  //访问 /home的时候，并且cookie里面存在数据，这个时候我就直接放行
  next()
})

export default router
