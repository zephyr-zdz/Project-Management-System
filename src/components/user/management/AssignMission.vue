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
        <el-form-item label="任务描述" prop="taskDescription">
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
      userList: [
        {id: 1, username: '张三'},
      ],
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
            required: false,
            trigger: 'blur',
          }
        ],
      },
      projectList: [
        {
          project: {
            id: 1,
            title: "片候设",
            intro: "已候军制全局点数山公更先风劳习所。六已该院战世元后以义观件亲此厂业本知。分识着应众越正金准数展受正细采阶。西集代南力组安油信转方才准队员月了适。历平青什光近深活段所速电半毛始办七。该往学办看电及参及件到九全类被。",
            owner_id: 1
          },
          memberList: [
            {
              id: 1,
              name: "csdv",
              auth: 1,
            }
          ],
          number: 2
        }
      ]
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
      console.log(this.userList)
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
          this.$axios
            .post('/project/issue/create', issue)
            .then(resp => {
              console.log(resp.data.code)
              if (resp.data.code === 0) {
                this.$message.success(resp.data.msg)
              } else{
                this.$message({
                  message: resp.data.msg,
                  type: 'error'
                })
              }
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
