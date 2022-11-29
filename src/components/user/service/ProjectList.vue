<template>
  <el-table
      :data="projectData"
      style="width: 100%"
      :header-row-style="{height: '100px'}"
      stripe>
      <el-table-column type="expand">
        <template v-slot="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="项目名称">
              <span>{{ props.row.project.title }}</span>
            </el-form-item>
            <el-form-item label="项目id">
              <span>{{ props.row.project.id }}</span>
            </el-form-item>
            <el-form-item label="项目所有者id">
              <span>{{ props.row.project.owner_id }}</span>
            </el-form-item>
            <el-form-item label="项目管理者id">
              <span v-for="(p,index) of props.row.managerIdList" :key="index">
					      {{p}}
				      </span>
            </el-form-item>
            <el-form-item label="项目参与者id">
              <span v-for="(p,index) of props.row.memberIdList" :key="index">
					      {{p}}
				      </span>
            </el-form-item>
            <el-form-item label="项目总人数">
              <span>{{ props.row.number }}</span>
            </el-form-item>
            <el-form-item style="width:100%" label="项目介绍">
              <span>{{ props.row.project.intro }}</span>
            </el-form-item>

          </el-form>
        </template>
      </el-table-column>
      <el-table-column
        label="项目id"
        prop="project_id">
      </el-table-column>
      <el-table-column
        label="项目名称"
        prop="name">
      </el-table-column>
      <el-table-column
        label="项目所有者id"
        prop="owner_id">
      </el-table-column>
      <el-table-column
        prop="operation"
        label="操作"
        width="150">
        <template v-slot="scope">
          <el-button
          size="mini"
          @click="myMission(scope.$index, scope.row)">我的任务</el-button>
        <el-button
          v-if="isManager(scope.$index, scope.row)"
          size="mini"
          type="primary"
          @click="assignMission(scope.$index, scope.row)">任务指派</el-button>
          <el-button
          size="mini"
          type="danger"
          @click="exit(scope.$index, scope.row)">退出项目</el-button>
        </template>
      </el-table-column>
    </el-table>
</template>

  <style>
    .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 100px;
    color: #99a9bf;

  }
  .demo-table-expand span{
    word-wrap:break-word;
    word-break:break-all;
    overflow: hidden;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 100%;
  }
  </style>

  <script>
    export default {
      name:'ProjectList',
      data() {
        return {
          projectData: [],

        }
      },


      mounted () {
      this.getProject()
      },
      methods:{
      getProject(){
          this.$axios.get('/project/list', {params: {user_id: this.$store.getters.user_id}}).then(res => {
          this.projectData = res.data.data
      })
        },
        isManager(index,row){
        return row.managerIdList.includes(this.user_id)||this.user_id===row.project.owner_id
      },
        exit(index,row){
          this.$axios.delete('/project/list/delete',
          {params: {
            project_id: row.project_id,
            user_id: this.user_id
          }}
      )
      .then(res => {
          if (res.data.code === 0) {
            this.$message({
              message: res.data.msg,
              type: 'success'
            })
            this.getProject()
          } else {
            this.$message({
              message: res.data.msg,
              type: 'error'
            })
          }
        })
        },
        myMission(index,row){
          //TODO:
        },
        assignMission(index,row){
          //TODO:
        },
  }
    }
  </script>
