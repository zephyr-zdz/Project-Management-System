<template>
  <div>
    <el-button type="primary" plain @click="dialogFormVisible = true" icon="el-icon-plus" :disabled="disabled">邀请成员</el-button>
    <el-dialog
      title="邀请成员"
      :visible.sync="dialogFormVisible"
      class="dialog">
      <el-form
        :model="inviteForm"
        ref="inviteForm">
        <el-form-item  label="邮箱/昵称" prop="email">
          <el-input
            v-model="inviteForm.email"
            type="text"
            placeholder="Email/Name">
            <el-button type="primary" slot="append" @click="onSubmit()">提交</el-button>
            </el-input>
        </el-form-item>
      </el-form>
      <el-divider></el-divider>
      <el-table
        :data="userList"
        border
        stripe
        height="350"
        style="width: 100%">
        <el-table-column
          type="index"
          width="50">
        </el-table-column>
        <el-table-column
          prop="id"
          label="ID"
          width="150">
        </el-table-column>
        <el-table-column
          prop="email"
          label="邮箱"
          width="auto">
        </el-table-column>
        <el-table-column
          prop="username"
          label="昵称"
          width="auto">
        </el-table-column>
        <el-table-column
          label="操作"
          width="100">
          <template v-slot="scope">
            <el-button @click="selectUser(scope.row)" type="text">邀请</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Invite",
  data () {
    return {
      dialogFormVisible: false,
      disabled: true,
      inviteForm: {
        email: '',
      },
      userList: []
    }
  },
  methods: {
    onSubmit() {
          const param = new FormData();
          param.append('name', this.inviteForm.email)
          this.$axios
            .post('/user/search', param)
            .then(resp => {
              console.log(resp.data.code)
              if (resp.data.code === 0) {
                this.userList = resp.data.data
              } else{
                this.$message({
                  message: resp.data.msg,
                  type: 'error'
                })
              }
            })
    },
    selectUser (row) {
      let jsonObj = JSON.parse(window.localStorage.user);
      let id = jsonObj.user.userid
      const param = new FormData();
      param.append('user_id', id)
      param.append('receiver_id', row.id)
      param.append('project_id', this.$route.params.id)
      this.$axios
        .post('/project/invite', param)
        .then(resp => {
          console.log(resp.data.code)
          if (resp.data.code === 0) {
            this.$message({
              message: resp.data.msg,
              type: 'success'
            })
          } else{
            this.$message({
              message: resp.data.msg,
              type: 'error'
            })
          }
        })
    }
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
