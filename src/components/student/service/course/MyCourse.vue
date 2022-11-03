<template>
  <div id="bg">
    <el-container>
      <el-main>
        <h1>我的课程</h1>
        <el-card className="card" style="width: 60%; margin: 0 auto">
          <el-form>
            <el-form-item
              prop="courseTerm">
              <el-date-picker
                v-model="searchCourse.courseYear"
                value-format="yyyy"
                type="year"
                placeholder="请选择开课学年">
              </el-date-picker>
              <el-radio v-model="searchCourse.courseTerm" label="1">春季学期</el-radio>
              <el-radio v-model="searchCourse.courseTerm" label="2">秋季学期</el-radio>
              <el-button  icon="el-icon-search" @click="search()"></el-button>
            </el-form-item>

          </el-form>
          <el-table
            :data="allCompletedCourse"
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
              width="160">
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
          </el-table>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import Vue from "vue";

export default {
  name: "MyCourse",
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
      searchCourse: {
        courseYear: '',
        courseTerm: '1'
      }
    }
  },
  computed: { // for real-time rendering table data
    allCompletedCourse: {
      get: function() {
        return this.allCourseTemp;
      }
    },
  },
  mounted: function() {
    this.loadTeachers()
    this.loadDepartments()
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
    search() {
      let _this = this
      let params = new URLSearchParams();
      let jsonObj = JSON.parse(window.localStorage.user);
      let no = jsonObj.user.number
      params.append('studentNo', no);
      params.append('term', this.searchCourse.courseYear + '-' + this.searchCourse.courseTerm,);
      this.$axios
        .post('/courses/completed', params)
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
    }
  }
}
</script>

<style scoped>
#bg {
  background-color: ghostwhite;
  height: 100%;
}


.el-main {
  background-color: ghostwhite;
  color: #333;
  text-align: center;
  line-height: 60px;
}
</style>
