<template>
  <div>
    <el-descriptions class="margin-top" :column="2" direction="vertical" border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-s-order"></i>
          项目名称
        </template>
        {{ projectData.project.title }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-s-data"></i>
          项目ID
        </template>
        {{ projectData.project.id }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          所有者
        </template>
        {{ projectData.owner.username }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-s-custom"></i>
          项目人数
        </template>
        {{ projectData.number }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-info"></i>
          项目介绍
        </template>
        {{ projectData.project.intro }}
      </el-descriptions-item>
    </el-descriptions>

    <el-row>
      <el-col :span="12"><div><assign-mission ref="assign"></assign-mission></div></el-col>
      <el-col :span="12"><div><invite ref="invite"></invite></div></el-col>
    </el-row>

    <el-divider>
      <h2 style="height: 40px; margin: 0">成员列表</h2>
    </el-divider>

    <el-table
      :data="projectData.memberList"
      style="width: 100%"
      :header-row-style="{height: '60px'}"
      stripe>
      <el-table-column
        label="成员id"
        prop="id"
        width="100">
      </el-table-column>
      <el-table-column
        label="成员名"
        prop="username">
      </el-table-column>
      <el-table-column
        label="成员邮箱"
        prop="email">
      </el-table-column>
      <el-table-column
        label="成员角色">
        <template v-slot="scope">
          {{ $getLabel(roleList, scope.row.role, 'value', 'label') }}
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        prop="status"
        width="200"
        fixed="right">
        <template v-slot="scope">
          <el-popover
            placement="top"
            width="160"
            @show="visible[scope.$index]">
            <p>修改成员权限</p>
            <el-radio v-model="roleEdit" label="manager">管理员</el-radio>
            <el-radio v-model="roleEdit" label="member">成员</el-radio>
            <div style="text-align: right; margin: 0">
              <el-button size="mini" type="text" @click="visible[scope.$index] = false">取消</el-button>
              <el-button type="primary" size="mini" @click="editMember(scope)">确定</el-button>
            </div>
            <el-button
              slot="reference"
              size="small"
              type="primary"
              icon="el-icon-edit"
              circle
              plain
              :disabled="$getLabel(roleList, projectData.role, 'value', 'cont') !== 2 || scope.row.role === 'owner'">
            </el-button>
          </el-popover>
          <el-button
            size="small"
            type="danger"
            icon="el-icon-delete"
            circle
            plain
            @click="deleteMember(scope.row.id)"
            :disabled="$getLabel(roleList, projectData.role, 'value', 'cont') === 0 || scope.row.role === 'owner' || scope.row.role === 'manager'">
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-divider></el-divider>

    <mission-list ref="mission"></mission-list>

  </div>
</template>

<script>
import AssignMission from "../management/AssignMission.vue";
import Invite from "../management/Invite.vue";
import {getLabel} from "../../../utils/methods";
import MissionList from "./MissionList.vue";
import Vue from "vue";
Vue.prototype.$getLabel = getLabel;

export default {
  name: "ContentTable",
  components: {MissionList, Invite, AssignMission},
  data () {
    return {
      visible: [],
      roleEdit: 'member',
      roleList:[
        {value: "owner", label: "项目所有者", cont: 2},
        {value: "manager", label: "项目管理员", cont: 1},
        {value: "member", label: "项目成员", cont: 0}
      ],
      projectData: {},
      allProjectData: [],
    }
  },
  mounted () {
    this.getProject()
  },
  methods: {
    async getProject() {
      let jsonObj = JSON.parse(window.sessionStorage.user);
      let id = jsonObj.user.userid
      let url = '/user/participating/' + id
      this.$axios
        .get(url)
        .then(res => {
          this.allProjectData = res.data.data
          for (let i = 0; i < this.allProjectData.length; i++) {
            if (this.allProjectData[i].project.id === parseInt(this.$route.params.id)) {
              this.projectData = this.allProjectData[i]
              console.log(this.projectData.role)
              this.visible = new Array(this.projectData.number).fill(false)
              this.$nextTick(() => {
                this.$refs.assign.disabled = this.projectData.role === "member"
                //console.log(this.$refs.assign.disabled)
                this.$refs.invite.disabled = this.projectData.role === "member"
                this.$refs.assign.assignForm.pjID = this.projectData.project.id
                this.$refs.assign.assignForm.pjName = this.projectData.project.title
                this.$refs.assign.userList = this.projectData.memberList
              })
            }
          }
        })
    },
    deleteMember(id) {
      this.$confirm('此操作将删除该成员, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const params = new FormData()
        params.append('project_id', this.$route.params.id)
        params.append('user_id', id)
        this.$axios
          .post('/project/member/kick', params)
          .then(resp => {
            if (resp.data.code === 0) {
              this.$message(
                {
                  message: resp.data.msg,
                  type: 'success'
                },
              ),
                this.getProject()
            } else if (resp.data.code === 1) {
              this.$message(
                {
                  message: resp.data.msg,
                  type: 'error'
                }
              )
            }
          })
          .catch(() => {
            this.$message(
              {
                message: '发生错误',
                type: 'error'
              })
          })
      })
    },
    editMember(scope) {
      this.visible[scope.$index] = false
      const params = new FormData()
      params.append('projectId', this.$route.params.id)
      params.append('userId', scope.row.id)
      params.append('role', this.roleEdit)
      this.$axios
        .post('/project/member/role', params)
        .then( resp => {
          if ( resp.data.code === 0 ) {
            this.$message(
              {
                message: resp.data.msg,
                type: 'success'
              }
            ),
              this.getProject()
          } else if ( resp.data.code === 1) {
            this.$message(
              {
                message: resp.data.msg,
                type: 'error'
              }
            )
          } else {
            this.$message(
              {
                message: resp.data.msg,
                type: 'error'
              }
            )
          }
        })
        .catch(error =>{
          this.$message(
            {
              message: error,
              type: 'error'
            })
        })
    }
  }
}
</script>

<style scoped>
.el-row {
  margin-bottom: 20px;
  &:last-child {
   margin-bottom: 0;
 }
}
</style>
