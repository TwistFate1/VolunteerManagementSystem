<script setup>
  import { getStorage } from '../../utils/localStorage';
  import { getAdminById } from '../../api/getData';
</script>

<script>
  export default {
    data () {
      return {
        adminInfo: {

        },
        menuWidth: '200px',
        contentWidth: '1060px',
        key: 'admin/infoContent'
      }
    },
    created () {
      this.getInfo();
    },
    methods: {
      handleSelect (key, indexPath) {
        // 确保在跳转前在挂载点下，即 '/'
        this.key = key;
        this.$router.push('/');
      },
      getInfo () {
        const adminId = getStorage('adminId');
        getAdminById({
					adminId
				}).then((res) => {
					this.adminInfo = res.result;
				});
      }
    },
  };
</script>

<template>
  <div class="menu">
    <el-container>
      <el-aside :style="{ width:menuWidth }">
        <el-menu @select="handleSelect" :unique-opened="true" default-active="infoContent" :router="true">
          <el-submenu index="info">
            <template slot="title">
              <i class="el-icon-user-solid"></i>
            <span slot="title">我的信息</span>
            </template>
            <el-menu-item-group>
              <span slot="title">信息</span>
              <el-menu-item index="admin/infoContent">信息内容</el-menu-item>
              <el-menu-item index="admin/infoUpdate">信息修改</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="manage">
            <template slot="title">
              <i class="el-icon-s-management"></i>
              <span slot="title">内容管理</span>
            </template>
            <el-menu-item-group>
              <span slot="title">管理</span>
              <div v-if="this.adminInfo.status == 0">
                <el-menu-item index="admin/adminManage">管理员管理</el-menu-item>
              </div>
              <el-menu-item index="admin/volunteerManage">志愿者管理</el-menu-item>
              <el-menu-item index="admin/teamManage">志愿团队管理</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="audit">
            <template slot="title">
              <i class="el-icon-s-check"></i>
              <span slot="title">内容审核</span>
            </template>
            <el-menu-item-group>
              <span slot="title">审核</span>
              <el-menu-item index="admin/newsAudit">新闻审核</el-menu-item>
              <el-menu-item index="admin/projectAudit">项目审核</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <div v-if="this.adminInfo.status == 0">
            <el-submenu index="upload">
              <template slot="title">
                <i class="el-icon-s-check"></i>
                <span slot="title">导入视频</span>
              </template>
              <el-menu-item-group>
                <span slot="title">导入</span>
                <el-menu-item index="admin/uploadVideo">视频上传</el-menu-item>
              </el-menu-item-group>
            </el-submenu>
          </div>
        </el-menu>
      </el-aside>
      <el-main :style="{ width:contentWidth }" style="padding: 0%">
        <router-view style="width: 100%"></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<style scoped>
.menu {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  box-sizing: content-box;
}
.menu-card {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 0%;
}
</style>
