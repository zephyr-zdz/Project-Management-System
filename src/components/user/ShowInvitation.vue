<template>
  <div id="bg">
    <el-container>
      <el-main style="line-height: 60px">
        <el-card class="card">
          <h2 style="height: 80px; margin: 10px 0 0;">邀请信息</h2>
          <invitation-list></invitation-list>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import InvitationList from "./service/InvitationList.vue";
export default {
  name: "ShowInvitation",
  components: {InvitationList}
}
</script>

<style scoped>
.el-container {
  top: 0;
}
.card {
  position: relative;
  margin: 0 auto;
  width: 85%;
}
</style>
