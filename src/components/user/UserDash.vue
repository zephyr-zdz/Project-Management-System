<template>
  <div id="bg">
    <el-container>
      <el-main>
        <!-- <section id="banner">
          <div id="main-slider" class="flexslider">
            <img src="../../assets/title1.jpg" alt="flexslider" style="height: 95%; width: 95%" />
            <div class="flex-caption" style="margin-left: 18%">
              <div style="font-size: 70px;background-color:rgba(255,255,255,0.5);margin-right:45%;width: 375px">
                <h3 style="font-size: 60px;color:#204F9F; opacity: 100%;background-color: ghostwhite">项目管理系统</h3>
              </div>
            </div>
          </div>
        </section> -->
        <div style="position:absolute;">
          <div class="app-container check-calendar">
            <el-date-picker 
              v-model="value" 
              type="month" 
              :clearable="false" 
              value-format="yyyy-MM" 
              placeholder="请选择月份"
              @change="loadAtten()"
              class="calendarInput"
            />
            <el-calendar v-model="value">
            </el-calendar>
          </div>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "StudentDash",
  data () {
      return {
        value: "",
        date:new Date(),
        newgetDate:[],
        newgetMonth:[],
        newgetYear:[],
        newresDate:[],
        resData:[]
      }
    },

    created(){
      this.nowday();
      this.$nextTick(() => {
        // 点击上个月
        let prevBtn1 = document.querySelector('.el-calendar__button-group .el-button-group>button:nth-child(1)');
        prevBtn1.addEventListener('click',() => {
          this.newgetAtten(this.dateFormat('YYYY-mm-dd',this.value));
        })
        // 点击今天
        let prevBtn2 = document.querySelector('.el-calendar__button-group .el-button-group>button:nth-child(2)');
        prevBtn2.addEventListener('click',() => {
          this.newgetAtten(this.dateFormat('YYYY-mm-dd',this.value));
        })
        // 点击下个月
        let prevBtn3 = document.querySelector('.el-calendar__button-group .el-button-group>button:nth-child(3)');
        prevBtn3.addEventListener('click',() => {
          this.newgetAtten(this.dateFormat('YYYY-mm-dd',this.value));
        })
      })
    },
    methods: {
      loadAtten(){
        this.newnowday();
        this.newgetAtten(this.newresDate)
      },
      // 日期
      nowday(){
        var d = this.date;
        this.newgetYear = d.getFullYear();
        this.newgetMonth = d.getMonth() + 1;
        this.newgetDate = d.getDate();
        this.newresDate = this.newgetYear + "-" + (this.newgetMonth < 10 ? String("0" + this.newgetMonth) : this.newgetMonth) + "-" + (this.newgetDate < 10 ? String("0" + this.newgetDate) : this.newgetDate)
        this.newgetAtten(this.newresDate)
      },
      //新日期
      newnowday(){
        this.newresDate = this.value + "-" + "01"
      },
      // 考勤
      newgetAtten(currdate){
        console.log('currdate',currdate)
      },
      //点击事件
      calendarOnClick(date){
        console.log(date)
      },   
      dateFormat(fmt, date) {
        let ret = "";
        date = new Date(date);
        const opt = {
          'Y+': date.getFullYear().toString(), // 年
          'm+': (date.getMonth() + 1).toString(), // 月
          'd+': date.getDate().toString(), // 日
          'H+': date.getHours().toString(), // 时
          'M+': date.getMinutes().toString(), // 分
          'S+': date.getSeconds().toString() // 秒
          // 有其他格式化字符需求可以继续添加，必须转化成字符串
        }
        for (let k in opt) {
          ret = new RegExp('(' + k + ')').exec(fmt)
          if (ret) {
            fmt = fmt.replace(
              ret[1],
              ret[1].length == 1 ? opt[k] : opt[k].padStart(ret[1].length, '0')
            )
          }
        }
        return fmt
       },
    }
  }
</script>

<style scoped>
#bg {
  background-color: ghostwhite;
  height: 100%;
}
.calendarInput{position:absolute;top:5px; left:120px;}
  .calendar-day{width: calc(100% - 11px); height: calc(100% - 11px); padding: 8px;}
  .calendarDiv{
    width:100%;
    height:100%;
  }
  .item{ 
    width: calc(100% - 11px); 
    height: calc(100% - 11px); 
    padding: 8px;
    background:rgba(255, 240, 240, 1);
  }
  .item2{ 
    width: calc(100% - 11px); 
    height: calc(100% - 11px); 
    padding: 8px;
    background:#fff;
  }
  .sunday{
    width: calc(100% - 11px); 
    height: calc(100% - 11px); 
    padding: 8px;
    background:rgba(255, 240, 240, 1);
  }
  .Saturday{
    width: calc(100% - 11px); 
    height: calc(100% - 11px); 
    padding: 8px;
    background:rgba(255, 240, 240, 1);
  }
</style>
<style>
  .el-calendar-table .el-calendar-day{padding: 0px !important;}
</style>
