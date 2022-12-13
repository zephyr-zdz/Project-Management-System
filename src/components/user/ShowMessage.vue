<template>
  <div id="bg">
    <el-container>
      <el-main style="line-height: 60px">
        <el-card class="card">
          <h2 style="height: 80px; margin: 10px 0 0;">任务信息</h2>
          <message-list></message-list>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import MessageList from "./service/MyMissionList.vue";
export default {
  name: "ShowMessage",
  components: {MessageList}
}
</script>

<style scoped>
.card {
  margin: 0 auto;
  width: 85%;
}
</style>
