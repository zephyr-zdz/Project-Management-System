<template>
  <div>
    <div class="course-management-wrapper">
      <add-course-form ref="editCourse"></add-course-form>
      <upload-course></upload-course>
    </div>
    <el-row>
      <el-col :span="12">
        <el-input
          placeholder="请输入 课程代码/课程名/教师 进行搜索"
          v-model="courseSearch.content"
          clearable>
          <el-select v-model="courseSearch.contentType" style="width: 120px" slot="prepend" placeholder="请选择">
            <el-option label="课程代码" :value="1"></el-option>
            <el-option label="课程名" :value="2"></el-option>
            <el-option label="授课教师" :value="3"></el-option>
          </el-select>
          <el-button slot="append" icon="el-icon-search" @click="searchCourse()"></el-button>
        </el-input>
      </el-col>
      <el-col :span="12">
        <el-form
          :model="searchCourseByTime"
          ref="searchCourseByTime"
          :rules="searchByTimeRule"
        >
          <el-form-item
            prop="courseTime">
            <el-select v-model="searchCourseByTime.courseTime.courseDay"
                       style="width: 100px"
                       clearable
                       placeholder="日">
              <el-option
                v-for="item in dayOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <el-select v-model="searchCourseByTime.courseTime.courseStartTime"
                       clearable
                       style="width: 100px"
                       placeholder="开始">
              <el-option
                v-for="item in scheduleOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <el-select v-model="searchCourseByTime.courseTime.courseEndTime"
                       clearable
                       style="width: 100px"
                       placeholder="结束">
              <el-option
                v-for="item in scheduleOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <el-button
              style="background-color: #F5F7FA"
              icon="el-icon-search"
              @click="filterCourseByTimeAndClassroom()"></el-button>
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
        prop="courseTerm"
        fixed
        label="开课学期"
        :filters="headFilter['courseTerm']"
        :filter-method="filterHandler"
        width="90">
        <template v-slot="scope">
          {{ $splitTerm(scope.row.courseTerm) }}
        </template>
      </el-table-column>
      <el-table-column
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
        :filters="headFilter['courseClassroom']"
        :filter-method="filterHandler"
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
          <span v-if="scope.row.courseType === 3">
            <el-popover
              placement="bottom"
              trigger="click"
              offset="0"
              width="150"
            >
              <el-button
                type="text"
                slot="reference"
                style="width: 10px; color: #606266"
                @click="display(scope.row.courseType,scope.row.courseCode,scope.row.courseTerm,scope.$index)">
                面向部分专业
              </el-button>
              <div v-html="popoverData">?</div>
            </el-popover>
            </span>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="200"
      >
        <template v-slot="scope">
          <el-button-group>
            <el-button type="primary" @click="editCourse(scope.row)" icon="el-icon-edit"></el-button>
            <el-button type="primary" @click="viewCourseRoster(scope.row.courseCode, scope.row.courseTerm)" icon="el-icon-user"></el-button>
            <el-popconfirm
              title="此操作将永久删除该课程，是否继续?"
              @confirm="deleteCourse(scope.row.courseCode,scope.row.courseTerm)">
              <el-button type="primary" slot="reference" icon="el-icon-delete"></el-button>
            </el-popconfirm>
          </el-button-group>
        </template>
      </el-table-column>
      <display-course-roster ref="showForm"></display-course-roster>
    </el-table>
  </div>
</template>

<script>

import {getLabel, splitTerm} from '@/utils/methods';
import Vue from "vue";
import AddCourseForm from "./AddCourseForm";
import UploadCourse from "./UploadCourse";
import DisplayCourseRoster from "../../../common/DisplayCourseRoster";
Vue.prototype.$getLabel = getLabel
Vue.prototype.$splitTerm = splitTerm
export default {
  name: "DisplayCourses",
  components: {DisplayCourseRoster, UploadCourse, AddCourseForm},
  data() {
    return {
      popoverData: "",
      allType: [
        { number: 1, name: '通用课程'},
        { number: 2, name: '专业课程'},
        { number: 3, name: '面向部分专业课程'},
      ],
      courseSearch: {
        contentType: '',
        content: '',
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
      ],
      dayOptions: [
        {
          value: 1,
          label: '周一'
        }, {
          value: 2,
          label: '周二'
        }, {
          value: 3,
          label: '周三'
        }, {
          value: 4,
          label: '周四'
        }, {
          value: 5,
          label: '周五'
        }, {
          value: 6,
          label: '周六'
        }, {
          value: 7,
          label: '周日'
        }],
      scheduleOptions: [
        {
          value: 1,
          label: '第1节'
        }, {
          value: 2,
          label: '第2节'
        }, {
          value: 3,
          label: '第3节'
        }, {
          value: 4,
          label: '第4节'
        }, {
          value: 5,
          label: '第5节'
        }, {
          value: 6,
          label: '第6节'
        }, {
          value: 7,
          label: '第7节'
        }, {
          value: 8,
          label: '第8节'
        }, {
          value: 9,
          label: '第9节'
        }, {
          value: 10,
          label: '第10节'
        }, {
          value: 11,
          label: '第11节'
        }, {
          value: 12,
          label: '第12节'
        }, {
          value: 13,
          label: '第13节'
        }],
      searchCourseByTime: {
        courseTime: {
          courseDay: '',
          courseStartTime: '',
          courseEndTime: ''
        }
      },
      searchByTimeRule: {
        courseTime: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ]
      },
      headFilter: {}
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
      let _this = this
      this.$axios.get('/courses').then(resp => {
        if (resp && resp.status === 200) {
          _this.allCourseTemp = resp.data
          _this.headFilter = this.tableFilter(resp.data)
        }
      })
    },
    async display(type, code, term, index) {
      let re = ''
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
          this.popoverData = re;
        })
      // console.log(this.popoverData)
    },
    editCourse(course) {
      console.log(course.courseCode)
      this.$refs.editCourse.addFormVisible = true
      this.$nextTick( function () {
        this.$refs.editCourse.disabled = true
        this.$refs.editCourse.addCourse.courseCode = course.courseCode
        this.$refs.editCourse.addCourse.courseName = course.courseName
        this.$refs.editCourse.addCourse.courseDepartment = course.courseDepartment
        this.$refs.editCourse.addCourse.courseTeacherNumber = course.courseTeacherNumber
        this.$refs.editCourse.addCourse.courseCredit = course.courseCredit
        this.$refs.editCourse.addCourse.courseTime = {
          courseDay: course.courseDay,
          courseStartTime: course.courseStartTime,
          courseEndTime: course.courseEndTime,
        }
        let y = course.courseTerm.split('-')
        this.$refs.editCourse.addCourse.courseYear = y[y.length - 2]
        this.$refs.editCourse.addCourse.courseTerm = y[y.length - 1]
        this.$refs.editCourse.addCourse.courseClassroom = course.courseClassroom
        this.$refs.editCourse.addCourse.courseCapacity = course.courseCapacity
        this.$refs.editCourse.addCourse.courseType = course.courseType
        if (course.courseType === 3) {
          let params = new URLSearchParams();
          params.append('courseCode', course.courseCode);
          params.append('courseTerm', course.courseTerm);
          this.$axios
            .post('/get-course-access',params)
            .then(resp => {
              this.$refs.editCourse.addCourse.courseAccess = resp.data
            })
        }
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
    },
    filterCourseByTimeAndClassroom() {
      let _this = this
      let params = new URLSearchParams();
      params.append('courseDay', this.searchCourseByTime.courseTime.courseDay);
      params.append('courseStartTime', this.searchCourseByTime.courseTime.courseStartTime);
      params.append('courseEndTime', this.searchCourseByTime.courseTime.courseEndTime);
      this.$axios
        .post('/filter-course', params)
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
    tableFilter(list){
      let filters = {}
      if (list.length) {
        Object.keys(list[0]).forEach(item => {
        filters[item] = []
       })
         list.forEach(item => {
           for (let key in item) {
              if (filters.hasOwnProperty(key) && !filters[key].find(i => i.text === item[key])) {
                if (key === "courseTerm") filters[key].push({text: item[key], value: item[key]})
                else if (key === "courseClassroom") {
                  let classroom = getLabel(this.allClassrooms, item[key], 'id', 'classroom')
                  if (!filters[key].find((i => i.text === classroom))) filters[key].push({text: classroom, value: item[key]})
                }
              }
           }
         })
      }
      return filters;
    },
    filterHandler(value, row, column) {
      const property = column['property'];
      return row[property] === value;
    },
    searchCourse() {
      console.log("loadSearched")
      this.allCourseTemp = null
      let _this = this
      let params = new URLSearchParams();
      params.append('content', this.courseSearch.content);
      params.append('contentType', this.courseSearch.contentType);
      this.$axios
        .post('/search-course-fuzzy', params)
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
  }
}
</script>

<style scoped>
.course-management-wrapper {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
}
</style>
