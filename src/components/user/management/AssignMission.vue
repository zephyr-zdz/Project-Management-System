<template>
  <div>
    <el-button type="primary" plain @click="dialogFormVisible = true" icon="el-icon-plus" :disabled="disabled">指派任务</el-button>
    <el-dialog
      title="指派任务"
      :visible.sync="dialogFormVisible"
      class="dialog">
      <el-form
        :model="assignForm"
        ref="assignForm"
        :rules="rules">
        <el-form-item label="项目名称" prop="pjName">
          <el-select v-model="assignForm.pjID" filterable :placeholder="this.assignForm.pjName" disabled>
            <el-option
              v-for="item in projectList"
              :key="item.project.id"
              :label="item.project.title"
              :value="item.project.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="指派成员" prop="userID">
          <el-select v-model="assignForm.userID" filterable placeholder="请选择">
            <el-option
              v-for="item in userList"
              :key="item.id"
              :label="item.username"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="任务名称" prop="taskName">
          <el-input v-model="assignForm.taskName" style="width: 60%"></el-input>
        </el-form-item>
        <el-form-item label="任务描述" prop="taskInfo">
          <el-input v-model="assignForm.taskInfo" style="width: 60%" type="textarea" :autosize="{ minRows: 3, maxRows: 5}">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="clear()">取 消</el-button>
        <el-button @click="submitForm()" type="primary">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { Loading } from 'element-ui';
export default {
  name: "AssignMission",
  data () {
    return {
      dialogFormVisible: false,
      disabled: true,
      assignForm: {
        pjID: '',
        pjName: '',
        userID: '',
        taskName: '',
        taskInfo: '',
      },
      userList: [],
      rules: {
        pjName: [
          {
            required: true,
            message: '请选择项目',
            trigger: 'blur',
          }
        ],
        userID: [
          {
            required: true,
            message: '请选择成员',
            trigger: 'blur',
          }
        ],
        taskName: [
          {
            required: true,
            message: '请输入任务名称',
            trigger: 'blur',
          }
        ],
        taskInfo: [
          {
            required: true,
            message: '请输入任务简介',
            trigger: 'blur',
          }
        ],
      },
      projectList: []
    }
  },
  mounted: function () {
    this.loadProject();
  },
  methods: {
    async loadProject() {
      let jsonObj = JSON.parse(window.sessionStorage.user);
      let id = jsonObj.user.userid;
      let url = '/user/participating/' + id
      //console.log(this.userList)
      this.$axios
        .get(url)
        .then(response => {
          if (response.data.code === 0) {
            this.projectList = response.data.data;
          } else {
            this.$message.error(response.data.msg);
          }
        })
    },
    submitForm() {
      this.$refs.assignForm.validate((valid) => {
        if (valid) {
          let jsonObj = JSON.parse(window.sessionStorage.user);
          let id = jsonObj.user.userid
          let issue = {
            projectId: this.assignForm.pjID,
            assigneeId: this.assignForm.userID,
            title: this.assignForm.taskName,
            description: this.assignForm.taskInfo,
            reviewerId: id,
            status: "open"
          }
          let loadingInstance = Loading.service({ fullscreen: true });
          this.$axios
            .post('/project/issue/create', issue)
            .then(resp => {
              console.log(resp.data.code)
              loadingInstance.close();
              if (resp.data.code === 0) {
                this.$message.success(resp.data.msg)
                this.clear()
              } else{
                this.$message({
                  message: resp.data.msg,
                  type: 'error'
                })
              }
            })
            .catch(error => {
              this.$message({
                message: error,
                type: 'error'
              })
            })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    clear() {
      this.dialogFormVisible = false;
      this.$refs.assignForm.resetFields();
    },
  }
}
</script>

<style scoped>
.dialog {
  overflow-y: scroll;
  text-align: center;
  line-height: 30px;
}
</style>
