<template>
  <el-container>
    <el-header>管理学生选课</el-header>
    <el-main>
      <el-card shadow="always" header="开放选课">
        <el-form>
          <el-radio v-model="courseSelectionStatus.round" :label="1">一轮选课</el-radio>
          <el-radio v-model="courseSelectionStatus.round" :label="2">二轮选课</el-radio>
          <el-switch
            v-model="courseSelectionStatus.isOpen"
            :active-value="1"
            :inactive-value="0"
            active-text="开放"
            inactive-text="关闭"
            @change="openSelection()">
          </el-switch>
          <el-divider></el-divider>
          <el-popconfirm
            title="是否进行操作？"
            @confirm="clearSelection()">
            <el-button  type="primary" slot="reference">退选超量学生</el-button>
          </el-popconfirm>
        </el-form>
      </el-card>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: "ManageCourseSelection",
  data() {
    return {
      courseSelectionStatus: {
        round: 1,
        isOpen: 0
      }
    }
  },
  mounted: function () {
    this.loadSelection()
  },
  methods: {
    async loadSelection() {
      console.log("checkSelection called")
      this.$axios.get('/course-selection-status').then(resp => {
        if (resp && resp.status === 200) {
          console.log(resp)
          this.courseSelectionStatus.round = resp.data.round
        }
      })
      if (this.courseSelectionStatus.round !== 0) {
        this.courseSelectionStatus.isOpen = 1;
      }
    },
    openSelection() {
      let round = 0
      if (this.courseSelectionStatus.isOpen !== 0) round = this.courseSelectionStatus.round
      console.log("changeSelection called " + round)
      this.$axios
        .post('/course-selection-status',{
          id: 1,
          round: round
        })
        .then(resp=> {
          if (resp && resp.data.code === 200) {
            this.$message({
              message: resp.data.msg,
              type: 'success'
            });
          } else {
            this.$message({
              message: resp.data.msg,
              type: 'error'
            });
          }
        })
    },
    clearSelection() {
      console.log("call clear selection")
      this.$axios
        .get('/clear-selection')
        .then(resp=> {
          if (resp && resp.data.code === 200) {
            this.$message({
              message: resp.data.msg,
              type: 'success'
            });
          } else {
            this.$message({
              message: resp.data.msg,
              type: 'error'
            });
          }
        })
    }
  }
}
</script>

<style scoped>
.el-header {
  /*background-color: #a7d7e8;*/
  border-bottom: #1f1e33;
  color: #ffffff;
  text-align: center;
  font-size: 20px;
  line-height: 60px;
}

.el-main {
  position: absolute;
  left: 200px;
  right: 0;
  top: 60px;
  bottom: 0;
  overflow-y: scroll;

  background-color: ghostwhite;
  color: #333;
  text-align: center;
}
</style>
