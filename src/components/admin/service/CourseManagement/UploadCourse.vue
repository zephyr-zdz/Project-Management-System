<template>
  <div class="upload-file">
    <el-upload
      class="upload-demo"
      :action = actionUrl
      accept=".csv"
      :on-success="handleSuccess"
    >
      <el-button type="primary">批量导入</el-button>
      <div slot="tip" class="el-upload__tip"></div>
    </el-upload>
  </div>
</template>

<script>
export default {
  name: "UploadCourse",
  data(){
    let baseUrl = this.$baseURL
    return {
      baseUrl: this.$baseURL,
      actionUrl: baseUrl + '/uploadCourse',
      fileInfo: {
        fileName: "",
        virtualPath: ""
      },
    }
  },
  methods: {
    handleSuccess(result) {
      this.$message({
        message: result.msg,
        type: 'success'
      });
      location.reload()
    },
  }
}
</script>

<style scoped>
.el-button {
  margin: 10px;
}
</style>
