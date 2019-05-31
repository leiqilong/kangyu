<template>
  <div id="app" class="fillcontain">
    <!--<img src="./assets/logo.png">-->
    <router-view/>
  </div>
</template>

<script>
  export default {
    name: 'App'
  }
</script>

<style>
  .fillcontain {
    height: 100%;
  }
</style>
<style lang="less">
  @import './style/common';
</style>
