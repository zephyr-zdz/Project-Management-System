<template>
  <div>
    <el-button @click="addFormVisible = true" type="primary">新增项目</el-button>
    <el-dialog title="项目信息" :visible.sync="addFormVisible" style="line-height: 100px;">
      <el-form
        :model="addProject"
        ref="addProject"
        :rules="projectFormRules"
      >
        <el-form-item
          label="项目id"
          prop="projectCode"
          :label-width="formLabelWidth"
        >
          <el-input  :disabled="disabled" v-model="addProject.projectCode"></el-input>
        </el-form-item>
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
          <el-input v-model="addProject.projectIntroduction"></el-input>
        </el-form-item>
        <el-form-item
          label="项目所有者"
          prop="projectLeaderNumber"
          :label-width="formLabelWidth">
          <el-select v-model="addProject.projectLeaderNumber"
                     placeholder="项目所有者">
            <el-option
              v-for="item in userList"
              :key="item.number"
              :label="item.name"
              :value="item.number">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          label="项目管理员"
          prop="projectAdminNumber"
          :label-width="formLabelWidth">
          <el-select v-model="addProject.projectAdminNumber"
                     placeholder="项目管理员">
            <el-option
              v-for="item in userList"
              :key="item.number"
              :label="item.name"
              :value="item.number">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          label="项目成员"
          prop="projectMemberNumber"
          :label-width="formLabelWidth">
          <el-select v-model="addProject.projectMemberNumber"
                     placeholder="项目成员">
            <el-option
              v-for="item in userList"
              :key="item.number"
              :label="item.name"
              :value="item.number">
            </el-option>
          </el-select>
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
let allDepartments = [
  {
    departmentNumber: 1,
    school: {
      schoolId: 3,
      schoolName: "前端-医学院"
    },
    departmentName: '临床'
  },
  {
    departmentNumber: 2,
    school: {
      schoolId: 1,
      schoolName: "前端-环境学院"
    },
    departmentName: '环境工程'
  }
]
export default {
  name: "AddProject",
  data() {
    return {
      addFormVisible: false,
      disabled: false,
      formLabelWidth: '120px',
      userList: [
        { number: "20220403", name:'用户1'},
        { number: "20220319", name:'用户2'},
      ],
      search: [],
      addProject: {
        projectCode: '',
        projectName: '',
        projectIntroduction:'',
        projectLeaderNumber: '',
        projectAdminNumber:'',
        projectMemberNumber:'',
      },
      projectFormRules: {
        projectCode: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
          {
            pattern: /\d\d\d\d\d\d+.\d\d/,
            message: '注意项目id格式',
            trigger: 'blur'
          }
        ],
        projectName: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ],
        projectIntroduction: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ],
        projectLeaderNumber: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ],
        projectAdminNumber:[
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ],
        projectMemberNumber:[
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ],
      },
    }
  },
  mounted: function() {
    this.getUserId()
  },
  methods: {
    getUserId () {
      this.$axios.get('/project/list', {params: {user_id: this.$store.getters.user_id}})
        .then(response => {
          console.log(response.data.data)
          if (response.data.code === 0) {
            this.res.id = response.data.data
            console.log(this.res.id)
          } else {
            this.$message({
              message: response.data.msg,
              type: 'error'
            })
            this.res.id = -1
          }
        })
        .catch(error => {
          console.log(error)
        })
    },
    clear() {
      this.addFormVisible = true;
      this.disabled = false;
      this.$refs.addProject.resetFields();
    },
    SubmitProject(formName) {
      console.log("Submit called")

      this.$refs.addProject.validate((valid) => {
        if ((valid) && (this.addProject.projectLeaderNumber !== -1)) {
          console.log("on Submit")
          this.$axios
            .post('/project/create', {
              projectCode: this.addProject.projectCode,
              projectName: this.addProject.projectName,
              projectIntroduction: this.addProject.projectIntroduction,
              projectLeaderNumber: this.addProject.projectLeaderNumber,
              projectAdminNumber: this.addProject.projectAdminNumber,
              projectMemberNumber: this.addProject.projectMemberNumber
            })
            .then(resp => {
              console.log(resp.data)
              if (resp.data.code === 0) {
                this.$message({
                  message: '添加成功',
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
