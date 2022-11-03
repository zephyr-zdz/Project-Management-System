<template>
  <div id="bg">
    <el-card class="card">
      <div slot="header">
        <span class="chr">注册账号</span>
      </div>
      <el-form
        :model="form"
        ref="form"
        :rules="formRules"
      >
        <el-form-item
          label="昵称"
          prop="name">
          <el-input v-model="form.name" placeholder="Name"></el-input>
        </el-form-item>
        <el-form-item
          label="邮箱"
          prop="email">
          <el-input v-model="form.email" placeholder="xxx@xx.xx"></el-input>
        </el-form-item>
        <el-form-item
          label="密码"
          prop="password">
          <el-input v-model="form.password" type="password" placeholder="长度8-14"></el-input>
        </el-form-item>
        <div class="u-form-group">
          <el-button type="primary" @click.prevent="register('form')">注册</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import Vue from "vue";

export default {
  name: 'Register',
  data () {
    return {
      form: {
        name: '',
        email: '',
        password: '',
      },
      msg: '',
      formRules: {
        name: [
          {
            required: true,
            message: '请输入昵称',
            trigger: 'blur',
          }
        ],
        email: [
          {
            required: true,
            pattern: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
            message: '邮箱格式不正确',
            trigger: 'blur',
          }
        ],
        password: [
          {
            required: true,
            message: '请输入密码',
            trigger: 'blur',
          },
          {
            min: 8,
            max: 14,
          }
        ]
        },
      }
  },
  methods: {
    register (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios
            .post('/register', {
              name: this.form.name,
              email: this.form.email,
              password: this.form.password
            })
            .then(resp => {
              if (resp.data.code === 1) {
                console.log(resp.data.code)
                Vue.prototype.$message({
                  message: resp.data.msg,
                  type: 'success',
                });
                this.$router.replace({path: path === '/' || path === undefined ? '/login' : path})
              } else if (resp.data.code === 0) {
                this.$message.error("注册失败")
              } else {
                Vue.prototype.$message({
                  message: 'Error happens',
                  type: 'error'
                });
              }
            }
            )
            .catch(failResponse => {
              console.log("Error happens")
            })
        }
      })
    }
  }
}
</script>

<style scoped>
.card {
  width: 500px;
  margin: 20% auto auto;
}
.chr {
  font-weight: bold;
  font-size: large;
}
</style>
