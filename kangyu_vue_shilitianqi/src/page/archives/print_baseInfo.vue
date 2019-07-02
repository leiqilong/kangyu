<template>
  <div class="container">
    <div class="heart-head Noprint">
      <span @click="returnPrevisou()" style="cursor:pointer;">返回</span>
    </div>
    <div class="archives_container">
      <div class="Noprint">
        <el-row :gutter="20">
          <el-col :span="24">
            <el-tabs v-model="activeName" @tab-click="routerTo" type="card">
              <el-tab-pane label="档案信息" name="print_archives"></el-tab-pane>
              <el-tab-pane label="出生记录" name="archives_birthcondition_print"></el-tab-pane>
            </el-tabs>
          </el-col>
        </el-row>
      </div>
      <router-view></router-view>
    </div>
  </div>
</template>

<script>

  export default {
    data: function () {
      return {
        activeName: 'print_archives',
        backurl: ''
      }
    },

    mounted() {
      const self = this;
      const data = this.mystore.getStore('data');
      if (data) {
        self.backurl = data.backurl;
      }
      let str = window.location.href;
      let index = str.lastIndexOf("\/");
      self.activeName = str.substring(index + 1, str.length);

    },
    methods: {
      returnPrevisou() {
        const self = this;
        self.$router.push({path: '/examination_items/' + self.backurl});
      },
      routerTo: function (tab, event) {
        this.mystore.setStore('printBtnShow', {titleShow: false});
        this.$router.push('/print_baseInfo/' + tab.name);
      }
    }
  }
</script>
<style lang="less" scoped>
  .heart-head {
    background-color: #EFF2F7;
    height: 60px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-left: 20px;
  }

  .archives_container {
    padding: 15px;
  }
</style>
