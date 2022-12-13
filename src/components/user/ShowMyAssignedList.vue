<template>
  <div id="bg">
    <el-container>
      <el-main style="line-height: 60px">
        <el-card class="card">
          <h2 style="height: 80px; margin: 10px 0 0;">指派的任务</h2>
          <my-assigned-list></my-assigned-list>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import MyAssignedList from "./service/MyAssignedList.vue";
import MyAssignmentList from "./service/MyAssignedList.vue";
export default {
  name: "ShowMyAssignedList",
  components: {MyAssignedList, MyAssignmentList}
}
</script>

<style scoped>
.card {
  margin: 0 auto;
  width: 85%;
}
</style>
