<!--2022/11/3 modified by JZT-->
<template>
  <div class="login-box">
    <div class="lb-header">
      <a href="javascript:void(0)"  id="login-box" >登陆</a>
    </div>
    <el-form
      :model="loginForm"
      ref="loginFormRef"
      :rules="loginFormRules"
    >
      <el-form-item class="u-form-group" prop="email">
        <el-input
          type="text"
          title="email"
          v-model="loginForm.email"
          placeholder="Email"
        />
      </el-form-item>
      <el-form-item class="u-form-group" prop="password">
        <el-input
          type="password"
          v-model="loginForm.password"
          placeholder="Password"
        />
      </el-form-item>
      <div class="u-form-group">
        <button type="submit" @click.prevent="login">登录</button>
        <p class="login-error">{{ msg }}</p>
      </div>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data () {
    return {
      loginForm: {
        email: '',
        numError: '',
        password: '',
      },
      msg: '',
      flag:false,
      loginFormRules: {
        email: [
          {
            required: true,
            pattern: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
            message: '邮箱格式不正确',
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
      const _this = this;
      console.log(this.$store.state)
      this.$axios
        .post('/login', {
          email: this.loginForm.email,
          password: this.loginForm.password
        })
        .then(resp => {
          if (resp.data.code === 0 || resp.data.code === 1) {
            console.log(resp.data.code)
            _this.$store.commit('login', _this.loginForm)
            const path = this.$route.query.redirect;
            console.log(resp.data.data.type)
            this.msg = resp.data.msg
            _this.$store.commit('login', {
              user: {
                email: this.loginForm.email,
                }
              })
            if (resp.data.code === 0) {
              this.$router.replace({path: path === '/' || path === undefined ? '/user-home/dashboard' : path})
            } else {
              this.$message.error(this.msg)
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
  @import '../assets/styles/login.css';
</style>
