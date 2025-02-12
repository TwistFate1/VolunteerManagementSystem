<script setup>
  import { getAllHeadline, getNewsAudit } from '../../../api/getData';
  import { updateNewsHeadline } from '../../../api/getData';
  import { updateNewsState } from '../../../api/getData';
  import { getStorage } from '../../../utils/localStorage';
</script>

<script>
  export default {
    data() {
      return {
        root: false,
        loading: true,
        page: 1,
        limit: 0,
        active: 0,
        activeName: 'first',
        search: '',
        row: {

        },
        dataHead: [],
        dataNot: [],
        detailVisible: false
      };
    },
    created() {
      this.checkLoginStatus();
      this.getTableData();
		},
		methods: {
			checkLoginStatus() {
				const isLoggedIn = getStorage('adminId') != null;
        if (getStorage('adminId') == 'root') {
          this.root = true;
        }
				if (!isLoggedIn) {
					alert('您还没有登录，请先登录！');
					this.$router.push('/site/login');
				}
			},
      refuse () {
        this.$alert(this.row.title, '是否驳回新闻', {
          lockScroll: false,
          confirmButtonText: '确定',
          callback: action => {
            if (action == 'confirm') { // 只有当用户点击确定时执行
              this.active = 2;
              var params = {
                newsId: this.row.newsId,
                state: 2
              };
              updateNewsState(params).then((res) => {
                if (res.result.code == 0) {
                  this.$notify.error({
                    title: '失败',
                    message: '驳回失败',
                  });
                } else {
                  this.$notify({
                    title: '成功',
                    message: '驳回成功',
                    type: 'success'
                  });
                  // 无需请求 列表内容修改
                  const index = this.dataNot.indexOf(this.row);
                  if (index > -1) {
                    this.dataNot[index].state = 1;
                  }
                }
              })
            }
          }
        });
      },
      accept () {
        this.$alert(this.row.title, '是否通过新闻', {
          lockScroll: false,
          confirmButtonText: '确定',
          callback: action => {
            if (action == 'confirm') { // 只有当用户点击确定时执行
              this.active = 3;
              var params = {
                newsId: this.row.newsId,
                state: 1
              };
              updateNewsState(params).then((res) => {
                if (res.result.code == 0) {
                  this.$notify.error({
                    title: '失败',
                    message: '通过失败',
                  });
                } else {
                  this.$notify({
                    title: '成功',
                    message: '通过成功',
                    type: 'success'
                  });
                  // 无需请求 列表内容修改
                  const index = this.dataNot.indexOf(this.row);
                  console.log(this.row);
                  console.log(index)
                  if (index > -1) {
                    this.dataNot[index].state = 2;
                  }
                }
              })
            }
          }
        });
      },
      getTableData () {
        var params = {
          page: this.page,
          limit: this.limit
        };
        getAllHeadline(params).then((res) => {
          this.limit = res.result.total;
          params.limit = res.result.total;
          getAllHeadline(params).then((res) => {
            this.dataHead = res.result.newsList;
          })
        });
        getNewsAudit(params).then((res) => {
          this.dataNot = res.result;
        });
        setTimeout(() => {
          this.loading = false;
        }, 1000);
      },
      tableRowClassName ({row, rowIndex}) {
        if (row.state == 0) {
          return 'wait-row';
        } else if (row.state == 1) {
          return 'pass-row';
        } else if (row.state == 2){
          return 'audit-row'
        }
        return '';
      },
      handleClick (row) {
        this.row = row;
        this.active = row.state;
        this.detailVisible = true;
      },
      handleClose () {
        this.detailVisible = false;
      },
      handleAdd (row) {
        this.$alert(row.title, '是否添加头条', {
          lockScroll: false,
          confirmButtonText: '确定',
          callback: action => {
            if (action == 'confirm') { // 只有当用户点击确定时执行
              var params = {
                newsId: row.newsId
              };
              updateNewsHeadline(params).then((res) => {
                if (res.result.code == 0) {
                  this.$notify.error({
                    title: '失败',
                    message: '添加失败',
                  });
                } else {
                  this.$notify({
                    title: '成功',
                    message: '添加成功',
                    type: 'success'
                  });
                  // 无需请求 列表之间移动
                  const index = this.dataNot.indexOf(row);
                  if (index > -1) {
                    this.dataNot.splice(index, 1);
                  }
                  this.dataHead.push(row);
                }
              });
            }
          }
        });
      },
      handleMove (row) {
        this.$alert(row.title, '是否移除头条', {
          lockScroll: false,
          confirmButtonText: '确定',
          callback: action => {
            if (action == 'confirm') { // 只有当用户点击确定时执行
              var params = {
                newsId: row.newsId
              };
              updateNewsHeadline(params).then((res) => {
                if (res.result.code == 0) {
                  this.$notify.error({
                    title: '失败',
                    message: '移除失败',
                  });
                } else {
                  this.$notify({
                    title: '成功',
                    message: '移除成功',
                    type: 'success'
                  });
                  // 无需请求 列表之间移动
                  const index = this.dataHead.indexOf(row);
                  if (index > -1) {
                    this.dataHead.splice(index, 1);
                  }
                  this.dataNot.push(row);
                }
              });
            }
          }
        });
      },
      newsFormatter(row, column, cellValue) {
        return cellValue == 0 ? '待审核' : (cellValue == 1 ? '已通过' : '已驳回');
      }
    }
  }
</script>

<template>
  <div>
    <el-card>
      <div slot="header" style="padding: 0%">
        <el-card>
          <div class="tag">
            <el-tag>状态情况：</el-tag>
            <el-tag type="success">已通过</el-tag>
            <el-tag type="warning">待审核</el-tag>
            <el-tag type="danger">已驳回</el-tag>
          </div>
        </el-card>
      </div>
      <el-card>
        <el-tabs v-model="activeName">
          <el-tab-pane label="常规新闻" name="first">
            <el-table v-loading="loading" height="400px" :data="dataNot.filter(data => !this.search || data.title.includes(this.search))" :row-class-name="tableRowClassName">
              <el-table-column sortable align="center" prop="newsId" label="新闻ID"></el-table-column>
              <el-table-column sortable align="center" prop="newsTime" label="发布时间"></el-table-column>
              <el-table-column align="center" prop="title" label="标题" width="320px"></el-table-column>
              <el-table-column sortable align="center" prop="state" label="状态" :formatter="newsFormatter"></el-table-column>
              <el-table-column align="center" fixed="right" label="操作">
                <template slot="header" slot-scope="scope">
                  <el-input v-model="search" size="mini" placeholder="输入标题搜索"/>
                </template>
                <template slot-scope="scope"> 
                  <div v-if="root">
                    <el-button @click="handleAdd(scope.row)" type="text" size="small" :disabled="scope.row.state != 1">头条</el-button>
                  </div>
                  <el-button @click="handleClick(scope.row)" type="text" size="small" :disabled="scope.row.state == 1">审核</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
          <el-tab-pane label="头条新闻" name="second">
            <el-table v-loading="loading" height="400px" :data="dataHead.filter(data => !this.search || data.title.includes(this.search))" :stripe="true">
              <el-table-column sortable align="center" prop="newsId" label="新闻ID"></el-table-column>
              <el-table-column sortable align="center" prop="newsTime" label="发布时间"></el-table-column>
              <el-table-column align="center" prop="title" label="标题" width="320px"></el-table-column>
              <el-table-column align="center" prop="state" label="状态" :formatter="newsFormatter"></el-table-column>
              <el-table-column align="center" fixed="right" label="操作">
                <template slot="header" slot-scope="scope">
                  <el-input v-model="search" size="mini" placeholder="输入标题搜索"/>
                </template>
                <template slot-scope="scope"> 
                  <div v-if="root">
                    <el-button @click="handleMove(scope.row)" type="text" size="small">移除</el-button>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
        </el-tabs>
      </el-card>
      <el-dialog top="100px" width="1260px" title="详情信息" :modal-append-to-body="false" :append-to-body="true" :lock-scroll="false" :visible.sync="detailVisible" :before-close="handleClose">
        <el-form label-position="right" label-width="auto" :model="row" :disabled="true">
          <!-- TODO -->
          <span>具体信息</span>
        </el-form>
        <el-divider></el-divider>
        <el-steps :active="active" finish-status="success">
          <el-step title="待审核"></el-step>
          <el-step title="已驳回"></el-step>
          <el-step title="已通过"></el-step>
        </el-steps>
        <span slot="footer" style="display: flex;align-items: center;justify-content: center">
          <div v-if="active != 2">
            <el-button type="danger" @click="refuse">驳回</el-button>
          </div>
          <el-button type="primary" @click="accept">通过</el-button>
        </span>
      </el-dialog>
    </el-card>
  </div>
</template>

<style>
.el-table .audit-row {
  background: #f9eceb;
}

.el-table .wait-row {
  background: oldlace;
}

.el-table .pass-row {
  background: #f0f9eb;
}
</style>