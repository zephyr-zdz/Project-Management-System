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
        <i class="el-icon-s-home" style="'color:#455368'; position: relative; left: -30px"></i>
        <span class="character">首页</span>
      </el-menu-item>
      <el-submenu index="">
        <template slot="title">
          <i class="el-icon-document" style="'color:#455368'; position: relative; left: -30px"></i>
          <span class="character">项目</span>
        </template>
        <el-menu-item index="/user-home/my-project">
          <i class="el-icon-files" style="'color:#455368'"></i>
          <span>我的项目</span>
        </el-menu-item>
        <el-menu-item index="/user-home/my-invitation">
          <i class="el-icon-message-solid" style="'color:#455368'"></i>
          <span>邀请信息</span>
        </el-menu-item>
      </el-submenu>
      <el-menu-item
        index="/user-home/edit-info"
        style="position: fixed; bottom: 0; left: 0%; width: 200px">
        <i class="el-icon-user-solid" style="'color:#455368'; position: relative; left: -30px"></i>
        <span class="character">个人信息</span>
      </el-menu-item>
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
.character{
  position: relative;
  left: -30px;
}
.el-menu{
  box-shadow: #cccccc 0 0 10px;
}
.el-menu-item:hover{
  border-left:#33A2EF solid 6px !important;
  background-color: ghostwhite !important;
  color: #38B2FF !important;
/*less语法，实现鼠标悬停时icon变色*/
  i {
    color: #38B2FF;
  }
}
/*设置选中el-menu-item时的样式*/
.el-menu-item.is-active {
  border-left:#33A2EF solid 6px !important;
  background-color: ghostwhite !important;
  color: #38B2FF !important;
}

</style>
