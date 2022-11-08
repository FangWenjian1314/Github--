<template>
<div>
  <h2 style="margin-bottom: 30px">新增借书记录</h2>
<!--  status-icon属性为输入框添加了表示校验结果的反馈图标-->
  <el-form :inline="true" :model="form" status-icon :rules="rules" ref="form" style="width: 80%" label-width="100px">
    <el-form-item label="图书标准码" prop="bookNo">
      <el-select v-model="form.bookNo" clearable filterable placeholder="请选择" @change="getBookNo">
        <el-option
          v-for="item in books"
          :key="item.id"
          :label="item.bookNo"
          :value="item.bookNo">
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="图书名称" prop="bookName">
      <el-input v-model="form.bookName" disabled></el-input>
    </el-form-item>
    <el-form-item label="所需积分" prop="score">
      <el-input v-model="form.score" disabled></el-input>
    </el-form-item>
    <el-form-item label="图书数量" prop="nums">
      <el-input-number v-model="form.nums" label="图书数量" disabled></el-input-number>
    </el-form-item>
    <br/>
    <el-form-item label="用户会员码" prop="userNo">
      <el-select v-model="form.userNo" filterable @change="getUserId">
        <el-option
            v-for="item in users"
            :key="item.id"
            :label="item.username"
            :value="item.username">
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="用户名称" prop="userName">
      <el-input v-model="form.userName" disabled></el-input>
    </el-form-item>
    <el-form-item label="用户联系方式" prop="userPhone">
      <el-input v-model="form.userPhone" disabled></el-input>
    </el-form-item>
    <el-form-item label="用户账户积分" prop="account">
      <el-input v-model="form.account" disabled></el-input>
    </el-form-item>
    <el-form-item label="借出天数" prop="days">
      <el-input-number v-model="form.days" :min="1" :max="30" label="借出天数"></el-input-number>
    </el-form-item>
  </el-form>

  <div style="text-align: center; margin-top: 30px;">
    <el-button type="primary" @click="save()" size="medium">提交</el-button>
<!--    <el-button type="danger">返回列表</el-button>-->
  </div>
</div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "AddBook",
  data() {
    return {
      form: {
        days: 1
      },
      //表单校验
      books: [],
      users: [],
      rules: {
        bookNo: [
          {required: true, message: '请输入图书标准码', trigger: 'blur'},
        ],
        userNo: [
          {required: true, message: '请输入会员码', trigger: 'blur'},
        ],
      }
    }
  },
  created() {
    request.get('/book/list').then( res => {
      this.books = res.data
    })

    request.get('/user/list').then( res => {
      this.users = res.data.filter(v => v.status)
    })
  },
  methods: {
    save() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          request.post("/borrow/save",this.form).then(res => {
            if(res.code === '200') {
              this.$notify.success('新增成功')
              //第一种：对整个表单进行重置，将所有字段值重置为初始值并移除校验结果  /推荐使用该方法！！！
              this.$refs['form'].resetFields()

              //第二种：初始化form数据
              this.form = {}
            }else {
              this.$notify.error(res.msg)
            }
          })
        }
      })
    },
    getBookNo() {
      const book = this.books.find(v => v.bookNo === this.form.bookNo)
      request.get("/book/" + book.id).then(res => {
        // this.$set 强制设置对象属性
        this.$set(this.form,'bookName',res.data.name)
        this.form.score = res.data.score
        this.form.nums = res.data.nums
      })
    },
    getUserId() {
      const user = this.users.find(v => v.username === this.form.userNo)
      request.get("/user/" + user.id).then(res => {
        this.$set(this.form,'userName',res.data.name)
        this.form.userPhone = res.data.phone
        this.form.account = res.data.account
      })
    }
  }
}
</script>

<style scoped>

</style>