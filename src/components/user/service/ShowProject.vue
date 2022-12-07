<template>
  <div id="bg">
    <el-container>
      <el-main style="line-height: 60px">
        <el-card class="card">
          <el-row>
            <h2 style="height: 25px; margin: 20px 0 0;">我的项目</h2>
            <add-project style="position: relative; left: 40%;"></add-project>
          </el-row>
          <project-list></project-list>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import ProjectList from "./ProjectList";
import AddProject from "../management/AddProject";
import {methods} from "babel-plugin-transform-runtime/lib/definitions";
export default {
  name: "ShowProject",
  components: {AddProject, ProjectList},
}
</script>

<style scoped>
.card {
  margin: 0 auto;
  width: 85%;
}

</style>
