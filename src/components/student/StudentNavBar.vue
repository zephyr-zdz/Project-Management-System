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
      <el-menu-item index="/student-home/dashboard">
        <span>学生-首页</span>
      </el-menu-item>
      <el-menu-item index="/student-home/edit-info">
        <span>个人信息修改</span>
      </el-menu-item>
      <el-submenu index="">
        <template slot="title">
          <span>课程</span>
        </template>
        <el-menu-item index="/student-home/select-course">
          <span>选课</span>
        </el-menu-item>
        <el-menu-item index="/student-home/my-course">
          <span>我的课程</span>
        </el-menu-item>
        <el-menu-item index="/student-home/course-selection-request">
          <span>选课申请</span>
        </el-menu-item>
      </el-submenu>
      <div style="color: whitesmoke">
        欢迎，{{ userName }}
      </div>
    </el-menu>
  </div>
</template>

<script>
import Vue from "vue";

export default {
  name: "StudentNavBar",
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
