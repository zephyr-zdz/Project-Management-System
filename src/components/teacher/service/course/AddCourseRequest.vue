<template>
<div>
  <el-button @click="formVisible=true" type="text">申请增加课程</el-button>
  <el-dialog title="课程信息" :visible.sync="formVisible" style="line-height: 100px;">
    <el-form
      :model="requestForm"
      ref="requestForm"
      :rules="requestFormRules">
      <el-form-item
        label="课程名"
        prop="courseName"
        :label-width="formLabelWidth">
        <el-input v-model="requestForm.courseName" placeholder="课程名"></el-input>
      </el-form-item>
      <el-form-item
        label="开设院系"
        prop="courseDepartmentNumber"
        :label-width="formLabelWidth">
        <el-select v-model="requestForm.schoolId"
                   @change="getSchool($event)"
                   placeholder="学院">
          <el-option
            v-for="item in requestForm.schools"
            :key="item.schoolId"
            :label="item.schoolName"
            :value="item.schoolId">
          </el-option>
        </el-select>
        <el-select v-model="requestForm.courseDepartmentNumber"
                   placeholder="专业">
          <el-option
            v-for="item in requestForm.departments"
            :key="item.departmentNumber"
            :label="item.departmentName"
            :value="item.departmentNumber">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item
        label="课程类型"
        prop="courseType"
        :label-width="formLabelWidth">
        <el-radio v-model="requestForm.courseType" :label="1">通用课程</el-radio>
        <el-radio v-model="requestForm.courseType" :label="2">专业课程</el-radio>
        <el-radio v-model="requestForm.courseType" :label="3">面向部分专业课程</el-radio>
      </el-form-item>
      <el-form-item
        label="可选专业"
        prop="courseAccess"
        :label-width="formLabelWidth"
        v-if="Math.floor(requestForm.courseType/3)">
        <el-select
          v-model="requestForm.courseAccess"
          multiple
          placeholder="请选择专业">
          <el-option
            v-for="item in requestForm.departmentList"
            :key="item.departmentNumber"
            :label="item.departmentName"
            :value="item.departmentNumber">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item
        label="学分"
        prop="courseCredit"
        :label-width="formLabelWidth">
        <el-input v-model="requestForm.courseCredit" placeholder="学分"></el-input>
      </el-form-item>
      <el-form-item
        label="时间"
        prop="courseTime"
        :label-width="formLabelWidth">
        <el-select v-model="requestForm.courseTime.courseDay"
                   style="width: 90px"
                   placeholder="日">
          <el-option
            v-for="item in dayOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-select v-model="requestForm.courseTime.courseStartTime"
                   placeholder="开始">
          <el-option
            v-for="item in scheduleOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-select v-model="requestForm.courseTime.courseEndTime"
                   placeholder="结束">
          <el-option
            v-for="item in scheduleOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item
        label="开课学期"
        prop="courseTerm"
        :label-width="formLabelWidth">
        <el-date-picker
          v-model="requestForm.courseYear"
          value-format="yyyy"
          type="year"
          placeholder="请选择开课学年">
        </el-date-picker>
        <el-radio v-model="requestForm.courseTerm" label="1">春季学期</el-radio>
        <el-radio v-model="requestForm.courseTerm" label="2">秋季学期</el-radio>
      </el-form-item>
      <el-form-item
        label="地点"
        prop="courseClassroomId"
        :label-width="formLabelWidth">
        <el-select v-model="requestForm.courseClassroomId"
                   placeholder="地点">
          <el-option
            v-for="item in requestForm.classrooms"
            :key="item.id"
            :label="item.classroom"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item
        label="容量"
        prop="courseCapacity"
        :label-width="formLabelWidth">
        <el-input v-model="requestForm.courseCapacity" placeholder="容量"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="formVisible = false; ">取 消</el-button>
      <el-button @click="onSubmitCourse('requestForm')" type="primary">确 定</el-button>
    </div>
  </el-dialog>
</div>
</template>

<script>
let allDepartments = [
  {
    departmentNumber: 1,
    school: {
      schoolId: 3,
      schoolName: "前端-医学院"
    },
    departmentName: '临床'
  },
  {
    departmentNumber: 2,
    school: {
      schoolId: 1,
      schoolName: "前端-环境学院"
    },
    departmentName: '环境工程'
  }
]
export default {
  name: "AddCourseRequest",
  data() {
    let allSchools = [
      { schoolId: 3, schoolName: '前端-医学院'},
      { schoolId: 2, schoolName: '前端-物理学院'},
      { schoolId: 1, schoolName: '前端-环境学院'}
    ]
    let allAvailableClassroom = [
      {
        "id": 1,
        "classroom": "H3201",
        "isAvailable": 1
      },
      {
        "id": 2,
        "classroom": "H3401",
        "isAvailable": 1
      }
    ]
    return {
      formVisible: false,
      formLabelWidth: "120px",
      requestForm: {
        courseName: '',
        schools: allSchools,
        departments: allDepartments,
        departmentList: allDepartments, // for selection
        courseDepartmentNumber: '',
        courseTeacherNumber: '',
        courseCredit: '',
        courseType: 1,
        courseAccess: [],
        courseTime: {
          courseDay: '',
          courseStartTime: '',
          courseEndTime: ''
        },
        courseYear: '',
        courseTerm: '',
        classrooms: allAvailableClassroom,
        courseClassroomId: '',
        courseCapacity: ''
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
      requestFormRules: {
        courseName: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ],
        courseDepartmentNumber: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ],
        courseCredit: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
          {
            pattern: /^\d{1,2}$/,
            message: "请输入有效学分",
            trigger: 'blur'
          }
        ],
        courseType: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ],
        courseAccess: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ],
        courseTime: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ],
        courseTerm: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ],
        courseClassroom: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ],
        courseCapacity: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
          {
            pattern: /^\d{1,4}$/,
            message: "请输入有效容量",
            trigger: 'blur'
          }
        ]
      },
    }
  },
  mounted: function() {
    this.loadSchoolAndDepartment()
    this.loadAvailableClassrooms()
    this.getUserId()
  },
  methods: {
    async loadSchoolAndDepartment() {
      this.$axios.get('/schools').then(resp => {
        if (resp && resp.status === 200) {
          this.requestForm.schools = resp.data
        }
      })
      this.$axios.get('/departments').then(resp => {
        if (resp && resp.status === 200) {
          this.requestForm.departments = resp.data
          this.requestForm.departmentList = resp.data
        }
      })
    },
    async loadAvailableClassrooms() {
      this.$axios.get('/classrooms/1').then(resp => {
        if (resp && resp.status === 200) {
          this.requestForm.classrooms = resp.data
        }
      })
    },
    getUserId() {
      let jsonObj = JSON.parse(window.localStorage.user);
      this.requestForm.courseTeacherNumber = jsonObj.user.number
    },
    getSchool(school) {
      console.log(school)
      let d = []
      this.requestForm.courseDepartment = ''
      let url = '/schools/' + school + '/departments'
      this.$axios.get(url).then(resp => {
        if (resp && resp.status === 200) {
          this.requestForm.departments = resp.data
        } else {
          for (let val of allDepartments) {
            if (school === val.school.schoolId) {
              console.log(val)
              d.push({departmentNumber: val.departmentNumber, departmentName: val.departmentName})
            }
            this.requestForm.departments = d
          }
        }
      })
    },
    onSubmitCourse(formName) {
      console.log("onSubmit called")
      if (this.requestForm.courseTime.courseStartTime > this.requestForm.courseTime.courseEndTime) {
        this.$alert('节次无效', 'Warning', {
          confirmButtonText: '确定',
        });
        return false
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log("on Submit")
          this.$axios
            .post('/course-requests', {
              courseName: this.requestForm.courseName,
              courseTeacherNumber: this.requestForm.courseTeacherNumber,
              courseCredit: this.requestForm.courseCredit,
              courseDay: this.requestForm.courseTime.courseDay,
              courseStartTime: this.requestForm.courseTime.courseStartTime,
              courseEndTime: this.requestForm.courseTime.courseEndTime,
              courseClassroomId: this.requestForm.courseClassroomId,
              courseCapacity: this.requestForm.courseCapacity,
              courseDepartmentNumber: this.requestForm.courseDepartmentNumber,
              requestStatus: 2,
              courseTerm: this.requestForm.courseYear + '-' + this.requestForm.courseTerm,
              courseType: this.requestForm.courseType,
              courseAccess: this.requestForm.courseAccess.toString()
            })
            .then(resp => {
              if (resp) {
                if (resp.data.code === 200) {
                  this.addFormVisible = false
                  this.$alert(resp.data.msg, '结果', {
                    confirmButtonText: '确定'
                  });
                  location.reload()
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

</style>
