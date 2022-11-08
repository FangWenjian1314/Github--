<template>
  <div>
    <!-- 头部区域 -->
    <div style="height:60px;line-height: 60px;background-color: white; margin-bottom: 2px; display: flex">
      <div style="width: 300px">
        <img src="@/assets/logo.png" alt="" style="width:35px;position:relative;top:10px;left: 20px;">
        <span style="margin-left: 25px; font-size: 24px;">图书管理系统</span>
      </div>
      <div style="flex: 1; text-align: right; padding-right: 20px">
        <el-dropdown style="cursor: pointer" size="medium">
          <span class="el-dropdown-link">
            {{ admin.username }}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown" style="margin-top: -10px; width: 80px; text-align: center">
            <el-dropdown-item @click.native="logout">退出</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>

    <!-- 侧边栏和主体 -->
    <div style="display:flex" >
      <!-- 侧边栏导航 -->
      <div style="width: 200px; overflow: hidden; background-color: white; margin-right: 2px; min-height: calc(100vh - 62px);">
        <!--        为NavMenu导航菜单的空间，其中在菜单上的属性值default-active，它的说明内容为：当前激活菜单的 index,其实，即使不加这个属性，也能正常显示选中的item变亮，但是之所以要加这个属性，是为了浏览器刷新后，仍然可以定位到之前选中的路由。-->
        <!--        :default-active需要传进去的值就是子导航的index值（也就是其路由值），达到点击路由就可以引起高亮。-->
        <!--        :default-openeds 默认展开侧边栏-->
        <el-menu :default-active="$route.path" router class="el-menu-demo" style="width: 200px">
          <el-menu-item index="/home">
            <i class="el-icon-s-home"></i>
            <span>首页</span>
          </el-menu-item>
          <el-submenu index="user">
            <template slot="title">
              <i class="el-icon-s-custom"></i>
              <span>会员管理</span>
            </template>
            <el-menu-item index="/userList">会员列表</el-menu-item>
<!--            <el-menu-item index="/addUser">会员添加</el-menu-item>-->
            <!--            <el-menu-item index="editUser">会员编辑</el-menu-item>-->
          </el-submenu>
          <el-submenu index="admin">
            <template slot="title">
              <i class="el-icon-user"></i>
              <span>管理员管理</span>
            </template>
            <el-menu-item index="/adminList">管理员列表</el-menu-item>
            <el-menu-item index="/addAdmin">管理员添加</el-menu-item>
            <!--            <el-menu-item index="editUser">管理员编辑</el-menu-item>-->
          </el-submenu>
          <el-submenu index="category">
            <template slot="title">
              <i class="el-icon-notebook-1"></i>
              <span>图书分类管理</span>
            </template>
            <el-menu-item index="/categoryList">图书分类列表</el-menu-item>
            <el-menu-item index="/addCategory">图书分类添加</el-menu-item>
          </el-submenu>
          <el-submenu index="book">
            <template slot="title">
              <i class="el-icon-notebook-2"></i>
              <span>图书管理</span>
            </template>
            <el-menu-item index="/bookList">图书列表</el-menu-item>
            <el-menu-item index="/addBook">图书添加</el-menu-item>
          </el-submenu>
          <el-submenu index="borrow">
            <template slot="title">
              <i class="el-icon-document-copy"></i>
              <span>借书管理</span>
            </template>
            <el-menu-item index="/borrowList">借书列表</el-menu-item>
            <el-menu-item index="/addBorrow">借书添加</el-menu-item>
          </el-submenu>
          <el-submenu index="return">
            <template slot="title">
              <i class="el-icon-folder-checked"></i>
              <span>还书管理</span>
            </template>
            <el-menu-item index="/returnList">还书列表</el-menu-item>
          </el-submenu>
        </el-menu>
      </div>

      <!-- 主体数据 -->
      <!-- width:0; 可以限制容器的宽度，不被子元素无限撑开-->
<!--      <div style="background:url('https://tenapi.cn/acg') no-repeat; background-size: 100% 100%; background-attachment: fixed; width:0; flex: 1; padding: 10px;">-->
     <div style="background-color: white; width:0; flex: 1; padding: 10px;">
      <router-view />
      </div>
    </div>
  </div>
</template>

<script>
import Cookies from "js-cookie";
import request from "@/utils/request";
import axios from "axios";
export default {
  name: "Layout",
  data () {
    return {
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {}
    }
  },
  created() {
  },
  methods: {
    logout() {
      //清除浏览器用户数据
      Cookies.remove('admin')
      this.$router.push('/login')
    }
  }
}
</script>

<style>

</style>