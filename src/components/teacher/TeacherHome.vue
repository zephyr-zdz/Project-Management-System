<!--2022/3/2 created by Yifan Yu-->
<template>
  <div>
    <el-container>
      <el-header height="auto"><teacher-nav-bar></teacher-nav-bar></el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import TeacherNavBar from "./TeacherNavBar";
export default {
  name: "TeacherHome",
  components: {TeacherNavBar}
}
</script>

<style scoped>
.el-header {
  color: #333;
  text-align: center;
  line-height: 60px;
}


.el-main {
  height: 840px;
  text-align: center;
  line-height: 160px;
  padding: 0;
}
</style>
