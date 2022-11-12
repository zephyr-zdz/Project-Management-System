<template>
  <div>
    <el-table
      :data="invitationData"
      style="width: 100%"
      :header-row-style="{height: '100px'}"
      stripe
      border>
      <el-table-column
        label="编号"
        prop="id"
        width="50px">
      </el-table-column>
      <el-table-column
        label="昵称"
        prop="name">
      </el-table-column>
      <el-table-column
        label="项目名称"
        prop="projectName">
      </el-table-column>
      <el-table-column
        label="操作"
        width="160px">
        <template v-slot="scope">
          <el-button-group>
            <el-button
              size="medium"
              type="primary" plain
              @click="acceptInvitation(scope.row.id)">
              加入
            </el-button>
            <el-button
              size="medium"
              plain
              @click="refuseInvitation(scope.row.id)">
              拒绝
            </el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: "InvitationList",
  data () {
    return {
      invitationData: [
        {
          id: 1,
          name: "vdsbssfb",
          projectName: "vdsbssfb",
        },
        {
          id: 2,
          name: "vdsbssfb",
          projectName: "vdsbssfb",
        }
      ],
    }
  },
  mounted: function() {
    this.loadInvitations();
  },
  methods: {
    async loadInvitations() {
      console.log("loadInvitations called")
      let _this = this
      const params = new FormData();
      params.append("user_id", this.$store.state.user.id);
      this.$axios
        .get('/user/invitation', params)
        .then(resp => {
        if (resp && resp.status === 200) {
          _this.invitationData = resp.data
        }
      })
    },
    acceptInvitation(invitationId) {
      let params = new FormData();
      params.append('invitationId', invitationId);
      this.$axios
        .post('/user/invitation/accept', params)
        .then(resp => {
          if (resp) {
            if (resp.data.code === 0) {
              this.$alert(resp.data.msg, '结果', {
                confirmButtonText: '确定'
              });
              location.reload();
            } else if (resp.data.code === 1) {
              this.$alert(resp.data.msg, '结果', {
                confirmButtonText: '确定'
              });
            } else {
              this.$alert('Error', {confirmButtonText: 'OK'})
            }
          }
        })
    },
    refuseInvitation(invitationId) {
      let params = new FormData();
      params.append('invitationId', invitationId);
      this.$axios
        .post('/user/invitation/refuse', params)
        .then(resp => {
          if (resp) {
            if (resp.data.code === 0) {
              this.$alert(resp.data.msg, '结果', {
                confirmButtonText: '确定'
              });
              location.reload();
            } else if (resp.data.code === 1) {
              this.$alert(resp.data.msg, '结果', {
                confirmButtonText: '确定'
              });
            } else {
              this.$alert('Error', {confirmButtonText: 'OK'})
            }
          }
        })
    }
  }
}
</script>

<style scoped>

</style>
