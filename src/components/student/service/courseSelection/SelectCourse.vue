<template>
  <div id="bg">
    <el-container>
      <el-main>
        <h1 v-if="round === 0">当前选课未开放！</h1>
        <h1 v-if="round === 1">一轮选课</h1>
        <h1 v-if="round === 2">二轮选课</h1>
        <el-card className="card" v-if="round">
          <student-course-table :round="round"></student-course-table>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import StudentCourseTable from "./SelectCourseTable";

export default {
  name: "StudentCourse",
  components: {StudentCourseTable},
  data() {
    return {
      round: 1,
    }
  },
  mounted: function () {
    this.checkCourse()
  },
  methods: {
    async checkCourse() {
      console.log("checkSelection called")
      this.$axios.get('/course-selection-status').then(resp => {
        if (resp && resp.status === 200) {
          this.round = resp.data.round
          if (this.round === 0) {
            this.$alert("当前选课未开放!", "Warning",
              {
                confirmButtonText: '确定',
              });
          }
        } else {
          this.$message({
            message: 'ERROR!',
            type: 'error'
          });
        }
      })
    },
  }
}
</script>

<style scoped>
#bg {
  background-color: ghostwhite;
  height: 100%;
}

.card {
  position: relative;
  left: 300px;
  width: 70%;
}

.el-main {
  background-color: ghostwhite;
  color: #333;
  text-align: center;
  line-height: 60px;
}
</style>
