<template>
  <div>
    <el-table
      :data="requestData"
      style="width: 100%"
      border
      stripe>
      <el-table-column
        label="编号"
        prop="requestId"
        width="50px">
      </el-table-column>
      <el-table-column
        label="课程名"
        prop="courseName">
      </el-table-column>
      <el-table-column
        prop="courseTerm"
        label="开课学期"
        width="100">
      </el-table-column>
      <el-table-column
        prop="courseDepartmentNumber"
        label="专业"
        width="100">
        <template slot-scope="scope">
          {{ $getLabel(allDepartment, scope.row.courseDepartmentNumber, 'departmentNumber', 'departmentName') }}
        </template>
      </el-table-column>
      <el-table-column
        prop="courseCredit"
        label="学分"
        width="50">
      </el-table-column>
      <el-table-column
        prop="courseStartTime"
        label="时间"
        width="120">
        <template slot-scope="scope">
          周{{ scope.row.courseDay }}，
          {{ scope.row.courseStartTime }} - {{ scope.row.courseEndTime }}
        </template>
      </el-table-column>
      <el-table-column
        prop="courseClassroomId"
        label="教室"
        width="100">
        <template slot-scope="scope">
          {{ $getLabel(allClassrooms, scope.row.courseClassroomId, 'id', 'classroom') }}
        </template>
      </el-table-column>
      <el-table-column
        prop="courseCapacity"
        label="容量"
        width="80">
      </el-table-column>
      <el-table-column
        prop="courseType"
        label="课程类型"
        width="auto">
        <template v-slot="scope">
          <span v-if="scope.row.courseType%3">
            {{ $getLabel(allType, scope.row.courseType, 'number', 'name') }}
          </span>
          <span v-if="scope.row.courseType === 3">
            <el-popover
              placement="bottom-start"
              trigger="click"
              offset="0"
              width="150"
            >
              <div v-html="popoverData">?</div>
              <el-button
                type="text"
                slot="reference"
                style="width: 10px; color: #606266"
                @click="display(scope.row.courseAccess)">
                面向部分专业
              </el-button>
            </el-popover>
            </span>
        </template>
      </el-table-column>
      <el-table-column
        prop="requestStatus"
        label="状态"
        width="80"
        fixed="right">
        <template slot-scope="scope">
          {{ $getLabel(allStatus, scope.row.requestStatus, 'number', 'name') }}
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import {getLabel} from '@/utils/methods'
import Vue from "vue";
Vue.prototype.$getLabel = getLabel
export default {
  name: "DisplayCourseRequest",
  data() {
    return {
      popoverTemp: '',
      allType: [
        { number: 1, name: '通用课程'},
        { number: 2, name: '专业课程'},
        { number: 3, name: '面向部分专业课程'},
      ],
      allDepartment: [
        {
          departmentNumber: 1,
          departmentName: "Dpt1"
        },
        {
          departmentNumber: 2,
          departmentName: "Dpt2"
        },
        {
          departmentNumber: 3,
          departmentName: "Dpt3"
        },
      ],
      allClassrooms: [
        {
          id: 1,
          classroom: "H3201",
          isAvailable: 1
        },
        {
          "id": 2,
          "classroom": "H3401",
          "isAvailable": 1
        }
      ],
      allStatus: [
        {number: 2, name: '待处理'},
        {number: 1, name: '已通过'},
        {number: 0, name: '已拒绝'},
      ],
      requestData: [
        {
        "requestId": 1,
        "requestStatus": 2,
        "courseName": "高级英语",
        "courseDepartmentNumber": 3,
        "courseCredit": 2,
        "courseDay": 3,
        "courseStartTime": 4,
        "courseEndTime": 5,
        "courseClassroomId": 2,
        "courseCapacity": 30,
        "courseTeacherNumber": "23052222"
      },
        {
          "requestId": 2,
          "requestStatus": 2,
          "courseName": "高级英语",
          "courseDepartmentNumber": 3,
          "courseCredit": 2,
          "courseDay": 3,
          "courseStartTime": 4,
          "courseEndTime": 5,
          "courseClassroomId": 2,
          "courseCapacity": 30,
          "courseTeacherNumber": "23052223"
        },
      ],
    }
  },
  mounted: function() {
    this.loadDepartments()
    this.loadClassrooms()
    this.loadRequests()
  },
  computed: { // for real-time rendering table data
    popoverData: {
      get: function() {
        return this.popoverTemp;
      }
    }
  },
  methods: {
    async loadDepartments() {
      let _this = this
      this.$axios.get('/departments').then(resp => {
        if (resp && resp.status === 200) {
          _this.allDepartment = resp.data
        }
      })
    },
    async loadClassrooms() {
      let _this = this
      this.$axios.get('/classrooms').then(resp => {
        if (resp && resp.status === 200) {
          _this.allClassrooms = resp.data
        }
      })
    },
    async loadRequests() {
      console.log("loadRequests called")
      let jsonObj = JSON.parse(window.localStorage.user);
      let no = jsonObj.user.number
      let _this = this
      let url = '/course-requests/' + no
      this.$axios.get(url).then(resp => {
        if (resp && resp.status === 200) {
          _this.requestData = resp.data
        }
      })
    },
    display(access) {
      let _this = this
      _this.popoverTemp = ""
      access.split(',').forEach(function (item, index) {
        _this.popoverTemp += getLabel(_this.allDepartment, parseInt(item), 'departmentNumber', 'departmentName') + '<br>'
      });

    },
  }
}
</script>

<style scoped>

</style>
