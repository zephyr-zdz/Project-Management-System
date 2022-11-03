<template>
  <div>
<!--  <el-button type="text" @click="dialogFormVisible = true" style="font-size: 12px;">编辑</el-button>-->
  <el-dialog title="编辑" :visible.sync="dialogFormVisible" append-to-body>
    <el-form
      :model="form"
      ref="form"
      :rules="formRules"
    >
      <el-form-item label="用户身份" prop="type" :label-width="formLabelWidth">
        <el-radio :disabled="true" v-model="form.type" label="1">学生</el-radio>
        <el-radio :disabled="true" v-model="form.type" label="2">教师</el-radio>
      </el-form-item>
      <el-form-item label="专业"
                    prop="departmentNumber"
                    :label-width="formLabelWidth">
        <el-select v-model="form.schoolId"
                   @change="getSchool($event)"
                   placeholder="学院">
          <el-option
            v-for="item in form.schools"
            :key="item.schoolId"
            :label="item.schoolName"
            :value="item.schoolId">
          </el-option>
        </el-select>
        <el-select v-model="form.departmentNumber"

                   placeholder="专业">
          <el-option
            v-for="item in form.departments"

            :key="item.departmentNumber"
            :label="item.departmentName"
            :value="item.departmentNumber">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item
        label="学号/工号"
        prop="number"
        :label-width="formLabelWidth"
      >
        <el-input :disabled="true" v-model="form.number" placeholder="No."></el-input>
      </el-form-item>
      <el-form-item
        label="姓名"
        prop="name"
        :label-width="formLabelWidth" >
        <el-input v-model="form.name" placeholder="Name"></el-input>
      </el-form-item>
      <el-form-item
        label="身份证号"
        prop="identity"
        :label-width="formLabelWidth"
      >
        <el-input v-model="form.identity" placeholder="ID"></el-input>
      </el-form-item>
      <el-form-item
        label="手机号"
        prop="mobile"
        :label-width="formLabelWidth"
      >
        <el-input v-model="form.mobile" placeholder="Mobile"></el-input>
      </el-form-item>
      <el-form-item
        label="邮箱"
        prop="email"
        :label-width="formLabelWidth"
      >
        <el-input v-model="form.email" placeholder="xxx@xx.xx"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false; ">取 消</el-button>
      <el-button type="primary" @click="onSubmit('form')" >确 定</el-button>
    </div>
  </el-dialog>
  </div>
</template>

<script>

export default {
  name: "EditUserForm",
  data () {
    return {
      dialogTableVisible: false,
      dialogFormVisible: false,
      form: {
        // unused
        type: '1',
        // schools: allSchools,
        schoolId: '',
        // departments: allDepartments,
        departmentNumber: '',
        name: '',
        number: '',
        identity: '',
        mobile: '',
        email: ''
      },
      formRules: {
        departmentNumber: [
          {required: true, message: '必填', trigger: ['blur', 'change']}
        ],
        name: [
          {required: true, message: '必填', trigger: 'blur'},
          {
            min: 2,
            max: 10,
            pattern: /^[\u4e00-\u9fa5a-zA-Z]*$/,
            message: '仅能输入中英文字符',
            trigger: 'blur'
          }
        ],
        identity: [
          { required: true, message: '必填', trigger: 'blur'},
          {
            pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/,
            message: '身份证号格式有误',
            trigger: 'blur',
          },
        ],
        mobile: [
          {
            required: false,
            len: 11,
            pattern: /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/,
            message: '格式有误',
            trigger: 'blur'
          }
        ],
        email: [
          {
            type: 'string',
            required: false,
            pattern: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
            message: '邮箱格式不正确',
            trigger: 'blur',
          },
        ]
      },
      formLabelWidth: '120px'
    }
  },
  methods: {
    getSchool (school) {
      console.log(school)
      let d = []
      this.form.departmentNumber = ''
      let url = '/schools/' + school + '/departments'
      this.$axios.get(url).then(resp => {
        if (resp && resp.status === 200) {
          console.log("flag")
          this.form.departments = resp.data
        } else {
          console.log("error")
          this.$message.error("Error")
          // for (let val of allDepartments) {
          //   if (school === val.school.schoolId) {
          //     console.log(val)
          //     d.push({departmentNumber: val.departmentNumber,departmentName: val.departmentName})
          //   }
          //   this.form.departments = d
          // }
        }
      })
    },
    clear() { // reset form
      this.form = {
        type: '1',
        name: '',
        number: '',
        identity: '',
        mobile: '',
        email: ''
      }
    },
    onSubmit(formName) {
      console.log("onSubmit called")
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //  valid
          console.log("is valid")
          this.$axios
            .post('/editUser', {
              department: {
                departmentNumber: this.form.departmentNumber
              },
              name: this.form.name,
              number: this.form.number,
              identity: this.form.identity,
              mobile: this.form.mobile,
              email: this.form.email
            })
            .then(resp => {
              if (resp) {
                if (resp.data.code === 200) {
                  this.dialogFormVisible = false
                  this.$emit('onSubmit')
                  this.$alert('修改成功', '结果', {
                    confirmButtonText: '确定'
                  });
                  // this.$message({
                  //   message: '录入成功',
                  //   type: 'success'
                  // });
                } else if (resp.data.code === 100) {
                  this.$alert(resp.data.msg, '结果', {
                    confirmButtonText: '确定'
                  });
                } else {
                  this.$alert('Error', { confirmButtonText: 'OK'} )
                }
              }
            })
        } else {
          console.log("not valid")
          this.$alert('请按提示填写正确内容', 'Warning', {
            confirmButtonText: '确定',
          });
          return false;
        }

      })
    }
  }
}

</script>

<style scoped>

</style>
