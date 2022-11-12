<template>
  <div>
    <el-button type="primary" @click="dialogFormVisible = true"></el-button>
    <el-dialog
      title="邀请成员"
      :visible.sync="dialogFormVisible"
      class="dialog">
      <el-form
        :model="inviteForm"
        ref="inviteForm"
        :rules="rules">
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
          prop="email"
          label="邮箱"
          width="auto">
        </el-table-column>
        <el-table-column
          prop="name"
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
      inviteForm: {
        email: '',
      },
      rules: {
        email: [
          {
            required: true,
            message: '请输入邮箱/昵称',
            trigger: 'blur',
          }
        ],
      },
      userList: [
        {
          email: 'bdfbsv@jkk.com',
          name: 'dsbdb',
        },
      ]
    }
  },
  methods: {
    onSubmit() {
      this.$refs.InviteForm.validate((valid) => {
        if (valid) {
          const param = new FormData();
          param.append('email', this.inviteForm.email)
          this.$axios
            .post('project/search', param)
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
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    selectUser (row) {
      let jsonObj = JSON.parse(window.localStorage.user);
      let name = jsonObj.user.username
      const param = new FormData();
      param.append('user', name)
      param.append('email', row.email)
      this.$axios
        .post('project/invite', param)
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
