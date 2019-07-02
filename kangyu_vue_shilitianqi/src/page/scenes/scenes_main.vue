<template>
  <div>
    <head-top></head-top>
    <div class="comm_container">
      <el-form :inline="true" label-width="100px" class="demo-form-inline" v-model="queryData">
        <el-form-item label="场景名称">
          <el-input v-model="queryData.scenesName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchScenesList">查询</el-button>
          <el-button type="primary" @click="handleScenesAdd">新建</el-button>
        </el-form-item>
        <el-form-item style="float: right">
          <el-button v-if="backButtonVisible" type="primary" @click="goBack">返回</el-button>
        </el-form-item>
      </el-form>
      <div style="overflow:hidden">
        <div class="form_style">
          <el-table v-loading="loading" :data="scenesList" class="croll" highlight-current-row @current-change="scenesCurrentChange" :style="'overflow:auto;width:100%;border: 1px solid #ddd;'+leftHeight" border>
            <el-table-column sortable label="场景名称" prop="scenesName"></el-table-column>
            <el-table-column sortable label="场景代码" prop="scenesCode"></el-table-column>
            <el-table-column label="操作" width="180px" align="center">
              <template slot-scope="scope">
                <el-button size="mini" type="primary" @click="handleDeviceAdd(scope.$index, scope.row)">添加设备</el-button>
                <el-button size="mini" @click="handleScenesEdit(scope.$index, scope.row)">编辑</el-button>
                <el-button size="mini" type="danger" @click="handleScenesDelete(scope.$index, scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination
            @size-change="scenesSizeChange"
            @current-change="scenesPageChange"
            :page-sizes="[5, 10, 15, 20, 50, 100]"
            :page-size="queryData.pageSize"
            layout="total, sizes, prev, pager, next"
            :total="scenesTotal">
          </el-pagination>
        </div>

        <div class="table_style">
          <el-table v-loading="loading" :data="deviceList" highlight-current-row
                    @current-change="deviceCurrentChange" class="croll"  border :style="'overflow:auto;width:100%;'+rightHeight">
            <el-table-column label="序号" type="index" width="65px"></el-table-column>
            <el-table-column sortable label="设备名称" prop="deviceName"></el-table-column>
            <el-table-column sortable label="设备代码" prop="deviceCode"></el-table-column>
            <el-table-column sortable label="权重" prop="weights"></el-table-column>
            <el-table-column sortable label="优先级" prop="priority"></el-table-column>
            <el-table-column label="操作" width="250px"align="center">
              <template slot-scope="scope">
                <el-button size="small" type="primary" @click="judgeStandardAdd(scope.$index, scope.row)">添加规则</el-button>
                <el-button size="small" @click="handleDeviceEdit(scope.$index, scope.row)">编辑</el-button>
                <el-button size="small" type="danger" @click="handleDeviceDelete(scope.$index, scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-table v-loading="loading" class="croll" :data="judgeStandardList" :style="'overflow:auto;width: 100%;margin-top: 20px;border: 1px solid #ddd;'+rightHeight"border>
            <el-table-column label="序号" type="index" width="65px"></el-table-column>
            <el-table-column label="标签" prop="tagName" :formatter="formatTagName"></el-table-column>
            <el-table-column label="规则" prop="ruler"></el-table-column>
            <el-table-column label="分数" prop="score"></el-table-column>
            <el-table-column label="操作" width="180px"align="center">
              <template slot-scope="scope">
                <el-button size="small" @click="judgeStandardEdit(scope.$index, scope.row)">编辑</el-button>
                <el-button size="small" type="danger" @click="judgeStandardDelete(scope.$index, scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <!--场景弹窗-->
      <el-dialog title="场景" :visible.sync="scenesFormVisible" @close="scenesFormClose">
        <el-form :model="scenesForm" label-width="100px" :rules="scenesFormRules" ref="scenesForm">
          <el-form-item label="场景名称" prop="scenesName">
            <el-input v-model="scenesForm.scenesName" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="场景代码" prop="scenesCode">
            <el-input v-model="scenesForm.scenesCode" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="scenesFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveScenes">确 定</el-button>
        </div>
      </el-dialog>

      <!--设备弹窗-->
      <el-dialog title="设备" :visible.sync="deviceFormVisible" @close="deviceFormClose">
        <el-form :model="deviceForm" label-width="100px" :rules="deviceFormRules" ref="deviceForm">
          <el-form-item label="设备名称" prop="deviceName">
            <el-select v-model="deviceForm.deviceName" placeholder="请选择" @change="deviceChange" style="width: 100%">
              <el-option v-for="item in defalutDeviceList" :label="item.deviceName" :value="item.deviceName" :key="item.index">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="设备代码" prop="deviceCode">
            <el-input v-model="deviceForm.deviceCode" autocomplete="off" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="权重" prop="weights">
            <el-input v-model="deviceForm.weights" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="优先级" prop="priority">
            <el-input v-model="deviceForm.priority" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>

        <div slot="footer" class="dialog-footer">
          <el-button @click="deviceFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveDeviceForm">确 定</el-button>
        </div>
      </el-dialog>

      <!--规则弹窗-->
      <el-dialog title="规则" :visible.sync="judgeStandardFormVisible" @close="judgeStandardFormClose">
        <el-form :model="judgeStandardForm" label-width="100px" :rules="judgeStandardFormRules" ref="judgeStandardForm">
          <el-form-item label="标签" prop="tagId">
            <el-select v-model="judgeStandardForm.tagId" placeholder="请选择" @change="tagChange"
                       filterable style="width: 100%">
              <el-option v-for="item in typeTags" :label="item.tagName + (item.tagValue ? item.tagValue : '')"
                         :value="item.id" :key="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="规则" prop="ruler">
            <el-input v-model="judgeStandardForm.ruler" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="分数" prop="score">
            <el-input v-model="judgeStandardForm.score" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="judgeStandardFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveJudgeStandard">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
  import headTop from '@/components/headTop'
  import {validateNumber, validateFloat} from '@/utils/validate'
  import {
    searchScenesListByParams, // 查询所有场景
    saveOrEditScenes, // 保存场景
    deleteScenesById, // 删除场景信息
    saveOrEditDevice, // 保存设备
    searchDeviceList, // 查询设备列表
    deleteDeviceById, // 删除设备
    saveJudgeStandard, // 保存规则
    searchJudgeStandardList, // 查询规则列表
    deleteJudgeStandard, // 根据主键删除规则
    getCustomFormTagList
  } from '@/api/scenes.js'

  import {defalutDeviceList} from './js/constant'

  export default {
    name: 'scenes_main',

    data() {
      return {
        leftHeight:'',
        rightHeight:'',
        queryData: {
          scenesName: '',
          pageSize: 5,
          pageNum: 1
        },

        scenesTotal: 0,

        // 场景列表
        scenesList: [],
        // 设备列表
        deviceList: [],
        // 规则列表
        judgeStandardList: [],

        typeTags: [],

        loading: false,
        // total: 0,
        deviceFormVisible: false,
        scenesFormVisible: false,
        judgeStandardFormVisible: false,

        // 当前场景 id
        scenesId: '',

        // 场景表单数据
        scenesForm: {
          scenesId: '',
          scenesName: '',
          scenesCode: ''
        },

        scenesFormRules: {
          scenesName: [{required: true, message: '请输入场景名称', trigger: 'blur'}],
          scenesCode: [{required: true, message: '请输入场景代码', trigger: 'blur'}]
        },

        // 当前添加规则设备id
        deviceOfScenesId: '',

        // 设备表单数据
        deviceForm: {
          scenesId: '',
          deviceOfScenesId: '',
          deviceName: '',
          deviceCode: '',
          weights: '',
          priority: '',
        },

        defalutDeviceList: defalutDeviceList,

        deviceFormRules: {
          deviceName: [{required: true, message: '请输入设备名称', trigger: 'blur'}],
          deviceCode: [{required: true, message: '请输入设备代码', trigger: 'blur'}],
          weights: [
            {required: true, message: '请输入权重', trigger: 'blur'},
            {validator: validateFloat, minValue: 0, maxValue: '1', trigger: 'blur'}
          ],
          priority: [
            {required: true, message: '请输入优先级', trigger: 'blur'},
            {validator: validateNumber, trigger: 'blur'}
          ]
        },

        // 规则 表单数据
        judgeStandardForm: {
          deviceOfScenesId: '',
          judgeStandardId: '',
          tagId: '',
          tagName: '',
          tagValue: '',
          ruler: '',
          score: ''
        },

        judgeStandardFormRules: {
          tagId: [{required: true, message: '请选择标签名称', trigger: 'blur'}],
          ruler: [{required: true, message: '请输入规则表达式', trigger: 'blur'}],
          score: [
            {required: true, message: '请输入分数', trigger: 'blur'},
            {validator: validateNumber, trigger: 'blur'}
          ],
        },

        backButtonVisible: false
      }
    },

    components: {
      headTop
    },

    mounted() {
      let backParam = this.$myStore.getStore('backParam');
      if (backParam) {
        this.backButtonVisible = true
      }
      this.$myStore.removeStore('backParam');
      this.searchScenesList()
    },

    created() {
      this.leftHeight = 'height:' + parseFloat(window.innerHeight - 240) + 'px';
      this.rightHeight='height:' + parseFloat(window.innerHeight - 260)/2 + 'px';
    },

    methods: {
      /**
       * 查询场景下拉
       */
      searchScenesList() {
        let self = this
        searchScenesListByParams(self.queryData)
          .then(res => {
            if (!res) {
              return
            }
            self.scenesList = res.list
            self.scenesTotal = res.page.total;

            self.deviceList = [];
            self.judgeStandardList = [];
          })

        getCustomFormTagList({tagType: '04'})
          .then(res => {
            self.typeTags = res;
          })

        return self
      },

      /**
       * 分页插件每页大小改变
       */
      scenesSizeChange(pageSize) {
        this.queryData.pageSize = pageSize
        this.searchScenesList()
      },

      /**
       * 分页插件当前页改变
       */
      scenesPageChange(currentPage) {
        this.queryData.pageNum = currentPage
        this.searchScenesList()
      },

      /**
       *
       */
      scenesCurrentChange(currentRow, oldCurrentRow) {
        let self = this;
        self.scenesId = currentRow.scenesId
        self.searchDeviceList(currentRow.scenesId)
      },

      /**
       * 打开添加场景弹窗
       */
      handleScenesAdd() {
        let self = this;
        self.scenesFormVisible = true
      },

      /**
       * 打开修改场景页签
       */
      handleScenesEdit(index, row) {
        let self = this;
        let scenesForm = self.scenesForm;
        for (let prop in scenesForm) {
          scenesForm[prop] = row[prop];
        }

        self.scenesFormVisible = true
      },

      /**
       * 场景删除
       */
      handleScenesDelete(index, row) {
        let self = this;
        deleteScenesById(row.scenesId)
          .then(res => {
            if (!res) {
              return
            }
            self.searchScenesList()
              .$message({
                type: 'success',
                message: '操作成功'
              })
          })
      },

      /**
       * 场景弹窗关闭, 清空表单数据
       */
      scenesFormClose() {
        let self = this;
        self.$refs['scenesForm'].resetFields()

        self.scenesForm.scenesId = ''
        self.scenesForm.scenesCode = ''
        self.scenesForm.scenesName = ''

        return self;
      },

      /**
       * 保存场景
       */
      saveScenes() {
        let self = this;
        self.$refs['scenesForm'].validate(async (valid) => {
          if (!valid) {
            return
          }

          saveOrEditScenes(self.scenesForm)
            .then(res => {
              if (!res) {
                return
              }
              self.searchScenesList()
                .scenesFormClose()
                .$message({
                  type: 'success',
                  message: '操作成功'
                })
            })
        })
      },

      /**
       * 查询场景对应的设备
       */
      searchDeviceList(scenesId) {
        let self = this;
        searchDeviceList(scenesId)
          .then(res => {
            if (!res) {
              return
            }
            self.deviceList = res;
            self.judgeStandardList = [];
          })
        return self;
      },

      /**
       * 选中一件设备 查询对应的规则列表
       */
      deviceCurrentChange(currentRow, oldCurrentRow) {
        let self = this;
        if (!currentRow) {
          return;
        }
        self.deviceOfScenesId = currentRow.deviceOfScenesId
        self.searchJudgeStandardList(currentRow.deviceOfScenesId)
      },

      /**
       * 打开添加设备弹窗
       */
      handleDeviceAdd(index, row) {
        let self = this;

        self.scenesId = row.scenesId
        self.deviceForm.scenesId = self.scenesId
        self.deviceFormVisible = true
      },

      /**
       * 修改设备弹窗
       */
      handleDeviceEdit(index, row) {
        let self = this;
        let deviceForm = self.deviceForm
        for (let prop in deviceForm) {
          deviceForm[prop] = row[prop]
        }

        this.deviceFormVisible = true
      },

      /**
       * 删除设备
       */
      handleDeviceDelete(index, row) {
        let self = this;
        deleteDeviceById(row.deviceOfScenesId)
          .then(res => {
            if (!res) {
              return
            }
            self.searchDeviceList(self.scenesId)
              .$message({
                type: 'success',
                message: '操作成功'
              })
          })
      },

      /**
       * 保存设备
       */
      saveDeviceForm() {
        let self = this;
        this.$refs['deviceForm'].validate(async (valid) => {
          if (!valid) {
            return
          }

          saveOrEditDevice(self.deviceForm)
            .then(res => {
              if (!res) {
                return
              }
              self.searchDeviceList(self.scenesId)
                .deviceFormClose()
                .$message({
                  type: 'success',
                  message: '操作成功'
                })
            })
        })

      },

      /**
       * 设备表单关闭时清空
       */
      deviceFormClose() {
        let self = this;

        self.$refs['deviceForm'].resetFields()

        self.deviceForm.scenesId = self.scenesId
        self.deviceForm.deviceOfScenesId = ''
        self.deviceForm.deviceCode = ''
        self.deviceForm.deviceName = ''
        self.deviceForm.priority = ''
        self.deviceForm.weights = ''

        return self
      },


      /**
       * 根据场景设备主键 查询场景设备规则列表
       */
      searchJudgeStandardList(deviceOfScenesId) {
        let self = this;
        searchJudgeStandardList(deviceOfScenesId)
          .then(res => {
            if (!res) {
              return
            }
            self.judgeStandardList = res;
          })
        return self
      },

      /**
       * 格式化tag名称
       */
      formatTagName(row, column, cellValue, index) {
        return row.tagName + (row.tagValue ? row.tagValue : '')
      },

      /**
       * 打开添加规则弹窗
       */
      judgeStandardAdd(index, row) {
        let self = this;
        self.judgeStandardFormVisible = true
        self.deviceOfScenesId = row.deviceOfScenesId
        self.judgeStandardForm.deviceOfScenesId = self.deviceOfScenesId
      },

      /**
       * 规则修改
       */
      judgeStandardEdit(index, row) {
        let self = this;
        let judgeStandardForm = self.judgeStandardForm;
        for (let prop in judgeStandardForm) {
          judgeStandardForm[prop] = row[prop]
        }

        self.judgeStandardFormVisible = true
      },

      /**
       * 保存规则
       */
      saveJudgeStandard() {
        let self = this;
        this.$refs['judgeStandardForm'].validate(async (valid) => {
          if (!valid) {
            return
          }

          saveJudgeStandard(self.judgeStandardForm)
            .then(res => {
              if (!res) {
                return
              }
              self.judgeStandardFormClose()
                .searchJudgeStandardList(self.deviceOfScenesId)
            })
        })

      },

      /**
       * 标签下拉改变
       */
      tagChange(tagId) {
        let self = this;
        let typeTags = self.typeTags;
        for (let element of typeTags) {
          if (element.id === tagId) {
            self.judgeStandardForm.tagName = element.tagName
            self.judgeStandardForm.tagValue = element.tagValue
            return
          }
        }
      },

      deviceChange(deviceName) {
        let self = this;
        for (let device of self.defalutDeviceList) {
          if (device.deviceName === deviceName) {
            self.deviceForm.deviceCode = device.deviceCode
          }
        }
      },

      /**
       * 规则弹窗关闭时清空
       */
      judgeStandardFormClose() {
        let self = this;

        self.$refs['judgeStandardForm'].resetFields()

        self.judgeStandardForm.deviceOfScenesId = self.deviceOfScenesId
        self.judgeStandardForm.judgeStandardId = ''
        self.judgeStandardForm.tagName = ''
        self.judgeStandardForm.ruler = ''
        self.judgeStandardForm.score = ''
        self.judgeStandardForm.tagId = ''

        return self
      },

      /**
       * 规则删除
       */
      judgeStandardDelete(index, row) {
        let self = this;
        deleteJudgeStandard(row.judgeStandardId)
          .then(res => {
            if (!res) {
              return;
            }
            this.searchJudgeStandardList(self.deviceOfScenesId)
          })
      },

      goBack() {
        this.$router.go(-1);
      }
    }
  }
</script>

<style scoped>
  .form_style {
    height: 100%;
    position: relative;
    width: 400px;
    float: left;
    margin-bottom: 30px;
  }

  .table_style{
    margin-left:420px;
    margin-bottom: 25px;
    overflow-y:auto;
  }
</style>
