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
        <el-form-item label="附加标签" prop="additionalTags">
          <el-select v-model="formData.additionalTags" placeholder="请选择" :multiple="true" style="width: 100%">
            <el-option v-for="item in otherTypeTags" :label="item.tagName" :value="item.id" :key="item.id">
            </el-option>
          </el-select>
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
  import {addOrEditCustomFormTag, getCustomFormTagList} from '@/api/tag_warehouse'
  import {tagTypeList, customFromList} from './js/tag_list_data.js'

  export default {
    data() {
      return {
        visible: false,
        formData: {
          tagName: '',
          tagCode: '',
          correspondingForms: [],
          additionalTags: [],
          tagType: ''
        },
        customFromList: [],
        tagTypeList: tagTypeList,
        otherTypeTags: [],
        rules: {
          tagName: [
            {required: true, message: '请输入标签名称', trigger: 'blur'}
          ],
          tagCode: [
            {required: true, message: '请输入标签代码', trigger: 'blur'}
          ],
          tagType: [
            {required: true, message: '请选择标签类型', trigger: 'change'}
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
      /**
       * props.show 监听方法
       */
      show() {
        let self = this;
        self.visible = self.show;
        if (self.show) {
          self.formData = self.data
          getCustomFormTagList({tagType: '03'})
            .then(res => {
              self.otherTypeTags = res;
            })
        } else {
          self.$emit('listenToChildEvent', false)
        }

        console.log('customFromList', customFromList);
      }
    },
    methods: {
      /**
       * 确定
       * @param formName from 表单ref 参数
       */
      submitForm(formName) {
        let self = this;
        this.$refs[formName].validate(async (valid) => {
          if (valid) {
            addOrEditCustomFormTag(self.formData)
              .then(res => {
                if (!res) {
                  return;
                }
                self.$message({
                  type: 'success',
                  message: '操作成功'
                });
                self.$refs[formName].resetFields()
                self.$emit('update:show', false)
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
       * @param formName from 表单ref 参数
       */
      closeForm(formName) {
        this.$refs[formName].resetFields()
        this.$emit('update:show', false)
      }
    }
  }

</script>
<style scoped></style>
