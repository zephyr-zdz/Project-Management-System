<template>
  <div>
  <el-button type="text" @click="dialogFormVisible = true">添加用户</el-button>
  <el-dialog
    title="添加用户"
    :visible.sync="dialogFormVisible"
  >
    <el-form
      :model="form"
      ref="form"
      :rules="formRules"
    >
      <el-form-item label="用户身份" :label-width="formLabelWidth">
        <el-radio v-model="form.type" label="1">学生</el-radio>
        <el-radio v-model="form.type" label="2">教师</el-radio>
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
        <el-input v-model="form.number" placeholder="No."></el-input>
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
      <el-button @click="onSubmit('form')">确 定</el-button>
    </div>
  </el-dialog>
  </div>
</template>

<script>
let allDepartments = [
  {
    departmentNumber: 1,
    school: {
      schoolId: 3,
      schoolName: "前端-医学院"
    },
    departmentName: '临床'
  },
  {
    departmentNumber: 2,
    school: {
      schoolId: 1,
      schoolName: "前端-环境学院"
    },
    departmentName: '环境工程'
  }
]
export default {
  name: "AddUserForm",
  data () {
    let allSchools = [
      { schoolId: 3, schoolName: '前端-医学院'},
      { schoolId: 2, schoolName: '前端-物理学院'},
      { schoolId: 1, schoolName: '前端-环境学院'}
    ]

    let numberRules = (rule, value, callback) => {
      if(value){
        if (this.form.type === '1') {
          var reg = new RegExp("^\\d{6}$");
          if (!reg.test(value)) {
            callback("格式有误");
          } else {
            callback()
          }
        } else {
          var reg = new RegExp("^\\d{8}$");
          if (!reg.test(value)) {
            callback("格式有误");
          } else {
            callback()
          }
        }
      }
    }
    return {
      dialogTableVisible: false,
      dialogFormVisible: false,
      form: {
        type: '1',
        schools: allSchools,
        schoolId: '',
        departments: allDepartments,
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

        ],
        number: [
          { required: true, message: '必填', trigger: 'blur'},
          {
            validator: numberRules,
            message: '学号为6位，工号为8位',
            trigger: "blur" ,
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
  mounted: function () {
    this.loadSchoolAndDepartment()
  },
  methods: {
    async loadSchoolAndDepartment() {
      this.$axios.get('/schools').then(resp => {
        if (resp && resp.status === 200) {
          this.form.schools = resp.data
        } else {
          this.$message({
            type: 'error',
            message:'Fail to fetch department data'
          })
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
    getSchool (school) {
      console.log(school)
      let d = []
      this.form.departmentNumber = ''
      let url = '/schools/' + school + '/departments'
      this.$axios.get(url).then(resp => {
        if (resp && resp.status === 200) {
          this.form.departments = resp.data
        } else {
          for (let val of allDepartments) {
            if (school === val.school.schoolId) {
              console.log(val)
              d.push({departmentNumber: val.departmentNumber,departmentName: val.departmentName})
            }
            this.form.departments = d
          }
        }
      })
    },
    onSubmit(formName) {
      console.log("onSubmit called")
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //  valid
          console.log("is valid")
          this.$axios
            .post('/users', {
              type: this.form.type,
              department: {
                departmentNumber: this.form.departmentNumber
              },
              name: this.form.name,
              number: this.form.number,
              identity: this.form.identity,
              mobile: this.form.mobile,
              email: this.form.email,
              password: '123456'
            })
            .then(resp => {
              if (resp) {
                if (resp.data.code === 200) {
                  this.dialogFormVisible = false
                  this.$emit('onSubmit')
                  this.$alert('录入成功', '结果', {
                    confirmButtonText: '确定'
                  });
                  // this.$message({
                  //   message: '录入成功',
                  //   type: 'success'
                  // });
                } else if (resp.data.code === 100) {
                  this.$alert('用户已存在，录入失败', '结果', {
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
