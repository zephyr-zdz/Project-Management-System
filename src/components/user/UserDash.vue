<template>
  <div id="bg">
    <h1 style="font-size: 80px;color:ghostwhite; background-opacity: 0%; position: relative; top:30%">项目管理系统</h1>
  </div>
</template>

<script>
export default {
  name: "StudentDash",
}
</script>

<style scoped>
#bg {
  background: linear-gradient(to bottom, rgba(255, 255, 255, 0) 50%, rgba(248, 248, 255, 1)100%),url("~@/assets/BG02.png") no-repeat center center;
  height: 90%;
  width: 88.5%;
  position: fixed;
  background-size: cover;
}
h1 {
  font-family: "Source Han Sans CN";
}
</style>
