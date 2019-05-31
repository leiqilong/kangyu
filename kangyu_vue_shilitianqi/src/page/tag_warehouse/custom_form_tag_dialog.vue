<template>
  <div class="dialog-container doctor-edit-form">
    <el-dialog :title="title" :visible.sync="visible" @close="closeForm('formData')" :show="show">
      <el-form :model="formData" :rules="rules" ref="formData">
        <el-form-item label="标签名称" prop="tagName">
          <el-input v-model="formData.tagName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="对应表单">
          <el-select v-model="formData.correspondingForms" placeholder="请选择" style="width: 100%" :multiple="true">
            <el-option v-for="item in customeFromList" :label="item.lable" :value="item.value"
                       :key="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="附加信息">
          <el-input v-model.number="formData.additionalInformation"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('formData')">确定</el-button>
          <el-button @click="closeForm('formData')">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
  import {addCustomeFormTag} from '@/api/tag_warehouse'

  export default {
    data() {
      return {
        visible: false,
        formData: {
          tagName: '',
          correspondingForms: '',
          additionalInformation: ''
        },
        customeFromList: [
          {lable: '表单1', value: '789447944244'},
          {lable: '表单2', value: '578964789475'},
          {lable: '表单3', value: '587654894656'}
        ],
        rules: {
          tagName: [
            {required: true, message: '请输入标签名称', trigger: 'blur'}
          ]
        }
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
    watch: {
      show() {
        var self = this;
        self.visible = self.show;
      }
    },
    methods: {
      submitForm(formName) {
        var self = this;
        this.$refs[formName].validate(async (valid) => {
          if (valid) {
            addCustomeFormTag(self.formData)
              .then(res => {
                if (!res) {
                  return;
                }
                self.$message({
                  type: 'success',
                  message: '操作成功'
                });
                self.$refs[formName].resetFields()
                self.$emit('listenToChildEvent', false)
              });
          } else {
            self.$notify.error({
              title: '错误',
              message: '请检查输入是否正确',
              offset: 100
            });
          }
        })
      },

      /**
       * 关闭弹窗
       * @param formName
       */
      closeForm(formName) {
        this.$refs[formName].resetFields()
        this.$emit('update:show', false)
      }
    }
  }

</script>
<style scoped></style>
