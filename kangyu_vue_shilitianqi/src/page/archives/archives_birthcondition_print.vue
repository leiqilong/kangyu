<template>
  <div class="container">
    <div class="heart-head Noprint" v-if="titleShow">
      <span @click="returnPrevisou()" style="cursor:pointer;">返回</span>
    </div>
    <div class="container-birthcondition">
      <div class="wrap">
        <div class="con" id="wrap">
          <table>
            <tr>
              <td colspan="6" class="tab_title" style="text-align:center">出生情况记录</td>
            </tr>
            <tr>
              <td class="tab_title">出生地点：</td>
              <td class="td_content">{{chushengdidian}}</td>
              <td class="tab_title">胎龄：</td>
              <td class="td_content">{{renshenzhoushu}}周+{{renshenzhoushuday}}天</td>
              <td class="tab_title">分娩方式：</td>
              <td class="td_content">{{fenmiantype}}</td>
            </tr>
            <tr>
              <td class="tab_title">系第</td>
              <td class="td_content"> {{jidjitai}} <span class="tab_title">胎</span>
                <!--</td>
                <td>-->
                <span class="tab_title">第</span>
                <!--</td>
                <td class="td_content"> -->
                {{dijichan}} <span class="tab_title">产</span></td>
              <td class="tab_title">胎数：</td>
              <td class="td_content">{{taishu}}<span class="tab_title">胎</span></td>
            </tr>
            <tr>
              <td class="tab_title">孕期患病：</td>
              <td>{{yqhuanbing}}</td>
              <td class="tab_title">孕期服药：</td>
              <td>{{yqfuyao}}</td>
              <td class="tab_title">受孕方式：</td>
              <td>{{shouyunfangshi}}</td>
            </tr>
            <tr>
              <td class="tab_title">Apgar评分：</td>

              <td colspan="6">
                <span class="tab_title">1分钟：</span>
                {{yifenzhong}}<span class="tab_title">分|</span>
                <!-- </td>
                 <td >-->
                <span class="tab_title">5分钟：</span>
                {{wufenzhong}}<span class="tab_title">分|</span>
                <!--</td>
                <td >-->
                <span class="tab_title">10分钟：</span>
                {{shifenzhong}}<span class="tab_title">分</span>
              </td>
              <!--<td></td>      -->
            </tr>
            <tr>
              <td class="tab_title">窒息情况：</td>
              <td colspan="2">{{zhixiqingkuang}}</td>
              <td class="tab_title">复苏措施：</td>
              <td colspan="2">{{fusucuoshi}}</td>
            </tr>
            <tr>
              <td class="tab_title">抽搐：</td>
              <td>{{chouchu}}</td>
              <td class="tab_title">黄疸消退时间（天）：</td>
              <td>{{huangdanxtsj}}</td>
              <td class="tab_title">出生缺陷：</td>
              <td>{{chushengquexian}}</td>
            </tr>
            <tr>
              <td class="tab_title">羊水：</td>
              <td>{{yangshui}}</td>
              <td class="tab_title">脐带胎盘：</td>
              <td>{{qidaitaipan}}</td>
              <td class="tab_title">其它：</td>
              <td>{{qita}}</td>
            </tr>
            <tr>
              <td class="tab_title">身长：</td>
              <td>{{shenchang}}<span class="tab_title">cm</span></td>
              <td class="tab_title">体重：</td>
              <td colspan="3">{{tizhong}} <span class="tab_title">kg</span></td>
            </tr>
            <tr>
              <td class="tab_title">听力筛查：</td>
              <td>{{tinglishaichai}}</td>
              <td class="tab_title">PKU：</td>
              <td>{{pku}}</td>
              <td class="tab_title">CH：</td>
              <td>{{ch}}</td>
            </tr>
            <tr>
              <td class="tab_title">新生儿期疾病：</td>
              <td colspan="2">{{xseqjb}}</td>
              <td class="tab_title">家族遗传病史：</td>
              <td colspan="2">{{jzycbs}}</td>
            </tr>
            <tr>
              <td class="tab_title">高危信息：</td>
              <td colspan="5"></td>
            </tr>
            <tr>
              <td class="tab_title">胎儿期：</td>
              <td colspan="2">{{taierqi}}</td>
              <td class="tab_title">分娩期：</td>
              <td colspan="2">{{fenmianqi}}</td>
            </tr>
            <tr>
              <td class="tab_title">新生儿期：</td>
              <td colspan="2">{{xinshengerqi}}</td>
              <td class="tab_title">婴儿期：</td>
              <td colspan="2">{{yingerqi}}</td>
            </tr>
          </table>
        </div>
      </div>
      <div style="width:590px;margin:30px auto; text-align:right">
        <button class="achbtn Noprint" @click="printPage()">打印</button>
      </div>
    </div>

  </div>
</template>
<script>
  import headTop from '../../components/headTop'
  import {baseUrl} from '../../config/env'
  import {getbirthInfobyguId} from '../../api/getData'

  export default {
    data() {
      return {
        renshenzhoushu: '',
        renshenzhoushuday: '',
        chushengdidian: '',
        fenmiantype: '',
        jidjitai: '',
        dijichan: '',
        taishu: '',
        yqhuanbing: '',
        yqfuyao: '',
        shouyunfangshi: '',
        yifenzhong: '',
        wufenzhong: '',
        shifenzhong: '',
        zhixiqingkuang: '',
        fusucuoshi: '',
        chouchu: '',
        huangdanxtsj: '',
        chushengquexian: '',
        yangshui: '',
        qidaitaipan: '',
        qita: '',
        shenchang: '',
        tizhong: '',
        tinglishaichai: '',
        pku: '',
        ch: '',
        xseqjb: '',
        jzycbs: '',
        taierqi: '',
        fenmianqi: '',
        xinshengerqi: '',
        yingerqi: '',
        titleShow: true
      }
    },

    components: {
      headTop
    },

    beforeRouteLeave(to, from, next) {
      next(true);
    },

    mounted() {
      const self = this;
      const printBtnShow = self.mystore.getStore('printBtnShow');
      self.titleShow = printBtnShow ? printBtnShow.titleShow : true;
      const data = self.mystore.getStore('data');
      console.log('data', data);
      if (data && data.flag == 'search') {
        getbirthInfobyguId({guid: data.data.guid}).then(res => {
          if (!res) return;
          self.mystore.setStore('birthConditionPrintData', res)
          self.initData();
        });
      } else {
        self.initData();
      }
    },

    methods: {
      initData() {
        const self = this;
        const data = self.mystore.getStore('birthConditionPrintData');
        // console.log(data)
        self.renshenzhoushu = data.renshenzhoushu;
        self.renshenzhoushuday = data.renshenzhoushuday;
        self.chushengdidian = data.chushengdidian;
        self.fenmiantype = data.fenmiantype;
        self.jidjitai = data.jidjitai;
        self.dijichan = data.dijichan;
        self.taishu = data.taishu;
        self.yqhuanbing = data.yqhuanbing;
        self.yqfuyao = data.yqfuyao;
        self.shouyunfangshi = data.shouyunfangshi;
        self.yifenzhong = data.yifenzhong;
        self.wufenzhong = data.wufenzhong;
        self.shifenzhong = data.shifenzhong;
        self.zhixiqingkuang = data.zhixiqingkuang;
        self.fusucuoshi = data.fusucuoshi;
        self.chouchu = data.chouchu;
        self.huangdanxtsj = data.huangdanxtsj;
        self.chushengquexian = data.chushengquexian;
        self.yangshui = data.yangshui;
        self.qidaitaipan = data.qidaitaipan;
        self.qita = data.qita;
        self.shenchang = data.shenchang;
        self.tizhong = data.tizhong;
        self.tinglishaichai = data.tinglishaichai;
        self.pku = data.pku;
        self.ch = data.ch;
        self.xseqjb = data.xseqjb;
        self.jzycbs = data.jzycbs;
        self.taierqi = data.taierqi;
        self.fenmianqi = data.fenmianqi;
        self.xinshengerqi = data.xinshengerqi;
        self.yingerqi = data.yingerqi;
      },

      returnPrevisou() {
        this.$router.push({path: '/archives_card/sub_archives'});
      },

      printPage() {
        let subOutputRankPrint = document.getElementById('wrap');
        let newContent = subOutputRankPrint.innerHTML;
        let oldContent = document.body.innerHTML;
        document.body.innerHTML = newContent;
        window.print();
        window.location.reload();
        document.body.innerHTML = oldContent;
        return false;
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

  @media print {
    .Noprint {
      DISPLAY: none;
    }
  }

  ;
  .container-birthcondition {
    width: 740px;
    margin: 0 auto;
  }

  .con {
    margin: auto;
  }

  table {
    width: 680px;
    border-collapse: collapse;
  }

  table tr {
    font: 14px Helvetica, Helvetica, Tahoma, Arial, sans-serif;
    height: 40px;
  }

  table td {
    font-size: 16px;
    /*border:1px solid #000408;*/
    padding: 3px 7px 2px 7px;
    cursor: default;
  }

  .tab_title {
    font-weight: bold;
    padding: 20px 0;
    text-align: right;
  }

  .td_content {
    text-align: left;
  }

  .achbtn {
    font: 14px Helvetica Helvetica, Tahoma, Arial, sans-serif;
    color: #fff;
    border: 0px;
    padding-top: 4px;
    padding-bottom: 4px;
    padding-left: 9px;
    padding-right: 9px;
    background-color: #FC7300;
    border-radius: 5px;
    outline: none;
  }
</style>
