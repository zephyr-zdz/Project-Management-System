<template>
  <el-container>
    <el-header>管理教室</el-header>
    <el-main>
      <el-card class="card">
        <manage-classroom-table></manage-classroom-table>
      </el-card>
    </el-main>
  </el-container>
</template>

<script>
import ManageClassroomTable from "./ManageClassroomTable";
export default {
  name: "ManageClassroom",
  components: {ManageClassroomTable},
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
.card {
  width: 1000px;
  margin: auto;
}
</style>
