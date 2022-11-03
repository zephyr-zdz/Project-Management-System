<template>
  <div>
    <el-menu
      style="min-width: calc(100vh);
      height: auto;
      border: none"
      mode= "horizontal"
      router
      :default-active="$route.path"
      text-color="#fff"
      background-color="#28283C"
      active-text-color="#8F8F99"
      class="el-menu">
      <el-menu-item index="/teacher-home/dashboard">
        <span>教师-首页</span>
      </el-menu-item>
      <el-menu-item index="/teacher-home/edit-info">
        <span>个人信息修改</span>
      </el-menu-item>
      <el-submenu index="">
        <template slot="title">
          <span>课程</span>
        </template>
        <el-menu-item index="/teacher-home/my-course">
          <span>我的课程</span>
        </el-menu-item>
        <el-menu-item index="/teacher-home/course-request">
          <span>申请新课程</span>
        </el-menu-item>
        <el-menu-item index="">
          <span>placeholder</span>
        </el-menu-item>
      </el-submenu>
      <div style="color: whitesmoke">
        欢迎，{{ userName }}
      </div>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "TeacherNavBar",
  data() {
    return {
      userName: '???'
    }
  },
  mounted: function () {
    this.getUserName()
  },
  methods: {
    async getUserName() {
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
            _this.userName = resp.data.data.name
          } else {
            this.$message({
              message: 'Error happens',
              type: 'error'
            });
          }
        })
    }
  }
}
</script>

<style scoped>

</style>
