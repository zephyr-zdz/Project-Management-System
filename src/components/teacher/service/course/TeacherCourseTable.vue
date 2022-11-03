<template>
  <div>
    <edit-course-form ref="edit"></edit-course-form>
    <el-row>
      <el-col :span="12">
        <el-form
          :model="searchCourseByTerm"
          ref="searchCourseByTerm"
          :rules="searchByTermRule">
          <el-form-item
            prop="courseTerm">
            <el-date-picker
              v-model="searchCourseByTerm.courseYear"
              value-format="yyyy"
              type="year"
              placeholder="学年">
            </el-date-picker>
            <el-select v-model="searchCourseByTerm.courseTerm"
                       style="width: 100px"
                       placeholder="学期">
              <el-option
                v-for="item in termOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <el-button
              style="background-color: #F5F7FA"
              icon="el-icon-search"
              @click="searchCourseTerm()">
            </el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-table
      :data="allCourse"
      border
      stripe
      style="width: 100%">
      <el-table-column
        fixed
        prop="courseCode"
        label="选课序号"
        width="90">
      </el-table-column>
      <el-table-column
        label="开课学期"
        width="100">
        <template v-slot="scope">
          {{ $splitTerm(scope.row.courseTerm) }}
        </template>
      </el-table-column>
      <el-table-column
        prop="courseName"
        label="课程名"
        width="auto">
      </el-table-column>
      <el-table-column
        prop="courseDepartment"
        label="开课专业"
        width="100">
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
      <el-table-column
        prop="courseType"
        label="课程类型"
        width="auto">
        <template v-slot="scope">
        <span v-if="scope.row.courseType%3">
          {{ $getLabel(allType, scope.row.courseType, 'number', 'name') }}
        </span>
            <el-button
              type="text"
              slot="reference"
              style="width: 10px; color: #606266"
              v-if="Math.floor(scope.row.courseType/3)">
              面向部分专业
            </el-button>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="100">
        <template v-slot="scope">
          <el-popconfirm
            title="此操作将永久删除该课程，是否继续?"
            @confirm="deleteCourse(scope.row.courseCode,scope.row.courseTerm)">
            <el-button slot="reference" type="text" size="small">删除</el-button>
          </el-popconfirm>
          <el-button @click="editCourse(scope.row)" type="text" size="small" >编辑</el-button>
          <el-button size="mini" icon="el-icon-user" @click="viewCourseRoster(scope.row.courseCode, scope.row.courseTerm)" circle></el-button>
        </template>
      </el-table-column>
    </el-table>
    <display-course-roster ref="showForm"></display-course-roster>
  </div>
</template>

<script>
import {getLabel} from '@/utils/methods';
import {splitTerm} from "@/utils/methods";
import Vue from "vue";
import EditCourseForm from "./EditCourseForm";
import DisplayCourseRoster from "../../../common/DisplayCourseRoster";
Vue.prototype.$getLabel = getLabel
Vue.prototype.$splitTerm = splitTerm
export default {
  name: "TeacherCourseTable",
  components: {EditCourseForm, DisplayCourseRoster},
  data() {
    return {
      allType: [
        { number: 1, name: '通用课程'},
        { number: 2, name: '专业课程'},
        { number: 3, name: '面向部分专业课程'},
      ],
      termOptions: [
        { label: '春', value: 1},
        { label: '秋', value: 2},
      ],
      searchCourseByTerm: {
        courseYear: '',
        courseTerm: '',
      },
      searchByTermRule: {
        courseTerm: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ],
        courseYear: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ],
      },
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
      allCourseTemp: [
        {
          "id": 2,
          "courseCode": "111111.01",
          "courseName": "课程名1",
          "courseDepartment": 2,
          "courseCredit": 3,
          "courseDay": 5,
          "courseStartTime": 4,
          "courseEndTime": 5,
          "courseTerm": "2023-1",
          "courseClassroom": 2,
          "courseCapacity": 50,
          "courseTeacherNumber": "20220403",
          "courseType": 3,
          "courseAccess": [1,2]
        },
        {
          "id": 1,
          "courseCode": "222222.01",
          "courseName": "课程名2",
          "courseDepartment": 1,
          "courseCredit": 2,
          "courseDay": 4,
          "courseStartTime": 1,
          "courseEndTime": 3,
          "courseTerm": "2021-2",
          "courseClassroom": 1,
          "courseCapacity": 40,
          "courseTeacherNumber": "20220319",
          "courseType": 2,
        }
      ]
    }
  },
  computed: { // for real-time rendering table data
    allCourse: {
      get: function() {
        return this.allCourseTemp;
      }
    }
  },
  mounted: function() {
    this.loadTeachers()
    this.loadDepartments()
    this.loadClassrooms()
    this.loadCourses()
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
    async loadCourses() {
      console.log("loadCourses called")
      let jsonObj = JSON.parse(window.localStorage.user);
      let no = jsonObj.user.number
      let _this = this
      let url = '/courses/teacher/' + no
      this.$axios.get(url).then(resp => {
        console.log(resp.data)
        _this.allCourseTemp = resp.data
      })
    },
    deleteCourse(courseCode,courseTerm) {
      console.log(courseCode)
      let params = new URLSearchParams();
      params.append('courseCode', courseCode);
      params.append('courseTerm', courseTerm);
      this.$axios
        .post('/delete-course', params).then(resp => {
        if (resp && resp.status === 200) {
          console.log(resp)
          this.$message({
            type: 'success',
            message: resp.data.msg
          })
          this.loadCourses()
        } else {
          this.$message({
            type: 'error',
            message: "未知错误"
          })
        }
      })
    },
    editCourse(course) {
      console.log(course.courseCode)
      this.$refs.edit.editFormVisible = true
      this.$refs.edit.editCourse.courseCode = course.courseCode
      this.$refs.edit.editCourse.courseName = course.courseName
      this.$refs.edit.editCourse.courseDepartment = course.courseDepartment
      this.$refs.edit.editCourse.courseType = course.courseType
      let y = course.courseTerm.split('-')
      this.$refs.edit.editCourse.courseYear = y[y.length - 2]
      this.$refs.edit.editCourse.courseTerm = y[y.length - 1]
      this.$refs.edit.editCourse.courseTeacherNumber = course.courseTeacherNumber
      this.$refs.edit.editCourse.courseCredit = course.courseCredit
      this.$refs.edit.editCourse.courseTime = {
        courseDay: course.courseDay,
        courseStartTime: course.courseStartTime,
        courseEndTime: course.courseEndTime,
      }
      this.$refs.edit.editCourse.courseClassroom = course.courseClassroom
      this.$refs.edit.editCourse.courseCapacity = course.courseCapacity
      if (course.courseType === 3) {
        let params = new URLSearchParams();
        params.append('courseCode', course.courseCode);
        params.append('courseTerm', course.courseTerm);
        this.$axios
          .post('/get-course-access',params)
          .then(resp => {
            this.$refs.edit.editCourse.courseAccess = resp.data
          })
      }
    },
    async display(type,code,term,index) {
      let re = ''
      console.log("course access", code)
      let params = new URLSearchParams();
      params.append('courseCode', code);
      params.append('courseTerm', term);
      await this.$axios.post('/get-course-access', params)
        .then(resp => {
          let ch = resp.data
          re = getLabel(this.allDepartment, ch[0], 'departmentNumber', 'departmentName')
          for (var i = 1; i < ch.length; i++) {
            re = re + ',' + getLabel(this.allDepartment, ch[i], 'departmentNumber', 'departmentName')
          }
          re = re.replace(/\,/g,'<br/>')
          this.popoverdata = re;
          this.visible[index] = true
        })
    },
    searchCourseTerm() {
      this.allCourseTemp = null
      let jsonObj = JSON.parse(window.localStorage.user);
      let no = jsonObj.user.number
      let term = this.searchCourseByTerm.courseYear + '-' + this.searchCourseByTerm.courseTerm
      let _this = this
      let params = new URLSearchParams();
      params.append('teacherNo', no);
      params.append('term', term);
      this.$axios.post('/courses/teacher', params)
        .then(resp => {
          if (resp && resp.status === 200) {
            _this.allCourseTemp = resp.data
          } else {
            Vue.prototype.$message({
              message: '查询失败',
              type: 'error'
            });
          }
        })
    },
    viewCourseRoster(code,term) {
      this.$refs.showForm.tableVisible = true
      let params = new URLSearchParams();
      params.append('courseCode', code);
      params.append('courseTerm', term);
      this.$axios
        .post('/course-roster',params)
        .then(resp => {
          this.$refs.showForm.tableData = resp.data
        })
    }
  }
}
</script>

<style scoped>

</style>
