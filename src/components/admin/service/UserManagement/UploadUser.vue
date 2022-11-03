<template>
  <div class="upload-file">
  <el-upload
    class="upload-demo"
    :action = actionUrl
    accept=".csv"
    :file-list="fileList"
    :on-success="handleSuccess"
  >
    <el-button type="primary">批量导入</el-button>
    <div slot="tip" class="el-upload__tip">只能上传csv文件</div>
  </el-upload>
  </div>
</template>

<script>
export default {
  name: "UploadUser",
  data(){
    let baseUrl = this.$baseURL
    return {
      baseUrl: this.$baseURL,
      actionUrl: baseUrl + '/uploadUser',
      fileList: [],
      fileInfo: {
        fileName: "",
        virtualPath: ""
      },
    }
  },
  methods: {
    handleSuccess(result) {
      this.$alert(result.msg,{
        confirmButtonText: '确定',
      });
      location.reload()
    },
  }
}
</script>

<style scoped>
.upload-file {
  margin: 10px
}
</style>
