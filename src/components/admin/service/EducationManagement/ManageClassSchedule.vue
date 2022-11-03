<template>
  <el-container>
    <el-header>管理上课安排</el-header>
    <el-main>
      <el-card class="card">
        <class-schedule-table/>
      </el-card>
    </el-main>
  </el-container>
</template>

<script>
import ClassScheduleTable from "./ClassScheduleTable";
export default {
  name: "ManageClassSchedule",
  components: {ClassScheduleTable},
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
