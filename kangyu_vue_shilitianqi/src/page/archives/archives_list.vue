<template>
  <div class="container">
    <head-top></head-top>
    <div class="comm_container">
      <div class="list-query">
        <el-form :model="queryData">
          <el-row type="flex" class="row-bg" justify="space-between">
            <el-col :span="5">
              <el-form-item label="卡号" label-width="60px">
                <el-input v-model="queryData.cardId" placeholder="卡号"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="姓名" label-width="80px">
                <el-input v-model="queryData.name" placeholder="姓名"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="性别" label-width="80px">
                <el-select v-model="queryData.sex" placeholder="请选择" style="width:100%">
                  <el-option label="请选择" value=""></el-option>
                  <el-option label="男" value="男"></el-option>
                  <el-option label="女" value="女"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <el-button type="primary" size="medium" @click="initData">查询</el-button>
              <el-button type="primary" size="medium" @click="handleAdd">创建</el-button>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <div class="list-table">
        <el-table v-loading="loading" :height="table_height" :data="tableData" border style="width: 100%">
          <el-table-column label="序号" type="index" width="70"></el-table-column>
          <el-table-column label="卡号" prop="cardId"></el-table-column>
          <el-table-column label="姓名" prop="name"></el-table-column>
          <el-table-column label="性别" prop="sex"></el-table-column>
          <el-table-column label="出生日期" prop="birthday"></el-table-column>
          <el-table-column label="操作" width="250">
            <template slot-scope="scope">
              <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
              <el-button size="small" type="danger" @click="searchreserv(scope.$index, scope.row)">删除</el-button>
              <el-button @click="createBarcode(scope.row)">打印条码</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :page-sizes="[15, 20,50, 100]"
          :page-size="queryData.page.pageSize"
          layout="total, sizes, prev, pager, next"
          :total="queryData.page.total">
        </el-pagination>
      </div>
    </div>
    <!--打印区域-->
    <div v-show="ifprint" id="print"
         style="width:188px;height:150px;line-height:130px;text-align:center;font-size:10px;margin-left:0">
      <div>
        <div style="width:100%;">
          <svg id="svg" style="width:100%;" height="100px"></svg>
        </div>
        <div style="height:18px;line-height:18px;overflow:hidden;padding:0 10px;margin-top:10px">
          <span id="name" style="display:block;float:left;width:63px;text-align:left;font-size:10px;"></span>
          <span id="sex" style="display:block;float:right;width:24px;text-align:right;font-size:10px;"></span>
          <span id="birthday"
                style="display:block;margin-left:66px;width:85px;text-align:center;font-size:10px;"></span>
        </div>
        <div style="height:18px;line-height:18px;padding:0 10px;text-align:center;font-size:12px; margin-top:5px;">
          <!-- 康宇医疗孕期检测 -->
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import headTop from '../../components/headTop'
  import {doctor_fanout, getreservuserbyguid, achivesDelete, achivesSearch} from '@/api/archives'
  import jsbarcode from 'jsbarcode';

  export default {
    data() {
      return {
        rownumber: 1,
        ifprint: false,
        printData: {},
        tableHeight: '',
        tableData: [],
        queryData: {
          cardId: '',
          name: '',
          sex: '',
          page: {
            pageSize: 15,
            pageNum: 1
          }
        },
        loading: true,
        table_height: ''
      }
    },
    components: {
      headTop
    },
    created() {
      this.table_height = parseFloat(window.innerHeight - 250);
    },
    mounted() {
      this.initData();
    },
    methods: {
      async initData() {
        const self = this;
        try {
          const data = await achivesSearch(this.queryData);
          this.tableData = data.data;
          self.queryData.page = data.page;
          let pageSize = self.queryData.page.pageSize;
          let pageNum = self.queryData.page.pageNum;
          let rownumber = (pageNum - 1) * pageSize + 1;
          self.rownumber = rownumber;
          this.loading = false;
        } catch (err) {
          this.loading = false;
          this.$message.error('获取数据失败');
        }
      },

      getBirthDate(val) {
        this.queryData.birthdate = val;
      },

      // 判断用户是否预约
      searchreserv(index, row) {
        const self = this;
        const searchReserv = getreservuserbyguid(row.guid)
        searchReserv.then(function (res) {
          var msg = "";
          if (res.length > 0) {
            msg = '该用户已预约就诊，删除后将清空该用户所有数据，是否删除';
          } else {
            msg = '是否确认删除'
          }
          self.handleDelete(row.guid, msg)
        })
      },
      //删除
      handleDelete(guid, msg) {
        const self = this;
        try {
          self.$confirm(msg, '提示', {
            confirmButtonText: '是',
            cancelButtonText: '否',
            type: 'warning'
          }).then(() => {
            const obj = {};
            obj.guid = guid;
            const promise = records_delete(obj);
            promise.then(function (res) {
              if (res.flag == 'success') {
                self.$message({
                  type: 'success',
                  message: '删除成功'
                });
                self.initData();
                doctor_fanout(obj)
              } else {
                throw new Error(res.message)
              }
            });
          }).catch(() => {
            self.$message({
              type: 'info',
              message: '已取消删除'
            });
          });
        } catch (err) {
          this.$message({
            type: 'error',
            message: err.message
          });
          console.log('删除失败')
        }
      },
      //新增
      handleAdd() {
        var operateType = {
          flag: 'add'
        }
        this.$myStore.setStore('operateType', operateType);
        this.$myStore.setStore('tabname', {tabName: 'archives_info'});
        this.$router.push({path: '/archives_card/archives_info'})
      },
      //编辑
      handleEdit(index, row) {
        var operateType = {
          flag: 'edit',
          data: {
            idtype: 'guid',
            id: row.guid
          }
        }
        this.$myStore.setStore('operateType', operateType);
        this.$myStore.setStore('tabname', {tabName: 'archives_info'});
        this.$router.push({path: '/archives_card/archives_info'})
      },
      //打印
      print() {
        let printContent = document.getElementById('print');
        let newContent = printContent.innerHTML;
        let oldContent = document.body.innerHTML;
        document.body.innerHTML = newContent;
        window.print();
        window.location.reload();
        document.body.innerHTML = oldContent;
        this.ifprint = false;
        return false;
      },
      //生成条形码
      createBarcode(row) {
        var that = this;
        var code = row.cardId;
        that.printData = row;
        that.ifprint = true;
        //动态数据打印不出来，先用原始方法赋值
        document.getElementById("name").innerText = row.name
        document.getElementById("sex").innerText = row.sex
        document.getElementById("birthday").innerText = row.birthday
        jsbarcode('#svg', code, {
          lineColor: '#000',
          width: 1.5,
          height: 36,
          margin: 5,
          fontSize: 14,
          displayValue: true
        })
        that.print();
      },
      handleSizeChange(val) {
        const self = this;
        self.queryData.page.pageSize = val;
        self.initData();
      },
      handleCurrentChange(val) {
        const self = this;
        self.queryData.page.pageNum = val;
        self.initData();
      },

    },
  }
</script>
<style></style>
