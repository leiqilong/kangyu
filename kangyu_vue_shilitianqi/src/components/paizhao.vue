<template>
    <div class=" paizhao dialog-container">
        <el-dialog :title="title" :visible.sync="visible"  @close="close" :show="show">
          <div class="photograph_page">
            <h1 class="pageTitle">请对着镜头拍照</h1>
            <div>
              <div class="video_box">
                <div class="border" v-if="!isShowImg">
                  <span class="top_left"></span>
                  <span class="top_right"></span>
                  <span class="bottom_left"></span>
                  <span class="bottom_right"></span>
                </div>
                <video ref="video" width="300" height="400"></video>
                <img ref="img" class="img" v-show="isShowImg">
              </div>
              <canvas class="canvas" v-show="false" ref="canvas"></canvas>
            </div>
            <div class="button_group">
              <el-button type="primary" size="mini" value='拍照'  @click="photo" v-show="!isShowImg">拍照</el-button>
              <el-button type="primary" size="mini" value='重新拍照' @click="resetPhoto">重新拍照</el-button>
              <el-button type="primary" size="mini" value='确定'  @click="surePhoto">确定</el-button>
            </div>
          </div>
        </el-dialog>
    </div>
</template>
<script>
export default {
  name: 'Photograph',
  data () {
    return {
      visible: this.show,
      video: null,
      isShowImg: false,
      dataURL: null,
      track: null
    }
  },
  props: {
      show: {
          type: Boolean,
          default: true
      },
      title:{
          type:String,
          default:''
      }
  },
  watch: {
      show () {
          this.visible = this.show;
          if(this.show){
            this.initPhoto();
          }
      }
  },
  methods: {
    initPhoto () {
      let self = this;
      let mediaDevices = navigator.mediaDevices.getUserMedia({ audio: false, video: { width: 300, height: 400 } });
      mediaDevices.then(mediaStream => {
        let video = self.$refs.video;
        video.src = window.URL.createObjectURL(mediaStream);
        video.onloadedmetadata = (e) => {
          video.play();
        };
        self.video = video;
        self.track = mediaStream.getTracks()[0];
      })
      .catch(err => {
        console.log('err.message' + err.name);
      })
    },
    photo () {
      let self = this;
      let dataURL = null;
      let img = self.$refs.img;
      let canvas = self.$refs.canvas;
      let context = canvas.getContext('2d');
      let width = 300;
      let height = 400;
      canvas.width = width;
      canvas.height = height;
      img.height = height;
      context.drawImage(self.video, 0, 0, width, height);
      dataURL = canvas.toDataURL('image/jpeg');
      img.src = dataURL;
      // 记录dataURL，并且改变按钮的状态
      self.dataURL = dataURL
      self.isShowImg = true
    },
    resetPhoto () {
      let self = this
      self.isShowImg = false
    },
    surePhoto () {
      this.$emit('listenToChildEvent', this.dataURL);
      this.isShowImg = false
    },
    stopPhoto () {
      this.track.stop()
    },
    close () {
      this.$emit('update:show', false)
      this.stopPhoto()
    }
  }
}
</script>
<style>
.paizhao div>.el-dialog{
  width:20%!important;
};
</style>
<style lang="less" scoped>
  .photograph_page {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    .button_group {
      margin-top:20px;
      .MyButton {
        &:first-child {
          margin-right: 60px;
        }
      }
    }
  }
  .video_box {
    width: 300px;
    height:400px;
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
    box-shadow: 1px 3px 10px #000;
    border: 10px solid #fff;
    background: #fff;
    border-radius: 2px;
    box-sizing: content-box;
    video {
      z-index: 999;
      margin: 0;
    }
    .img {
      width: 100%;
      position: absolute;
      top: 0;
      left: 0;
      z-index: 9999;
      display: block;
      background: red;
    }
    .border {
      width: 100%;
      height: 100%;
      position: absolute;
      top:0;
      bottom:0;
      left:0;
      right: 0;
      span {
        width: 30px;
        height: 30px;
        position: absolute;
        z-index: 99999;
        transform: translate3d(0, 0, 0);
        animation: flashing 1s linear 1s infinite alternate;
      }
      @border: 2px solid #000;
      @size: 20px;
      .top_left {
        border-top: @border;
        border-left: @border;
        top: @size;
        left: @size;
      }
      .top_right {
        border-top: @border;
        border-right: @border;
        top: @size;
        right: @size;
      }
      .bottom_left {
        border-bottom: @border;
        border-left: @border;
        bottom: @size;
        left: @size;
      }
      .bottom_right {
        border-right: @border;
        border-bottom: @border;
        right: @size;
        bottom: @size;
      }
    }
  }
  @keyframes flashing {
    0% {
      opacity: 1;
    }
    100% {
      opacity: 0;
    }
  }
</style>
