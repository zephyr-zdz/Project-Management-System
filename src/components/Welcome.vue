<template>
  <div id="bg">
    <div class="welcome">
      <h1>{{ msg }}</h1>
      <h4>{{ caption }}</h4>
      <a href="/login"><button class="button">LOGIN</button></a>
      <a href="/register"><button class="button">REGISTER</button></a>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Welcome',
  data () {
    return {
      msg: 'Welcome to the Project Management System',
      caption: new Date()
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#bg {
  background:url("~@/assets/BG02.png");
  width:100%;
  height:100%;
  position:fixed;
}
.welcome {
  position: absolute;
  transform: translate(-50%,-50%);
  top: 50%;
  left: 50%;
  color: ivory;
  font-family: 'Futura', sans-serif;
}

a{
  font-family: 'Futura', sans-serif;
  color: #f9ffff;
  text-decoration: none;
}

.button {
  margin: 20px;
  border: none;
  width: 130px;
  height: 40px;
  border-radius: 5px;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  display: inline-block;
  box-shadow:inset 2px 2px 2px 0px rgba(255,255,255,.5),
    7px 7px 20px 0px rgba(0,0,0,.1),
    4px 4px 5px 0px rgba(0,0,0,.1);
  outline: none;
  background-color: ghostwhite;
  color: black;
  font-family: 'Futura', sans-serif;
  font-weight: bolder;
  font-size: 20px;
}

.button:after {
  border-radius: 5px;
  position: absolute;
  content: "";
  width: 0;
  height: 100%;
  top: 0;
  left: 0;
  direction: ltr;
  z-index: -1;
  box-shadow:
    7px 7px 20px 0px #fff9;
  transition: all 0.3s ease;
}

.button:hover:after {
  left: auto;
  right: 0;
  width: 100%;
}

.button:active {
  top: 2px;
}

</style>
