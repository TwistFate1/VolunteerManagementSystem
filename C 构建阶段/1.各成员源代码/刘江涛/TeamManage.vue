<script setup>
  import { getAllTeams } from '../../../api/getData';
  import { getVolunteerDetailByTeamId } from '../../../api/getData';
  import { getStorage } from '../../../utils/localStorage';
</script>

<script>
  export default {
    data () {
      return {
        loading: true,
        page: 1,
        limit: 0,
				teams: [],
        form: [],
        name: '',
        search: '',
        row: {

        },
        detailVisible: false,
        multipleSelection: []
      }
    },
    created () {
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
      getTableData () {
        var params = {
          page: this.page,
          limit: this.limit,
          name: this.name
        };
        getAllTeams(params).then((res) => {
          this.limit = res.result.total;
          params.limit = res.result.total;
          getAllTeams(params).then((res) => {
            this.teams = res.result.teamList;
          });
        });
        setTimeout(() => {
          this.loading = false;
        }, 1000);
      },
      tableRowClassName ({row, rowIndex}) {
        if (row.max == row.memberCount) {
          return 'success-row';
        } else if (row.memberCount === 0) {
          return 'fail-row';
        } else if (row.memberCount < 0.5 * row.max){
          return 'warning-row'
        }
        return '';
      },
      handleClick (row) {
        this.row = row;
        var params = {
          teamId: row.teamId
        };
        getVolunteerDetailByTeamId(params).then((res) => {
          this.form = res.result;
          console.log(this.form);
        })
        this.detailVisible = true;
      },
      handleClose () {
        this.detailVisible = false;
      },
      genderFormatter(row, column, cellValue) {
        return cellValue === 1 ? '男' : '女';
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      exportData() {
        if (this.multipleSelection.length === 0) {
          alert('请先选择要导出的数据!');
          return;
        }
        // 导出逻辑，例如导出为 CSV
        var csvContent = '团队ID,名称,团队负责人,联系方式,团队人数,人数上限\n'; // 示例 CSV 格式

        function escapeCSV(value) {
          if (value === null || value === undefined) return '';
          // 转义双引号，并用双引号包围字段值
          return `"${value.toString().replace(/"/g, '""')}"`;
        }

        this.multipleSelection.forEach(row => {
          // 确保每个字段都被转义，并且将换行符替换为 CSV 格式
          csvContent += [
            escapeCSV(row.teamId),
            escapeCSV(row.name),
            escapeCSV(row.leader),
            escapeCSV(row.contact),
            escapeCSV(row.memberCount),
            escapeCSV(row.max)
          ].join(',') + '\n';
        });

        // 解决乱码
        csvContent = "\ufeff" + csvContent;

        const blob = new Blob([csvContent], { type: 'text/csv;charset=UTF-8;' });
        const link = document.createElement('a');
        const url = URL.createObjectURL(blob);
        link.setAttribute('href', url);
        link.setAttribute('download', 'exported_data.csv');
        link.style.visibility = 'hidden';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      }
    }
  }
</script>

<template>
  <div>
    <el-card style="padding: 0%">
      <div slot="header">
        <el-card>
          <div class="tag">
            <el-tag>状态情况：</el-tag>
            <el-tag type="success">已满员</el-tag>
            <el-tag type="info">活跃正常</el-tag>
            <el-tag type="warning">未满员(少于50%)</el-tag>
            <el-tag type="danger">无人加入</el-tag>
            <el-button type="primary" @click="exportData">导出选中数据</el-button>
          </div>
        </el-card>
      </div>
      <el-card>
        <el-table size="medium" height="400px" @selection-change="handleSelectionChange" v-loading="loading" :data="teams.filter(data => !this.search || data.name.includes(this.search))" :row-class-name="tableRowClassName">
          <el-table-column align="center" type="selection" width="50"></el-table-column>
          <el-table-column align="center" prop="teamId" label="团队ID"></el-table-column>
          <el-table-column align="center" prop="name" label="名称"></el-table-column>
          <el-table-column align="center" prop="leader" label="团队负责人"></el-table-column>
          <el-table-column align="center" prop="contact" label="联系方式"></el-table-column>
          <el-table-column align="center" prop="memberCount" label="团队人数"></el-table-column>
          <el-table-column align="center" prop="max" label="人数上限"></el-table-column>
          <el-table-column align="center" fixed="right" label="操作">
            <template slot="header" slot-scope="scope">
              <el-input v-model="search" size="mini" placeholder="输入名称搜索"/>
            </template>
            <template slot-scope="scope"> 
              <el-button @click="handleClick(scope.row)" type="text" size="small">查看详情</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </el-card>
    <el-dialog top="50px" width="1260px" title="详情信息" :modal-append-to-body="false" :append-to-body="true" :lock-scroll="false" :visible.sync="detailVisible" :before-close="handleClose">
      <el-form label-position="right" label-width="auto" :model="row" :disabled="true">
        <el-form-item label="团队名称">
          <el-input v-model="row.name"></el-input>
        </el-form-item>
        <el-form-item label="团队介绍">
          <el-input type="textarea" v-model="row.introduction"></el-input>
        </el-form-item>
      </el-form>
      <el-divider></el-divider>
      <el-table max-height="300px" :stripe="true" title="团队成员" :data="form">
        <el-table-column align="center" prop="volunteerId" label="志愿者ID"></el-table-column>
        <el-table-column align="center" prop="name" label="姓名"></el-table-column>
        <el-table-column align="center" prop="nation" label="民族"></el-table-column>
        <el-table-column align="center" prop="gender" label="性别" :formatter="genderFormatter"></el-table-column>
        <el-table-column align="center" prop="area" label="地区"></el-table-column>
        <el-table-column align="center" prop="phone" label="电话"></el-table-column>
      </el-table>
      <!-- TODO -->
      <!-- <span slot="footer">
        <el-button type="primary">导出 CSV</el-button>
      </span> -->
    </el-dialog>
  </div>
</template>

<style>
.tag {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-around;
  gap: 10px;
}

.el-table .fail-row {
  background: #f9eceb;
}

.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}
</style>