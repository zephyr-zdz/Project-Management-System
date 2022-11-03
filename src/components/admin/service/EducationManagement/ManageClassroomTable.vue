<template>
  <div>
    <el-button type="text" @click="dialogFormVisible=true">添加教室</el-button>
    <el-table
      :data="classroom"
      stripe
      style="width: 100%;"
      border>
      <el-table-column
        prop="id"
        label="ID"
        fixed="left"
        width="150">
      </el-table-column>
      <el-table-column
        prop="classroom"
        label="教室">
        <template slot-scope="scope">
          <el-input
            v-model="scope.row.classroom"
            type="text"
            placeholder="Classroom"
            @change="changeSubmit(scope.row)">
          </el-input>
        </template>
      </el-table-column>
      <el-table-column
        prop="capacity"
        label="容量">
        <template slot-scope="scope">
          <el-input
            v-model="scope.row.capacity"
            type="text"
            placeholder="Classroom"
            @change="changeSubmit(scope.row)">
          </el-input>
        </template>
      </el-table-column>
      <el-table-column
        label="启用状态"
        fixed="right"
        width="250">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.isAvailable"
            active-text="使用中"
            inactive-text="未使用"
            :active-value="1"
            :inactive-value="0"
            @change="changeSubmit(scope.row)">
          </el-switch>
        </template>
      </el-table-column>
    </el-table>

    <!--添加教室-->
    <el-dialog
      title="添加教室"
      :visible.sync="dialogFormVisible"
      class="dialog">
      <el-form
        :model="classroomForm"
        ref="classroomForm"
        :rules="classroomFormRule">
        <el-form-item
          label="教室"
          prop="classroom"
          :label-width="formLabelWidth">
          <el-input v-model="classroomForm.classroom"></el-input>
        </el-form-item>
        <el-form-item
          label="容量"
          prop="capacity"
          :label-width="formLabelWidth">
          <el-input v-model="classroomForm.capacity"></el-input>
        </el-form-item>
        <el-form-item
          label="是否启用"
          prop="isAvailable"
          :label-width="formLabelWidth">
          <el-switch
            v-model="classroomForm.isAvailable"
            active-text="启用"
            inactive-text="不启用"
            :active-value="1"
            :inactive-value="0">
          </el-switch>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false; ">取 消</el-button>
        <el-button @click="addSubmit('classroomForm')" type="primary">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "ManageClassroomTable",
  data() {
    return{
      dialogFormVisible: false,
      formLabelWidth: '120px',
      classroom: [
        //格式测试
        {
          id: '1',
          classroom: 'H3108',
          capacity: '120',
          isAvailable: 1,
        },
        {
          id: '2',
          classroom: 'H3209',
          capacity: '60',
          isAvailable: 0,
        },
      ],
      classroomForm: {
        id: '',
        classroom: '',
        capacity: '',
        isAvailable: 1,
      },
      classroomFormRule: {
        classroom: [
          { required:true, message: '必填', trigger:'blur'}
        ],
        capacity:  [
          { required:true, message: '必填', trigger:'blur'}
        ],
      }
    }
  },
  mounted: function () {
    this.loadClassroom()
  },
  methods: {
    async loadClassroom() {
      console.log("loadClassroom called")
      this.$axios.get('/classrooms').then(resp => {
        if (resp && resp.status === 200) {
          this.classroom = resp.data
        }
      })
    },
    changeSubmit(classroomTable) {
      console.log("changeSubmit called")
      this.$axios
        .post('/classrooms', {
          id: classroomTable.id,
          classroom: classroomTable.classroom,
          capacity: classroomTable.capacity,
          isAvailable: classroomTable.isAvailable,
        })
        .then(resp => {
          if (resp && resp.data.code === 200) {
            this.dialogFormVisible = false
            this.$emit('onSubmit')
            this.$message({
              message: resp.data.msg,
              type: 'success'
            });
            this.loadClassroom();
          } else {
            this.$message({
              message: resp.data.msg,
              type: 'error'
            });
          }
        })
    },
    addSubmit(classroomForm) {
      console.log("addSubmit called")
      this.$refs[classroomForm].validate((valid) => {
        if (valid) {
          //  valid
          console.log("is valid")
          this.$axios
            .post('/classrooms', {
              classroom: this.classroomForm.classroom,
              capacity: this.classroomForm.capacity,
              isAvailable: this.classroomForm.isAvailable,
            })
            .then(resp => {
              if (resp && resp.data.code === 200) {
                this.dialogFormVisible = false
                this.$emit('onSubmit')
                this.$message({
                  message: resp.data.msg,
                  type: 'success'
                });
                this.loadClassroom();
              } else {
                this.$message({
                  message: '添加失败',
                  type: 'error'
                });
              }
            })
        } else {
          console.log("not valid")
          this.$alert("请按提示正确填写内容", "Warning",
            {
              confirmButtonText: '确定',
            });
          return false;
        }
      })
    },
  }
}
</script>

<style scoped>
.dialog {
  margin: 0 auto;
  width: 1000px;

}
</style>

