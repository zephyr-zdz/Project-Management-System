<template>
  <div>
    <el-dialog title="课程信息" :visible.sync="editFormVisible" style="line-height: 100px;">
      <el-form
        :model="editCourse"
        ref="editCourse"
        :rules="courseFormRules"
      >
        <el-form-item
          label="课程序号"
          prop="courseCode"
          :label-width="formLabelWidth"
        >
          <el-input  :disabled="true" v-model="editCourse.courseCode"></el-input>
        </el-form-item>
        <el-form-item
          label="课程名"
          prop="courseName"
          :label-width="formLabelWidth">
          <el-input v-model="editCourse.courseName"></el-input>
        </el-form-item>
        <el-form-item
          label="课程类型"
          prop="courseType"
          :label-width="formLabelWidth">
          <el-radio v-model="editCourse.courseType" :label="1" disabled>通用课程</el-radio>
          <el-radio v-model="editCourse.courseType" :label="2" disabled>专业课程</el-radio>
          <el-radio v-model="editCourse.courseType" :label="3" disabled>面向部分专业课程</el-radio>
        </el-form-item>
        <el-form-item
          label="可选专业"
          prop="courseAccess"
          :label-width="formLabelWidth"
          v-if="Math.floor(editCourse.courseType/3)">
          <el-select
            v-model="editCourse.courseAccess"
            multiple
            placeholder="请选择专业"
            disabled>
            <el-option
              v-for="item in editCourse.departmentList"
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
          <el-input :disabled="true" v-model="editCourse.courseCredit" placeholder="学分"></el-input>
        </el-form-item>
        <el-form-item
          label="时间"
          prop="courseTime"
          :label-width="formLabelWidth">
          <el-select v-model="editCourse.courseTime.courseDay"
                     style="width: 90px"
                     placeholder="日">
            <el-option
              v-for="item in dayOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <el-select v-model="editCourse.courseTime.courseStartTime"
                     placeholder="开始">
            <el-option
              v-for="item in scheduleOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <el-select v-model="editCourse.courseTime.courseEndTime"
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
            v-model="editCourse.courseYear"
            value-format="yyyy"
            type="year"
            placeholder="请选择开课学年"
            disabled>
          </el-date-picker>
          <el-radio v-model="editCourse.courseTerm" label="1" disabled>春季学期</el-radio>
          <el-radio v-model="editCourse.courseTerm" label="2" disabled>秋季学期</el-radio>
        </el-form-item>
        <el-form-item
          label="地点"
          prop="courseClassroom"
          :label-width="formLabelWidth">
          <el-select v-model="editCourse.courseClassroom"
                     placeholder="地点">
            <el-option
              v-for="item in editCourse.classrooms"
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
          <el-input :disabled="true" v-model="editCourse.courseCapacity"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editFormVisible = false; ">取 消</el-button>
        <el-button @click="onSubmitCourse('editCourse')" type="primary">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "EditCourseForm",
  data() {
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
      editFormVisible: false,
      disabled: false,
      formLabelWidth: '120px',
      teacherList: [
        { number: "20220403", name:'教师1'},
        { number: "20220319", name:'教师2'},
      ],
      search: [],
      editCourse: {
        courseCode: '',
        courseName: '',
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
        courseType: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ],
        courseAccess: [
          { required: true, message: '必填', trigger: ['blur', 'change']},
        ],
        courseTerm: [
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
    this.loadDepartment()
  },
  methods: {
    loadTeacherList() {
      let _this = this
      this.$axios.get('/users/2').then(resp => {
        if (resp && resp.status === 200) {
          _this.teacherList = resp.data
        }
      })
    },
    async loadAvailableClassrooms() {
      this.$axios.get('/classrooms/1').then(resp => {
        if (resp && resp.status === 200) {
          this.editCourse.classrooms = resp.data
        }
      })
    },
    async loadDepartment() {
      this.$axios.get('/departments').then(resp => {
        if (resp && resp.status === 200) {
          this.editCourse.departments = resp.data
        }
      })
    },
    onSubmitCourse(formName) {
      if (this.editCourse.courseTime.courseStartTime >= this.editCourse.courseTime.courseEndTime) {
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
              courseCode: this.editCourse.courseCode,
              courseName: this.editCourse.courseName,
              courseDepartment: this.editCourse.courseDepartment,
              courseTeacherNumber: this.editCourse.courseTeacherNumber,
              courseType: this.editCourse.courseType,
              courseAccess: this.editCourse.courseAccess,
              courseTerm: this.editCourse.courseYear + '-' + this.editCourse.courseTerm,
              courseCredit: this.editCourse.courseCredit,
              courseDay: this.editCourse.courseTime.courseDay,
              courseStartTime: this.editCourse.courseTime.courseStartTime,
              courseEndTime: this.editCourse.courseTime.courseEndTime,
              courseClassroom: this.editCourse.courseClassroom,
              courseCapacity: this.editCourse.courseCapacity
            })
            .then(resp => {
              if (resp) {
                if (resp.data.code === 200) {
                  this.editFormVisible = false
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
