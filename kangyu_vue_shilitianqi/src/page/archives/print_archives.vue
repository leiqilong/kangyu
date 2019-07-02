<template>
  <div class="container" style="position:relative;width:100%;height:100%;">
    <!-- <div class="heart-head Noprint">
        <span @click="returnPrevisou()" style="cursor:pointer;">返回</span>
    </div> -->
    <div v-show="titleShow" class="heart-head" style="top:0;left:0;right:0;overflow:hidden">
      <span @click="returnPrevisou()" style="cursor:pointer;">返回</span>
    </div>
    <div class="wrap" style="position:absolute;top:85px;left:0;right:0;overflow:auto">
      <div class="con-pgarc" id="wrap">
        <table>
          <tr>
            <td colspan="10" style="text-align:center;font-size:20px;font-weight: bold;">儿童健康档案</td>
          </tr>
          <tr>
            <td colspan="1"></td>
            <!-- <td colspan="5" ><span class="tab_title">保健号：</span>{{healthnum}}</td>        -->
            <td colspan="3" style="text-align:right">
              <!-- <span class="tab_title">卡类型：</span>{{cardtype}} -->
            </td>
            <td colspan="1"></td>
          </tr>
          <tr style="height:0px;">
            <td colspan="10" style="border-bottom: solid black 3px;"></td>
          </tr>
          <tr>
            <td colspan="1"></td>
            <td colspan="5"><span class="tab_title">姓名：</span>{{name}}</td>
            <td colspan="3" rowspan="6" style="padding-right:5px;">
              <img id='img' v-bind:src="archivesimgdata" style="height:200px;width:190px;float:right">
            </td>
            <td colspan="1"></td>
          </tr>
          <tr style="height:0px;">
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
          </tr>
          <tr>
            <td colspan="1"></td>
            <td colspan="6"><span class="tab_title">性别：</span>{{sex}}</td>
          </tr>
          <tr style="height:0px;">
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
          </tr>
          <tr>
            <td colspan="1"></td>
            <td colspan="6"><span class="tab_title">出生日期：</span>{{birthdate}}</td>
          </tr>
          <tr style="height:0px;">
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
          </tr>
          <tr style="height:0px;">
            <td colspan="10" style="border-bottom:  solid black 3px;"></td>
          </tr>
          <tr>
            <td colspan="1" class="tdback"></td>
            <td colspan="3"><span class="tab_title">父亲姓名：</span> {{fatherName}}</td>
            <td colspan="2">
              <span class="tab_title">年龄：</span>
              <span v-if="fatherAge">{{fatherAge}} 岁</span>
            </td>
            <td colspan="3"><span class="tab_title">职业：</span>{{fatherJob}}</td>
            <td colspan="1"></td>
          </tr>
          <tr>
            <td colspan="1" class="tdback"></td>
            <td colspan="3"><span class="tab_title">母亲姓名：</span> {{montherName}}</td>
            <td colspan="2">
              <span class="tab_title">年龄：</span>
              <span v-if="montherAge">{{montherAge}} 岁</span>
            </td>
            <td colspan="3"><span class="tab_title">职业：</span>{{montherJob}}</td>
            <td colspan="1"></td>
          </tr>
          <tr>
            <td colspan="1" class="tdback"></td>
            <td colspan="9"><span class="tab_title">手机号：</span>{{phone}}</td>
          </tr>
          <tr>
            <td colspan="1" class="tdback"></td>
            <td colspan="9"><span class="tab_title">家庭住址：</span>{{city}}{{area}}{{street}}{{address}}</td>
          </tr>
          <tr>
            <td colspan="1" class="tdback"></td>
            <td colspan="9"><span class="tab_title">建档医院：</span>{{hospitalname}}</td>
          </tr>
          <tr>
            <td colspan="1" class="tdback"></td>
            <td colspan="9"><span class="tab_title">咨询电话：</span>{{consultingPhone}}</td>
          </tr>
          <tr style="height:0px;">
            <td colspan="10" style="border-bottom:  solid black 3px;"></td>
          </tr>
        </table>
        <div style="width:590px;margin:30px auto; text-align:right">
          <button style="margin-right:20px;" class="btn Noprint" @click="printPage()">打印</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import headTop from '../../components/headTop'
  import {getArchivesDetail, hospital_detail} from '../../api/getData'

  export default {
    data() {
      return {
        archivesimgdata: '',
        cardtype: '',
        healthnum: '',
        xingming: '',
        name: '',
        sex: '',
        birthdate: '',
        birthday: '',
        phone: '',
        city: '',
        area: '',
        street: '',
        address: '',
        createtimeV: '',
        fatherName: '',
        fatherAge: '',
        fatherJob: '',
        montherName: '',
        montherAge: '',
        montherJob: '',
        father: {
          name: '',
          age: '',
          job: '',
          company: ''
        },
        monther: {
          name: '',
          age: '',
          nation: '',
          job: '',
          company: '',
        },
        hospitalname: '',
        consultingPhone: '',
        titleShow: true,
        archivePrintData: {}
      }

    },
    components: {
      headTop
    },
    mounted() {
      const self = this;
      const data = self.mystore.getStore('data');
      if (data && data.flag == 'search') {
        if (typeof (data.data) != 'undefined') {
          const guid = data.data.guid;
          const promise = getArchivesDetail(guid);
          promise.then(res => {
            self.archivesimgdata = res.archivesimgdata;
            self.cardtype = res.cardtype;
            self.healthnum = res.healthnum;
            self.name = res.name;
            self.sex = res.sex;
            self.birthdate = res.birthdate;
            self.phone = res.phone;
            self.area = res.area;
            self.street = res.street;
            self.address = res.address;
            self.createtimeV = res.createtimeV;
            self.fatherName = res.fatherName;
            self.montherName = res.montherName;
            self.montherAge = res.montherAge;
            self.fatherAge = res.fatherAge;
            self.fatherJob = res.fatherJob;
            self.montherJob = res.montherJob;

            if (self.monther == null) {
              self.monther = {};
            }
            if (self.father == null) {
              self.father = {};
            }
          });
          const hospitalInfo = hospital_detail('hosptid1234567890');
          hospitalInfo.then(res => {
            self.hospitalname = res.hospitalname;
            self.consultingPhone = res.linktype;
          });
        }
      } else {
        self.initData()
      }
      let printBtnShow = self.mystore.getStore('printBtnShow');
      self.titleShow = printBtnShow ? printBtnShow.titleShow : true;
    },
    methods: {
      initData() {
        var self = this;
        const archivePrintData = self.mystore.getStore('archivePrintData');
        this.archivePrintData = archivePrintData
        self.archivesimgdata = archivePrintData.archivesimgdata;
        self.cardtype = archivePrintData.cardtype;
        self.healthnum = archivePrintData.healthnum;
        self.name = archivePrintData.name;
        self.sex = archivePrintData.sex;
        self.birthday = archivePrintData.birthday;
        self.birthdate = archivePrintData.birthday;
        self.phone = archivePrintData.phone;
        self.area = archivePrintData.area;
        self.street = archivePrintData.street;
        self.address = archivePrintData.address;
        self.createtime = archivePrintData.createtime;
        self.father = archivePrintData.father;
        self.monther = archivePrintData.monther;
        self.fatherName = archivePrintData.fatherName;
        self.montherName = archivePrintData.montherName;
        self.montherAge = archivePrintData.montherAge;
        self.fatherAge = archivePrintData.fatherAge;
        self.fatherJob = archivePrintData.fatherJob;
        self.montherJob = archivePrintData.montherJob;
        const hospitalInfo = hospital_detail('hosptid1234567890');
        hospitalInfo.then(res => {
          self.hospitalname = res.hospitalname;
          self.consultingPhone = res.linktype;
        });
      },

      returnPrevisou() {
        //this.$router.push({path:'/archives_card/archives_info'});
        this.$router.go(-1)

        var operateType = {
          flag: 'print',
          data: this.archivePrintData
        }
        this.mystore.setStore('operateType', operateType)

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
  @media print {
    .Noprint {
      DISPLAY: none;
    }
  }

  .heart-head {
    background-color: #EFF2F7;
    height: 60px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-left: 20px;
  }

  .con-pgarc {
    border: 1px solid #e8dfdf;
    padding: 5px;
    padding-top: 0px;
    width: 590px;
    margin: auto;

  }

  table {
    width: 148mm;
  }

  table tr {
    font: 14px Helvetica Neue, Helvetica, Tahoma, Arial, sans-serif;
    height: 34px;
  }

  .tab_title {
    font-weight: bold;
  }

  .tdback {
    padding: 30px 0;
  }

  button {
    font: 14px Helvetica, Helvetica, Tahoma, Arial, sans-serif;
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
