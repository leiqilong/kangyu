<template>
  <div class="dialog-container doctor-edit-form">
    <el-dialog :title="title" :visible.sync="visible" @close="closeForm('formData')" :show="show">
      <el-form :model="formData" :rules="rules" ref="formData" label-width="100px">
        <el-form-item label="标签名称" prop="tagName">
          <el-input v-model="formData.tagName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="标签代码" prop="tagCode">
          <el-input v-model="formData.tagCode" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="对应表单" prop="correspondingForms">
          <el-select v-model="formData.correspondingForms" placeholder="请选择" :multiple="true" style="width: 100%">
            <el-option v-for="item in customFromList" :label="item.label" :value="item.value" :key="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="附加标签" prop="additionalInformation">
          <el-input v-model="formData.additionalInformation"></el-input>
        </el-form-item>
        <el-form-item label="标签类型" prop="tagType">
          <el-select v-model="formData.tagType" placeholder="请选择" style="width: 100%">
            <el-option v-for="item in tagTypeList" :label="item.label" :value="item.value" :key="item.value">
            </el-option>
          </el-select>
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
  import {addCustomFormTag} from '@/api/tag_warehouse'
  import {tagTypeList} from './js/tag_list_data.js'

  export default {
    data() {
      return {
        visible: false,
        formData: {
          tagName: '',
          tagCode: '',
          correspondingForms: '',
          additionalInformation: '',
          tagType: ''
        },
        customFromList: [
          {label: '表单1', value: '789447944244'},
          {label: '表单2', value: '578964789475'},
          {label: '表单3', value: '587654894656'}
        ],
        tagTypeList: tagTypeList,
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
        let self = this;
        self.visible = self.show;
        self.formData = self.data;
      }
    },
    methods: {
      submitForm(formName) {
        let self = this;
        this.$refs[formName].validate(async (valid) => {
          if (valid) {
            addCustomFormTag(self.formData)
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
