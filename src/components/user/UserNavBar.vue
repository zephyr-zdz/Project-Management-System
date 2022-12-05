<template>
  <div>
    <el-menu
      style="width: 200px;
      min-height: calc(100vh);
      border: none"
      router
      :default-active="$route.path"
      class="el-menu-vertical-demo">
      <el-menu-item index="/user-home/dashboard">
        <span>首页</span>
      </el-menu-item>
      <el-menu-item index="/user-home/edit-info">
        <span>个人信息修改</span>
      </el-menu-item>
      <el-submenu index="">
        <template slot="title">
          <span>项目</span>
        </template>
        <el-menu-item index="/user-home/my-project">
          <span>我的项目</span>
        </el-menu-item>
        <el-menu-item index="/user-home/my-invitation">
          <span>邀请信息</span>
        </el-menu-item>
        <el-menu-item index="/user-home/project-leader-request">
          <span>项目负责人申请</span>
        </el-menu-item>
      </el-submenu>
      <div style="color: #333333">
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
      userName: 'xxx'
    }
  },
  mounted: function () {
    this.getUserName()
  },
  methods: {
    async getUserName() {
      let jsonObj = JSON.parse(window.localStorage.user);
      let no = jsonObj.user.name
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
.el-menu-item.is-active{
  background-color: ghostwhite;
}

</style>
