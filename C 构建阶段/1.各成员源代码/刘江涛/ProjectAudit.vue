<script setup>
  import { getAllActivity, updateActivityState } from '../../../api/getData';
  import { getStorage } from '../../../utils/localStorage';
</script>

<script>
  export default {
    data() {
      return {
        admin: {},
        loading: true,
        page: 1,
        limit: 0,
        active: 0,
        activeName: 'first',
        search: '',
        row: {

        },
        dataAct: [],
        detailVisible: false
      };
    },
    created() {
      var userData = getStorage("admin");
      this.admin = userData ? JSON.parse(userData) : {};
      if (this.admin == {}) {
        this.$router.push('/site/login');
      }
      this.getTableData();
      this.checkLoginStatus();
		},
		methods: {
			checkLoginStatus() {
				const isLoggedIn = getStorage('adminId') != null;

				if (!isLoggedIn) {
					alert('您还没有登录，请先登录！');
					this.$router.push('/site/login');
				}
			},
      refuse () {
        this.$alert(this.row.title, '是否驳回项目', {
          lockScroll: false,
          confirmButtonText: '确定',
          callback: action => {
            if (action == 'confirm') { // 只有当用户点击确定时执行
              this.active = 2;
              var params = {
                activityId: this.row.activityId,
                state: 2
              };
              updateActivityState(params).then((res) => {
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
                  const index = this.dataAct.indexOf(this.row);
                  if (index > -1) {
                    this.dataAct[index].state = 2;
                  }
                }
              })
            }
          }
        });
      },
      accept () {
        this.$alert(this.row.title, '是否通过项目', {
          lockScroll: false,
          confirmButtonText: '确定',
          callback: action => {
            if (action == 'confirm') { // 只有当用户点击确定时执行
              this.active = 3;
              var params = {
                activityId: this.row.activityId,
                state: 1
              };
              updateActivityState(params).then((res) => {
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
                  const index = this.dataAct.indexOf(this.row);
                  console.log(this.row);
                  console.log(index)
                  if (index > -1) {
                    this.dataAct[index].state = 2;
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
        }
        getAllActivity(params).then((res) => {
          this.dataAct = res.result;
        });
        setTimeout(() => {
          this.loading = false;
        }, 1000);
      },
      tableRowClassName ({row, rowIndex}) {
        if (row.state == 0) {
          return 'pro-wait-row';
        } else if (row.state == 1) {
          return 'pro-pass-row';
        } else if (row.state == 2){
          return 'pro-audit-row'
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
      stateFormatter(row, column, cellValue) {
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
        <el-table v-loading="loading" height="400px" :data="dataAct.filter(data => !this.search || data.name.includes(this.search))" :row-class-name="tableRowClassName">
          <el-table-column sortable align="center" prop="activityId" label="项目ID"></el-table-column>
          <el-table-column align="center" width="200px" prop="name" label="名称"></el-table-column>
          <el-table-column sortable align="center" prop="postDate" label="发布时间"></el-table-column>
          <el-table-column align="center" prop="sort" label="类别"></el-table-column>
          <el-table-column align="center" prop="numberVolunteers" label="人数"></el-table-column>
          <el-table-column sortable align="center" prop="state" label="状态" :formatter="stateFormatter"></el-table-column>
          <el-table-column align="center" fixed="right" label="操作">
            <template slot="header" slot-scope="scope">
              <el-input v-model="search" size="mini" placeholder="输入名称搜索"/>
            </template>
            <template slot-scope="scope"> 
              <el-button @click="handleClick(scope.row)" type="text" size="small" :disabled="scope.row.state == 1">审核</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
      <el-dialog top="100px" width="1260px" title="详情信息" :modal-append-to-body="false" :append-to-body="true" :lock-scroll="false" :visible.sync="detailVisible" :before-close="handleClose">
        <el-form label-position="right" label-width="auto" :model="row" :disabled="true">
          <el-form-item label="项目内容">
            <el-input v-model="row.content"></el-input>
          </el-form-item>
          <el-form-item label="活动地区">
            <el-input v-model="row.location"></el-input>
          </el-form-item>
          <el-form-item label="开始时间">
            <el-input v-model="row.startDate"></el-input>
          </el-form-item>
          <el-form-item label="结束时间">
            <el-input v-model="row.endDate"></el-input>
          </el-form-item>
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
.el-table .pro-audit-row {
  background: #f9eceb;
}

.el-table .pro-wait-row {
  background: oldlace;
}

.el-table .pro-pass-row {
  background: #f0f9eb;
}
</style>