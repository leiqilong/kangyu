<template>
  <div>
    <div>
      <div style="float:right; margin-top:24px;margin-right:8px" id="erweima" ref="qrCodeUrl" v-show="erweima"></div>
    </div>
    <el-form :model="formData" style="overflow:hidden;padding:15px;" ref="formData" label-width="120px">
      <div class="mbasemid">
        <el-row>
          <el-col :span="12">
            <el-form-item label="卡号："
                          :rules="[
                            {required: true,  message: '卡号不能为空', trigger: 'blur'},
                            {pattern: /^(\w){5,20}$/,message: '只能输入5-20个字母、数字、下划线', trigger: 'blur'}
                            ]"
                          prop="cardId">
              <el-input v-model="formData.cardId" :readonly="cardIdReadonly"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="身份证号："
                          :rules="[{pattern:/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/, message: '请输入正确身份证号' }]"
                          prop="idnumber">
              <el-input v-model="formData.idnumber"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名：" :rules="[{required: true, validator: validatePass}]" prop="name">
              <el-input v-model="formData.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别：" :rules="{required: true, message: '性别不能为空', trigger: 'change'}" prop="sex">
              <el-select v-model="formData.sex" placeholder="请选择" style="width:100%">
                <el-option label="男" value="男"></el-option>
                <el-option label="女" value="女"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <div class="grid-content">
              <el-form-item label="民族：" prop="nation">
                <el-select v-model="formData.nation" filterable allow-create placeholder="请选择" style="width:100%">
                  <el-option v-for="item in nationOptions" :key="item.value" :label="item.label" :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </div>
          </el-col>
          <el-col :span="12">
            <el-form-item label="出生日期：" :rules="{required: true, message: '出生日期不能为空', trigger: 'blur'}" prop="birthday">
              <el-date-picker
                v-model="formData.birthday"
                type="date"
                suffix-icon="el-icon-date"
                :picker-options="pickerOptions1"
                placeholder="选择日期"
                value-format="yyyy-MM-dd"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <div class="grid-content">
              <el-form-item label="手机号：" :rules="[{validator: validateMobilePhone, trigger: 'blur'}]" prop="phone">
                <el-input auto-complete="off" v-model="formData.phone"></el-input>
              </el-form-item>
            </div>
          </el-col>
        </el-row>
      </div>
      <!-- -->
      <div class="mbasemid">
        <el-row>
          <el-col :span="12">
            <div class="grid-content">
              <el-form-item label="父亲姓名：" label-width="120px" prop="fatherName" :rules="[{validator: validatePass}]">
                <el-input auto-complete="true" v-model="formData.fatherName" placeholder="父亲姓名"></el-input>
              </el-form-item>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="grid-content">
              <el-form-item label="母亲姓名：" label-width="120px" prop="montherName"
                            :rules="[{required: true, validator: validatePass}]">
                <el-input auto-complete="true" v-model="formData.montherName" placeholder="母亲姓名"></el-input>
              </el-form-item>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <div class="grid-content">
              <el-form-item label="文化程度：" label-width="120px">
                <el-input auto-complete="off" v-model="formData.fatherEducation" placeholder="父亲文化程度"></el-input>
              </el-form-item>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="grid-content">
              <el-form-item label="文化程度：" label-width="120px">
                <el-input auto-complete="off" v-model="formData.montherEducation" placeholder="母亲文化程度"></el-input>
              </el-form-item>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <div class="grid-content">
              <el-form-item label="年 龄(岁)：" label-width="120px" prop="fatherAge"
                            :rules="[{validator: validateAge}]">
                <el-input auto-complete="off" v-model="formData.fatherAge" placeholder="父亲年龄"></el-input>
              </el-form-item>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="grid-content">
              <el-form-item label="年 龄(岁)：" label-width="120px" prop="montherAge"
                            :rules="[{required: true, validator: validateAge}]">
                <el-input auto-complete="off" v-model="formData.montherAge" placeholder="母亲年龄"></el-input>
              </el-form-item>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <div class="grid-content">
              <el-form-item label="职 业：" label-width="120px">
                <el-input auto-complete="off" v-model="formData.fatherJob" placeholder="父亲职业"></el-input>
              </el-form-item>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="grid-content">
              <el-form-item label="职 业：" label-width="120px">
                <el-input auto-complete="off" v-model="formData.montherJob" placeholder="母亲职业"></el-input>
              </el-form-item>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <div class="grid-content">
              <el-form-item label="家庭住址：" label-width="120px" prop="address">
                <el-input auto-complete="off" v-model="formData.address" style="width:100%"></el-input>
              </el-form-item>
            </div>
          </el-col>
          <el-col :span="1">
            <div class="grid-content">
            </div>
          </el-col>
        </el-row>
      </div>
    </el-form>
    <!---->
    <div style="text-align:center;margin:30px 0">
      <el-button type="primary" @click="goback">取消</el-button>
      <el-button type="primary" @click="saveInfo">保存</el-button>
      <el-button type="primary" v-show="showPrint" @click="print">打印</el-button>
      <el-button style="float:right;margin-right:20px;" v-if="newAdd" type="primary" @click="newCreat">新建</el-button>
    </div>
  </div>
</template>
<script>
  import headTop from '../../components/headTop'
  import {getCardNumber, editrecord, getRecordsInfo} from '@/api/getData'
  import {validateMobilePhone, validateAge} from '@/utils/validate'
  import qrCode from 'qrcodejs2' // 二维码

  export default {
    data() {
      var validatePass = (rule, value, callback) => {
        if (!rule.required && (!value)) {
          callback();
          return;
        }
        var regName = /^[\u4E00-\u9FA5\uf900-\ufa2d·s]{2,20}$/
        if (!value) {
          callback(new Error('请输入姓名'));
        } else if (!regName.test(value)) {
          callback(new Error('请输入正确的姓名'));
        } else {
          callback();
        }
      };
      return {
        newAdd: false,
        erweima: false,
        showPrint: true, // 显示打印功能
        formData: {
          cardId: '',
          unionId: '',
          name: '',
          sex: '',
          birthday: '',
          Weixinid: '',
          idnumber: '',
        },
        mother: true, // 是不是只有母亲档案
        validatePass: validatePass,
        validateMobilePhone: validateMobilePhone,
        validateAge: validateAge,
        pickerOptions1: {
          disabledDate(time) {
            return time.getTime() > Date.now();
          },
        },

        nodeList: [],
        nationOptions: [
          {label: '汉', value: '汉'},
          {label: '回', value: '回'},
          {label: '满', value: '满'},
          {label: '蒙古', value: '蒙古'},
          {label: '维吾尔', value: '维吾尔'},
          {label: '藏', value: '藏'},
          {label: '壮', value: '壮'},
          {label: '苗', value: '苗'},
          {label: '其它少数民族', value: '其它少数民族'}
        ],
        cardIdReadonly: false
      }
    },
    components: {
      headTop
    },
    mounted() {
      this.$myStore.setStore('subflag', {flag: 'false'})
      const that = this;
      const operateType = this.$myStore.getStore('operateType');

      if (operateType.flag == 'add') {
        that.cardIdReadonly = false;
        that.showPrint = false
        that.erweima = false
        const result = getCardNumber();
        result.then(function (res) {
          if (res) {
            that.formData.cardId = res;
            that.cardIdReadonly = true;
          }

          that.$myStore.setStore('newBirthUser', {})
        })
        that.newAdd = true;
        that.formData.guid = that.generateMixed(32);
      } else if (operateType.flag == 'edit') {
        that.cardIdReadonly = true;
        that.erweima = false
        that.erweima = true
        let obj = operateType.data
        const info = getRecordsInfo(obj)
        info.then(function (res) {
          that.formData = res
          that.creatQrCode(res.guid)
        })
      } else if (operateType.flag == 'print') {
        that.erweima = true
        var obj = operateType.data
        that.formData = obj
        that.creatQrCode(obj.guid)
        that.newAdd = true;
      }
    },

    methods: {
      // 保存
      saveInfo() {
        var that = this;
        that.$refs.formData.validate((valid) => {
          if (valid) {
            const result = editrecord(that.formData);
            result.then(res => {
              if (res.flag === 'success' || res.flag === 'nochange') {
                that.$message({
                  type: 'success',
                  message: '提交成功'
                });
                if (!that.erweima) {
                  that.creatQrCode(that.formData.guid)
                  that.erweima = true
                }
                that.$myStore.setStore('newBirthUser', that.formData)
              } else {
                that.$message({
                  type: 'error',
                  message: res.errorInfo || '提交失败'
                });
                that.$myStore.setStore('newBirthUser', {})
              }
              that.showPrint = true
            })
          } else {
            that.$alert('请检查内容是否填写完整', '提示', {
              confirmButtonText: '确定',
            });
          }
        })
      },
      // 生成二维码
      creatQrCode(res) {
        document.getElementById("erweima").innerHTML = "";
        var qrcode = new qrCode(this.$refs.qrCodeUrl, {
          text: res,
          width: 120,
          height: 120,
          colorDark: '#000000',
          colorLight: '#ffffff'
        })
      },
      // 生成随机数
      generateMixed(n) {
        var chars = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z']
        var res = "";
        for (var i = 0; i < n; i++) {
          var id = Math.ceil(Math.random() * 35);
          res += chars[id];
        }
        return res;
      },
      // 返回
      goback() {
        let returnRouter = this.$myStore.getStore('returnRouter');
        let router = returnRouter ? returnRouter.router : '/archives';
        this.$router.push({path: router});
        this.$myStore.removeStore('returnRouter');
      },
      // 清空该用户
      newCreat() {
        var that = this;
        this.formData = {
          cardId: '',
          name: '',
          sex: '',
          birthday: '',
          Weixinid: '',
        }
        this.erweima = false;
        that.$myStore.setStore('newBirthUser', {})
        that.showPrint = false
        this.$myStore.setStore('subflag', {flag: 'false'})
        this.$myStore.setStore('operateType', {'flag': 'add'});

        const result = getCardNumber();
        result.then(function (res) {
          if (res) {
            that.formData.cardId = res;
          }

          that.formData.guid = that.generateMixed(32);
        })
      },

      //打印
      print() {
        const self = this;
        self.$myStore.setStore('archivePrintData', self.formData);
        self.$myStore.setStore('newBirthUser', self.formData)
        self.$myStore.setStore('printBtnShow', {titleShow: true});
        self.$myStore.setStore('data', {flag: ''});
        self.$router.push({path: '/print_archives'})
      },

      // node改变
      nodeChange(node) {
        var that = this;
        if (node == 3) {
          that.mother = false
        } else {
          that.mother = true
          that.formData.c_name = '';
          that.formData.c_sex = '';
          that.formData.c_birthday = '';
        }
      }
    }
  }
</script>
<style scoped>
  .info_box {
    padding: 0 15px;
  }

  .el-checkbox {
    margin-bottom: 12px;
  }

  .wlabel {
    margin: 10px 0 20px 30px
  }

  .mbasemid {
    border: 1px solid #e8dfdf;
    margin-top: 10px;
    padding-top: 10px;
    padding-right: 10px;
  }

  .Wlable {
    float: left;
  }

  .Wcontent {
    margin-left: 120px;
  }

  .el-date-editor.el-input, .el-date-editor.el-input__inner {
    width: 100%
  }
</style>
