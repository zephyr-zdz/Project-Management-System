<template>
  <el-table
    :data="messageData"
    style="width: 100%"
    :header-row-style="{height: '60px'}"
    stripe>
    <el-table-column
      type="expand"
      fixed="left">
      <template v-slot="props">
        <p style="width: 80%; line-height: 15pt; position: relative; left: 10%; margin: 0">{{props.row.description}}</p>
      </template>
    </el-table-column>
    <el-table-column
      prop="projectName"
      label="所属项目"
      fixed="left">
    </el-table-column>
    <el-table-column
      prop="date"
      label="日期"
      sortable>
    </el-table-column>
    <el-table-column
      prop="title"
      label="任务名称"
      width="180">
    </el-table-column>
    <el-table-column
      prop="assigneeName"
      label="任务执行人"
      width="180">
    </el-table-column>
    <el-table-column
      label="状态"
      width="100"
      fixed="right">
      <template v-slot="scope">
        <el-popover
          placement="top"
          width="160"
          :v-model="visible[scope.$index]">
          <el-radio v-model="statusEdit[scope.$index]" label="open">
            <i class="el-icon-d-arrow-right" style="color: #38B2FF"></i> 开发中
          </el-radio>
          <el-radio v-model="statusEdit[scope.$index]" label="done">
            <i class="el-icon-check" style="color: #13ce66"></i> 已完成
          </el-radio>
          <el-radio v-model="statusEdit[scope.$index]" label="closed">
            <i class="el-icon-close" style="color: #ff4949"></i> 已关闭
          </el-radio>
          <div style="text-align: right; margin: 0">
            <el-button size="mini" type="text" @click="visible[scope.$index] = false">取消</el-button>
            <el-button type="primary" size="mini" @click="changeStatus(scope)">确定</el-button>
          </div>
          <el-button
            style="padding: 3px"
            type="text"
            slot="reference">
            <i class="el-icon-close" style="color: #ff4949" v-if="scope.row.status === 'closed'"></i>
            <i class="el-icon-check" style="color: #13ce66" v-if="scope.row.status === 'done'"></i>
            <i class="el-icon-d-arrow-right" style="color: #38B2FF" v-if="scope.row.status === 'open'"></i>
            <span>{{ $getLabel(iconList, scope.row.status, 'status', 'text') }}</span>
          </el-button>
        </el-popover>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import {getLabel} from "../../../utils/methods";
import Vue from "vue";
Vue.prototype.$getLabel = getLabel;
export default {
  name: "MyAssignedList",
  data() {
    return {
      visible: [],
      statusEdit: [],
      iconList: [
        {
          status: "open",
          text: "开发中"
        },
        {
          status: "closed",
          text: "已关闭"
        },
        {
          status: "done",
          text: "已完成"
        }
      ],
      messageData: [],
    }
  },
  mounted () {
    this.getMessage()
  },
  methods: {
    getMessage(){
      let jsonObj = JSON.parse(window.sessionStorage.user);
      let id = jsonObj.user.userid
      let url = '/user/issue/list/assigning/' + id
      this.$axios
        .get(url)
        .then(res => {
          this.messageData = res.data.data
          this.visible = new Array(this.messageData.length).fill(false)
          for (let i = 0; i < this.messageData.length; i++) {
            this.statusEdit[i] = this.messageData[i].status
          }
        })
    },
    changeStatus(scope) {
      this.visible[scope.$index] = false
      let data = {
        id: scope.row.id,
        status: this.statusEdit[scope.$index]
      }
      this.$axios
        .post('/project/issue/edit/', data)
        .then(res => {
          if (res.data.code === 0) {
            this.$message({
              message: res.data.msg,
              type: 'success'
            })
            this.getMessage()
          } else {
            this.$message({
              message: res.data.msg,
              type: 'error'
            })
          }
          this.getMessage()
        })
        .catch(error => {
          this.$message({
            message: error,
            type: 'error'
          })
        })
    }
  },
}
</script>

<style scoped>

</style>
