<template>
  <div class="dialog-container doctor-edit-form">
    <el-dialog :title="title" :visible.sync="visible" @close="closeForm('formData')" :show="show">
      <el-form :model="formData" :rules="rules" ref="formData" label-width="100px">
        <el-form-item label="标签名称" prop="tagName">
          <el-input v-model="formData.tagName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="标签值" prop="tagValue">
          <el-input v-model="formData.tagValue" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="标签代码" prop="tagCode">
          <el-input v-model="formData.tagCode" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="对应表单" prop="correspondingForms">
          <!--<el-select v-model="formData.correspondingForms" placeholder="请选择" :multiple="true" style="width: 100%">
            <el-option-group
              v-for="group in correspondingFromList"
              :key="group.groupLabel"
              :label="group.groupLabel">
              <el-option
                v-for="item in group.groupOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-option-group>
          </el-select>-->
          <!--<el-cascader v-model="formData.correspondingForms" :options="formTree" :props="props" :show-all-levels="false"
                       clearable collapse-tags style="width: 100%"></el-cascader>-->

          <el-tag
            v-for="tag in formData.correspondingForms"
            :key="tag"
            closable
            :disable-transitions="false"
            @close="handleClose(tag)">
            {{getFormName(tag)}}
          </el-tag>
          <!--添加-->
          <!--<div style="display:inline-block">
            <el-popover
              placement="right"
              width="400"
              v-model="visibleAdd"
              trigger="click">
              <div style="padding:20px">
                <el-cascader v-model="currentLeaf" :options="formTree" :props="props"
                             clearable collapse-tags style="width:100%"
                             @change="selectValue">
                </el-cascader>
              </div>
              <el-button slot="reference" class="button-new-tag" size="small">添加</el-button>
            </el-popover>
          </div>-->
          <el-cascader v-model="currentLeaf" :options="formTree" :props="props" v-if="cascaderVisible"
                       clearable collapse-tags @change="correspondingFormsChange">
          </el-cascader>
          <el-button v-else class="button-new-tag" size="small" @click="cascaderShow">添加</el-button>
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
  import {addOrEditCustomFormTag, getCustomFormTagList, getCorrespondingFromList} from '@/api/tag_warehouse'
  import {getAllModelTree} from '@/api/getData'
  import {tagTypeList} from './js/tag_list_data.js'

  export default {
    data() {
      return {
        visible: false,
        formData: {
          tagName: '',
          tagValue: '',
          tagCode: '',
          correspondingForms: [],
          additionalTags: [],
          tagType: ''
        },
        correspondingFromList: [],
        formTree: [],
        formList: [],
        currentLeaf: [],
        visibleAdd: false, //添加表单
        props: {
          multiple: true,
          value: 'leaf',
          children: 'children',
          label: 'name',
        },
        tagTypeList: tagTypeList,
        otherTypeTags: [],
        cascaderVisible: false,
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
          self.formData.correspondingForms = self.formData.correspondingForms || []

          getCustomFormTagList({tagType: '03'})
            .then(res => {
              self.otherTypeTags = res;
            })

          getCorrespondingFromList()
            .then(res => {
              self.correspondingFromList = res;
            })
          getAllModelTree({}).then(function (list) {
            let menu = list[3].menu;
            menu = self.delChirld(menu)
            self.formTree = menu
            self.getFormList(menu)
          })
        } else {
          self.$emit('listenToChildEvent', false)
        }

        // console.log('customFromList', customFromList);
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
      },

      /**
       * 相关表单下拉改变
       * @param value
       */
      correspondingFormsChange(value) {
        let self = this;
          let v = value[value.length-1];
          self.formData.correspondingForms.push(v + ';02')
        self.cascaderVisible = false
      },

      /*selectValue(value) {
        let that = this;
        let leaf = value[value.length - 1];
        if (this.formLeaf.indexOf(leaf) < 0) {//去重
          let treeList = that.treeList;
          for (var l = 0; l < treeList.length; l++) {
            if (treeList[l].leaf == value[0]) {
              that.formLeaf.push(leaf)
              let name = '';
              if (value.length > 1) {//多层数据
                name = that.getName(treeList[l], leaf)
              } else {//单层数据
                name = treeList[l].name
              }
              that.formData.formTags.push({//带着leaf，删除时容易操作
                name: name,
                leaf: value[value.length - 1]
              });
              break;
            }
          }
        } else {
          that.$notify.error({
            title: '提示',
            message: '该表单已添加',
            offset: 100
          });
        }
        this.visibleAdd = false
        console.log(value)
      },*/

      //删除某一附加表单
      handleClose(tag) {
        let correspondingForms = this.formData.correspondingForms;
        correspondingForms.splice(correspondingForms.indexOf(tag), 1)
      },

      //递归删除空子对象
      delChirld(treeList) {
        let newData = treeList;
        try {
          const findName = (data) => {
            if (data.children && data.children.length == 0) {
              delete data.children;     //找到节点抛出
            } else {  //子节点进行递归
              for (let i in data.children) {
                findName(data.children[i]);
              }
            }
          }
          for (var i = 0; i < newData.length; i++) {
            findName(newData[i]);//最外层循环c
          }
        } catch (newData) {
          return newData   //找到的节点
        }
        return newData
      },

      cascaderShow() {
        let self = this;
        self.currentLeaf = [];
        self.cascaderVisible = true
      },

      getFormList(menu) {
        let self = this;
        self.formList = self.formList.concat(menu)
        for (let element of menu) {
          if (element.children && element.children.length) {
            self.formList = self.formList.concat(element.children)
          }
        }
      }
    },

    computed: {
      getFormName() {
        return (formTag) => {
          let self = this;
          let formTagArr = formTag.split(';')
          for (let element of self.formList) {
            if (formTagArr[1] === '02' && formTagArr[0] === element.leaf) {
              return element.name
            }
          }
        }
      }
    }
  }

</script>
<style scoped></style>
