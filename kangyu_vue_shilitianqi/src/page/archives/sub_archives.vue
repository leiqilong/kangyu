<template>
  <div class="">
    <div class="archives-main">
      <el-form :model="formData" :rules="rules" ref="formData">
        <div class="birthrecord_mod">
          <el-row>
            <el-col :span="8">
              <el-form-item label="胎龄：" label-width="130px" prop="renshenzhoushu">
                <el-input auto-complete="off" v-model="formData.renshenzhoushu" style="width:42%"></el-input>
                周
                <el-select v-model="formData.renshenzhoushuday" style="width:42%;" @change="checkrenshenzhoushuday">
                  <el-option v-for="item in csrsdayOption" :key="item.value" :label="item.label"
                             :value="item.label"></el-option>
                </el-select>
                天
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="出生地点：" label-width="120px" prop="yqweight">
                <el-input auto-complete="off" v-model="formData.chushengdidian"></el-input>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="分娩方式：" label-width="120px" prop="yqweight">
                <el-select v-model="fenmianempty" multiple collapse-tags style="width:100%;" @change="changefmfs">
                  <el-option v-for="item in fmfsOption" :key="item.value" :label="item.label"
                             :value="item.label"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="8">
              <el-form-item label="系第几胎：" label-width="130px" prop="height">
                <el-select v-model="formData.jidjitai" style="width:100%;">
                  <el-option v-for="item in jdjtcOption" :key="item.value" :label="item.label"
                             :value="item.label"></el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="第几产：" label-width="120px" prop="yqweight">
                <el-select v-model="formData.dijichan" style="width:100%;">
                  <el-option v-for="item in jdjtcOption" :key="item.value" :label="item.label"
                             :value="item.label"></el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="胎数：" label-width="120px" prop="yqweight">
                <el-select v-model="formData.taishu" style="width:100%;" @change="checktaishu">
                  <el-option v-for="item in jdjtcOption" :key="item.value" :label="item.label"
                             :value="item.label"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="8">
              <el-form-item label="孕期患病：" label-width="130px" prop="yqhuanbingempty">
                <el-select v-model="yqhuanbingempty" filterable allow-create multiple collapse-tags style="width:100%;"
                           @change="checkyqhb">
                  <el-option v-for="item in yqhbOption" :key="item.value" :label="item.label"
                             :value="item.label"></el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="孕期服药：" label-width="120px" prop="yqfuyao">
                <el-input auto-complete="off" v-model="formData.yqfuyao"></el-input>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="受孕方式：" label-width="120px" prop="shouyunfangshi">
                <el-select v-model="formData.shouyunfangshi" style="width:100%;" @change="checksyfs">
                  <el-option v-for="item in syfsOption" :key="item.value" :label="item.label"
                             :value="item.label"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </div>

        <div class="birthrecord_mod">
          <el-row>
            <el-col :span="8">

              <el-form-item label="Apgar评分：" label-width="130px" prop="height">
                1/
                <el-input auto-complete="off" style="width:20%" v-model="formData.yifenzhong"></el-input>
                5/
                <el-input auto-complete="off" style="width:20%" v-model="formData.wufenzhong"></el-input>
                10/
                <el-input auto-complete="off" style="width:20%" v-model="formData.shifenzhong"></el-input>
              </el-form-item>

            </el-col>
            <el-col :span="8">

              <el-form-item label="窒息情况：" label-width="170px" prop="height">
                <el-select v-model="formData.zhixiqingkuang" style="width:90%;" @change="checkzhixiqingkuang">
                  <el-option v-for="item in zxqkOption" :key="item.value" :label="item.label"
                             :value="item.label"></el-option>
                </el-select>
              </el-form-item>

            </el-col>
            <el-col :span="8">

              <el-form-item label="复苏措施：" label-width="120px" prop="yqweight">
                <el-input auto-complete="off" v-model="formData.fusucuoshi"></el-input>
              </el-form-item>

            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">

              <el-form-item label="抽搐：" label-width="130px" prop="yqweight">
                <el-select v-model="formData.chouchu" style="width:100%;">
                  <el-option v-for="item in youwuOption" :key="item.value" :label="item.label"
                             :value="item.label"></el-option>
                </el-select>
              </el-form-item>

            </el-col>
            <el-col :span="8">

              <el-form-item label="黄疸消退时间（天）：" label-width="170px" prop="height">
                <el-input auto-complete="off" v-model="formData.huangdanxtsj" style="width:90%"></el-input>
              </el-form-item>

            </el-col>
            <el-col :span="8">
              <el-form-item label="出生缺陷：" label-width="120px" prop="chushengquexian">
                <el-select v-model="csqxempty" multiple collapse-tags style="width:100%;"
                           @change="checkchushengquexian">
                  <el-option v-for="item in csqxOption" :key="item.value" :label="item.label"
                             :value="item.label"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="8">
              <el-form-item label="羊水：" label-width="130px" prop="yangshui">
                <el-select v-model="yangshuiempty" filterable allow-create multiple collapse-tags style="width:100%;"
                           @change="checkyangshui">
                  <el-option v-for="item in yangshuiOption" :key="item.value" :label="item.label"
                             :value="item.label"></el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="脐带胎盘：" label-width="170px" prop="qidaitaipan">
                <el-select v-model="qidaitaipanempty" filterable allow-create multiple collapse-tags style="width:90%;"
                           @change="checkqidaitaipan">
                  <el-option v-for="item in qidaitaipanOption" :key="item.value" :label="item.label"
                             :value="item.label"></el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="其它：" label-width="120px" prop="qita">
                <el-input auto-complete="off" v-model="formData.qita"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </div>

        <div class="birthrecord_mod">
          <el-row>
            <el-col :span="8">
              <el-form-item label="身长（cm）：" label-width="130px" prop="shenchang">
                <el-input auto-complete="off" v-model="formData.shenchang"></el-input>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="体重（kg）：" label-width="130px" prop="tizhong">
                <el-input auto-complete="off" v-model="formData.tizhong"></el-input>
              </el-form-item>
            </el-col>

            <el-col :span="8"></el-col>
          </el-row>

          <el-row>
            <el-col :span="8">
              <el-form-item label="听力筛查：" label-width="130px" prop="height">
                <el-select v-model="formData.tinglishaichai" style="width:100%;">
                  <el-option v-for="item in tlscOption" :key="item.value" :label="item.label"
                             :value="item.label"></el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="PKU：" label-width="130px" prop="height">
                <el-select v-model="formData.pku" style="width:100%;" @change="checkpku">
                  <el-option v-for="item in checkpkuOption" :key="item.value" :label="item.label"
                             :value="item.label"></el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="CH：" label-width="130px" prop="height">
                <el-select v-model="formData.ch" style="width:100%;" @change="checkch">
                  <el-option v-for="item in checkpkuOption" :key="item.value" :label="item.label"
                             :value="item.label"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="8">
              <el-form-item label="新生儿期疾病：" label-width="130px" prop="xseqjb">
                <el-select v-model="xseqjbempty" filterable allow-create multiple collapse-tags style="width:100%;"
                           @change="checkxseqjb">
                  <el-option v-for="item in xseqjbOption" :key="item.value" :label="item.label"
                             :value="item.label"></el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="家族遗传病史：" label-width="130px" prop="jzycbs">
                <el-select v-model="jzycbsempty" multiple collapse-tags style="width:100%;" @change="checkjzycbs">
                  <el-option v-for="item in jzycbsOption" :key="item.value" :label="item.label"
                             :value="item.label"></el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="8"></el-col>
          </el-row>
        </div>

        <div class="birthrecord_mod">
          <el-row>
            <el-col :span="24">
              <div class="grid-content birthrecord_title" style="width:100%;">高危信息：</div>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="8">
              <el-form-item label="胎儿期：" label-width="120px" prop="taierqi">
                <el-select v-model="taierqiempty" multiple collapse-tags style="width:100%;" @change="checktaierqi">
                  <el-option v-for="item in taierqiOption" :key="item.value" :label="item.label"
                             :value="item.label"></el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="分娩期：" label-width="120px" prop="fenmianqi">
                <el-select v-model="fenmianqiempty" multiple collapse-tags style="width:100%;" @change="checkfenmianqi">
                  <el-option v-for="item in fenmianqiOption" :key="item.value" :label="item.label"
                             :value="item.label"></el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="8"></el-col>
          </el-row>

          <el-row>
            <el-col :span="8">
              <el-form-item label="新生儿期：" label-width="120px" prop="xinshengerqi">
                <el-select v-model="xinshengerqiempty" multiple collapse-tags style="width:100%;"
                           @change="checkxinshengerqi">
                  <el-option v-for="item in xinshengerqiOption" :key="item.value" :label="item.label"
                             :value="item.label"></el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="婴儿期：" label-width="120px" prop="yingerqi">
                <el-select v-model="yingerqiempty" multiple collapse-tags style="width:100%;" @change="checkyingerqi">
                  <el-option v-for="item in yingerqiOption" :key="item.value" :label="item.label"
                             :value="item.label"></el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="8"></el-col>
          </el-row>
        </div>
      </el-form>

      <el-row>
        <el-col :span="24">
          <div class="grid-content" style="margin-top:10px;">
            <el-button type="primary" @click="submitForm('formData')">保存</el-button>
            <el-button type="primary" @click="print()">打 印</el-button>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
  import {arrayPop, arrayPush} from '@/utils/utils.js'
  import {validateFloat} from '@/utils/validate.js'
  import {getbirthInfobyguId, hospital_detail, select, updatebirthInfo} from '../../api/getData'

  export default {
    data() {
      return {
        formData: {
          guid: '',
          renshenzhoushu: '',
          renshenzhoushuday: '0',
          chushengdidian: '',
          fenmiantype: '',
          jidjitai: '1',
          dijichan: '1',
          taishu: '1',
          yqhuanbing: '',
          yqfuyao: '',
          shouyunfangshi: '自然受孕',
          yifenzhong: '',
          wufenzhong: '',
          shifenzhong: '',
          zhixiqingkuang: '无',
          fusucuoshi: '',
          chouchu: '无',
          huangdanxtsj: '',
          chushengquexian: '无',
          yangshui: '',
          qidaitaipan: '',
          qita: '',
          shenchang: '',
          tizhong: '',
          tinglishaichai: '通过',
          pku: '通过',
          ch: '通过',
          xseqjb: '',
          jzycbs: '',
          taierqi: '',
          fenmianqi: '',
          xinshengerqi: '',
          yingerqi: '',
        },
        fenmianempty: ['自然顺产'],
        yqhuanbingempty: ['无'],
        csqxempty: ['无'],
        yangshuiempty: ['正常'],
        qidaitaipanempty: ['正常'],
        xseqjbempty: ['正常'],
        jzycbsempty: ['无'],
        taierqiempty: ['无'],
        fenmianqiempty: ['无'],
        xinshengerqiempty: ['无'],
        yingerqiempty: ['无'],
        csrsdayOption: [],
        fmfsOption: [],
        jdjtcOption: [],
        yqhbOption: [],
        syfsOption: [],
        zxqkOption: [],
        youwuOption: [],
        tlscOption: [],
        checkpkuOption: [],
        csqxOption: [],
        yangshuiOption: [],
        qidaitaipanOption: [],
        xseqjbOption: [],
        jzycbsOption: [],
        xsrscbjOption: [],
        xsrscjgOption: [],
        xsrscistgOption: [],
        taierqiOption: [],
        fenmianqiOption: [],
        xinshengerqiOption: [],
        yingerqiOption: [],
        rules: {
          renshenzhoushu: [
            {required: true, message: '请输入胎龄', trigger: 'blur'},
            {maxValue: 50, validator: validateFloat}
          ],
          shenchang: [
            {required: true, message: '请输入身长', trigger: 'blur'},
            {maxValue: 80, validator: validateFloat, trigger: 'blur'}
          ],
          tizhong: [
            {required: true, message: '请输入体重', trigger: 'blur'},
            {maxValue: 10, validator: validateFloat, trigger: 'blur'}
          ]
        },
        showPrint: true
      };
    },

    mounted() {
      const self = this;
      const data = this.mystore.getStore('operateType');
      const subFlag = this.mystore.getStore('subflag');
      this.initSelectData();
      if (typeof (data) != 'undefined' && data.flag == 'edit') {
        self.initData(data);
      } else if (typeof (data) != 'undefined' && data.flag == 'print') {
        self.initData(data);
        self.showPrint = false
      } else if (typeof (subFlag) != 'undefined' && subFlag.flag == 'true') {
        self.initData(subFlag);
      } else {
        self.showPrint = false
        self.mystore.setStore('subflag', {flag: 'false'});
        const birthdata = this.mystore.getStore('newBirthUser');
        if (birthdata && birthdata.guid) {
          self.formData.guid = birthdata.guid
        } else {
          self.$notify.error({
            title: '提示',
            message: '请先保存基础档案信息',
            offset: 100
          });
        }

        const hospitalInfo = hospital_detail('hosptid1234567890');
        hospitalInfo.then(function (res) {
          self.formData.chushengdidian = res.hospitalname;
        });
      }
    },

    methods: {
      async initData(data) {

        const self = this;
        try {
          if (data.flag == 'edit') {
            self.formData.guid = data.data.id
          } else {
            self.formData.guid = data.data.guid
          }
          const promise = getbirthInfobyguId({guid: self.formData.guid});
          promise.then(function (res) {
            if (res.guid != '' && typeof (res.guid) != 'undefined') {
              self.formData.guid = res.guid;
              self.formData.renshenzhoushu = res.renshenzhoushu;
              self.formData.renshenzhoushuday = res.renshenzhoushuday;
              self.formData.chushengdidian = res.chushengdidian;
              self.fenmianempty = res.fenmiantype.split(',');
              self.yqhuanbingempty = res.yqhuanbing.split(',');
              self.csqxempty = res.chushengquexian.split(',');
              self.yangshuiempty = res.yangshui.split(',');
              self.qidaitaipanempty = res.qidaitaipan.split(',');
              self.xseqjbempty = res.xseqjb.split(',');
              self.jzycbsempty = res.jzycbs.split(',');
              self.taierqiempty = res.taierqi.split(",");
              self.fenmianqiempty = res.fenmianqi.split(",");
              self.xinshengerqiempty = res.xinshengerqi.split(",");
              self.yingerqiempty = res.yingerqi.split(",");
              self.formData.jidjitai = res.jidjitai;
              self.formData.dijichan = res.dijichan;
              self.formData.taishu = res.taishu;
              self.formData.shenchang = res.shenchang;
              self.formData.tizhong = res.tizhong;
              self.formData.yifenzhong = res.yifenzhong;
              self.formData.wufenzhong = res.wufenzhong;
              self.formData.shifenzhong = res.shifenzhong;
              self.formData.zhixiqingkuang = res.zhixiqingkuang;
              self.formData.fusucuoshi = res.fusucuoshi;
              self.formData.chouchu = res.chouchu;
              self.formData.huangdanxtsj = res.huangdanxtsj;
              self.formData.yqfuyao = res.yqfuyao;
              self.formData.shouyunfangshi = res.shouyunfangshi;
              self.formData.qita = res.qita;
              self.formData.pku = res.pku;
              self.formData.ch = res.ch;
              self.formData.tinglishaichai = res.tinglishaichai;
            } else {
              self.formData.userguid = data.data.guid;
              const hospitalInfo = hospital_detail('hosptid1234567890');
              hospitalInfo.then(function (res) {
                self.formData.chushengdidian = res.hospitalname;
              });
            }
          });

        } catch (err) {
          console.log('获取数据失败', err);
        }
      },
      async initSelectData() {
        try {
          const self = this;
          const data = select;
          self.csrsdayOption = this.selectGet(data.csrsday);
          self.fmfsOption = this.selectGet(data.fmfs);
          self.jdjtcOption = this.selectGet(data.jdjtc);
          self.yqhbOption = this.selectGet(data.yqhb);
          self.syfsOption = this.selectGet(data.syfs);
          self.youwuOption = this.selectGet(data.youwu);
          self.zxqkOption = this.selectGet(data.zxqk);
          self.csqxOption = this.selectGet(data.csqx);
          self.yangshuiOption = this.selectGet(data.yangshui);
          self.qidaitaipanOption = this.selectGet(data.qidaitaipan);
          self.xseqjbOption = this.selectGet(data.xseqjb);
          self.jzycbsOption = this.selectGet(data.jzycbs);
          self.tlscOption = this.selectGet(data.tlsc);
          self.checkpkuOption = this.selectGet(data.checkpku);
          self.taierqiOption = this.selectGet(data.taierqi);
          self.fenmianqiOption = this.selectGet(data.fenmianqi);
          self.xinshengerqiOption = this.selectGet(data.xinshengerqi);
          self.yingerqiOption = this.selectGet(data.yingerqi);

        } catch (err) {
          console.log('获取数据失败', err);
        }
      },
      selectGet(data) {
        var selectData = [];
        data.find((item) => {
          var newItem = {};
          newItem.label = item.text;
          newItem.value = item.value;
          selectData.push(newItem);
        });
        return selectData;
      },
      changefmfs(selVal) {
        let array = this.fenmianempty;
        if (array.indexOf("自然顺产") > array.indexOf("刨宫产")) {
          arrayPop(array, "刨宫产");
        } else {
          arrayPop(array, "自然顺产");
        }
      },
      checkyqhb(selVal) {
        if (selVal.length == 0) {
          this.yqhuanbingempty.push("无");
        } else if (selVal.length > 1) {
          if (this.yqhuanbingempty.indexOf("无") != -1)
            this.yqhuanbingempty.splice(this.yqhuanbingempty.indexOf("无"), 1);
        }
        if (selVal.indexOf("孕期感染") != -1) {
          if (this.taierqiempty.indexOf("无") != -1)
            this.taierqiempty.splice(this.taierqiempty.indexOf("无"), 1);
          if (this.taierqiempty.indexOf("母亲孕期感染") === -1)
            this.taierqiempty.push("母亲孕期感染");
        } else {
          if (this.taierqiempty.indexOf("母亲孕期感染") != -1)
            this.taierqiempty.splice(this.taierqiempty.indexOf("母亲孕期感染"), 1);
        }
        if (selVal.indexOf("妊高征") != -1) {

          if (this.taierqiempty.indexOf("无") != -1)
            this.taierqiempty.splice(this.taierqiempty.indexOf("无"), 1);
          if (this.taierqiempty.indexOf("妊娠高血压病") === -1)
            this.taierqiempty.push("妊娠高血压病");
        } else {
          if (this.taierqiempty.indexOf("妊娠高血压病") != -1)
            this.taierqiempty.splice(this.taierqiempty.indexOf("妊娠高血压病"), 1);
        }
        if (selVal.indexOf("糖尿病") != -1) {
          if (this.taierqiempty.indexOf("无") != -1)
            this.taierqiempty.splice(this.taierqiempty.indexOf("无"), 1);
          if (this.taierqiempty.indexOf("妊娠合并糖尿病") === -1)
            this.taierqiempty.push("妊娠合并糖尿病");
        } else {
          if (this.taierqiempty.indexOf("妊娠合并糖尿病") != -1)
            this.taierqiempty.splice(this.taierqiempty.indexOf("妊娠合并糖尿病"), 1);
        }
        if (selVal.indexOf("贫血") != -1) {

          if (this.taierqiempty.indexOf("无") != -1)
            this.taierqiempty.splice(this.taierqiempty.indexOf("无"), 1);
          if (this.taierqiempty.indexOf("妊娠期贫血") === -1)
            this.taierqiempty.push("妊娠期贫血");
        } else {
          if (this.taierqiempty.indexOf("妊娠期贫血") != -1)
            this.taierqiempty.splice(this.taierqiempty.indexOf("妊娠期贫血"), 1);
        }

        if (!this.taierqiempty.length) {
          this.taierqiempty.push('无');
        }

      },
      checktaishu(selVal) {
        if (selVal != "1") {
          if (this.taierqiempty.indexOf("无") != -1)
            this.taierqiempty.splice(this.taierqiempty.indexOf("无"), 1);
          if (this.taierqiempty.indexOf("多胎、双胎") === -1)
            this.taierqiempty.push("多胎、双胎");
        } else {
          if (this.taierqiempty.indexOf("多胎、双胎") != -1)
            this.taierqiempty.splice(this.taierqiempty.indexOf("多胎、双胎"), 1);
        }
      },
      checkzhixiqingkuang(val) {
        if (val != "无") {
          if (this.fenmianqiempty.indexOf("无") != -1)
            this.fenmianqiempty.splice(this.fenmianqiempty.indexOf("无"), 1);
          if (this.fenmianqiempty.indexOf("新生儿窒息") === -1)
            this.fenmianqiempty.push("新生儿窒息");
        } else {
          if (this.fenmianqiempty.indexOf("新生儿窒息") != -1)
            this.fenmianqiempty.splice(this.fenmianqiempty.indexOf("新生儿窒息"), 1);
        }
      },
      checkchushengquexian(val) {
        if (val.length == 0) {
          this.csqxempty.push("无");
        } else if (val.length > 1) {
          if (this.csqxempty.indexOf("无") != -1)
            this.csqxempty.splice(this.csqxempty.indexOf("无"), 1);
        }
        if (val != "无") {
          if (this.fenmianqiempty.indexOf("无") != -1)
            this.fenmianqiempty.splice(this.fenmianqiempty.indexOf("无"), 1);
          if (this.fenmianqiempty.indexOf("出生缺陷") === -1)
            this.fenmianqiempty.push("出生缺陷");
        } else {
          if (this.fenmianqiempty.indexOf("出生缺陷") != -1)
            this.fenmianqiempty.splice(this.fenmianqiempty.indexOf("出生缺陷"), 1);

          if (!this.fenmianqiempty.length) {
            this.fenmianqiempty.push("无");
          }
        }
      },
      checkyangshui(selVal) {
        //console.log(selVal)
        let array = this.yangshuiempty;
        if (selVal.length == 0) {
          arrayPush(array, "正常");
        } else if (selVal.length > 1) {
          arrayPop(array, "正常");
          if (array.indexOf("过多") > array.indexOf("过少")) {
            arrayPop(array, "过少");
          } else {
            arrayPop(array, "过多");
          }
        }
        if (selVal.indexOf("正常") === -1) {
          if (this.taierqiempty.indexOf("无") != -1)
            this.taierqiempty.splice(this.taierqiempty.indexOf("无"), 1);
          if (this.taierqiempty.indexOf("羊水异常") === -1)
            this.taierqiempty.push("羊水异常");
        } else {
          if (this.taierqiempty.indexOf("羊水异常") != -1)
            this.taierqiempty.splice(this.taierqiempty.indexOf("羊水异常"), 1);
        }
      },
      checkqidaitaipan(selVal) {
        if (selVal.length == 0) {
          this.qidaitaipanempty.push("正常");
        } else if (selVal.length > 1) {
          if (this.qidaitaipanempty.indexOf("正常") != -1)
            this.qidaitaipanempty.splice(this.qidaitaipanempty.indexOf("正常"), 1);
        }
        if (selVal.indexOf("脐带绕颈") === -1 && selVal.indexOf("脐带打结") === -1 && selVal.indexOf("脐带细小") === -1) {
          if (this.taierqiempty.indexOf("脐带问题") != -1)
            this.taierqiempty.splice(this.taierqiempty.indexOf("脐带问题"), 1);
        } else {
          if (this.taierqiempty.indexOf("无") != -1)
            this.taierqiempty.splice(this.taierqiempty.indexOf("无"), 1);
          if (this.taierqiempty.indexOf("脐带问题") === -1)
            this.taierqiempty.push("脐带问题");
        }
        if (selVal.indexOf("胎盘钙化") === -1 && selVal.indexOf("胎盘老化") === -1 && selVal.indexOf("胎盘梗塞") === -1 && selVal.indexOf("胎盘早剥") === -1 && selVal.indexOf("胎盘前置") === -1) {
          if (this.taierqiempty.indexOf("胎盘问题") != -1)
            this.taierqiempty.splice(this.taierqiempty.indexOf("胎盘问题"), 1);
        } else {
          if (this.taierqiempty.indexOf("无") != -1)
            this.taierqiempty.splice(this.taierqiempty.indexOf("无"), 1);
          if (this.taierqiempty.indexOf("胎盘问题") === -1)
            this.taierqiempty.push("胎盘问题");
        }
      },
      checkxseqjb(xseqjb) {
        if (xseqjb.length == 0) {
          this.xseqjbempty.push("正常");
        } else if (xseqjb.length > 1) {
          if (this.xseqjbempty.indexOf("正常") != -1)
            this.xseqjbempty.splice(this.xseqjbempty.indexOf("正常"), 1);
        }
      },
      checkjzycbs(selVal) {
        let array = this.jzycbsempty;
        if (selVal.length == 0) {
          arrayPush(array, "无");
        } else if (selVal.length > 1) {
          arrayPop(array, "无");
        }
      },
      checktaierqi(selVal) {
        if (selVal.length == 0) {
          this.taierqiempty.push("无");
        } else if (selVal.length > 1) {
          if (this.taierqiempty.indexOf("无") != -1)
            this.taierqiempty.splice(this.taierqiempty.indexOf("无"), 1);
        }
      },
      checkfenmianqi(selVal) {
        let array = this.fenmianqiempty;
        console.log('array', this.fenmianqiempty);
        if (selVal.length == 0) {
          arrayPush(array, "无");
        } else if (selVal.length > 1) {
          arrayPop(array, "无");
          if (array.indexOf("早产儿") > array.indexOf("过期产儿")) {
            arrayPop(array, "过期产儿");
          } else {
            arrayPop(array, "早产儿");
          }
        }

      },
      checkxinshengerqi(selVal) {
        if (selVal.length == 0) {
          this.xinshengerqiempty.push("无");
        } else if (selVal.length > 1) {
          if (this.xinshengerqiempty.indexOf("无") != -1)
            this.xinshengerqiempty.splice(this.xinshengerqiempty.indexOf("无"), 1);
        }
      },
      checkyingerqi(selVal) {
        if (selVal.length == 0) {
          this.yingerqiempty.push("无");
        } else if (selVal.length > 1) {
          if (this.yingerqiempty.indexOf("无") != -1)
            this.yingerqiempty.splice(this.yingerqiempty.indexOf("无"), 1);
        }
      },
      checkrenshenzhoushu(rczs) {
        const tizhong = this.formData.tizhong;
        let array = this.fenmianqiempty;
        if (rczs != null && rczs != "") {
          if (rczs > 42) {
            arrayPop(array, "无");
            arrayPop(array, "早产儿");
            arrayPush(array, "过期产儿");
          } else if (rczs === "42") {
            if (this.formData.renshenzhoushuday > 0) {
              arrayPop(array, "无");
              arrayPop(array, "早产儿");
              arrayPush(array, "过期产儿");
            } else {
              arrayPop(array, "早产儿");
              arrayPop(array, "过期产儿");
              if (array.length === 0) {
                arrayPush(array, '无');
              }
            }
          } else if (rczs >= 37) {
            if (tizhong != null && tizhong != "" && tizhong < 2.5) {
              if (this.taierqiempty.indexOf("无") != -1)
                this.taierqiempty.splice(this.taierqiempty.indexOf("无"), 1);
              if (this.taierqiempty.indexOf("胎儿生长受限（小于胎龄儿）") === -1)
                this.taierqiempty.push("胎儿生长受限（小于胎龄儿）");
            } else {
              if (this.taierqiempty.indexOf("胎儿生长受限（小于胎龄儿）") != -1)
                this.taierqiempty.splice(this.taierqiempty.indexOf("胎儿生长受限（小于胎龄儿）"), 1);
            }
            arrayPop(array, "早产儿");
            arrayPop(array, "过期产儿");
            if (array.length === 0) {
              arrayPush(array, '无');
            }
          } else {
            arrayPop(array, "无");
            arrayPop(array, "过期产儿");
            arrayPush(array, "早产儿");
            if (this.taierqiempty.indexOf("胎儿生长受限（小于胎龄儿）") != -1)
              this.taierqiempty.splice(this.taierqiempty.indexOf("胎儿生长受限（小于胎龄儿）"), 1);
          }
        }
      },

      checkrenshenzhoushuday(val) {
        if (this.formData.renshenzhoushu === "42") {
          if (val > 0) {
            if (this.fenmianqiempty.indexOf("无") != -1)
              this.fenmianqiempty.splice(this.fenmianqiempty.indexOf("无"), 1);
            if (this.fenmianqiempty.indexOf("早产儿") != -1)
              this.fenmianqiempty.splice(this.fenmianqiempty.indexOf("早产儿"), 1);
            if (this.fenmianqiempty.indexOf("过期产儿") === -1)
              this.fenmianqiempty.push("过期产儿");
          } else {
            if (this.fenmianqiempty.indexOf("早产儿") != -1)
              this.fenmianqiempty.splice(this.fenmianqiempty.indexOf("早产儿"), 1);
            if (this.fenmianqiempty.indexOf("过期产儿") != -1)
              this.fenmianqiempty.splice(this.fenmianqiempty.indexOf("过期产儿"), 1);
          }

        }
      },
      checktizhong(tizhong) {
        const rczs = this.formData.renshenzhoushu;
        console.log('tizhong', tizhong);
        if (tizhong != null && tizhong != '' && tizhong < 2.5) {
          if (rczs != null && rczs != "" && rczs >= 37) {
            if (this.taierqiempty.indexOf("无") != -1)
              this.taierqiempty.splice(this.taierqiempty.indexOf("无"), 1);
            if (this.taierqiempty.indexOf("胎儿生长受限（小于胎龄儿）") === -1)
              this.taierqiempty.push("胎儿生长受限（小于胎龄儿）");
          } else {
            if (this.taierqiempty.indexOf("胎儿生长受限（小于胎龄儿）") != -1)
              this.taierqiempty.splice(this.taierqiempty.indexOf("胎儿生长受限（小于胎龄儿）"), 1);
          }
          if (this.fenmianqiempty.indexOf("无") != -1)
            this.fenmianqiempty.splice(this.fenmianqiempty.indexOf("无"), 1);
          if (this.fenmianqiempty.indexOf("低出生体重儿") === -1)
            this.fenmianqiempty.push("低出生体重儿");
        } else if (tizhong != null && tizhong != '' && tizhong >= 4) {
          if (this.taierqiempty.indexOf("无") != -1)
            this.taierqiempty.splice(this.taierqiempty.indexOf("无"), 1);
          if (this.taierqiempty.indexOf("巨大儿") === -1)
            this.taierqiempty.push("巨大儿");
          if (this.fenmianqiempty.indexOf("低出生体重儿") != -1)
            this.fenmianqiempty.splice(this.fenmianqiempty.indexOf("低出生体重儿"), 1);
        } else {
          if (this.taierqiempty.indexOf("胎儿生长受限（小于胎龄儿）") != -1)
            this.taierqiempty.splice(this.taierqiempty.indexOf("胎儿生长受限（小于胎龄儿）"), 1);
          if (this.taierqiempty.indexOf("巨大儿") != -1)
            this.taierqiempty.splice(this.taierqiempty.indexOf("巨大儿"), 1);
          if (this.fenmianqiempty.indexOf("低出生体重儿") != -1)
            this.fenmianqiempty.splice(this.fenmianqiempty.indexOf("低出生体重儿"), 1);

          if (!this.fenmianqiempty.length) {
            this.fenmianqiempty.push('无');
          }
          if (!this.taierqiempty.length) {
            this.taierqiempty.push('无');
          }
        }

      },
      checksyfs(val) {
        if (val === "人工授精" || val === "试管婴儿") {
          if (this.taierqiempty.indexOf("无") != -1)
            this.taierqiempty.splice(this.taierqiempty.indexOf("无"), 1);
          if (this.taierqiempty.indexOf("人工辅助生殖") === -1)
            this.taierqiempty.push("人工辅助生殖");
        } else {
          if (this.taierqiempty.indexOf("人工辅助生殖") != -1)
            this.taierqiempty.splice(this.taierqiempty.indexOf("人工辅助生殖"), 1);
        }
      },
      checkpku(val) {
        if (val === '阳性') {
          if (this.fenmianqiempty.indexOf('新生儿筛查异常儿') === -1) {
            this.fenmianqiempty.push('新生儿筛查异常儿');
          }
          if (this.fenmianqiempty.indexOf('无') !== -1) {
            this.fenmianqiempty.splice(this.fenmianqiempty.indexOf('无'), 1);
          }
        } else {
          if (this.formData.ch !== '阳性') {
            this.fenmianqiempty.splice(this.fenmianqiempty.indexOf('新生儿筛查异常儿'), 1);
            if (this.fenmianqiempty.length === 0) {
              this.fenmianqiempty.push('无');
            }
          }
        }
      },
      checkch(val) {
        if (val === '阳性') {
          if (this.fenmianqiempty.indexOf('新生儿筛查异常儿') === -1) {
            this.fenmianqiempty.push('新生儿筛查异常儿');
          }
          if (this.fenmianqiempty.indexOf('无') !== -1) {
            this.fenmianqiempty.splice(this.fenmianqiempty.indexOf('无'), 1);
          }
        } else {
          if (this.formData.pku !== '阳性') {
            this.fenmianqiempty.splice(this.fenmianqiempty.indexOf('新生儿筛查异常儿'), 1);
          }
          if (this.fenmianqiempty.length === 0) {
            this.fenmianqiempty.push('无');
          }
        }
      },

      closeForm() {
        this.$router.push({path: '/archives_list'});
      },
      submitForm(formName) {
        const self = this;
        let guid = self.formData.guid;
        if (guid != '') {
          self.formData.guid = guid;
          self.formData.chushengquexian = self.csqxempty.join(',');
          self.formData.fenmiantype = self.fenmianempty.join(',');
          self.formData.yqhuanbing = self.yqhuanbingempty.join(',');
          self.formData.yangshui = self.yangshuiempty.join(',');
          self.formData.qidaitaipan = self.qidaitaipanempty.join(',');
          self.formData.xseqjb = self.xseqjbempty.join(',');
          self.formData.jzycbs = self.jzycbsempty.join(',');
          self.formData.taierqi = self.taierqiempty.join(',');
          self.formData.fenmianqi = self.fenmianqiempty.join(',');
          self.formData.xinshengerqi = self.xinshengerqiempty.join(',');
          self.formData.yingerqi = self.yingerqiempty.join(',');
          self.$refs[formName].validate(async (valid) => {
            if (valid) {
              let result = await updatebirthInfo(self.formData);
              if (result.flag == 'success') {
                self.$message({
                  type: 'success',
                  message: '提交成功'
                });
                // this.$router.push({ path: '/archives_list'});
              } else {
                throw new Error('出现异常')
              }
              self.showPrint = true
            } else {
              self.showPrint = false
              self.$notify.error({
                title: '错误',
                message: '请检查输入是否正确',
                offset: 100
              });
              return false;
            }
          });
        } else {
          alert("请先保存基础档案信息！");
        }

      },
      print() {
        const self = this;
        self.formData.fenmiantype = self.fenmianempty.join(',');
        self.formData.yqhuanbing = self.yqhuanbingempty.join(',');
        self.formData.chushengquexian = self.csqxempty.join(',');
        self.formData.yangshui = self.yangshuiempty.join(',');
        self.formData.qidaitaipan = self.qidaitaipanempty.join(',');
        self.formData.xseqjb = self.xseqjbempty.join(',');
        self.formData.jzycbs = self.jzycbsempty.join(',');
        self.formData.taierqi = self.taierqiempty.join(',');
        self.formData.fenmianqi = self.fenmianqiempty.join(',');
        self.formData.xinshengerqi = self.xinshengerqiempty.join(',');
        self.formData.yingerqi = self.yingerqiempty.join(',');
        self.mystore.setStore('birthConditionPrintData', self.formData);
        self.mystore.setStore('subflag', {flag: 'true', data: self.formData});
        self.mystore.setStore('printBtnShow', {titleShow: true});
        self.mystore.setStore('data', {flag: ''});
        self.$router.push({path: '/archives_birthcondition_print'})
      }
    },
    watch: {
      'formData.renshenzhoushu'(val) {
        this.checkrenshenzhoushu(val);
      },
      'formData.tizhong'(val) {
        this.checktizhong(val);
      }
    }
  };
</script>
<style>

  .birthrecord_title {
    margin-top: 5px;
    font-size: 15px;
    text-align: left;
  }

  .form-img {
    border: 1px solid #e0d9d9;
    width: 183px;
    height: 230px;
    float: left;
  }

  .imgbody {
    width: 182px;
    height: 90%;
  }

  .imgfoot {
    height: 23px;
    text-align: center;
    background-color: rgba(179, 179, 179, 0.22);
  }

  .birthrecord_mod {
    border: 1px solid #e8dfdf;
    margin-top: 5px;
    width: 99%;
    padding-top: 15px;
    padding-right: 10px;
  }
</style>
