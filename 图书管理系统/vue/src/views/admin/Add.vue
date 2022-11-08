<template>
<div>
  <h2 style="margin-bottom: 30px">新增管理员</h2>
<!--  status-icon属性为输入框添加了表示校验结果的反馈图标-->
  <el-form :inline="true" :model="form" status-icon :rules="rules" ref="form" style="width: 80%" label-width="100px">
    <el-form-item label="用户名" prop="username">
      <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
    </el-form-item>
    <el-form-item label="联系方式" prop="phone">
      <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
    </el-form-item>
    <el-form-item label="邮箱" prop="address">
    <el-input v-model="form.email" placeholder="请输入邮箱"></el-input>
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
  name: "AddAdmin",
  data() {
    //自定义校验
    const checkPhone = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('手机号不能为空'));
      }
      setTimeout(() => {
        if (!/^1[3,4,5,6,7,8,9][0-9]{9}$/.test(value)) {
          callback(new Error('请输入合法的手机号'));
        }
        else {
          callback();
        }
      }, 1000);
    };

    return {
      form: {},
      //表单校验
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min:2, max:10, message: '长度在3-10个字符', trigger: 'blur'}
        ],
        //自定义校验
        phone: [
          { validator: checkPhone, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    save() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          request.post("/admin/save",this.form).then(res => {
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
    }
  }
}
</script>

<style scoped>

</style>