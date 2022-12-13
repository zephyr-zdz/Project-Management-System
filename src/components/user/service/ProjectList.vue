<template>
  <div>
    <el-button type="primary" @click="refresh()" icon="el-icon-refresh" size="mini" circle plain style="position: relative; left:47%; z-index: 20; bottom: -10px"></el-button>
    <el-table
      :data="projectData"
      style="width: 100%; position: relative; top: -60px"
      :header-row-style="{height: '60px'}"
      :cell-style="{padding:'2px'}"
      @row-click="showContent"
      stripe>
      <el-table-column type="expand">
        <template v-slot="props">
          <el-descriptions class="margin-top" :column="2" direction="vertical" border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-s-order"></i>
                项目名称
              </template>
              {{ props.row.project.title }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-s-data"></i>
                项目ID
              </template>
              {{ props.row.project.id }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                所有者
              </template>
              {{ props.row.owner.username }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-s-custom"></i>
                项目人数
              </template>
              {{ props.row.number }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-info"></i>
                项目介绍
              </template>
              {{ props.row.project.intro }}
            </el-descriptions-item>
          </el-descriptions>
        </template>
      </el-table-column>
      <el-table-column
        label="项目id"
        prop="project.id"
        width="150">
      </el-table-column>
      <el-table-column
        label="项目名称"
        prop="project.title">
      </el-table-column>
      <el-table-column
        label="项目所有者"
        prop="owner.username"
        width="200">
      </el-table-column>
      <el-table-column
        label="我的角色">
        <template v-slot="scope">
          <p v-if="scope.row.role === 'owner'">项目所有者</p>
          <p v-else-if="scope.row.role === 'manager'">项目管理员</p>
          <p v-else-if="scope.row.role === 'member'">项目成员</p>
        </template>
      </el-table-column>
      <el-table-column
        prop="project.operation"
        label="操作"
        width="100"
        type="index">
        <template v-slot="scope">
          <el-button
            size="medium"
            type="danger"
            plain
            @click="exit(scope.$index, scope.row)">
            退出
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

  <style scoped>

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
        async getProject(){
          let jsonObj = JSON.parse(window.sessionStorage.user);
          let id = jsonObj.user.userid
          let url ='/user/participating/' + id
          this.$axios
            .get(url)
            .then(res => {this.projectData = res.data.data})
          },
        exit(index,row){
          let jsonObj = JSON.parse(window.sessionStorage.user);
          let id = jsonObj.user.userid
          const params = new FormData();
          params.append('user_id', id);
          params.append('project_id', row.project.id);
          this.$confirm('此操作将退出该项目, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$axios
            .post('/project/member/quit', params)
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
            .catch(error => {
              this.$message({
                message: error,
                type: 'error'
              })
            })
          })
        },
        showContent(row, column) {
          //console.log(column)
          if (column.label != "操作") {
            this.$router.push({path: `/user-home/my-project/${row.project.id}`})
          }
        },
        refresh(){
          this.getProject()
        }
      }
    }
  </script>
