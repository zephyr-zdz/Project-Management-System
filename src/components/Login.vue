<!--2022/11/3 modified by JZT-->
<template>
  <div id="bg">
    <el-card class="card">
      <div slot="header">
        <span class="chr">登录</span>
      </div>
      <el-form
        :model="loginForm"
        ref="loginFormRef"
        :rules="loginFormRules">
        <el-form-item class="u-form-group" prop="email">
          <el-input
            type="text"
            title="email"
            v-model="loginForm.email"
            placeholder="Email/Name"/>
        </el-form-item>
        <el-form-item class="u-form-group" prop="password">
          <el-input
            type="password"
            v-model="loginForm.password"
            placeholder="Password"/>
        </el-form-item>
        <div class="u-form-group">
          <el-button type="primary" @click.prevent="login">登录</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data () {
    return {
      loginForm: {
        email: '',
        password: '',
      },
      flag:false,
      loginFormRules: {
        email: [
          {
            required: true,
            message: '请填入邮箱或用户名',
            trigger: 'blur',
          }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
        },
      }
  },
  methods: {
    login () {
      console.log("Try to login as user...")
      console.log(this.$store.state)
      const param = new FormData();
      param.append('username', this.loginForm.email)
      param.append('password', this.loginForm.password)
      this.$axios
        .post('/user/login', param)
        .then(resp => {
          if (resp.data.code === 0 || resp.data.code === 1) {
            console.log(resp.data.code)
            if (resp.data.code === 0) {
              console.log(resp.data.data.id)
              this.$store.commit('login', {
                user: {
                  userid: resp.data.data.id,
                  name: resp.data.data.username,
                  email: resp.data.data.email,
                  role: 'user',
                }
              })
              this.$message({
                message: resp.data.msg,
                type: 'success'
              })
              this.$router.replace({path: '/user-home/dashboard'})
            } else {
              this.$message({
                message: resp.data.msg,
                type: 'error'
              })
              }
          } else {
            this.msg = resp.data.msg
          }
        }
        )
        .catch(failResponse => {
          console.log("Error happens")
        })
    },
  }
}
</script>

<style scoped>
.card {
  width: 400px;
  margin: 20% auto auto;
}
.chr {
  font-weight: bold;
  font-size: large;
}
</style>
