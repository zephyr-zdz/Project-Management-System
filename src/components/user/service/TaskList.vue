<template>
    <el-table 
    ref="filterTable"
      :data="tableData"
      style="width: 100%" >
      <el-table-column
        prop="date"
        label="任务开始日期"
        width="180">
      </el-table-column>
      <el-table-column
        prop="name"
        label="任务名称"
        width="180">
      </el-table-column>
      <el-table-column
        prop="assigner"
        label="任务指派人"
        width="180">
      </el-table-column>
      <el-table-column
        prop="performer"
        label="任务执行人"
        width="180"
        :filters="getAllperformer"
      :filter-method="filterHandler">
      </el-table-column>
      <el-table-column
        prop="intro"
        label="任务简介">
      </el-table-column>

    </el-table>
  </template>
  
  <script>
    export default {
      data() {
        return {
          tableData: [{
            date: '2016-05-02',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1518 弄',
            tag: '家'
          }, {
            date: '2016-05-04',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1517 弄',
            tag: '公司'
          }, {
            date: '2016-05-01',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1519 弄',
            tag: '家'
          }, {
            date: '2016-05-03',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1516 弄',
            tag: '公司'
          }]
        }
      },
      methods: {
        resetDateFilter() {
          this.$refs.filterTable.clearFilter('date');
        },
        clearFilter() {
          this.$refs.filterTable.clearFilter();
        },
        formatter(row, column) {
          return row.address;
        },
        filterTag(value, row) {
          return row.tag === value;
        },
        filterHandler(value, row, column) {
          const property = column['property'];
          return row[property] === value;
        },
        getAllperformer()
        {

        }
      }
    }
  </script>