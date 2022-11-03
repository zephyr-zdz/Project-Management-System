<template>
  <el-table
    :data="requestData"
    style="width: 90%">
    <el-table-column
      label="编号"
      prop="id"
      width="50px">
    </el-table-column>
    <el-table-column
      prop="studentNumber"
      label="学号"
      width="100">
    </el-table-column>
    <el-table-column
      label="申请人"
      prop="studentName">
    </el-table-column>
    <el-table-column
      prop="requestNote"
      label="申请理由"
      width="auto">
    </el-table-column>
    <el-table-column
      prop="requestStatus"
      label="状态"
      width="80">
      <template slot-scope="scope">
        {{ $getLabel(allStatus, scope.row.requestStatus, 'number', 'name') }}
      </template>
    </el-table-column>
    <el-table-column
      align="right">
      <template slot-scope="scope">
        <el-button
          size="normal"
          type="success"
          :disabled="scope.row.requestStatus != 2 ? true:false"
          @click="handleRequest(scope.row.id, 1)">
          通过
        </el-button>
        <el-button
          size="normal"
          type="danger"
          :disabled="scope.row.requestStatus != 2 ? true:false"
          @click="handleRequest(scope.row.id, 0)">
          拒绝
        </el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import {getLabel} from '@/utils/methods'
import Vue from "vue";
Vue.prototype.$getLabel = getLabel
export default {
  name: "CourseSelectionRequestTable",
  data() {
    return {
      allStatus: [
        { number: 2, name:'待处理'},
        { number: 1, name:'已通过'},
        { number: 0, name:'已拒绝'},
      ],
      requestData: [{
        "id": 1,
        "studentNumber": "111111",
        "studentName": "John",
        "courseCode": "123123.01",
        "courseTerm": "2022-1",
        "requestNote": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        "requestStatus": 2
        }
      ],
    }
  },
  mounted: function() {
    this.loadRequests();
  },
  methods: {
    async loadRequests() {
      console.log("loadRequests called")
      let _this = this
      this.$axios.get('/course-selection-request').then(resp => {
        if (resp && resp.status === 200) {
          _this.requestData = resp.data
        }
      })
    },
    handleRequest(requestId, status) {
      console.log("set " + requestId + " as " + status)
      let params = new URLSearchParams();
      params.append('id', requestId);
      params.append('status', status) // 1 is int
      this.$axios
        .post('/handle-course-selection-request', params)
        .then(resp => {
          if (resp) {
            if (resp.data.code === 200) {
              this.$alert(resp.data.msg, '结果', {
                confirmButtonText: '确定'
              });
              location.reload();
            } else if (resp.data.code === 100) {
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
