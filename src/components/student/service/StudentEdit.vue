<template>
  <div id="bg">
    <el-container>
      <el-header>编辑个人信息</el-header>
      <el-main>
        <el-card class="card">
          <el-descriptions title="个人信息" direction="vertical" :column="4" border>
            <el-descriptions-item
              label="姓名">
              {{form.name}}
            </el-descriptions-item>
            <el-descriptions-item
              label="学号">
              {{form.number}}
            </el-descriptions-item>
            <el-descriptions-item
              label="身份证号">
              {{form.identity}}
            </el-descriptions-item>
            <el-descriptions-item
              label="学院">
              {{form.department.school.schoolName}}
            </el-descriptions-item>
            <el-descriptions-item
              label="专业">
              {{form.department.departmentName}}
            </el-descriptions-item>
            <el-descriptions-item
              label="手机号">
              {{form.mobile}}
            </el-descriptions-item>
            <el-descriptions-item
              label="邮箱">
              {{form.email}}
            </el-descriptions-item>
          </el-descriptions>
          <el-button @click="addDialogVisible = true" type="primary">修改信息</el-button>
          <el-button @click="changePasswordVisible = true" type="primary">修改密码</el-button>
        </el-card>
      </el-main>

      <!--修改信息-->
      <el-dialog title="修改个人信息" :visible.sync="addDialogVisible" style="line-height: 100px;">
        <el-form
          :model="edit"
          ref="edit"
          :rules="editRules">
          <el-form-item
            label="学号"
            prop="number"
            :label-width="formLabelWidth"
            disabled="true">
            <el-input :disabled="true" v-model="edit.number"></el-input>
          </el-form-item>
          <el-form-item
            label="姓名"
            prop="name"
            :label-width="formLabelWidth">
            <el-input :disabled="true" v-model="edit.name"></el-input>
          </el-form-item>
          <el-form-item
            label="身份证号"
            prop="identity"
            :label-width="formLabelWidth">
            <el-input :disabled="true" v-model="edit.identity"></el-input>
          </el-form-item>
          <el-form-item
            label="手机号"
            prop="mobile"
            :label-width="formLabelWidth">
            <el-input v-model="edit.mobile" placeholder="Mobile"></el-input>
          </el-form-item>
          <el-form-item
            label="邮箱"
            prop="email"
            :label-width="formLabelWidth">
            <el-input v-model="edit.email" placeholder="Email"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="addDialogVisible = false; ">取 消</el-button>
          <el-button type="primary" @click="onSubmit('edit')" >确 定</el-button>
        </div>
      </el-dialog>

      <!--修改密码-->
      <el-dialog :visible.sync="changePasswordVisible" class="dialog">
        <change-password-form></change-password-form>
      </el-dialog>
    </el-container>
  </div>
</template>

<script>
import ChangePasswordForm from "../../ChangePasswordForm";
export default {
  name: "StudentEdit",
  components: {ChangePasswordForm},
  data() {
    return {
      addDialogVisible: false,
      changePasswordVisible: false,
      formLabelWidth: '120px',
      form: {
        name: 'hhh',
        number: 'hhh',
        identity: 'hhh',
        department: {
          departmentName: '22',
          school: {
            schoolName: 'school',
            schoolId: '2'
          }
        },
        "mobile": 'hhh',
        "email": 'hhh',
      },
      edit: {
        email: '',
        mobile: '',
      },
      editRules: {
        email: [
          {
            required: false,
            pattern: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
            message: '邮箱格式不正确',
            trigger: 'blur',
          }
        ],
        mobile: [
          {
            required: false,
            len: 11,
            pattern: /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/,
            message: '格式有误',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  mounted: function () {
    this.loadUser()
  },
  methods: {
    changePassword() {
      this.$router.push({path: '/student-home/change-password'})
    },
    async loadUser() {
      let jsonObj = JSON.parse(window.localStorage.user);
      let no = jsonObj.user.number
      let params = new URLSearchParams();
      let _this = this
      params.append('userNumber', no);
      this.$axios
        .post('/getUser', params)
        .then(resp => {
          if (resp && resp.data.code === 200) {
            console.log(_this.userName)
            _this.form = resp.data.data
            this.edit = resp.data.data
          } else {
            this.$message({
              message: 'Error happens',
              type: 'error'
            });
          }
        })
    },
    onSubmit(form) {
      console.log("onSubmit called")
      this.$refs[form].validate((valid) => {
        if (valid) {
          //  valid
          console.log("is valid")
          this.$axios
            .post('/editUser', {
              number: this.form.number,
              mobile: this.edit.mobile,
              email: this.edit.email
            })
            .then(resp => {
              if (resp) {
                if (resp.data.code === 200) {
                  this.dialogFormVisible = false
                  this.$emit('onSubmit')
                  this.$alert('修改成功', '结果', {
                    confirmButtonText: '确定'
                  });
                } else if (resp.data.code === 100) {
                  this.$alert(resp.data.msg, '结果', {
                    confirmButtonText: '确定'
                  });
                } else {
                  this.$alert('Error', { confirmButtonText: 'OK'} )
                }
              }
              this.loadUser();
            })
        } else {
          console.log("not valid")
          this.$alert('请按提示填写正确内容', 'Warning', {
            confirmButtonText: '确定',
          });
          return false;
        }
      })
    },
  }
}
</script>

<style scoped>
#bg {
  background-color: ghostwhite;
  height: 100%;
}
.el-header {
  background-color: #8F8F99;
  border-bottom: #1f1e33;
  color: #ffffff;
  text-align: center;
  font-size: 20px;
  line-height: 60px;
}
.el-main {
  background-color: ghostwhite;
  color: #333;
  text-align: center;
  line-height: 60px;
}
.card {
  width: 1000px;
  margin: auto;
}

.dialog {
  width: 60%;
  height: 80%;
  margin: 0 auto;
}


</style>
