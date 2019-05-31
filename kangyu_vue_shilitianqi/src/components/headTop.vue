<template>
    <div class="header_container">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item v-for="(item,index) in $route.meta.routeList" :key="index">{{item}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
</template>
<script>

</script>
<style lang="less">
	@import '../style/mixin';
	.header_container{
		height: 30px;
		display: flex;
		justify-content: space-between;
		align-items: center;
    padding-left: 20px;
    margin-top:10px;
	}
  .header_container{
    .avator{
      .wh(36px, 36px);
      border-radius: 50%;
      margin-right: 37px;
    };
    .el-dropdown-menu__item{
      text-align: center;
    };
    .sign-out{
      cursor:pointer;
      margin-right:10px;
    }
  };

</style>
