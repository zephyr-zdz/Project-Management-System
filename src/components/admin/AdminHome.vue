<!--2022/3/3 created by yyf-->
<template>

  <div>
    <el-container>
      <el-aside width="auto" ><admin-nav-bar></admin-nav-bar></el-aside>
      <el-container>
        <router-view></router-view>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import AdminNavBar from "./AdminNavBar";
export default {
  name: "AdminHome",
  components: {AdminNavBar}
}
</script>

<style scoped>
.el-header {
  background-color: #B3C0D1;
  border-bottom: #1f1e33;
  color: #333;
  text-align: center;
  line-height: 60px;
}
.el-aside {
  position: fixed;

  bottom: 0;

  background-color: #D3DCE6;
  color: #333;
  text-align: center;
}
.el-main {
  position: fixed;
  top: 52px;
  bottom: 0;
  overflow: scroll;

  background-color: ghostwhite;
  color: #333;
  text-align: center;
}


</style>
