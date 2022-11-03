<template>
  <el-table
    :data="users"
    stripe
    style="width: 100%">
    <el-table-column type="expand">
      <template slot-scope="props">
        <el-form label-position="left" inline class="demo-table-expand">
          <el-form-item label="编号">
            <span>{{ props.row.number }}</span>
          </el-form-item>
          <el-form-item label="姓名">
            <span>{{ props.row.name }}</span>
          </el-form-item>
          <el-form-item
            props="type"
            label="角色">
            <span>{{ props.row.type === 1 ? '学生':'教师'}}</span>
<!--            <span v-if=" props.row.type=== '2'" >管理员</span>-->
<!--            <span v-else>学生</span>-->
          </el-form-item>
          <el-form-item label="专业">
            <span>{{ props.row.department.departmentName }}</span>
          </el-form-item>
          <el-form-item label="身份证号">
            <span>{{ props.row.identity }}</span>
          </el-form-item>
          <el-form-item label="手机号">
            <span>{{ props.row.mobile }}</span>
          </el-form-item>
          <el-form-item label="邮箱">
            <span>{{ props.row.email }}</span>
          </el-form-item>
        </el-form>
      </template>
    </el-table-column>
    <el-table-column
      prop="number"
      label="编号"
      width="180">
    </el-table-column>
    <el-table-column
      prop="name"
      label="姓名"
      width="180">
    </el-table-column>
    <el-table-column
      prop="type"
      :formatter="formatterRole"
      :filters="[{text: '学生', value: 1}, {text: '教师', value: 2}]"
      :filter-method="filterHandler"
      label="角色">
    </el-table-column>
    <el-table-column
      prop="department.departmentName"
      label="专业">
    </el-table-column>
    <el-table-column
      label="操作"
      width="100">
      <template slot-scope="scope">
        <el-button type="text" size="small" @click="deleteUser(scope.row.number)">删除</el-button>
        <el-button type="text" size="small" @click="editUser(scope.row)">编辑</el-button>
        <edit-user-form ref="edit"></edit-user-form>
      </template>
    </el-table-column>
    <el-table-column
      fixed="right"
      prop="status"
      label="状态"
      width="100">
      <template slot-scope="scope">
        <el-switch
          v-model="scope.row.status"
          :active-value="1"
          :inactive-value="0"
          active-color="#38384d"
          inactive-color="#ababb3"
          @change="changeUserStatus(scope.row)"
        > </el-switch>
      </template>
    </el-table-column>
  </el-table>
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
let allSchools = [
  { schoolId: 3, schoolName: '前端-医学院'},
  { schoolId: 2, schoolName: '前端-物理学院'},
  { schoolId: 1, schoolName: '前端-环境学院'}
]
import AddUserForm from "./AddUserForm";
import EditUserForm from "./EditUserForm";
import Vue from "vue";
export default {
  name: "DisplayUserTable",
  components: {EditUserForm, AddUserForm},
  data() {
    return {
      users: [
      {
        "department": {
          "departmentNumber": 1,
          "school": null,
          "departmentName": "SOE"
        },
        "number": "220114",
        "type": 1,
        "name": "王小虎",
        "identity": "220621199112158459",
        "mobile": "15313630621",
        "email": "",
        "status": 0
      },
      {
        "department": {
          "departmentNumber": 1,
          "school": null,
          "departmentName": "not null"
        },
        "number": "220113",
        "type": 1,
        "name": "带",
        "identity": "220621199112158459",
        "mobile": "15313630621",
        "email": "",
        "status": 1
      }]
    }
  },
  mounted: function () {
    this.loadUsers()
  },
  methods: {
    filterHandler(value, row, column) {
      const property = column['property'];
      return row[property] === value;
    },
    async loadSchoolAndDepartment() {
      this.$axios.get('/schools').then(resp => {
        if (resp && resp.status === 200) {
          this.$refs.edit.form.schools = resp.data
        } else {
          this.$message({
            type: 'error',
            message:'Fail to fetch department data'
          })
        }
      })
      this.$axios.get('/departments').then(resp => {
        if (resp && resp.status === 200) {
          allDepartments = resp.data
        }
      })
    },

    loadUsers () {
      console.log("loadUsers called")
      var _this = this
      this.$axios.get('/users').then(resp => {
        if (resp && resp.status === 200) {
          _this.users = resp.data
        }
      })
    },
    formatterRole(row, column) {
      switch(row.type) {
        case 1:
          return '学生'
        case 2:
          return '教师'
        default:
          return '?'
      }
    },
    deleteUser(number) {
      console.log("deleteUser")
      console.log(number)
      this.$confirm('此操作将永久删除该用户，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
          let params = new URLSearchParams();
          params.append('userNumber', number);
          this.$axios
            .post('/delete', params).then(resp => {
            if (resp && resp.status === 200) {
              console.log(resp)
              this.$message({
                type: 'success',
                message: resp.data.msg
              })
              this.loadUsers()
            } else {
              this.$message({
                type: 'error',
                message: "未知错误"
              })
            }
          })
        }
      ).catch(() => {
        Vue.prototype.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    editUser (item) {
      console.log(item.department.departmentNumber)
      this.loadSchoolAndDepartment()
      this.$refs.edit.dialogFormVisible = true
      this.$refs.edit.form = {
        number: item.number,
        name: item.name,
        type: item.type,
        identity: item.identity,
        mobile: item.mobile,
        email: item.email,
        // department: {
        //   departmentNumber: item.department.departmentNumber,
        //   departmentName: item.department.departmentName
        // }
        schools: allSchools,
        schoolId: '',
        departments: allDepartments,
        departmentNumber: item.department.departmentNumber
      }
    },
    changeUserStatus(item) {
      console.log("changeStatus")
      console.log(item.number)
      console.log(item.status)
      let params = new URLSearchParams();
      params.append('number', item.number);
      params.append('status', item.status);

      this.$axios
        .post('/update-user-status', params).then(resp => {
        if (resp && resp.data.code === 200) {
          this.$message({
            type: 'success',
            message: resp.data.msg
          })
          this.loadUsers()
        } else {
          this.$message({
            type: 'error',
            message: "未知错误"
          })
        }
      })
    }
  }
}
</script>

<style scoped>

/deep/ .el-form-item__label {
  margin-left: 30px;
  width: 90px;
  color: #99a9bf;
}

.el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}

</style>
