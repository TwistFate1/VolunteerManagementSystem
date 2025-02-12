<script setup>
  import { getAllvolunteers } from '../../../api/getData';
  import { getStorage } from '../../../utils/localStorage';
</script>

<script>
  export default {
    data () {
      return {
        loading: true,
        page: 1,
        limit: 0,
				volunteers: [],
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
        getAllvolunteers(params).then((res) => {
          this.limit = res.result.total;
          params.limit = res.result.total;
          getAllvolunteers(params).then((res) => {
            this.volunteers = res.result.volunteerList;
          });
        });
        setTimeout(() => {
          this.loading = false;
        }, 1000);
      },
      handleClick (row) {
        this.row = row;
        console.log(row)
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
        var csvContent = '账号,密码,姓名,民族,性别,地区,电话,身份证号,邮箱\n'; // 示例 CSV 格式

        function escapeCSV(value) {
          if (value === null || value === undefined) return '';
          // 转义双引号，并用双引号包围字段值
          return `"${value.toString().replace(/"/g, '""')}"`;
        }

        this.multipleSelection.forEach(row => {
          // 确保每个字段都被转义，并且将换行符替换为 CSV 格式
          csvContent += [
            escapeCSV(row.volunteerId),
            escapeCSV(row.password),
            escapeCSV(row.name),
            escapeCSV(row.nation),
            escapeCSV(row.gender),
            escapeCSV(row.area),
            escapeCSV(row.phone),
            escapeCSV(row.identity),
            escapeCSV(row.phone),
            escapeCSV(row.email)
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
          <div class="label">
            <el-tag>导出内容：</el-tag>
            <el-tag type="success">所选志愿者的个人信息</el-tag>
            <el-tag type="info">注意</el-tag>
            <el-tag type="warning">数据包含账号密码和个人隐私信息</el-tag>
            <el-tag type="danger">造成泄露将追究法律责任</el-tag>
            <el-button type="primary" @click="exportData">导出选中数据</el-button>
          </div>
        </el-card>
      </div>
      <el-card>
        <el-table size="medium" :stripe="true" height="400px" @selection-change="handleSelectionChange" v-loading="loading" :data="volunteers.filter(data => !this.search || data.name.includes(this.search))">
          <el-table-column align="center" type="selection" width="50"></el-table-column>
          <el-table-column align="center" prop="volunteerId" label="志愿者ID"></el-table-column>
          <el-table-column align="center" prop="name" label="姓名"></el-table-column>
          <el-table-column align="center" prop="nation" label="民族"></el-table-column>
          <el-table-column align="center" prop="gender" label="性别" :formatter="genderFormatter"></el-table-column>
          <el-table-column align="center" prop="area" label="地区"></el-table-column>
          <el-table-column align="center" prop="phone" label="电话"></el-table-column>
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
        <el-form-item label="账号" prop="id">
          <el-input v-model="row.volunteerId"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="row.password"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="row.name"></el-input>
        </el-form-item>
        <el-form-item label="民族" prop="nation">
          <el-input v-model="row.nation"></el-input>
        </el-form-item>
        <el-form-item label="地区" prop="area">
          <el-input v-model="row.area"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="row.phone"></el-input>
        </el-form-item>
        <el-form-item label="身份证号" prop="identity">
          <el-input v-model="row.identity"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="row.email"></el-input>
        </el-form-item>
      </el-form>
      <!-- TODO -->
      <!-- <span slot="footer">
        <el-button type="primary">导出 EXCEL</el-button>
      </span> -->
    </el-dialog>
  </div>
</template>

<style>
.label {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-around;
}
</style>