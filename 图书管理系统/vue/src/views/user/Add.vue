<template>
<div>
  <h2 style="margin-bottom: 30px">新增用户</h2>
<!--  status-icon属性为输入框添加了表示校验结果的反馈图标-->
  <el-form :inline="true" :model="form" status-icon :rules="rules" ref="form" style="width: 80%" label-width="100px">
    <el-form-item label="姓名" prop="name">
      <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
    </el-form-item>
<!--    因为后端已经写了用户名当作卡号来处理，自动生成唯一的编号。不需要手动写-->
<!--    <el-form-item label="用户名">-->
<!--      <el-input v-model="form.username" placeholder="请输入用户名"></el-input>-->
<!--    </el-form-item>-->
    <el-form-item label="年龄" prop="age">
      <el-input v-model="form.age" placeholder="请输入年龄"></el-input>
    </el-form-item>
    <el-form-item label="性别" prop="sex">
<!--      input输入框-->
<!--      <el-input v-model="form.sex" placeholder="请输入性别"></el-input>-->

<!--      radio单选框-->
<!--      <el-radio v-model="form.sex" label="男">男</el-radio>-->
<!--      <el-radio v-model="form.sex" label="女">女</el-radio>-->

<!--      select选择器-->
      <el-select v-model="form.sex" placeholder="请选择性别">
        <el-option v-for="item in options"
                   :key="item.value"
                   :label="item.label"
                   :value="item.label">
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="联系方式" prop="phone">
      <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
    </el-form-item>
    <el-form-item label="地址" prop="address">
    <el-input v-model="form.address" placeholder="请输入地址"></el-input>
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
  name: "AddUser",
  data() {
    //自定义校验
    const checkAge = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('年龄不能为空'));
      }
      setTimeout(() => {
        if(parseInt(value) > 120 || parseInt(value)<0) {
          callback(new Error('请输入合理的年龄'));
        }
        if (!/^[0-9]+$/.test(value)) {
          callback(new Error('请输入数字值'));
        }
        else {
          callback();
        }
      }, 1000);
    };
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
      form: {
        // sex: '男'  //单选框默认选择男
      },
      // select选择器
      options:[
        {
          value: '选项1',
          label: '男'
        },
        {
          value: '选项2',
          label: '女'
        }
      ],
      //表单校验
      rules: {
        name: [
          {required: true, message: '请输入姓名', trigger: 'blur'}
        ],
        //自定义校验
        age: [
          {validator: checkAge, trigger: 'blur' }
        ],
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
          request.post("/user/save",this.form).then(res => {
            if(res.code === '200') {
              this.$notify.success('新增成功')
              //第一种：对整个表单进行重置，将所有字段值重置为初始值并移除校验结果  /推荐使用该方法！！！
              this.$refs['form'].resetFields()

              //第二种：初始化form数据
              // this.form = {}
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