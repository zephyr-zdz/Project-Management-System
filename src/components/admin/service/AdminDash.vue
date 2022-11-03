<template>
  <el-container>
     <el-header>Welcome, Administrator</el-header>
     <el-main>HERE WILL BE THE DASHBOARD</el-main>
  </el-container>
</template>

<script>
export default {
  name: "AdminDash"
}
</script>

<style scoped>
.el-header {
  background-color: #8F8F99;
  border-bottom: #1f1e33;
  color: #ffffff;
  text-align: center;
  font-size: 20px;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
}
.el-main {
  background-color: ghostwhite;
  color: #333;
  text-align: center;
  position: absolute;
  left: 200px;
  right: 0;
  top: 60px;
  bottom: 0;
  overflow-y: scroll;
}
</style>
