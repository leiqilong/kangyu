<template>
  <div>
    <div class="list-query">
      <el-row type="flex" class="row-bg" justify="space-between">
        <el-col :span="3">
          <el-input v-model="queryData.tagName" placeholder="标签名称"></el-input>
        </el-col>
        <el-col :span="8">
          <el-button type="primary" @click="initData">查询</el-button>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" @click="handleAdd">创建</el-button>
        </el-col>
      </el-row>
    </div>
    <div class="list-table">
      <el-table v-loading="loading" :data="tableData" :height="tableHeight">
        <el-table-column sortable label="标签名称" prop="tagName"></el-table-column>
        <el-table-column label="对应表单" prop="correspondingFormNames"></el-table-column>
        <el-table-column label="附加标签" prop="additionalInformation"></el-table-column>
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
  import {getCustomeFormTagList} from '@/api/tag_warehouse.js'

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
        handleEditProp: {
          dialogFormVisible: false,
          title: '',
          data: {}
        },
        loading: false
      }
    },
    components: {
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

      },
      /**
       * 删除
       */
      handleDelete(index, row) {
        console.log('id:', row.id);
      },
      /**
       * 分页插件大小改变
       */
      handleSizeChange() {

      },
      /**
       * 分页插件当前页改变
       */
      handleCurrentChange() {

      },
      showMessageFormChild() {
        this.handleEditProp.dialogFormVisible = false;
        this.handleEditProp.title = '';
        this.handleEditProp.data = {};
      }
    },
    computed: {
      /**
       * 表格高度
       * @returns {number}
       */
      tableHeight: () => {
        return document.body.clientHeight - 280
      }
    }
  }
</script>

<style scoped>

</style>
