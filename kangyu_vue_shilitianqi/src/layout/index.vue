<template>
  <div class="manage_page fillcontain">
    <div class="headerBox">
      <div class="iconTitle">
        <img src="../.././static/login/img/yun2.png"/>
        <span>{{hospitalname}}</span>
      </div>
      <div class="sign-out">
        <div style="color:#fff;margin-right:30px; float:left">您好，{{doctorName}}</div>
        <div style="color:#fff;float:left" @click="logout()">
          <img src="../.././static/login/img/out.png"/>
        </div>
      </div>
    </div>
    <div class="wel-container">
      <div class="leftContent croll">
        <el-menu :default-active="$route.path" style="min-height: 100%;" background-color="#2d3e4f"
                 text-color="#fff" theme="dark" router>
          <div v-for="(item,index) in newrouter[0].children" v-if="item.meta.show" :key="index">
            <el-menu-item v-if="!item.meta.children" :index="item.path">{{item.name}}</el-menu-item>
            <el-submenu :index="''+index" v-if="item.meta.children">
              <template slot="title">{{item.name}}</template>
              <div v-for="(items,ind) in item.meta.children" :key="ind">
                <el-menu-item :index="items.path" v-if="!items.meta.children">{{items.name}}</el-menu-item>
                <el-submenu :index="''+index+ind" v-if="items.meta.children">
                  <template slot="title">{{items.name}}</template>
                  <el-menu-item v-for="(witem,wind) in items.meta.children" :key="wind" :index="witem.path">
                    {{witem.name}}
                  </el-menu-item>
                </el-submenu>
              </div>
            </el-submenu>
          </div>
        </el-menu>
      </div>
      <div class="rightContent croll">
        <keep-alive>
          <router-view v-if="$route.meta.keepAlive"></router-view>
        </keep-alive>
        <router-view v-if="!$route.meta.keepAlive"></router-view>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex';

  export default {
    data() {
      return {
        doctorName: '',
        hospitalname: '',
        color: ''
      }
    },
    mounted() {
      console.log('newrouter[0]:', this.newrouter[0]);
      this.doctorName = this.$store.getters.username;
      this.hospitalname = this.$store.getters.hospitalname;
    },
    methods: {
      logout() {
        this.$store.dispatch('Logout').then(() => {
          this.$router.push({path: '/login'});
        }).catch(err => {
          this.$message.error(err);
        });
      }
    },
    computed: {
      ...mapGetters([
        'newrouter'
      ])
    },
    components: {},
  }
</script>
<style lang="less">
  /* .wTableHeader{
      background:#ebeef5
  } */
  .headerBox {
    background-color: #2EC4B6;
    height: 60px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-left: 20px;
  }

  .headerBox {
    .sign-out {
      cursor: pointer;
      height: 30px;
      line-height: 30px;
      margin-right: 30px;

      a {
        color: #fff;
      }

      img {
        display: inline-block;
        height: 30px;
      }
    }
  }

  ;
  .iconTitle {
    img {
      display: inline-block;
      height: 40px;
    }

    span {
      display: inline-block;
      height: 30px;
      line-height: 30px;
      color: #fff;
      font-size: 24px;
      font-family: "SimHei"
    }
  }

  .iconTitle * {
    vertical-align: middle
  }

  .bottomSave {
    margin: 20px 0 30px 0;
    text-align: center;
  }

  .wel-container {
    position: absolute;
    top: 75px;
    bottom: 0;
    left: 0;
    right: 0;
  }

  .leftContent {
    height: 100%;
    position: absolute;
    overflow: auto;
    top: 0;
    left: 0;
    bottom: 0;
    width: 200px;
    background-color: #2d3e4f;
    border-top-right-radius: 10px
  }

  .rightContent {
    margin-left: 215px;
    height: 100%;
    overflow: auto;
    background: #fff;
    border-top-left-radius: 10px
  }

</style>
