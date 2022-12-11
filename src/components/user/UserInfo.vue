<template>
  <div id="bg">
    <el-container>
      <el-main style="line-height: 60px">
        <el-card class="card">
          <h2 style="height: 80px; margin: 10px 0 0;">个人信息</h2>
          <el-descriptions class="margin-top" :column="2" direction="vertical" border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                用户名
              </template>
              <p>{{ user.name }}</p>
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-message"></i>
                邮箱
              </template>
              <p>{{ user.email }}</p>
            </el-descriptions-item>
          </el-descriptions>

          <el-button type="primary" plain @click="tokenShow()">高级功能</el-button>
          <el-input v-if="tokenEdit" v-model="token">
            <el-button slot="append" @click="tokenSub()">提交</el-button>
          </el-input>

          <el-divider></el-divider>

          <el-button icon="el-icon-switch-button" type="danger" plain @click="quit"></el-button>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "UserInfo",
  data () {
    return {
      user: {},
      tokenEdit: false,
      token: ''
    }
  },
  mounted () {
    let jsonObj = JSON.parse(window.sessionStorage.user);
    this.user = jsonObj.user
  },
  methods: {
    quit() {
      this.$confirm('此操作将退出登录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        window.sessionStorage.removeItem("user");
        this.$router.replace("/");
      })
    },
    tokenShow() {
      this.tokenEdit = !this.tokenEdit
    },
    tokenSub() {
      if (this.token === '') {
        this.$message({
          message: '请输入token',
          type: 'warning'
        })
      } else {
        let url = '/user/token/' + this.user.userid;
        let params = new FormData();
        params.append('token', this.token);
        this.$axios
          .post(url, params)
          .then(res => {
            if (res.data.code === 0) {
              this.$message({
                message: res.data.msg,
                type: 'success'
              });
              this.tokenEdit = false
            } else {
              this.$message.error(res.data.msg)
            }
          })
          .catch(err => {
            this.$message({
              message: err,
              type: 'error'
            });
          })
      }
    }
  }
}
</script>

<style scoped>
.card {
  margin: 0 auto;
  width: 85%;
}
</style>
