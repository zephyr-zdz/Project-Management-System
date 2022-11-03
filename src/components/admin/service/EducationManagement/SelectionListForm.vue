<template>
<div>
  <el-dialog
    title="选课名单"
    style="width: 70%; margin: auto;"
    :visible.sync="tableVisible">
    <el-table
      :data="tableData"
      stripe
      border
      style="width: 90%; margin: auto;">
      <el-table-column
        label="编号"
        width="100">
        <template v-slot="scope">
          {{scope.$index + 1}}
        </template>
      </el-table-column>
      <el-table-column
        prop="studentNumber"
        label="学号"
        width="auto">
      </el-table-column>
      <el-table-column
        prop="studentName"
        label="姓名"
        width="auto">
      </el-table-column>
    </el-table>
  </el-dialog>
</div>
</template>

<script>
export default {
  name: "SelectionListForm",
  data() {
    return {
      tableVisible: false,
      tableData: [
        {
          "id": 3,
          "studentNumber":  "//",
          "studentName": "//",
          "courseCode": "112315.01",
          "courseTerm": "2022-1",
          "round": 2,
          "status": 1 // *
        },
        {
          "id": 10,
          "studentNumber":  "//",
          "studentName": "//",
          "courseCode": "112315.01",
          "courseTerm": "2022-1",
          "round": 1,
          "status": 1
        }
      ]
    }
  }
}
</script>

<style scoped>

</style>
