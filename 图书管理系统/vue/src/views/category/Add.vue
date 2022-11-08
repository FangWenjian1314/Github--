<template>
<div>
  <h2 style="margin-bottom: 30px">新增分类</h2>
<!--  status-icon属性为输入框添加了表示校验结果的反馈图标-->
  <el-form :inline="true" :model="form" status-icon :rules="rules" ref="form" style="width: 80%" label-width="100px">
    <el-form-item label="名称" prop="name">
      <el-input v-model="form.name" placeholder="请输入名称"></el-input>
    </el-form-item>
    <el-form-item label="备注" prop="remark">
      <el-input v-model="form.remark" placeholder="请输入备注"></el-input>
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
  name: "AddCategory",
  data() {
    return {
      form: {},
      //表单校验
      rules: {
        name: [
          {required: true, message: '请输入分类名称', trigger: 'blur'},
        ],
      }
    }
  },
  methods: {
    save() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          request.post("/category/save",this.form).then(res => {
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