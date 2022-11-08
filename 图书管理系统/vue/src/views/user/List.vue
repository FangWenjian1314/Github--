<template>
 <div>
   <!-- 搜索表单 -->
   <div style="margin-bottom: 20px;">
     <el-input style="width: 240px; " placeholder="请输入姓名" v-model="params.name"></el-input>
     <el-input style="width: 240px; margin-left: 5px;" placeholder="请输入联系方式" v-model="params.phone"></el-input>
     <el-button style="margin-left: 5px;" type="primary" @click="load"><i class="el-icon-search"></i>搜索</el-button>
     <el-button style="margin-left: 5px;" type="warning" @click="reset"><i class="el-icon-refresh"></i>重置</el-button>
     <el-button style="float: right" type="success" @click="addUserV=true"><i class="el-icon-refresh"></i>会员添加</el-button>
   </div>
   <el-table :data="tableData" stripe>
     <el-table-column prop="id" label="编号"></el-table-column>
     <el-table-column prop="username" label="会员号"></el-table-column>
     <el-table-column prop="name" label="姓名"></el-table-column>
     <el-table-column prop="age" label="年龄"></el-table-column>
     <el-table-column prop="address" label="地址"></el-table-column>
     <el-table-column prop="phone" label="联系方式"></el-table-column>
     <el-table-column prop="sex" label="性别"></el-table-column>
     <el-table-column prop="account" label="账户积分"></el-table-column>
     <el-table-column label="状态">
       <template v-slot="scope">
         <el-switch
             v-model="scope.row.status"
             @change="changeStatus(scope.row)"
             active-color="#13ce66"
             inactive-color="#ff4949">
         </el-switch>
       </template>
     </el-table-column>
     <el-table-column prop="createtime" label="创建时间"></el-table-column>
     <el-table-column prop="updatetime" label="更新时间"></el-table-column>

     <el-table-column label="操作" width="220">
       <template v-slot="scope">
<!--         scope.row 就是当前行数据-->
         <el-button type="warning" @click="handleAccountAdd(scope.row)">充值</el-button>
<!--         <el-button type="primary" @click="$router.push('/editUser?id=' + scope.row.id)">编辑</el-button>-->
         <el-button type="primary" @click="editUserScope(scope.row)">编辑</el-button>
         <el-popconfirm
             style="margin-left: 5px"
             title="您确定删除这条数据吗？"
             @confirm="del(scope.row.id)"
         >
           <el-button slot="reference" type="danger">删除</el-button>
         </el-popconfirm>
       </template>
     </el-table-column>
   </el-table>

   <!-- 分页 -->
   <div style="margin-top: 20px;">
     <el-pagination
         background
         :current-page="params.pageNum"
         :page-size="params.pageSize"
         layout="prev, pager, next"
         @current-change="handleCurrentChange"
         :total="total">
     </el-pagination>
   </div>

   <el-dialog title="充值" :visible.sync="dialogFormVisible" width="30%">
     <el-form :model="form" label-width="100px" ref="form" :rules="rules" style="width: 85%">
       <el-form-item label="当前账户积分" prop="account">
         <el-input v-model="form.account" disabled></el-input>
       </el-form-item>
       <el-form-item label="积分" prop="score">
         <el-input v-model="form.score"></el-input>
       </el-form-item>
     </el-form>
     <div slot="footer" class="dialog-footer">
       <el-button @click="dialogFormVisible = false">取 消</el-button>
       <el-button type="primary" @click="addAccount">确 定</el-button>
     </div>
   </el-dialog>

   <el-dialog title="新增用户" :visible.sync="addUserV" width="30%">
     <div>
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

     </div>
     <div slot="footer" class="dialog-footer">
       <el-button @click="cancle">取 消</el-button>
       <el-button type="primary" @click="save">提交</el-button>
     </div>
   </el-dialog>
   <el-dialog title="编辑用户" :visible.sync="editUserV" width="30%">
     <el-form :inline="true" :model="form" style="width: 80%" label-width="100px">
       <el-form-item label="会员号">
         <el-input disabled v-model="form.username"></el-input>
       </el-form-item>
       <el-form-item label="姓名">
         <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
       </el-form-item>
       <!--    因为后端已经写了用户名当作卡号来处理，自动生成唯一的编号。不用手动写-->
       <!--    <el-form-item label="用户名">-->
       <!--      <el-input v-model="form.username" placeholder="请输入用户名"></el-input>-->
       <!--    </el-form-item>-->
       <el-form-item label="年龄">
         <el-input v-model="form.age" placeholder="请输入年龄"></el-input>
       </el-form-item>
       <el-form-item label="性别">
         <el-select v-model="form.sex" placeholder="请选择性别">
           <el-option v-for="item in options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.label">
           </el-option>
         </el-select>
       </el-form-item>
       <el-form-item label="联系方式">
         <el-input v-model="form.phone" placeholder="请输入联系方式"></el-input>
       </el-form-item>
       <el-form-item label="地址">
         <el-input v-model="form.address" placeholder="请输入地址"></el-input>
       </el-form-item>
     </el-form>
     <div slot="footer" class="dialog-footer">
       <el-button @click="editUserV = false">取 消</el-button>
       <el-button type="primary" @click="saveUpdate">提交</el-button>
     </div>
   </el-dialog>
 </div>
</template>

<script>

import request from "@/utils/request";

export default {
  name: 'User',
  data() {
    const checkNums = (rule, value, callback) => {
      value = parseInt(value)
      if (value < 10 || value > 200) {
        callback(new Error("请输入大于等于10小于等于200的整数"))
      }
      callback()
    };

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
      addUserV: false,
      editUserV: false,
      tableData: [],
      dialogFormVisible: false,
      total:0,
      form: {},
      params: {
        pageNum: 1, //当前页
        pageSize: 10, //每页的数量
        name: '',
        phone: ''
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
          {required: true, validator: checkAge, trigger: 'blur' }
        ],
        phone: [
          {required: true, validator: checkPhone, trigger: 'blur' }
        ],
        score: [
          {required: true, message: '请输入充值积分', trigger: 'blur'},
          {validator:checkNums, trigger: 'blur'},
        ],
      }
    }
  },
  /**
   * created：html加载完成之前，执行。执行顺序：父组件-子组件
   * mounted：html加载完成后执行。执行顺序：子组件-父组件
   * methods：事件方法执行
   * watch：watch是去监听一个值的变化，然后执行相对应的函数。
   * computed：computed是计算属性，也就是依赖其它的属性计算所得出最后的值
   */
  created() {
    this.load()
  },
  methods: {
    //获取所有数据
    load() {
      //不用ajax的时候，用这个方法
      // fetch('http://localhost:8080/user/list').then(res => res.json()).then(res => {
      //   console.log(res) //console.log方法用于在控制台输出信息，方便查看调试
      //   this.tableData = res
      // })

      //用ajax的时候用这个方法,嫌麻烦的话也可以自己写api接口封装
      request.get('/user/page', {
        params: this.params
      }).then(res => {
        // res这个参数可以理解为一个Object。
        // res.data指服务器返回的内容。
        // res参数还有其他的属性，比如statusCode（指开发者服务器返回的 HTTP 状态码），还有header（指开发者服务器返回的 HTTP Response Header）
        if(res.code === '200') {
          this.tableData = res.data.list
          this.total = res.data.total
        }
      })
    },

    //重置数据
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 10,
      }
      //重置的时候执行获取所有数据
      this.load()
    },

    cancle() {
      this.$refs['form'].resetFields()
      this.addUserV = false
    },

    editUserScope(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.editUserV = true
    },
    saveUpdate() {
      request.put("/user/update",this.form).then(res => {
        if(res.code === '200') {
          this.$notify.success('更新成功')
          this.load()
          this.editUserV = false
        }else {
          this.$notify.error(res.msg)
        }
      })
    },
    //点击分页触发事件
    handleCurrentChange(pageNum) {
      //点击分页按钮触发分页
      this.params.pageNum = pageNum
      console.log(pageNum)
      this.load()
    },

    handleAccountAdd(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
    },

    addAccount() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          request.post('/user/account', this.form).then(res => {
            if(res.code === '200') {
              this.$notify.success("充值成功")
              this.load()
              this.dialogFormVisible = false
            }else {
              this.$notify.error(res.msg)
            }
          })
        }
      })
    },

    save() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.form.account = 0
          request.post("/user/save",this.form).then(res => {
            if(res.code === '200') {
              this.$notify.success('新增成功')
              this.addUserV = false
              //第一种：对整个表单进行重置，将所有字段值重置为初始值并移除校验结果  /推荐使用该方法！！！
              this.$refs['form'].resetFields()
              this.load()
              this.innerVisible = false
              this.outerVisible = false
              //第二种：初始化form数据
              // this.form = {}
            }else {
              this.$notify.error(res.msg)
            }
          })
        }
      })
    },

    //根据id删除数据
    del(id) {
      request.delete("/user/delete/" + id).then(res => {
        if(res.code === '200') {
         this.$notify.success("删除成功")
          this.load()
        }else {
          this.$notify.error(res.msg)
        }
      })
    },
    changeStatus(row) {
      request.put("/user/update",row).then(res => {
        if(res.code === '200') {
          this.$notify.success('操作成功')
          this.load()
        }else {
          this.$notify.error(res.msg)
        }
      })
    },
  }
}
</script>

<style scoped>

</style>