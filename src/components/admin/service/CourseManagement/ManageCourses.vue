<template>
  <el-container>
  <el-header>管理课程</el-header>
  <el-main>
    <display-courses></display-courses>
  </el-main>
  </el-container>
</template>

<script>
import DisplayCourses from "./DisplayCourses";
import AddCourseForm from "./AddCourseForm";

export default {
  name: "ManageCourses",
  components: {DisplayCourses}
}
</script>

<style scoped>
.el-header {
  border-bottom: #1f1e33;
  color: #ffffff;
  text-align: center;
  font-size: 20px;
  line-height: 60px;
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
