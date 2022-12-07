<template>
  <div>
    <el-button @click="addFormVisible = true" type="primary" size="small" icon="el-icon-plus">新增项目</el-button>
    <el-dialog title="项目信息" :visible.sync="addFormVisible">
      <el-form
        :model="addProject"
        ref="addProject"
        :rules="projectFormRules">
        <el-form-item
          label="项目名称"
          prop="projectName"
          :label-width="formLabelWidth">
          <el-input v-model="addProject.projectName"></el-input>
        </el-form-item>
        <el-form-item
          label="项目简介"
          prop="projectIntroduction"
          :label-width="formLabelWidth">
          <el-input v-model="addProject.projectIntroduction" type="textarea" :autosize="{ minRows: 3, maxRows: 5}"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addFormVisible = false; clear()">取 消</el-button>
        <el-button @click="SubmitProject('addProject')" type="primary">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

export default {
  name: "AddProject",
  data() {
    return {
      addFormVisible: false,
      formLabelWidth: '120px',
      addProject: {
        projectName: '',
        projectIntroduction:'',
      },
      projectFormRules: {
        projectName: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ],
        projectIntroduction: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ],
      },
    }
  },
  methods: {
    clear() {
      this.addFormVisible = false;
      this.$refs.addProject.resetFields();
    },
    SubmitProject(formName) {
      console.log("Submit called")
      this.$refs.addProject.validate((valid) => {
        if (valid) {
          console.log("on Submit")
          let jsonObj = JSON.parse(window.localStorage.user);
          let id = jsonObj.user.userid;
          this.$axios
            .post('/project/create', {
              title: this.addProject.projectName,
              intro: this.addProject.projectIntroduction,
              owner_id: id,
            })
            .then(resp => {
              console.log(resp.data)
              if (resp.data.code === 0) {
                this.$message({
                  message: resp.data.msg,
                  type: 'success'
                })
                this.clear()
              } else {
                this.$message({
                  message: '添加失败，请检查输入课程是否有冲突',
                  type: 'error'
                })
              }
            })
        } else {
          this.$alert('请检查输入是否正确', '添加失败', {confirmButtonText: '确定'})
        }
      })
    }
  }
}
</script>

<style scoped>
.el-button {
  margin: 10px;
}
</style>
