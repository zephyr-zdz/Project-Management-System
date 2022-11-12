<template>
  <div id="bg">
    <el-container>
      <el-main style="line-height: 80px">
        <el-card class="card">
          <h2 style="height: 75px; margin: 25px 0 0;">邀请信息</h2>
          <invitation-list></invitation-list>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import InvitationList from "./InvitationList";
export default {
  name: "ShowInvitation",
  components: {InvitationList}
}
</script>

<style scoped>
.card {
  margin: 0 auto;
  width: 85%;
}
</style>
