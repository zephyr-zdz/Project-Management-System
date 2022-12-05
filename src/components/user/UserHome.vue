<template>
  <div>
    <el-container>
      <el-aside height="100%"><student-nav-bar></student-nav-bar></el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import StudentNavBar from "./UserNavBar";
export default {
  name: "StudentHome",
  components: {StudentNavBar}
}
</script>

<style scoped>
.el-aside {
  color: #333;
  text-align: center;
  line-height: 60px;
  padding: 0;
}


.el-main {
  height: 90vh;
  text-align: center;
  line-height: 160px;
  padding: 0;
}
</style>
