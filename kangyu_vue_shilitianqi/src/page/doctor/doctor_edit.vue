<template>
  <div class="dialog-container doctor-edit-form">
    <el-dialog :title="title" :visible.sync="visible" @close="closeForm('formData')" :show="show">
      <div class="form-main">
        <el-form :model="formData" :rules="rules" ref="formData">
          <div class="form-img">
            <div class="imgbody">
              <img id='img' v-bind:src="formData.headpto" style="height:206px;width:183px;">
            </div>
            <div class="imgfoot">
              <el-button type="primary" size="mini" value='拍照' @click="paizhao">拍照</el-button>
            </div>
          </div>
          <div class="doctor-baseinfo" id="baseinfo">
            <el-form-item label="姓名：" label-width="100px" prop="name">
              <el-input auto-complete="off" v-model="formData.name" maxlength=10></el-input>
            </el-form-item>
            <el-form-item label="性别：" label-width="100px" prop="sex">
              <el-select v-model="formData.sex" placeholder="级别" style="width:100%;" @change="sexChange">
                <el-option v-for="item in sexOptions" :key="item.value" :label="item.label"
                           :value="item.value"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="年龄：" label-width="100px" prop="age">
              <el-input auto-complete="off" v-model="formData.age"></el-input>
            </el-form-item>
            <el-form-item label="电话：" label-width="100px" prop="phone">
              <el-input auto-complete="off" v-model="formData.phone"></el-input>
            </el-form-item>
          </div>
          <div class="basemid">
            <el-row>
              <el-col :span="12">
                <div class="grid-content">
                  <el-form-item label="职称：" label-width="120px" prop="zhicheng">
                    <el-input auto-complete="off" v-model="formData.zhicheng"></el-input>
                  </el-form-item>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="grid-content">
                  <el-form-item label="擅长领域：" label-width="120px" prop="scly">
                    <el-input auto-complete="off" v-model="formData.scly"></el-input>
                  </el-form-item>
                </div>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <div class="grid-content">
                  <el-form-item label="学术成就：" label-width="120px" prop="scholarship">
                    <el-input type="textarea" v-model="formData.scholarship"></el-input>
                  </el-form-item>
                </div>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <div class="grid-content">
                  <el-form-item label="级别：" label-width="120px" prop="level">
                    <el-select v-model="formData.level" placeholder="级别" style="width:100%;">
                      <el-option v-for="item in levelOptions" :key="item.value" :label="item.label"
                                 :value="item.value"></el-option>
                    </el-select>
                  </el-form-item>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="grid-content">
                  <el-form-item label="科室：" label-width="120px" prop="nodeName">
                    <el-input auto-complete="off" v-model="formData.nodeName" maxlength=10></el-input>
                  </el-form-item>
                </div>
              </el-col>
            </el-row>
          </div>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer" style="margin-right:17px;">
        <el-button @click="closeForm('formData')">取 消</el-button>
        <el-button type="primary" @click="submitForm('formData')">确 定</el-button>
      </div>
    </el-dialog>
    <el-paizhao :show.sync="handlePaiZhaoPro.dialogFormVisible" :title="handlePaiZhaoPro.title"
                v-on:listenToChildEvent="showMessageFormChild"></el-paizhao>
  </div>
</template>
<script>
  import {validatePhone, validateAge} from '@/utils/validate'
  import paizhao from '@/components/paizhao'
  import {fetchDoctorEdit, getDoctorDetail} from '@/api/doctor'

  let globGuid = '';
  export default {
    data() {
      return {
        visible: this.show,
        formData: {
          id: '',
          headpto: '/static/login/img/doctor-man.png',
          name: '',
          sex: '男',
          age: '',
          level: '',
          phone: '',
          zhicheng: '',
          scly: '',
          scholarship: '',
          createtime: '',
          node: 0,
          nodeName: '',
        },
        sexOptions: [
          {label: '男', value: '男'},
          {label: '女', value: '女'}
        ],
        levelOptions: [
          {label: '医生', value: '1'},
          {label: '主任', value: '2'}
        ],
        nodeList: [
          {label: '孕前', value: 0},
          {label: '孕期', value: 1},
          {label: '产后', value: 2},
          {label: '儿童', value: 3}
        ],
        rules: {
          name: [
            {required: true, message: '请输入姓名', trigger: 'blur'}
          ],
          age: [
            {required: true, message: '请输入年龄'},
            {validator: validateAge, trigger: 'blur'}
          ],
          phone: [
            {required: true, message: '请输入电话'},
            {validator: validatePhone, trigger: 'blur'}
          ],
          zhicheng: [
            {required: true, message: '请输入职称', trigger: 'blur'}
          ],
          scly: [
            {required: true, message: '请输入擅长领域', trigger: 'blur'}
          ],
          level: [
            {required: true, message: '请选择医生级别'}
          ],
          nodeName: [
            {required: true, message: '请输入科室'}
          ]
        },
        handlePaiZhaoPro: {
          dialogFormVisible: false,
          title: '拍照'
        },
      };
    },
    props: {
      show: {
        type: Boolean,
        default: false
      },
      title: {
        type: String,
        default: ''
      },
      data: {
        type: Object,
        default: null
      }
    },
    components: {
      'el-paizhao': paizhao
    },
    watch: {
      show() {
        var self = this;
        self.visible = self.show;
        if (self.show) {
          let id = self.data.id;
          if (id) {
            globGuid = id;
            getDoctorDetail(id)
              .then(function (res) {
                self.formData = res;
                let sex = res.sex;
                let headpto = res.headpto;
                if (headpto.indexOf('jpeg;base64') < 0) {
                  if (sex == '女') {
                    res.headpto = '/static/login/img/doctor-man.png';
                  } else {
                    res.headpto = '/static/login/img/doctor-man.png';
                  }
                }
              });
          }
        }
      }
    },
    methods: {
      paizhao() {
        this.handlePaiZhaoPro.dialogFormVisible = true;
      },

      sexChange(val) {
        let self = this;
        let headpto = self.formData.headpto;
        if (headpto.indexOf('jpeg;base64') < 0) {
          if (val == '女') {
            self.formData.headpto = '/static/login/img/doctor-man.png';
          } else {
            self.formData.headpto = '/static/login/img/doctor-man.png';
          }
        }
      },

      submitForm(formName) {
        var self = this;
        self.$refs[formName].validate(async (valid) => {
          if (valid) {
            fetchDoctorEdit(self.formData)
              .then((res) => {
                if (!res) {
                  return false;
                }
                self.$refs[formName].resetFields()
                this.data.id = '';
                this.formData.id = '';
                self.formData.headpto = '';
                self.$emit('listenToChildEvent', false);
              });

          } else {
            self.$notify.error({
              title: '错误',
              message: '请检查输入是否正确',
              offset: 100
            });
          }
        });
      },

      /**
       * 关闭弹窗
       * @param formName
       */
      closeForm(formName) {
        this.$refs[formName].resetFields()
        this.data.id = '';
        this.formData.id = '';
        this.formData.headpto = '';
        this.$emit('update:show', false)
      },
      /**
       * 拍照结束
       * @param data
       */
      showMessageFormChild(data) {
        this.handlePaiZhaoPro.dialogFormVisible = false;
        this.formData.headpto = data;
      }
    },
  };

</script>
<style>
  .form-main {
    float: left;
    margin-bottom: 10px;
    height: 500px;
    overflow: auto;
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

  .doctor-baseinfo {
    width: 66%;
    border: 1px solid #e8dfdf;
    padding-top: 10px;
    padding-right: 10px;
    padding-bottom: 0px;
    margin-left: 10px;
    float: left;
    position: relative;
  }

  .basemid {
    border: 1px solid #e8dfdf;
    margin-top: 10px;
    width: 97%;
    padding-top: 10px;
    padding-right: 10px;
    float: left;;
  }
</style>
