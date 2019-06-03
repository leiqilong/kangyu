<template>
  <div>
    <head-top></head-top>
    <div class="comm_container">
      <el-form :inline="true" :model="queryData" label-width="100px" class="demo-form-inline">
        <el-form-item label="标签名称" size="queryData">
          <el-input v-model="queryData.tagName" placeholder="标签名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="initData">查询</el-button>
          <el-button type="primary" @click="handleAdd">创建</el-button>
        </el-form-item>
      </el-form>

      <el-table v-loading="loading" :data="tableData" :height="tableHeight">
        <el-table-column label="序号" type="index"></el-table-column>
        <el-table-column sortable label="标签名称" prop="tagName"></el-table-column>
        <el-table-column sortable label="标签代码" prop="tagCode"></el-table-column>
        <el-table-column label="对应表单" prop="correspondingFormNames"></el-table-column>
        <el-table-column label="附加标签" prop="additionalTags"></el-table-column>
        <el-table-column label="标签类别" prop="tagType" :formatter="formatTagType"></el-table-column>
        <el-table-column label="操作" width="180">
          <template slot-scope="scope">
            <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :page-sizes="[15, 20, 50, 100]"
        :page-size="queryData.pageSize"
        layout="total, sizes, prev, pager, next"
        :total="queryData.total">
      </el-pagination>
    </div>
    <el-dialog :show.sync="handleEditProp.dialogFormVisible" :title="handleEditProp.title" :data="handleEditProp.data"
               @listenToChildEvent="showMessageFormChild"></el-dialog>
  </div>
</template>

<script>
  import elDialog from './custom_form_tag_dialog'
  import headTop from '@/components/headTop'
  import {getCustomeFormTagList, deleteCustomFormTagById} from '@/api/tag_warehouse.js'
  import {tagTypeList} from './js/tag_list_data.js'

  export default {
    name: 'customFormTag',
    data() {
      return {
        tableData: [],
        queryData: {
          tagName: '',
          pageSize: 15,
          pageNum: 1
        },
        tagTypeList: tagTypeList,
        handleEditProp: {
          dialogFormVisible: false,
          title: '',
          data: {}
        },
        loading: false
      }
    },
    components: {
      headTop,
      'el-dialog': elDialog
    },
    mounted() {
      this.initData();
    },
    methods: {
      /**
       * 查询
       */
      initData() {
        let self = this;
        getCustomeFormTagList(self.queryData)
          .then(res => {
            if (!res) {
              return;
            }

            this.tableData = res.list;
            this.queryData.total = res.page.total;
          })
      },

      /**
       * 新增
       */
      handleAdd() {
        this.handleEditProp.dialogFormVisible = true;
        this.handleEditProp.title = '新增标签';
        this.handleEditProp.data = {};
      },

      /**
       * 编辑
       */
      handleEdit(index, row) {
        this.handleEditProp.dialogFormVisible = true;
        this.handleEditProp.title = '编辑标签';
        this.handleEditProp.data = row;
      },

      /**
       * 删除
       */
      handleDelete(index, row) {
        const self = this
        self.$confirm('是否删除?', '提示', {type: 'warning'})
          .then(() => {
            deleteCustomFormTagById(row.id)
              .then(res => {
                if (!res) {
                  return
                }
                self.$message({
                  type: 'success',
                  message: '删除成功'
                })
                self.initData()
              })
          })
          .catch(() => {
            self.$message('已取消删除')
          })
      },

      /**
       * 分页插件每页大小改变
       */
      handleSizeChange() {

      },

      /**
       * 分页插件当前页改变
       */
      handleCurrentChange() {

      },

      /**
       * 弹窗关闭
       */
      showMessageFormChild() {
        this.handleEditProp.dialogFormVisible = false;
        this.handleEditProp.title = '';
        this.handleEditProp.data = null;
        this.initData();
      },

      formatTagType(row, column, cellValue, index) {
        console.log('cellValue', cellValue);

        let tagTypeList = this.tagTypeList;
        for (let type of tagTypeList) {
          console.log('type', type);
          if (type.value == cellValue) {
            console.log('type.label', type.label);
            return type.label
          }
        }
      }
    },
    computed: {
      /**
       * 表格高度
       * @returns {number}
       */
      tableHeight: () => {
        return document.body.clientHeight - 235
      }
    }
  }
</script>

<style scoped>

</style>
