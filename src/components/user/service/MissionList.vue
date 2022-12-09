<template>
  <el-table 
    ref="filterTable"
      :data="TaskData"
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
        prop="assignee"
        label="任务执行人"
        width="180"
        column-key="assignee"
        :filters="getAllassignee"
      :filter-method="filterHandler">
      </el-table-column>
      <el-table-column
        prop="intro"
        label="任务简介">
      </el-table-column>

  </el-table>
</template>
  
<script>
import AssignMission from "../management/AssignMission.vue";
import Invite from "../management/Invite.vue";
import {getLabel} from "../../../utils/methods";
import Vue from "vue";
Vue.prototype.$getLabel = getLabel;
    export default {
      data() {
        return {
          TaskData:[
          {
            title:"",
            assigner:"",
            assignee:"",
            taskinfo:""
          }],
          Allassignee:[{
            text:this.TaskData.assignee,
            value:this.TaskData.assignee
          }],
          projectData:
          {
            project: {
              id: 1,
              title: "片候设",
              intro: "已候军制全局点数山公更先风劳习所。六已该院战世元后以义观件亲此厂业本知。分识着应众越正金准数展受正细采阶。西集代南力组安油信转方才准队员月了适。历平青什光近深活段所速电半毛始办七。该往学办看电及参及件到九全类被。",
              owner_id: 1
            },
            memberList: [
              {
                id: 1,
                username: "张三",
                email: ""
              }
            ],
            number: 2,
            owner: {
              id: 1,
              username: "张三",
              email: ""
            },
            role: ''
          },
        allProjectData: [
          {
            project: {
              id: 1,
              title: "片候设",
              intro: "已候军制全局点数山公更先风劳习所。六已该院战世元后以义观件亲此厂业本知。分识着应众越正金准数展受正细采阶。西集代南力组安油信转方才准队员月了适。历平青什光近深活段所速电半毛始办七。该往学办看电及参及件到九全类被。",
              owner_id: 1
            },
            memberIdList: [
              1,
              2
            ],
            number: 2,
            owner: {
              id: 1,
              username: "张三",
              email: ""
            },
            role: ''
          },
        ]


        }
      },
      mounted () {
        this.getMission()
      },
      methods: {
        filterHandler(value, row, column) {
          const property = column['property'];
          return row[property] === value;
        },
        async getMission() {
        let jsonObj = JSON.parse(window.localStorage.user);
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
                this.$refs.assign.assignForm.taskName = this.TaskData.title
                this.$refs.assign.assignForm.userid = this.TaskData.assignee
                jsonObj.user.userid = this.TaskData.assigner
                this.$refs.assign.assignForm.taskInfo = this.TaskData.taskinfo
              }
            }
          })
        },


      }
    }
  </script>