<template>
  <div id="bg">
    <el-container>
      <el-main style="line-height: 60px">
        <el-card class="card">
          <el-page-header @back="goBack" content="项目详情">
          </el-page-header>
          <el-divider></el-divider>
          <content-table></content-table>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import ContentTable from "./ContentTable.vue";
export default {
  name: "ProjectContent",
  components: {ContentTable},
  methods: {
    goBack() {
      this.$router.go(-1);
    }
  }
}
</script>

<style scoped>
.card {
  margin: 0 auto;
  width: 85%;
}
</style>
