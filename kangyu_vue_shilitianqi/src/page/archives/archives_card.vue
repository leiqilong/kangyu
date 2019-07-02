<template>
<div class="container">
  <head-top ></head-top>
  <div class="archives_container">
    <el-row :gutter="20">
      <el-col :span="24">
        <el-tabs v-model="activeName"  @tab-click="routerTo" type="card">
          <el-tab-pane label="基本信息" name="archives_info"></el-tab-pane>
          <el-tab-pane label="出生情况记录" name="sub_archives"></el-tab-pane>
        </el-tabs>
      </el-col>
    </el-row>
    <keep-alive>
      <router-view v-if="$route.meta.keepAlive"></router-view>
    </keep-alive>
    <router-view v-if="!$route.meta.keepAlive"></router-view>
  </div>
</div>
</template>

<script>
  import headTop from '../../components/headTop'
  export default {
      data: function () {
          return {
            activeName: 'archives_info',
          }
      },
      components: {
        headTop
      },
      mounted(){
          var that=this;
         const tabname = this.mystore.getStore("tabname");
         if(tabname &&tabname.tabName!=''){
           that.activeName=tabname.tabName;
         }else{
           that.activeName='archives_info';
         }
      },
      methods: {
        routerTo: function (tab,event) {
          const self = this;  
          var tabname=tab.name;
          let tabName = {tabName:tab.name}; 
          self.mystore.setStore("tabname",tabName);
          this.$router.push('/archives_card/'+tab.name);
        },
         returnPrevisou(){
          const self = this;  
          self.$router.push({path:'/archives_list'});
        },
      }
    }
</script>
<style>
  .archives_container{
    padding: 15px;
  }
  .back-head{
		background-color: #EFF2F7;
		height: 60px;
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding-left: 20px;
	}
</style>
