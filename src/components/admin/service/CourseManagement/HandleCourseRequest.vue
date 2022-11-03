<template>
  <el-container>
    <el-header>审批课程</el-header>
    <el-main>
        <el-table
          :data="requestData"
          style="width: 90%">
          <el-table-column
            label="编号"
            prop="requestId"
            width="50">
          </el-table-column>
          <el-table-column
            prop="courseTeacherNumber"
            label="教师"
            width="100">
            <template slot-scope="scope">
              {{ $getLabel(allTeacher, scope.row.courseTeacherNumber, 'number', 'name') }}
            </template>
          </el-table-column>
          <el-table-column
            label="课程名"
            prop="courseName">
          </el-table-column>
          <el-table-column
            label="开课学期"
            prop="courseTerm">
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
            width="110">
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
                @click="approveRequest(scope.row.requestId)">
                通过
              </el-button>
              <el-dialog
                title="通过课程"
                :visible.sync="approveDialogVisible"
                width="30%"
                center>
                <el-form
                  :model="approveForm"
                  ref="approveForm"
                  :rules="approveFormRules">
                  <el-form-item  prop="courseCode">
                    <el-input
                      placeholder="指定选课序号"
                      v-model="approveForm.courseCode"
                    ></el-input>
                  </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                  <el-button @click="clearApproveForm()">取 消</el-button>
                  <el-button @click="submitRequestedCourse()">确 定</el-button>
                </span>
              </el-dialog>
              <el-button
                size="normal"
                type="danger"
                :disabled="scope.row.requestStatus != 2 ? true:false"
                @click="declineRequest(scope.row.requestId)">
                拒绝
              </el-button>
            </template>
          </el-table-column>
        </el-table>
    </el-main>
  </el-container>
</template>

<script>
import {getLabel} from '@/utils/methods'
import Vue from "vue";
Vue.prototype.$getLabel = getLabel
export default {
  name: "HandleCourseRequest",
  data() {
    return {
      popoverTemp: '',
      allType: [
        { number: 1, name: '通用课程'},
        { number: 2, name: '专业课程'},
        { number: 3, name: '面向部分专业课程'},
      ],
      allTeacher: [
        { number: "23052222", name:'教师1'},
        { number: "20220319", name:'教师2'},
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
        { number: 2, name:'待处理'},
        { number: 1, name:'已通过'},
        { number: 0, name:'已拒绝'},
      ],
      requestData: [{
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
      approveDialogVisible: false,
      approveForm: {
        requestId: 2,
        courseCode: '',
        requestStatus: 2
      },
      approveFormRules: {
        courseCode: [
          {required: true, message: '必填', trigger: ['blur', 'change']},
          {
            pattern: /\d\d\d\d\d\d+(\.\d\d)/,
            message: '注意选课序号格式',
            trigger: 'blur'
          }
        ],
      }
    }
  },
  mounted: function() {
    this.loadTeachers()
    this.loadDepartments()
    this.loadClassrooms()
    this.loadRequests()
  },
  computed: {
    popoverData: {
      get: function() {
        return this.popoverTemp;
      }
    }
  },
  methods: {
    async loadTeachers() {
      let _this = this
      this.$axios.get('/users/2').then(resp => {
        if (resp && resp.status === 200) {
          _this.allTeacher = resp.data
        }
      })
    },
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
      let _this = this
      this.$axios.get('/course-requests').then(resp => {
        if (resp && resp.status === 200) {
          _this.requestData = resp.data
        }
      })
    },
    approveRequest(requestId) {
      console.log("approve " + requestId)
      this.approveDialogVisible = true
      this.approveForm.requestId = requestId
      this.approveForm.requestStatus = 1
    },
    submitRequestedCourse() {
      this.approveDialogVisible = false
      this.$refs["approveForm"].validate((valid) => {
        if (valid) {
          console.log("on Submit Requested Course")
          console.log(this.approveForm)
          let params = new URLSearchParams();
          params.append('requestId', this.approveForm.requestId);
          params.append('courseCode', this.approveForm.courseCode);
          params.append('requestStatus', this.approveForm.requestStatus) // 1 is int
          this.$axios
            .post('/handle-course-request', params)
            .then(resp => {
              if (resp) {
                if (resp.data.code === 200) {
                  this.$alert(resp.data.msg, '结果', {
                    confirmButtonText: '确定'
                  });
                } else if (resp.data.code === 100) {
                  this.$alert(resp.data.msg, '结果', {
                    confirmButtonText: '确定'
                  });
                } else {
                  this.$alert('Error', {confirmButtonText: 'OK'})
                }
              }
              this.clearApproveForm()
            })
        } else {
          this.$alert('请按提示填写正确内容', 'Warning', {
            confirmButtonText: '确定',
          });
          return false;
        }
      })
    },
    clearApproveForm() {
      this.approveForm.courseCode = ''
      this.approveForm.requestId = ''
      this.approveForm.requestStatus = ''
      this.approveDialogVisible = false
    },
    declineRequest(requestId) {
      console.log("decline " + requestId)
      let params = new URLSearchParams();
      this.approveForm.requestStatus = 0
      console.log(this.approveForm.requestStatus)
      params.append('requestId', requestId);
      params.append('courseCode', '') // empty string;
      params.append('requestStatus', this.approveForm.requestStatus) // 1 is int
      this.$axios
        .post('/handle-course-request', params)
        .then(resp => {
          if (resp) {
            if (resp.data.code === 200) {
              this.$alert(resp.data.msg, '结果', {
                confirmButtonText: '确定'
              });
            } else if (resp.data.code === 100) {
              this.$alert(resp.data.msg, '结果', {
                confirmButtonText: '确定'
              });
            } else {
              this.$alert('Error', {confirmButtonText: 'OK'})
            }
          }
          this.clearApproveForm()
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
.el-header {
  border-bottom: #1f1e33;
  color: #ffffff;
  text-align: center;
  font-size: 20px;
  line-height: 60px;
}

.el-main {
  position: absolute;
  left: 200px;
  right: 0;
  top: 60px;
  bottom: 0;
  overflow-y: scroll;

  background-color: ghostwhite;
  color: #333;
  text-align: center;
}
</style>
