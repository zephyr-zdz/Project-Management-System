<template>
  <div id="bg">
    <el-container>

      <el-main>
        <h2>我的课程</h2>
        <el-card class="card">
          <teacher-course-table></teacher-course-table>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import TeacherCourseTable from "./TeacherCourseTable";
export default {
  name: "TeacherCourse",
  components: {TeacherCourseTable}
}
</script>

<style scoped>
#bg {
  background-color: ghostwhite;
  height: 100%;
}
.card {
  margin: 0 auto;
  width: 70%;
}

.el-main {
  background-color: ghostwhite;
  color: #333;
  text-align: center;
  line-height: 60px;
}
</style>

