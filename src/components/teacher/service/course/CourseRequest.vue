<template>
  <div id="bg">
    <el-container>
        <el-main>
          <h2>课程申请</h2>
          <el-card class="card">
            <add-course-request></add-course-request>
            <display-course-request></display-course-request>
          </el-card>
        </el-main>
    </el-container>
  </div>
</template>

<script>
import AddCourseRequest from "./AddCourseRequest";
import DisplayCourseRequest from "./DisplayCourseRequest";
export default {
  name: "CourseRequest",
  components: {AddCourseRequest, DisplayCourseRequest},
}
</script>

<style scoped>
#bg {
  background-color: ghostwhite;
  height: 100%;
}
.el-main {
  position: absolute;
  right: 0;
  left: 0;
  top: 60px;
  bottom: 0;
  overflow-y: scroll;

  background-color: ghostwhite;
  color: #333;
  text-align: center;
  line-height: 60px;
}
.card{
  width: 70%;
  margin: auto;
}
</style>
