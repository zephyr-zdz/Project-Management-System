<template>
  <div>
    <el-button @click="addFormVisible = true" type="primary">录入课程</el-button>
    <el-dialog title="课程信息" :visible.sync="addFormVisible" style="line-height: 100px;">
      <el-form
        :model="addCourse"
        ref="addCourse"
        :rules="courseFormRules"
      >
        <el-form-item
          label="课程序号"
          prop="courseCode"
          :label-width="formLabelWidth"
        >
          <el-input  :disabled="disabled" v-model="addCourse.courseCode"></el-input>
        </el-form-item>
        <el-form-item
          label="课程名"
          prop="courseName"
          :label-width="formLabelWidth">
          <el-input v-model="addCourse.courseName"></el-input>
        </el-form-item>
        <el-form-item
          label="开设院系"
          prop="courseDepartment"
          :label-width="formLabelWidth">
          <el-select v-model="addCourse.schoolId"
                     @change="getSchool($event)"
                     placeholder="学院">
            <el-option
              v-for="item in addCourse.schools"
              :key="item.schoolId"
              :label="item.schoolName"
              :value="item.schoolId">
            </el-option>
          </el-select>
          <el-select v-model="addCourse.courseDepartment"
                     placeholder="专业">
            <el-option
              v-for="item in addCourse.departments"
              :key="item.departmentNumber"
              :label="item.departmentName"
              :value="item.departmentNumber">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          label="教师"
          prop="courseTeacherNumber"
          :label-width="formLabelWidth">
          <el-select v-model="addCourse.courseTeacherNumber"
                     placeholder="授课教师">
            <el-option
              v-for="item in teacherList"
              :key="item.number"
              :label="item.name"
              :value="item.number">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          label="课程类型"
          prop="courseType"
          :label-width="formLabelWidth">
          <el-radio v-model="addCourse.courseType" :label="1">通用课程</el-radio>
          <el-radio v-model="addCourse.courseType" :label="2">专业课程</el-radio>
          <el-radio v-model="addCourse.courseType" :label="3">面向部分专业课程</el-radio>
        </el-form-item>
        <el-form-item
          label="可选专业"
          prop="courseAccess"
          :label-width="formLabelWidth"
          v-if="Math.floor(addCourse.courseType/3)">
          <el-select
            v-model="addCourse.courseAccess"
            multiple
            placeholder="请选择专业">
            <el-option
              v-for="item in addCourse.departmentList"
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
          <el-input v-model="addCourse.courseCredit" placeholder="学分"></el-input>
        </el-form-item>
        <el-form-item
          label="时间"
          prop="courseTime"
          :label-width="formLabelWidth">
          <el-select v-model="addCourse.courseTime.courseDay"
                     style="width: 80px"
                     placeholder="日">
            <el-option
              v-for="item in dayOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <el-select v-model="addCourse.courseTime.courseStartTime"
                     placeholder="开始">
            <el-option
              v-for="item in scheduleOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <el-select v-model="addCourse.courseTime.courseEndTime"
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
            v-model="addCourse.courseYear"
            value-format="yyyy"
            type="year"
            placeholder="请选择开课学年">
          </el-date-picker>
          <el-radio v-model="addCourse.courseTerm" label="1">春季学期</el-radio>
          <el-radio v-model="addCourse.courseTerm" label="2">秋季学期</el-radio>
        </el-form-item>
        <el-form-item
          label="地点"
          prop="courseClassroom"
          :label-width="formLabelWidth">
          <el-select v-model="addCourse.courseClassroom"
                     placeholder="地点">
            <el-option
              v-for="item in addCourse.classrooms"
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
          <el-input v-model="addCourse.courseCapacity"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addFormVisible = false; clear()">取 消</el-button>
        <el-button @click="onSubmitCourse('addCourse')" type="primary">确 定</el-button>
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
  name: "AddCourseForm",
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
      addFormVisible: false,
      disabled: false,
      formLabelWidth: '120px',
      teacherList: [
        { number: "20220403", name:'教师1'},
        { number: "20220319", name:'教师2'},
      ],
      search: [],
      addCourse: {
        courseCode: '',
        courseName: '',
        schools: allSchools, // for selection
        departments: allDepartments, // for selection
        departmentList: allDepartments, // for selection
        courseDepartment: '',
        courseTeacherNumber: '',
        courseType: 1,
        courseAccess: [],
        courseCredit: '',
        courseTime: {
          courseDay: '',
          courseStartTime: '',
          courseEndTime: ''
        },
        courseYear: '',
        courseTerm: '',
        classrooms: allAvailableClassroom, // for selection
        courseClassroom: '',
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
      courseFormRules: {
        courseCode: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
          {
            pattern: /\d\d\d\d\d\d+.\d\d/,
            message: '注意选课序号格式',
            trigger: 'blur'
          }
        ],
        courseName: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ],
        courseDepartment: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ],
        courseTeacherNumber: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ],
        courseType: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ],
        courseAccess: [
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
        courseTime: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ],
        courseYear: [
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
    this.loadTeacherList()
    this.loadAvailableClassrooms()
    this.loadSchoolAndDepartment()
  },
  methods: {
    async loadSchoolAndDepartment() {
      this.$axios.get('/schools').then(resp => {
        if (resp && resp.status === 200) {
          this.addCourse.schools = resp.data
        }
      })
      this.$axios.get('/departments').then(resp => {
        if (resp && resp.status === 200) {
          this.addCourse.departments = resp.data
          this.addCourse.departmentList = resp.data
        }
      })
    },
    async loadAvailableClassrooms() {
      this.$axios.get('/classrooms/1').then(resp => {
        if (resp && resp.status === 200) {
          this.addCourse.classrooms = resp.data
        }
      })
    },
    getSchool(school) {
      console.log(school)
      let d = []
      this.addCourse.courseDepartment = ''
      let url = '/schools/' + school + '/departments'
      this.$axios.get(url).then(resp => {
        if (resp && resp.status === 200) {
          this.addCourse.departments = resp.data
        } else {
          for (let val of allDepartments) {
            if (school === val.school.schoolId) {
              console.log(val)
              d.push({departmentNumber: val.departmentNumber, departmentName: val.departmentName})
            }
            this.addCourse.departments = d
          }
        }
      })
    },
    loadTeacherList() {
      let _this = this
      this.$axios.get('/users/2').then(resp => {
        if (resp && resp.status === 200) {
          _this.teacherList = resp.data
        }
      })
    },
    clear() {
      this.addFormVisible = true;
      this.disabled = false;
      this.$refs.addCourse.resetFields();
    },
    onSubmitCourse(formName) {
      console.log("onSubmit called")
      if (this.addCourse.courseTime.courseStartTime >= this.addCourse.courseTime.courseEndTime) {
        this.$alert('节次无效', 'Warning', {
          confirmButtonText: '确定',
        });
        return false
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log("on Submit")
          this.$axios
            .post('/courses', {
              courseCode: this.addCourse.courseCode,
              courseName: this.addCourse.courseName,
              courseDepartment: this.addCourse.courseDepartment,
              courseTeacherNumber: this.addCourse.courseTeacherNumber,
              courseType: this.addCourse.courseType,
              courseAccess: this.addCourse.courseAccess,
              courseCredit: this.addCourse.courseCredit,
              courseDay: this.addCourse.courseTime.courseDay,
              courseStartTime: this.addCourse.courseTime.courseStartTime,
              courseEndTime: this.addCourse.courseTime.courseEndTime,
              courseTerm: this.addCourse.courseYear + '-' + this.addCourse.courseTerm,
              courseClassroom: this.addCourse.courseClassroom,
              courseCapacity: this.addCourse.courseCapacity
            })
            .then(resp => {
              if (resp) {
                if (resp.data.code === 200) {
                  this.addFormVisible = false
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
.el-button {
  margin: 10px;
}
</style>
