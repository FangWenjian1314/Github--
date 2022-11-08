<template>
    <div  @keyup.enter="login" id="wrapper">
      <div style="background-color:white; width: 500px; height: 400px; margin: 150px auto; border-radius: 10px; padding: 50px">
        <div style="margin: 30px; text-align: center; font-size: 30px; font-weight: bold; color: dodgerblue">
          登录
        </div>
        <el-form :model="admin" ref="loginForm" :rules="rules">
          <el-form-item prop="username">
            <el-input placeholder="请输入账号" prefix-icon="el-icon-user" size="medium" v-model="admin.username"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input placeholder="请输入密码" show-password prefix-icon="el-icon-lock" size="medium" v-model="admin.password"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button style="width: 100%" size="medium" type="primary" @click="login">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
      <el-card class="cover" v-if="loginAdmin.id">
        <slide-verify :l="42"
                      :r="10"
                      :w="310"
                      :h="155"
                      slider-text="向右滑动"
                      @success="onSuccess"
                      @fail="onFail"
                      @refresh="onRefresh"
        ></slide-verify>
      </el-card>
    </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie"

export default {
  name: "Login",
  data() {
    return {
      loginAdmin: {},
      admin: {},
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min:2, max:10, message: '长度在3-10个字符', trigger: 'blur'}
        ],
        //自定义校验
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min:2, max:10, message: '长度在3-10个字符', trigger: 'blur'}
        ],
      }

    }
  },
  methods: {
    login() {
      this.$refs['loginForm'].validate((valid) => {
        if (valid) {
          request.post('/admin/login',this.admin).then(res => {
            if(res.code === '200') {
              this.loginAdmin = res.data  //滑块组件就出现了
            } else {
              this.$notify.error(res.msg)
            }
          })
        }
      })
    },
    onSuccess() { //滑块验证通过之后触发的
      //先存数据，然后路由守卫判断再跳转到相关页面
      // if ( res.data !== null) {
      //   Cookies.set('admin', JSON.stringify(res.data))
      // }
      Cookies.set('admin', JSON.stringify(this.loginAdmin))
      this.$notify.success("登录成功")
      setTimeout(() => {
        this.$router.push('/')
      },1000)
    },
    onFail() {
    },
    onRefresh() {

    }
  }
}
</script>

<style>
.cover {
  width: fit-content;
  background-color: white;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 1000;
}

#wrapper {
  overflow: auto;
  padding-bottom: 10px;
  background: url('https://tenapi.cn/acg') no-repeat fixed;
  background-size: 100% 100%;
}
</style>