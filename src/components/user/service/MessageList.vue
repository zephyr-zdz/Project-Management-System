<template>
    <el-table
      :data="messageData"
      style="width: 100%"
      :row-class-name="tableRowClassName">
      <el-table-column
        prop="project"
        label="所属项目"
        width="180"
        fixed="left">
      </el-table-column>
      <el-table-column
        prop="date"
        label="日期"
        width="180">
      </el-table-column>
      <el-table-column
        prop="name"
        label="任务名称"
        width="180">
      </el-table-column>
      <el-table-column
        prop="assigner"
        label="任务指派人"
        width="180">
      </el-table-column>
      <el-table-column
        prop="intro"
        label="任务简介">
      </el-table-column>
      <el-table-column
      fixed="right"
      label="操作"
      width="120">
      <template v-slot="scope">
        <el-button :plain="true"
          @click="readMessage(scope.$index, scope.row)"
          type="text"
          size="small">
          已读
        </el-button>
      </template>
      </el-table-column>
    </el-table>
  </template>

  <style>
    .el-table .unread-row {
      background: oldlace;
    }
  </style>

  <script>
    export default {
      name:"MessageList",
      data() {
        return {
          messageData: [],
          messageStatus: {
            messageName: '',
            messageStatus: ''
          },
        }
      },
      mounted () {
      this.getMessage()
      },
      methods: {
        getMessage(){
          this.$axios.get('/message/list', {params: {user_id: this.$store.getters.user_id}}).then(res => {
          this.messageData = res.data.data
      })
        },
        readMessage(index, row){
          row.status=1
        this.$refs.messageList.validate((valid) => {
          if (valid) {
            this.$axios
              .post('/message/list', {
                messageName: this.MessageList.messageName,
                messageStatus: this.MessageList.messageStatus
              })
              .then(resp => {
                console.log(resp.data)
                if (resp.data.code === 0) {
                  this.$message({
                    message: '消息已读。',
                    type: 'success'
                  })
                  this.clear()
                } else {
                  this.$message({
                    message: '消息已读失败。',
                    type: 'error'
                  })
                }
              })
          }
      })

        },
        tableRowClassName({row, rowIndex}) {
          if (row.status === 0) {
            this.messageStatus=0;
            return 'unread-row';
          }
          this.messageStatus=1;
          return '';
        }
      },

    }
  </script>
