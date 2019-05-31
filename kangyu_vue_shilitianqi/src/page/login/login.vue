<template>
  <div class="login_page flex_center">
    <div class="login">
      <div class='login_header'>
        <img src='../../../static/login/img/login_head.png'/>
      </div>
      <div class="item_box">
        <el-form :model="loginForm" :rules="rules" ref="loginForm">
          <div class="login_item">
            <el-form-item prop="phone">
              <el-input v-model="loginForm.phone" placeholder="用户名"></el-input>
            </el-form-item>
          </div>
          <div class="login_item">
            <el-form-item prop="password">
              <el-input v-model="loginForm.password" @keydown.native="submit($event)"
                        placeholder="密码" show-password>
              </el-input>
            </el-form-item>
          </div>
          <div class="login_item" style="margin-bottom:30px">
            <el-checkbox v-model="checked" style="font-size:16px;" size="medium">
              记住用户名和密码
            </el-checkbox>
          </div>
          <div class="sub_box">
            <el-button @click="submitForm('loginForm')" class="submit_btn">登&nbsp;录</el-button>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
  import {fetchLogin, fetchHospital} from '../../api/login'

  export default {
    data() {
      return {
        checked: true,
        loginForm: {
          phone: '',
          password: '',
        },
        name: '',
        rules: {
          phone: [
            {required: true, message: '请输入用户名', trigger: 'blur'},
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'}
          ],
        },
        showLogin: false,
      }
    },
    mounted() {
      console.log(this.$myStore)
      this.initData();
    },
    methods: {
      async initData() {
        let getters = this.$store.getters;
        if (getters.phone && getters.password) {
          this.loginForm = {
            phone: getters.phone,
            password: getters.password
          }
        }
        const self = this;
        fetchHospital('hosptid1234567890').then(res => {
          console.log('res:', res);
          self.$store.dispatch('Hospital', res);
        });
      },
      async submitForm(formName) {
        let self = this;
        let loginForm = self.loginForm
        fetchLogin(loginForm).then(res => {
          self.$store.dispatch('Logins', res).then((res) => {
            self.$router.push({path: '/home'});
          });
          if (self.checked) {
            self.$store.dispatch('PhoneAndPassWord', loginForm)
          } else {
            self.$store.dispatch('PhoneAndPassWord', {})
          }
        })
      },
      submit(e) {
        // key.Code === 13表示回车键
        if (e.keyCode === 13) {
          this.submitForm('loginForm');
        }
      }
    }
  }
</script>
<style lang="less" scoped>
  .flex_center {
    display: flex;
    justify-content: center; /* 水平居中 */
    align-items: center; /* 垂直居中 */
  }

  .login_page {
    width: 100%;
    height: 100%;
    background: url('../../../static/login/img/login.png') center no-repeat;
    background-size: 100% 100%
  }

  .login {
    height: 440px;
    width: 420px;
    padding: 10px 40px;
    background: #fff
  }

  .login_header {
    height: 60px;
    padding: 32px 0 30px 0;
    text-align: center
  }

  .item_box {
    padding: 10px 0;
  }

  .login_item {
    margin-bottom: 40px;
  }

  .login_item .el-form-item {

  }

  .login_item .el-input__inner {
    height: 50px;
    line-height: 50px;
    background: none;
    border: 1px solid #dfdfdf;
    color: #666;
    border-radius: 0;
    font-size: 16px;
  }

  .login_item .el-input__inner::-webkit-input-placeholder {
    color: #666 !important;
  }

  .login_item .el-form-item__error {
    color: #ff4444 !important
  }

  .login_item .el-form-item.is-success .el-input__inner {
    border-color: #dfdfdf !important;
  }

  .el-checkbox__input.is-checked + .el-checkbox__label {
    color: #999 !important
  }

  .el-checkbox__input.is-checked .el-checkbox__inner, .el-checkbox__input.is-indeterminate .el-checkbox__inner {
    background-color: #6296ea !important;
    border-color: #6296ea !important;
  }

  .el-checkbox__inner:hover {
    border-color: #6296ea !important;
  }

  .el-checkbox__label {
    font-size: 16px !important;
  }

  .el-checkbox__inner {
    width: 22px;
    height: 22px !important;
  }

  .el-checkbox__inner::after {
    height: 9px;
    width: 5px;
    left: 7px;
    top: 4px
  }

  .sub_box {
    margin-top: 30px;
    overflow: hidden
  }

  .submit_btn {
    width: 100%;
    background: #6296ea;
    color: #fff;
    border: none;
    font-size: 22px;
    height: 50px;
    border-radius: 0;
  }

  .submit_btn:hover, .submit_btn:focus {
    background: #4d88e8;
    color: #fff;
  }

</style>
