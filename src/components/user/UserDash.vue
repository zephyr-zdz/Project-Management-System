<template>
  <div id="bg">
    <el-container>
      <el-main>
        <section id="banner">
          <div id="main-slider" class="flexslider">
            <img src="../../assets/title.jpg" alt="flexslider" style="height: 95%; width: 95%" />
            <div class="flex-caption" style="margin-left: 18%">
              <div style="font-size: 70px;background-color:rgba(255,255,255,0.5);margin-right:45%;width: 375px">
                <h3 style="font-size: 60px;color:#204F9F; opacity: 100%;">项目管理系统</h3>
              </div>
            </div>
          </div>
        </section>


        <div class="el-affix" style="margin-left: 75%; display: none;">
          <div class="" style="">
            <button class="el-button" aria-disabled="false" type="button" id="topbutton">
          <!--v-if--><span class=""><b>🔝</b></span>
            </button>
          </div>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "StudentDash",
}
</script>

<style scoped>
#bg {
  background-color: ghostwhite;
  height: 100%;
}
</style>




<div class="el-affix" style="margin-left: 75%; display: none;">
<div class="" style=""><button class="el-button" aria-disabled="false" type="button" id="topbutton">
<!--v-if--><span class=""><b>🔝</b>
</span>
</button>
</div>
</div>
