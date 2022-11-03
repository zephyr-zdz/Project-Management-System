<template>
<div>
  <el-form
    :model="requestForm"
    ref="requestForm"
    :rules="requestFormRules">
    <el-form-item
      label="课程序号"
      prop="courseCode"
      :label-width="formLabelWidth">
      <el-input v-model="requestForm.courseCode" placeholder="课程序号" class="input-with-select">
        <el-button slot="append" icon="el-icon-search" @click="search()"></el-button>
      </el-input>
    </el-form-item>
    <el-form-item
      label="选择课程"
      prop="courseTerm"
      :label-width="formLabelWidth">
      <el-table
        :data="courseList"
        border
        style="width: 100%"
        highlight-current-row
        @current-change="selectCourse">
        <el-table-column
          fixed
          prop="courseCode"
          label="选课序号"
          width="90">
        </el-table-column>
        <el-table-column
          prop="courseName"
          label="课程名"
          width="auto">
        </el-table-column>
        <el-table-column
          prop="courseDepartment"
          label="开课专业"
          width="80">
          <template v-slot="scope">
            {{ $getLabel(allDepartment, scope.row.courseDepartment, 'departmentNumber', 'departmentName') }}
          </template>
        </el-table-column>
        <el-table-column
          prop="courseTeacherNumber"
          label="教师"
          width="100">
          <template v-slot="scope">
            {{ $getLabel(allTeacher, scope.row.courseTeacherNumber, 'number', 'name') }}
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
          width="100">
          <template v-slot="scope">
            周{{ scope.row.courseDay }}，
            {{ scope.row.courseStartTime }} - {{ scope.row.courseEndTime }}
          </template>
        </el-table-column>
        <el-table-column
          label="开课学期"
          width="80">
          <template v-slot="scope">
            {{ $splitTerm(scope.row.courseTerm) }}
          </template>
        </el-table-column>
        <el-table-column
          prop="courseClassroom"
          label="教室"
          width="100">
          <template v-slot="scope">
            {{ $getLabel(allClassrooms, scope.row.courseClassroom, 'id', 'classroom') }}
          </template>
        </el-table-column>
        <el-table-column
          prop="courseCapacity"
          label="容量"
          width="80">
        </el-table-column>
      </el-table>
    </el-form-item>
    <el-form-item
      label="申请理由"
      prop="requestNote"
      :label-width="formLabelWidth">
      <el-input v-model="requestForm.requestNote" placeholder="申请理由" type="textarea" maxlength="200" show-word-limit>
      </el-input>
      <el-button type="text" @click="onSubmitRequest('requestForm')" style="position: absolute; right: 30px; top: 60px;">提交</el-button>
    </el-form-item>
  </el-form>

  <el-divider> <h3>已提交选课申请</h3></el-divider>

  <el-table
    :data="requestList"
    border
    stripe
    style="width: 100%">
    <el-table-column
      fixed
      prop="studentNumber"
      label="学号"
      width="90">
    </el-table-column>
    <el-table-column
      prop="studentName"
      label="姓名"
      width="auto">
    </el-table-column>
    <el-table-column
      prop="courseCode"
      label="选课序号"
      width="100">
    </el-table-column>
    <el-table-column
      prop="courseName"
      label="课程名"
      width="auto">
    </el-table-column>
    <el-table-column
      prop="requestNote"
      label="申请理由"
      width="auto">
    </el-table-column>
    <el-table-column
      prop="requestStatus"
      label="申请状态"
      width="80"
      fixed="right">
      <template v-slot="scope">
        {{ $getLabel(statusList, scope.row.requestStatus, 'value', 'label') }}
      </template>
    </el-table-column>
  </el-table>
</div>
</template>

<script>
import {getLabel} from '@/utils/methods';
import {splitTerm} from "@/utils/methods";
import Vue from "vue";
Vue.prototype.$getLabel = getLabel
Vue.prototype.$splitTerm = splitTerm
export default {
  name: "SelectionRequestForm",
  data() {
    return {
      formLabelWidth: '80px',
      courseList: [],
      allType: [
        { number: 1, name: '通用课程'},
        { number: 2, name: '专业课程'},
        { number: 3, name: '面向部分专业课程'},
      ],
      statusList: [
        { label: '已拒绝', value: 0 },
        { label: '已通过', value: 1 },
        { label: '待处理', value: 2 },
      ],
      allTeacher: [
        { number: "20220403", name:'教师1'},
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
      requestForm: {
        courseCode: '',
        courseTerm: '',
        requestNote: '',
        studentNumber: '',
      },
      requestFormRules: {
        courseCode: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ],
        courseTerm: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ],
        requestNote: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ],
      },
      requestList: [
        {
          studentNumber: '222222',
          studentName: 'hhhh',
          courseCode: '111111.01',
          courseName: '课程1',
          requestNote: '缺学分',
          requestStatus: 2,
        }
      ],
    }
  },
  mounted: function() {
    this.loadTeachers()
    this.loadDepartments()
    this.loadClassrooms()
    this.loadSelectionRequests()
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
    async loadSelectionRequests() {
      let _this = this
      let jsonObj = JSON.parse(window.localStorage.user)
      let no = jsonObj.user.number
      console.log('load selection request', no)
      let url = '/course-selection-request/' + no
      this.$axios.get(url).then(resp => {
        _this.requestList = resp.data
      })
    },
    search() {
      this.courseList = null
      let _this = this
      let params = new URLSearchParams();
      params.append('content', this.requestForm.courseCode);
      params.append('contentType', '1');
      this.$axios.post('/search-course-fuzzy', params)
        .then(resp => {
          _this.courseList = resp.data
      })
    },
    selectCourse(val) {
      console.log(val.courseTerm)
      this.requestForm.courseTerm = val.courseTerm
      this.requestForm.courseCode = val.courseCode
    },
    onSubmitRequest(formName) {
      console.log("onSubmit called")
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log("on Submit")
          let jsonObj = JSON.parse(window.localStorage.user);
          let no = jsonObj.user.number
          this.$axios
            .post('/course-selection-request', {
              studentNumber: no,
              courseCode: this.requestForm.courseCode,
              courseTerm: this.requestForm.courseTerm,
              requestNote: this.requestForm.requestNote,
            })
            .then(resp => {
              if (resp) {
                if (resp.data.code === 200) {
                  this.addFormVisible = false
                  this.$alert(resp.data.msg, '结果', {
                    confirmButtonText: '确定'
                  });
                  window.reload();
                } else if (resp.data.code === 100) {
                  this.$alert(resp.data.msg, '结果', {
                    confirmButtonText: '确定'
                  });
                } else {
                  this.$alert('Error', {confirmButtonText: 'OK'})
                }
              }
            })
        } else {
          console.log("not valid")
          this.$alert('请按提示填写正确内容', 'Warning', {
            confirmButtonText: '确定',
          });
          return false;
        }
      })
    }
  }
}
</script>

<style scoped>
.el-divider {
  margin: 4em 0 4em 0;
}
</style>
