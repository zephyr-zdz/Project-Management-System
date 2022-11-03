<template>
  <div>
    <el-row>
      <el-col :span="12">
        <el-input
        placeholder="请输入 课程代码/课程名/教师 进行搜索"
        v-model="fuzzySearch.content"
        clearable>
          <el-select v-model="fuzzySearch.contentType" style="width: 120px" slot="prepend" placeholder="请选择">
            <el-option label="课程代码" :value="1"></el-option>
            <el-option label="课程名" :value="2"></el-option>
            <el-option label="授课教师" :value="3"></el-option>
          </el-select>
        <el-button slot="append" icon="el-icon-search" @click="searchCourseFuzzy()"></el-button>
        </el-input>
      </el-col>
      <el-col :span="12">
        <el-form
          :model="searchCourseByTime"
          ref="searchCourseByTime"
          :rules="searchByTimeRule"
        >
          <el-form-item
            prop="courseClassroom">
            <el-select v-model="searchCourseByTime.courseClassroom"
                       clearable
                       placeholder="地点">
              <el-option
                v-for="item in allClassrooms"
                :key="item.id"
                :label="item.classroom"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
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
      style="width: 100%">
      <el-table-column
        fixed
        prop="courseTerm"
        label=" 开课学期"
        width="150">
      </el-table-column>
      <el-table-column
        fixed
        prop="courseCode"
        label="选课序号"
        width="150">
      </el-table-column>
      <el-table-column
        prop="courseName"
        label="课程名"
        width="auto">
      </el-table-column>
      <el-table-column
        prop="courseDepartment"
        label="专业"
        width="150">
        <template slot-scope="scope">
          {{ $getLabel(allDepartment, scope.row.courseDepartment, 'departmentNumber', 'departmentName') }}
        </template>
      </el-table-column>
      <el-table-column
        prop="courseTeacherNumber"
        label="教师"
        width="80">
        <template slot-scope="scope">
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
        width="200">
        <template slot-scope="scope">
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
        <template slot-scope="scope">
          {{ scope.row.courseEnrollment }}/{{ scope.row.courseCapacity }}
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        width="100">
        <template slot-scope="scope">
          <el-button @click="selectCourse(scope.row, round.toString())" type="text" >选课</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-divider> <h3>已选课程  <el-button @click="reloadTable()" size="mini" icon="el-icon-refresh-right" circle></el-button></h3></el-divider>
    <el-table
      :data="allSelectedCourse"
      border
      style="width: 100%">
      <el-table-column
        fixed
        prop="courseTerm"
        label=" 开课学期"
        width="150">
      </el-table-column>
      <el-table-column
        fixed
        prop="courseCode"
        label="选课序号"
        width="150">
      </el-table-column>
      <el-table-column
        prop="courseName"
        label="课程名"
        width="auto">
      </el-table-column>
      <el-table-column
        prop="courseDepartment"
        label="专业"
        width="150">
        <template slot-scope="scope">
          {{ $getLabel(allDepartment, scope.row.courseDepartment, 'departmentNumber', 'departmentName') }}
        </template>
      </el-table-column>
      <el-table-column
        prop="courseTeacherNumber"
        label="教师"
        width="80">
        <template slot-scope="scope">
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
        width="200">
        <template slot-scope="scope">
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
        <template slot-scope="scope">
          {{ scope.row.courseEnrollment }}/{{ scope.row.courseCapacity }}
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        width="100">
        <template slot-scope="scope">
          <el-button @click="selectCourse(scope.row, 0)" type="text" >退课</el-button>
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
  name: "StudentCourseTable",
  props: {
    round: { type: Number, default: 1 }
  },
  data() {
    return {
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
      allCourseTemp: [
        // {
        //   "id": 0,
        //   "courseCode": "123221.01",
        //   "courseName": "英语发音",
        //   "courseDepartment": 3,
        //   "courseCredit": 2,
        //   "courseDay": 5,
        //   "courseStartTime": 4,
        //   "courseEndTime": 5,
        //   "courseClassroom": 3,
        //   "courseCapacity": 30,
        //   "courseTeacherNumber": "22000005",
        //   "courseTerm": "2022-1",
        //   "courseType": 3,
        //   "courseEnrollment": 0,
        //   "courseAccess": [
        //     2,
        //     3
        //   ]
        // },
        // {
        //   "id": 1,
        //   "courseCode": "222222.01",
        //   "courseName": "课程名2",
        //   "courseDepartment": 1,
        //   "courseCredit": 2,
        //   "courseDay": 4,
        //   "courseStartTime": 1,
        //   "courseEndTime": 3,
        //   "courseClassroom": 1,
        //   "courseCapacity": 40,
        //   "courseTeacherNumber": "20220319",
        //   "courseTerm": "2022-1",
        //   "courseType": 3,
        //   "courseEnrollment": 0,
        //   "courseAccess": [
        //     2,
        //     3
        //   ]
        // }
      ],
      allSelectedCourseTemp: [],
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
      fuzzySearch: {
        contentType: 1,
        content: '',
      },
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
        courseClassroom: '',
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
      }
    }
  },
  computed: { // for real-time rendering table data
    allCourse: {
      get: function() {
        return this.allCourseTemp;
      }
    },
    allSelectedCourse: {
      get: function() {
        return this.allSelectedCourseTemp
      }
    }
  },
  mounted: function() {
    this.loadTeachers()
    this.loadDepartments()
    this.loadClassrooms()
    this.loadSelectedCourses()
    // this.loadCourses()
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
      let params = new URLSearchParams();
      let _this = this
      params.append('userNumber', no);
      this.$axios
        .post('/getUser', params)
        .then(resp => {
          if (resp && resp.data.code === 200) {
            let userDepartment = resp.data.data.department.departmentNumber
            console.log(userDepartment)
            let url = '/courses/' + userDepartment
            this.$axios.get(url).then(resp => {
              _this.allCourseTemp = resp.data
            })
          }
        })
    },
    loadSelectedCourses() {
      console.log("loadSelected")
      let jsonObj = JSON.parse(window.localStorage.user);
      let no = jsonObj.user.number
      let _this = this
      let url = '/courses/selected/' + no
      this.$axios.get(url).then(resp => {
        _this.allSelectedCourseTemp = resp.data
      })
    },
    searchCourseFuzzy() {
      console.log("loadSearched")
      this.allCourseTemp = null
      let _this = this
      let params = new URLSearchParams();
      params.append('content', this.fuzzySearch.content);
      params.append('contentType', this.fuzzySearch.contentType);
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
    selectCourse(course, round) {
      console.log(course.courseCode, round)
      let jsonObj = JSON.parse(window.localStorage.user);
      let no = jsonObj.user.number
      this.$axios
        .post('/select-course', {
          studentNumber: no,
          courseCode: course.courseCode,
          round: round
        })
        .then(resp => {
          if (resp && resp.data.code === 200) {
            Vue.prototype.$message({
              message: resp.data.msg,
              type: 'success'
            });
          } else {
            Vue.prototype.$message({
              message: resp.data.msg,
              type: 'error'
            });
          }
        })
    },
    filterCourseByTimeAndClassroom() {
      let _this = this
      let params = new URLSearchParams();
      params.append('courseClassroom', this.searchCourseByTime.courseClassroom);
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
    reloadTable() {
      this.loadSelectedCourses()
      console.log("reload")
      if (this.fuzzySearch.content !== "") this.searchCourseFuzzy()
    }
  }
}
</script>

<style scoped>
.el-input {
  width: 50%;
}
.el-input .el-select{
  width: 50%;
}

.el-divider {
  margin: 4em 0 4em 0;
}
</style>
