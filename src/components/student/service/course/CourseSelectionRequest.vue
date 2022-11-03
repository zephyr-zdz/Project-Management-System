<template>
  <div id="bg">
    <el-main>
      <el-card className="card">
        <course-selection-request-form></course-selection-request-form>
      </el-card>
    </el-main>
  </div>
</template>

<script>
import CourseSelectionRequestForm from "./CourseSelectionRequestForm";

export default {
  name: "CourseSelectionRequest",
  components: {CourseSelectionRequestForm}
}
</script>

<style scoped>

#bg {
  background-color: ghostwhite;
  height: 100%;
}
.card {
  position: relative;
  left: 300px;
  width: 70%;
}


.el-main {
  background-color: ghostwhite;
  color: #333;
  text-align: center;
  line-height: 60px;
}
</style>
