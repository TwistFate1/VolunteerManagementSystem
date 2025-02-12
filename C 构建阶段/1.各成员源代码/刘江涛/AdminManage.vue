<script setup>
  import { getStorage } from '../../../utils/localStorage';
  import { getAllAdmin, insertAdmin } from '../../../api/getData';
</script>

<script>
  export default {
    data () {
      return {
        form: {
          adminId: null,
          password: null,
          status: 0
        },
        loading: false,
        admins: []
      }
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
      getTableData() {
        var params = {

        };
        getAllAdmin(params).then((res) => {
          this.admins = res.result;
        });
        setTimeout(() => {
          this.loading = false;
        }, 1000);
      },
      tableRowClassName ({row, rowIndex}) {
        if (row.status == 0) {
          return 'root';
        } else if (row.status == 1) {
          return 'common';
        }
        return '';
      },
      rootFormatter(row, column, cellValue) {
        return cellValue == 0 ? '根管理员' : '普通管理员';
      },
      // 自定义验证账号是否唯一
      validateAdminId() {
        return this.admins.every(admin => admin.adminId !== this.form.adminId);
      },
      // 自定义验证密码强度
      validatePassword() {
        const regex = /^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?]).{8,}$/;
        return regex.test(this.form.password);
      },
      // 自定义插入数据方法
      insert() {
        // 首先验证账号和密码
        if (!this.validateAdminId()) {
          alert('账号已存在，请使用其他账号');
          return;
        }
        if (!this.validatePassword()) {
          alert('密码不符合要求，必须包含数字、字母和特殊字符，且至少8位');
          return;
        }
        insertAdmin(this.form).then((res) => {
          if (res.code == 1) {
            // data 也插入
            this.admins.push(this.form);
            alert('数据插入成功');
          }
          else {
            alert('数据插入失败');
          }
        });
      }
    }
  }
</script>

<template>
  <div>
    <el-card>
      <div slot="header" style="padding: 0%">
        <el-card>
          <div class="form">
            <el-input v-model="form.adminId">
              <template slot="prepend">账号</template>
            </el-input>
            <el-input v-model="form.password">
              <template slot="prepend">密码</template>
            </el-input>
            <el-input v-model="form.status" :disabled="true">
              <template slot="prepend">是否根管理员</template>
            </el-input>
            <div>
              <el-radio-group style="display: flex;flex-direction:row" v-model="form.status">
                <el-radio-button label="0">是</el-radio-button>
                <el-radio-button label="1">否</el-radio-button>
              </el-radio-group>
            </div>
            <el-button @click="insert">插入</el-button>
          </div>
        </el-card>
      </div>
      <el-card>
        <el-table v-loading="loading" height="400px" :data="admins" :row-class-name="tableRowClassName">
          <el-table-column sortable align="center" prop="adminId" label="管理员账号"></el-table-column>
          <el-table-column align="center" prop="password" label="管理员密码"></el-table-column>
          <el-table-column sortable align="center" prop="status" label="状态" :formatter="rootFormatter"></el-table-column>
        </el-table>
      </el-card>
    </el-card>
  </div>
</template>

<style>
.form {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
}
.el-table .root {
  background: #f9eceb;
}

.el-table .common {
  background: oldlace;
}
</style>