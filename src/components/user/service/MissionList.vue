<template>
  <div>
    <el-button @click="dialogVisible = true" type="primary" icon="el-icon-view" plain>任务列表</el-button>
    <el-dialog title="任务列表" :visible.sync="dialogVisible">
      <el-table
        ref="filterTable"
        :data="allIssueData"
        style="width: 100%" >
        <el-table-column
          type="expand"
          fixed="left">
          <template v-slot="props">
            <p style="width: 80%; line-height: 15pt; position: relative; left: 10%; margin: 0">{{props.row.description}}</p>
          </template>
        </el-table-column>
        <el-table-column
          prop="date"
          label="任务开始日期"
          width="180">
        </el-table-column>
        <el-table-column
          prop="title"
          label="任务名称"
          width="180">
        </el-table-column>
        <el-table-column
          prop="reviewerName"
          label="任务指派人"
          width="180">
        </el-table-column>
        <el-table-column
          prop="assigneeName"
          label="任务执行人"
          width="180"
          column-key="assigneeName"
          :filters="setData"
          :filter-method="filterHandler">
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
                slot="reference"
                :disabled="name !== scope.row.reviewerName && name !== scope.row.assigneexName">
                <i class="el-icon-close" style="color: #ff4949" v-if="scope.row.status === 'closed'"></i>
                <i class="el-icon-check" style="color: #13ce66" v-if="scope.row.status === 'done'"></i>
                <i class="el-icon-d-arrow-right" style="color: #38B2FF" v-if="scope.row.status === 'open'"></i>
                <span>{{ $getLabel(iconList, scope.row.status, 'status', 'text') }}</span>
              </el-button>
            </el-popover>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import {getLabel} from "../../../utils/methods";
import Vue from "vue";
Vue.prototype.$getLabel = getLabel;
    export default {
      name: "MissionList",
      data() {
        return {
          dialogVisible: false,
          name: '',
          setData: [],
          getAllAssignee: [],
          allIssueData: [],
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
        }
      },
      mounted () {
        this.getMission()
      },
      methods: {
        filterHandler(value, row, column) {
          const property = column['property'];
          return row[property] === value;
        },
        async getMission() {
          let jsonObj = JSON.parse(window.sessionStorage.user);
          this.name = jsonObj.user.name;
          let url = '/project/issue/list/' + this.$route.params.id
          this.$axios
            .get(url)
            .then(resp => {
              this.allIssueData = resp.data.data
              this.visible = new Array(this.allIssueData.length).fill(false)
              for (let i = 0; i < this.allIssueData.length; i++) {
                this.getAllAssignee.push({text: this.allIssueData[i].assigneeName, value: this.allIssueData[i].assigneeName})
                this.statusEdit[i] = this.allIssueData[i].status
              }
              const res = new Map();
              this.setData = this.getAllAssignee.filter((item) => !res.has(item.value) && res.set(item.value, 1))
              //console.log(this.getAllAssignee)
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
        },
      }
    }
  </script>
