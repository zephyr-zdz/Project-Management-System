<template>
  <body>
  <el-form class="container"
           label-position="left"
           label-width="0px"
           :model="form"
           ref="form"
           :rules="passwordRules"
  >
    <h3 class="header">请修改密码</h3>
    <el-form-item
      prop="password1"
    >
      <el-input
        type="password"
        v-model="form.password1"
        auto-complete="off"
        placeholder="密码">
      </el-input>
    </el-form-item>
    <el-form-item
      prop="password2"
    >
      <el-input
        type="password"
        v-model="form.password2"
        auto-complete="off"
        placeholder="确认密码">
      </el-input>
    </el-form-item>
      <el-button type="primary" style="width: 100%;background: #323144;border: none"
                 @click="onSubmit('form')">
        确认
      </el-button>
  </el-form>
  </body>
</template>

<script>
import Vue from "vue";

export default {
  name: "ChangePassword",
  data() {
    var passwordRules = (rule, value, callback) => {
      if(value){
       let password1 = this.form.password1
        if (password1 !== value) {
          callback("两次输入不一致");
        } else {
          callback();
        }
      }
    };
    return {
      form: {
        password1: '',
        password2: ''
      },
      passwordRules: {
        password1: [
          {
            required: true,
            message: '请输入密码',
            trigger: "blur",
          },
          {
            min: "6",
            max: "32",
            pattern:
              /(?:\d.*_|-)|(?:_|-.*\d)|(?:[A-Za-z].*_|-)|(?:_|-.*[A-Za-z])|(?:[A-Za-z].*\d)|(?:\d.*[A-Za-z])/,
            message: "密码长度应在6-32位，并包含字母、数字、特殊字符中至少两种",
            trigger: "blur",
          },
        ],
          password2: [
            {
              required: true,
              message: "请确认密码",
              trigger: "blur"
            },
            {
              validator: passwordRules,
              message: '两次密码不一致',
              trigger: "blur" ,
            }
        ],
      },
    }
  },
  methods: {
    onSubmit(formName) {
      console.log("onSubmit called")
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let jsonObj = JSON.parse(window.localStorage.user);
          let no = jsonObj.user.number
          let params = new URLSearchParams();
          params.append('userNumber', no);
          params.append('newPassword', this.form.password1);
          this.$axios
            .post('/reset_password', params)
            .then(resp => {
              if (resp && resp.data.code === 200) {
                Vue.prototype.$message({
                  message: resp.data.msg,
                  type: 'success'
                });
                var path = this.$route.query.redirect
                this.$router.push({path: path === '/' || path === undefined ? 'dashboard' : path}) // need to be altered
              } else {
                Vue.prototype.$message({
                  message: 'Error happens',
                  type: 'error'
                });
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
body{
  margin: 0;
}
.container {
  border-radius: 3px;
  margin: 90px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: rgba(87, 87, 102, 0.95);
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #150404;
}
.header {
  margin: 0 auto 40px auto;
  text-align: center;
  color: #dbe7ea;
}


</style>
