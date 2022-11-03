<template>
  <el-container>
    <el-header>管理学院及专业</el-header>
    <el-main>
      <manage-school-card></manage-school-card>
    </el-main>
  </el-container>
</template>

<script>
import ManageSchoolCard from "./ManageSchoolCard";
export default {
  name: "ManageSchool",
  components: {ManageSchoolCard},

}
</script>

<style scoped>
.el-header {
  /*background-color: #a7d7e8;*/
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
  position: absolute;
  left: 200px;
  right: 0;
  top: 60px;
  bottom: 0;
  overflow-y: scroll;

  background-color: ghostwhite;
  color: #333;
  text-align: center;
}

</style>
