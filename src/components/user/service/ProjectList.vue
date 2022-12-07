<template>
  <el-table
      :data="projectData"
      style="width: 100%"
      :header-row-style="{height: '60px'}"
      @row-click="showContent"
      stripe>
      <el-table-column type="expand">
        <template v-slot="props">
          <el-descriptions class="margin-top" :column="2" direction="vertical" border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                项目名称
              </template>
              {{ props.row.project.title }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-mobile-phone"></i>
                项目ID
              </template>
              {{ props.row.project.id }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-location-outline"></i>
                所有者
              </template>
              {{ props.row.owner.username }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-mobile-phone"></i>
                项目人数
              </template>
              {{ props.row.number }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-mobile-phone"></i>
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
        width="200">
      </el-table-column>
      <el-table-column
        label="项目名称"
        prop="project.title">
      </el-table-column>
      <el-table-column
        label="项目所有者id"
        prop="project.owner_id"
        width="200">
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
</template>

  <style scoped>

  </style>

  <script>
    export default {
      name:'ProjectList',
      data() {
        return {
          projectData: [
            {
              project: {
                id: 1,
                title: "片候设",
                intro: "已候军制全局点数山公更先风劳习所。六已该院战世元后以义观件亲此厂业本知。分识着应众越正金准数展受正细采阶。西集代南力组安油信转方才准队员月了适。历平青什光近深活段所速电半毛始办七。该往学办看电及参及件到九全类被。",
                owner_id: 1
              },
              managerIdList: [],
              memberIdList: [
                1,
                2
              ],
              number: 2,
              owner: {
                id: 1,
                name: "张三",
                email: ""
              }
            },
            {
              "project": {
                "id": 3,
                "title": "制并当学保",
                "intro": "活数维性例电子派维信此展出。被线与电般三大过元养广断社场观。王该教儿前象化科话体地立关酸开共。记是过证次持院命省得引气们点去及因率。子实且科就人术程同形合直此才新。济油安离眼科七中革深备员民水无切林国。",
                "owner_id": 1
              },
              "managerIdList": [],
              "memberIdList": [
                1
              ],
              "number": 1
            },
            {
              "project": {
                "id": 4,
                "title": "团计真例龙",
                "intro": "今被群保口高走式色维命参研他导。军斯常时资位特于石划利适元志。空转达律族权长题走转办片间部行重节。响真据器等达什变才三转规发厂效验你。并员果在院位清大记儿她流打育于。",
                "owner_id": 1
              },
              "managerIdList": [],
              "memberIdList": [
                1
              ],
              "number": 1
            },
            {
              "project": {
                "id": 5,
                "title": "由物织始年器",
                "intro": "油划合质值变族四小后没省际。进计建身真准感机使头史记说时保角。马要区以白响也技还住资设观难。她风养个度法用眼联具公装。",
                "owner_id": 1
              },
              "managerIdList": [],
              "memberIdList": [
                1
              ],
              "number": 1
            },
            {
              "project": {
                "id": 6,
                "title": "马广称目",
                "intro": "工采规儿始道华划每段信阶林表走。低为响清西值院集主半品至子政消导。所取点国被号体作叫矿严几物型王消。矿书支导经数其什次书持照该毛料。总称进团己律号经边先二与斗难。究快原效住等质正三标间取。",
                "owner_id": 1
              },
              "managerIdList": [],
              "memberIdList": [
                1
              ],
              "number": 1
            },
            {
              "project": {
                "id": 7,
                "title": "定意本手把济二",
                "intro": "严许口地象名物指决厂下文料建何着。达段精对了验复路济研他到走。话候口权族被去六决层元组清记去完。小为也值观矿收团出气铁农步保务。全构山反己便行叫间定许交一整。家器历毛离身光市易先织养转之。",
                "owner_id": 1
              },
              "managerIdList": [],
              "memberIdList": [
                1
              ],
              "number": 1
            },
            {
              "project": {
                "id": 8,
                "title": "包电定点起音",
                "intro": "明车压许经白办划且变号毛着只来收向适。才响斗政进起育中着花往出验。段称位效统华作资论后己领位京族者清。之重她业共难员节型多几民放题华积识阶。力全比两江则资县亲十料查为。",
                "owner_id": 1
              },
              "managerIdList": [],
              "memberIdList": [
                1
              ],
              "number": 1
            },
            {
              "project": {
                "id": 9,
                "title": "包电定点起音",
                "intro": "明车压许经白办划且变号毛着只来收向适。才响斗政进起育中着花往出验。段称位效统华作资论后己领位京族者清。之重她业共难员节型多几民放题华积识阶。力全比两江则资县亲十料查为。",
                "owner_id": 1
              },
              "managerIdList": [],
              "memberIdList": [
                1
              ],
              "number": 1
            }
          ],
        }
      },
      mounted () {
      this.getProject()
      },
      methods:{
        async getProject(){
          let jsonObj = JSON.parse(window.localStorage.user);
          let id = jsonObj.user.userid
          let url ='/user/participating/' + id
          this.$axios
            .get(url)
            .then(res => {this.projectData = res.data.data})
          },
        exit(index,row){
          let jsonObj = JSON.parse(window.localStorage.user);
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
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消退出'
          });
        });
        },
        showContent(row, column) {
          //console.log(column)
          if (column.label != "操作") {
            this.$router.push({path: `/user-home/my-project/${row.project.id}`})
          }
        },
      }
    }
  </script>
