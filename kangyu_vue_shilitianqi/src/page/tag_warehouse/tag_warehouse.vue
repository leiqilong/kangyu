<template>
  <div class="container">
    <head-top></head-top>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="自定义表单标签" name="customForm"></el-tab-pane>
      <el-tab-pane label="消息推送类标签" name="messagePush"></el-tab-pane>
    </el-tabs>
    <div>
      <keep-alive>
        <router-view v-if="$route.meta.keepAlive"></router-view>
      </keep-alive>
      <router-view v-if="!$route.meta.keepAlive"></router-view>
    </div>
  </div>
</template>

<script>
  import headTop from '@/components/headTop'
  import tagRouter from './js/tag_router'
  export default {
    data() {
      return {
        activeName: 'customForm'
      };
    },
    methods: {
      handleClick(tab, event) {
        this.$router.push('/tag_warehouse/message_push_tag');
      }
    },
    components: {
      headTop
    }
  };
</script>

<style scoped>

</style>
