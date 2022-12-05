<template>
    <el-table
      :data="messageData"
      style="width: 100%"
      :row-class-name="tableRowClassName">
      <el-table-column
        prop="date"
        label="日期"
        width="180">
      </el-table-column>
      <el-table-column
        prop="project"
        label="所属项目"
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
      fixed="operation"
      label="操作"
      width="120">
      <template slot-scope="scope">
        <el-button
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

        },
        tableRowClassName({row, rowIndex}) {
          if (row.status === 0) {
            return 'unread-row';
          }
          return '';
        }
      },
     
    }
  </script>