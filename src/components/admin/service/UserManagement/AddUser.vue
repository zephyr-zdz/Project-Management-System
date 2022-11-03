<template>
  <el-container>
    <el-container>
      <el-header>管理用户信息</el-header>
      <el-main>

        <add-user-form></add-user-form>
        <upload-user></upload-user>
        <display-user-table></display-user-table>

      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import AddUserForm from "./AddUserForm";
import DisplayUserTable from "./DisplayUserTable";
import UploadUser from "./UploadUser";
export default {
  name: "AddUser",
  components: {DisplayUserTable, AddUserForm, UploadUser}
}
</script>

<style scoped>
  .el-header {
    /*background-color: #8F8F99;*/
    border-bottom: #1f1e33;
    color: #ffffff;
    text-align: center;
    font-size: 20px;
    line-height: 60px;
  }

  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
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
</style>
