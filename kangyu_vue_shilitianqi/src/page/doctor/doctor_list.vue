<template>
  <div class="container">
    <head-top></head-top>
    <div class="comm_container">
      <div class="list-query">
        <el-row type="flex" class="row-bg" justify="space-between">
          <el-col :span="3">
            <el-input v-model="queryData.name" placeholder="姓名"></el-input>
          </el-col>
          <el-col :span="3">
            <el-select v-model="queryData.sex" placeholder="性别" style="width:100%;">
              <el-option v-for="item in searchConditionOptions" :key="item.value" :label="item.label"
                         :value="item.value">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="3">
            <el-input v-model="queryData.age" placeholder="年龄"></el-input>
          </el-col>
          <el-col :span="3">
            <el-input v-model="queryData.phone" placeholder="手机号"></el-input>
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
          <el-table-column sortable label="序号" type="index"></el-table-column>
          <el-table-column sortable label="姓名" prop="name"></el-table-column>
          <el-table-column sortable label="性别" prop="sex"></el-table-column>
          <el-table-column sortable label="年龄" prop="age"></el-table-column>
          <el-table-column sortable label="手机号" prop="phone"></el-table-column>
          <el-table-column sortable label="职称" prop="zhicheng"></el-table-column>
          <el-table-column sortable label="级别" prop="level">
            <template slot-scope="scope">
              <span v-if="scope.row.level === '1'">医生</span>
              <span v-else>主任</span>
            </template>
          </el-table-column>
          <el-table-column sortable label="擅长领域" prop="scly"></el-table-column>
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
    </div>
    <el-dialog :show.sync="handleEditProp.dialogFormVisible" :title="handleEditProp.title" :data="handleEditProp.data"
               v-on:listenToChildEvent="showMessageFormChild"></el-dialog>
  </div>
</template>
<script>
  import headTop from '@/components/headTop'
  import elDialog from './doctor_edit'
  import {fetchDoctorList, fetchDoctorDelete} from '@/api/doctor'

  export default {
    data() {
      return {
        rownumber: 1,
        tableData: [],
        queryData: {
          name: '',
          sex: '',
          age: '',
          phone: '',
          pageSize: 15,
          pageNum: 1
        },
        searchConditionOptions: [
          {label: '全部', value: '0'},
          {label: '男', value: '男'},
          {label: '女', value: '女'}
        ],
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
       * 初始化数据
       * @returns {Promise<void>}
       */
      async initData() {
        const self = this;
        fetchDoctorList(self.queryData)
          .then(res => {
            self.tableData = res.list
            self.queryData.total = res.page.total;
            self.queryData.pageNum = res.page.pageNum;
          })
          .catch(() => {
            self.$message('获取数据失败');
          })
      },
      /**
       * 删除
       * @param index 行
       * @param row 数据
       */
      handleDelete(index, row) {
        const self = this;
        try {
          self.$confirm('是否删除?', '提示', {type: 'warning'})
            .then(() => {
              fetchDoctorDelete(row.id)
                .then(function (res) {
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
        } catch (err) {
          this.$message.error(err.message);
        }
      },
      /**
       * 新境
       */
      handleAdd() {
        this.handleEditProp.dialogFormVisible = true;
        this.handleEditProp.title = '新建医生';
      },
      /**
       * 修改
       * @param index 行
       * @param row 数据
       */
      handleEdit(index, row) {
        this.handleEditProp.dialogFormVisible = true;
        this.handleEditProp.title = '编辑医生';
        this.handleEditProp.data.id = row.id;
      },
      /**
       * 弹窗关闭事件
       */
      showMessageFormChild() {
        this.handleEditProp.dialogFormVisible = false;
        this.initData();
      },
      /**
       * 分页插件每页大小改变
       * @param val 每页条数
       */
      handleSizeChange(val) {
        const self = this;
        self.queryData.page.pageSize = val;
        self.initData();
      },
      /**
       * 分页插件当前页改变事件
       * @param val 页码
       */
      handleCurrentChange(val) {
        const self = this;
        self.queryData.page.pageNum = val;
        self.initData();
      }
    },
    computed: {
      /**
       * 表格高度
       * @returns {number}
       */
      tableHeight: () => {
        return document.body.clientHeight - 240
      }
    }
  }
</script>
