<template>
  <div class="container">
    <div class="home"></div>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        formData: {},
        menuList: [
          {
            type: '0',
          },
          {
            type: '1',
          },
          {
            type: '2',
          },
          {
            type: '3',
          },
          {
            type: '4',
          }
        ]
      };
    },
    components: {
      //headTop
    },
    mounted() {
      this.oversizeChange()
    },
    methods: {
      oversizeChange() {
        // $(".home").attr("style", "height:" + parseFloat(window.innerHeight - 110) + "px;overflow: auto;overflow-x: hidden;")
       /* window.setTimeout(function () {
          var h1 = $("#img").height()
          var h2 = $("#right").height()
          var top = parseInt((100 - (h1 * 100) / h2) / 2)
          $(".center").attr("style", "top:" + top + "%;bottom:" + top + "%")
        }, 1000)*/
      },
      goLogin(type) {
        var that = this;
        if (parseInt(that.$store.getters.node) == parseInt(type)) {
          that.$message('已登录当前系统');
        } else {
          var url = './../dist/#/login?type=' + type
          window.open(url, '_blank');
        }
      }
    },
  };

</script>
<style lang="less">
  .home {
    width: 98%;
    //background:url('../../static/login/img/home.png') no-repeat;
    background-size: 100%;
    text-align: left;
    padding-right: 2%
  }

  .left {
    width: 66%;
    padding: 0 2%;
  }

  .right {
    width: 30%
  }

  .left img, .right img {
    width: 100%;
    max-height: 100%
  }

  .fl {
    float: left;
    height: 100%;
    position: relative
  }

  .flex_center {
    display: flex;
    justify-content: center; /* 水平居中 */
    align-items: center; /* 垂直居中 */
  }

  .center {
    position: absolute;
    top: 22%;
    bottom: 22%;
    left: 0;
    right: 0;
  }
</style>
