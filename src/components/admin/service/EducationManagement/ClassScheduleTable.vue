<template>
  <div>
    <el-table
      :data="time"
      stripe
      style="width: 100%;"
      border>
      <el-table-column
        prop="sequence"
        label="节次"
        fixed="left"
        width="150">
      </el-table-column>
      <el-table-column
        prop="startTime"
        label="开始时间">
        <template v-slot="scope">
          <el-time-select
            placeholder="起始时间"
            v-model="scope.row.startTime"
            :picker-options="{
                start: '07:00',
                end: '22:00',
                step: '00:05',
                minTime: min,
              }"
            @focus="judge(scope.$index)">
          </el-time-select>
        </template>
      </el-table-column>
      <el-table-column
        prop="endTime"
        label="结束时间">
        <template v-slot="scope">
          <el-time-select
            placeholder="结束时间"
            v-model="scope.row.endTime"
            :picker-options="{
                start: '07:00',
                end: '22:00',
                step: '00:05',
                minTime: scope.row.startTime,
              }">
          </el-time-select>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="150">
        <template v-slot="scope">
          <el-button
            type="primary"
            @click="onSubmit(scope.row,scope.$index)">提交</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: "ClassScheduleTable",
  data() {
    return {
      dialogFormVisible: false,
      formLabelWidth: '120px',
      min: '06:00',
      time: [
        {
          sequence: 1,
          startTime: '08:00',
          endTime: '08:45',
        },
        {
          sequence: 2,
          startTime: '08:55',
          endTime: '09:40',
        },
        {
          sequence: 3,
          startTime: '09:55',
          endTime: '10:40',
        },
      ],
    }
  },
  mounted: function () {
    this.loadTime()
  },
  methods: {
    async loadTime() { // better be named loadSchedule
      console.log("loadSchedule called")
      this.$axios.get('/class-schedule').then(resp => {
        if (resp && resp.status === 200) {
          this.time = resp.data
        }
      })
    },
    judge(index) { //判断不为第一行并赋值为上一行endTime
      if (index !== 0) {
        this.min = this.time[index-1].endTime
      }else {
        this.min = '06:00'
      }
    },
    onSubmit(timeForm,index) {
      console.log(this.time[index])
      this.$axios
        .post('/edit-class-schedule', {
          sequence: timeForm.sequence,
          startTime: timeForm.startTime,
          endTime: timeForm.endTime,
        })
        .then(resp => {
          if (resp && resp.data.code === 200) {
            this.dialogFormVisible = false
            this.$emit('onSubmit')
            this.$message({
              message: resp.data.msg,
              type: 'success'
            });
            this.loadTime();
          } else {
            this.$message({
              message: '修改失败',
              type: 'error'
            });
          }
        })
    }
  },
}
</script>

<style scoped>

</style>
