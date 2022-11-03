<template>
  <div>
  <el-card class="card">
    <el-button @click="addDialog1Visible = true" type="primary">添加学院</el-button>
    <el-button @click="addDialog3Visible = true" type="primary">删除学院</el-button>
    <el-button @click="addDialog2Visible = true" type="primary">添加专业</el-button>
    <el-table
      :data="departmentData"
      style="width: 100%"
      stripe
      border>
      <el-table-column
        sortable="true"
        prop="departmentNumber"
        label="专业编号"
        width="150">
      </el-table-column>
      <el-table-column
        prop="school.schoolName"
        label="学院">
      </el-table-column>
      <el-table-column
        prop="departmentName"
        label="专业">
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="150">
        <template slot-scope="scope">
          <el-popconfirm
            title="此操作将永久删除该专业，是否继续?"
            @confirm="delDepartment(scope.row.departmentName)">
            <el-button slot="reference" type="text">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
  <el-dialog title="添加学院" :visible.sync="addDialog1Visible">
    <el-form
      :model="schoolAdd"
      ref="schoolAdd"
      :rules="schoolAddRules">
      <el-form-item
        label="学院名"
        prop="schoolName"
        :label-width="formLabelWidth">
        <el-input v-model="schoolAdd.schoolName" placeholder="学院名称"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="addDialog1Visible = false; ">取 消</el-button>
      <el-button @click="onSubmitSchool('schoolAdd')" type="primary">确 定</el-button>
    </div>
  </el-dialog>
  <!--删除学院界面-->
  <el-dialog title="删除学院" :visible.sync="addDialog3Visible">
    <el-form
      :model="schoolDelete"
      ref="schoolDelete"
      :rules="schoolDeleteRules"
    >
      <el-form-item
        label="学院名"
        prop="school"
        :label-width="formLabelWidth">
        <el-select v-model="schoolDelete.schoolName" placeholder="请选择学院">
          <el-option
            v-for="item in schoolChoose"
            :key="item.schoolId"
            :label="item.schoolName"
            :value="item.schoolName">
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="addDialog3Visible = false; ">取 消</el-button>
      <el-popconfirm
        title="此操作将永久删除该学院，是否继续?"
        @confirm="delSchool(schoolDelete.schoolName)">
        <el-button slot="reference" type="primary">确 定</el-button>
      </el-popconfirm>
    </div>
  </el-dialog>
  <!--添加专业界面-->
  <el-dialog title="添加专业" :visible.sync="addDialog2Visible">
    <el-form
      :model="form"
      ref="form"
      :rules="formRules">
      <el-form-item
        label="学院名"
        prop="school"
        :label-width="formLabelWidth">
        <el-select v-model="form.school" placeholder="请选择学院">
          <el-option
            v-for="item in schoolChoose"
            :key="item.schoolId"
            :label="item.schoolName"
            :value="item.schoolId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item
        label="专业名"
        prop="department"
        :label-width="formLabelWidth">
        <el-input v-model="form.department" placeholder="Department"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="addDialog2Visible = false; ">取 消</el-button>
      <el-button @click="onSubmitDepartment('form')" type="primary">确 定</el-button>
    </div>
  </el-dialog>
  </div>
</template>

<script>
export default {
  name: "ManageSchoolCard",
  data() {
    return{
      addDialog1Visible: false,
      addDialog2Visible: false,
      addDialog3Visible: false,
      formLabelWidth: '120px',
      schoolAdd: {
        schoolName: '',
      },
      schoolAddRules: {
        schoolName :[
          { required: true, message: '必填', trigger: 'blur' },
        ],
      },
      schoolDelete: {
        schoolName: '',
      },
      schoolDeleteRules: {
        school: [
          { required: true, message: '必填', trigger: 'blur' },
        ],
      },
      form: {
        school: {
          schoolId: 0,
        },
        department: '',
      },
      formRules: {
        school: [
          { required: true, message: '必填', trigger: 'blur'} ,
        ],
        department: [
          { required: true, message: '必填', trigger: 'blur'},
        ],
      },
      departmentData: [
        /*格式参考*/
        {
          "departmentNumber": '10002',
          "school": {
            "schoolName": '土木工程学院',
          },
          "departmentName": '土木工程',
        },
        {
          "departmentNumber": '10001',
          "school": {
            "schoolName": '土木工程学院',
          },
          "departmentName": '智能建造',
        },
        {
          "departmentNumber": '20003',
          "school": {
            "schoolName": '医学院',
          },
          "departmentName": '护理学',
        },
      ],
      schoolChoose: [
        /*格式参考*/
        {
          "schoolId": '3',
          "schoolName": '医学院',
        },
        {
          "schoolId": '2',
          "schoolName": '外国语学院',
        },
      ]
    }
  },
  mounted: function () {
    this.loadSchool()
    this.loadDepartment()
  },
  methods: {
    async loadDepartment () {
      console.log("loadDepartment called")
      this.$axios.get('/departments').then(resp => {
        if (resp && resp.status === 200) {
          this.departmentData = resp.data
        }
      })
    },
    async loadSchool () {
      console.log("loadSchool called")
      this.$axios.get('/schools').then(resp => {
        if (resp && resp.status === 200) {
          this.schoolChoose = resp.data
        }
      })
    },
    delSchool(schoolName) {
      console.log("deleteSchool called")
      console.log(schoolName)
      let params = new URLSearchParams();
      params.append('schoolName', schoolName);
      this.$axios
        .post('/deleteSchool', params)
        .then(resp => {
          if (resp && resp.data.code === 200) {
            console.log("resp" + resp)
            this.$message({
              type: 'success',
              message: "已删除"
            })
            this.addDialog3Visible = false;
            this.loadDepartment()
          } else {
            this.$message({
              type: 'error',
              message: "删除失败"
            })
          }
      })
    },
    delDepartment(department) {
      console.log("deleteDepartment called")
      console.log(department)
      let params = new URLSearchParams();
      params.append('departmentName', department);
      this.$axios
        .post('/deleteDepartment', params).then(resp => {
        if (resp && resp.status === 200) {
          console.log(resp)
          this.$message({
            type: 'success',
            message: resp.data.msg
          })
          this.loadDepartment()
        } else {
          this.$message({
            type: 'error',
            message: "删除失败"
          })
        }
      })
    },
    onSubmitSchool(school) {
      console.log("onSubmit called")
      this.$refs[school].validate((valid) => {
        if (valid) {
          this.$axios
            .post('/schools', {
                schoolName: this.schoolAdd.schoolName
            })
            .then(resp => {
              if (resp && resp.data.code === 200) {
                this.$message({
                  message: resp.data.msg,
                  type: 'success'
                });
                this.addDialog1Visible = false
              } else {
                this.$message({
                  message: resp.data.msg,
                  type: 'error'
                });
              }
            })
        } else {
          console.log("not valid")
          this.$alert("请按提示正确填写内容", "Warning",
            {
              confirmButtonText: '确定',
            });
          return false;
        }
      })
    },
    onSubmitDepartment(form) {
      console.log("onSubmitDepartment called")
      this.$refs[form].validate((valid) => {
        console.log(this.form)
        if (valid) {
          this.$axios
            .post('/departments', {
              school: {
                schoolId: this.form.school
              },
              departmentName: this.form.department,
            })
            .then(resp => {
              if (resp && resp.data.code === 200) {
                if (resp.data.code === 200) {
                  this.addDialog2Visible = false
                  this.$message({
                    message: '添加成功',
                    type: 'success'
                  });

                  this.loadDepartment()
                } else if (resp.data.code === 102) {
                  this.$alert(resp.data.msg, 'Warning', {
                    confirmButtonText: '确定'
                  });
                }
              } else {
                this.$message({
                  message: '添加失败',
                  type: 'error'
                });
              }
            })
        } else {
          console.log("not valid")
          this.$alert("请按提示正确填写内容", "Warning",
            {
              confirmButtonText: '确定',
            });
          return false;
        }
      })
    },
  }

}
</script>

<style scoped>
.card {
  width: 1000px;
  margin: auto;
}

.el-button {
  margin: 10px;
}
</style>
