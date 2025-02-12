<script setup>
  import { delStorage, getStorage } from '../utils/localStorage';
</script>

<script>
	export default {
		data() {
      return {
        code: getStorage("code"),
        flag: false,
        options: [{
          value: '选项1',
          label: '志愿团队'
        }, {
          value: '选项2',
          label: '志愿项目'
        },],
        value: '选项1',
        url: 'https://chinavolunteer.mca.gov.cn/site/static/img/header_bg.acdffe9.png',
        activeIndex: '/site/home',
        searchInput: null,
        cardWidth: '1260px',
      };
		},
    created() {
      // 组件创建后立即启动轮询
      this.startPolling();
    },
    beforeDestroy() {
      // 组件销毁前清除轮询定时器
      if (this.pollingInterval) {
        clearInterval(this.pollingInterval);
      }
    },
		methods: {
      startPolling() {
        // 设置轮询定时器
        this.pollingInterval = setInterval(() => {
          // 执行轮询逻辑，例如检查localStorage中的code值是否有变化
          const currentCode = getStorage("code");
          this.flag = this.code == 1 ? true : false;
          if (this.code !== currentCode) {
            // 如果code发生变化，更新Vue实例的响应式数据
            this.code = currentCode;
          }
        }, 100); // 每0.1秒检查一次
      },
      handleSelect (key, indexPath) {
        this.activeIndex = key;
      },
      login () {
        // 获取目标路径
        const targetPath = '/site/login';
        // 获取当前路由的路径
        const currentPath = this.$router.currentRoute.path;

        // 检查当前路径是否与目标路径相同
        if (currentPath !== targetPath) {
          // 如果不同，则跳转到目标路径
          this.$router.push(targetPath);
        }
      },
      register () {
        // 获取目标路径
        const targetPath = '/site/register';
        // 获取当前路由的路径
        const currentPath = this.$router.currentRoute.path;

        // 检查当前路径是否与目标路径相同
        if (currentPath !== targetPath) {
          // 如果不同，则跳转到目标路径
          this.$router.push(targetPath);
        }
      },
      user () {
        var admin = getStorage("adminId");
        console.log('test')
        if (admin != null) {
          // 获取目标路径
          const targetPath = '/site/admin';
          // 获取当前路由的路径
          const currentPath = this.$router.currentRoute.path;

          // 检查当前路径是否与目标路径相同
          if (currentPath !== targetPath) {
            // 如果不同，则跳转到目标路径
            this.$router.push(targetPath);
          }
        }
        var team = getStorage("teamId");
        if (team != null) {
          // 获取目标路径
          const targetPath = '/site/team';
          // 获取当前路由的路径
          const currentPath = this.$router.currentRoute.path;

          // 检查当前路径是否与目标路径相同
          if (currentPath !== targetPath) {
            // 如果不同，则跳转到目标路径
            this.$router.push(targetPath);
          }
        }
        var volunteer = getStorage("volunteerId");
        if (volunteer != null) {
          // 获取目标路径
          const targetPath = '/site/volunteer';
          // 获取当前路由的路径
          const currentPath = this.$router.currentRoute.path;

          // 检查当前路径是否与目标路径相同
          if (currentPath !== targetPath) {
            // 如果不同，则跳转到目标路径
            this.$router.push(targetPath);
          }
        }
      },
      exit () {
        delStorage('adminId');
        delStorage('teamId');
        delStorage('volunteerId');
        delStorage('code');
        // 获取目标路径
        const targetPath = '/site/home';
        // 获取当前路由的路径
        const currentPath = this.$router.currentRoute.path;

        // 检查当前路径是否与目标路径相同
        if (currentPath !== targetPath) {
          // 如果不同，则跳转到目标路径
          this.$router.push(targetPath);
        }

        this.flag = false;
      },
      handleSearch() {
        console.log(this.value);
        // 假设使用Vue Router进行路由跳转
        if (this.value == '选项1') {
          this.$router.push({
            path: '/site/teamShow',
            query: { search: this.searchInput } // 传递参数
          });
        }
        else {
          this.$router.push({
            path: '/site/projectShow',
            query: { search: this.searchInput } // 传递参数
          });
        }
      }
		}
	};
</script>

<template>
	<div class="top" style="width: 100%">
    <div class="header" style="width: 100%;height: 185px">
      <div :style="{ width: cardWidth }">
        <el-row style="display: flex;justify-content: space-between; width: 100%">
          <el-col style="display: flex;flex-direction:column;align-items: center;justify-content: center;width: auto">
            <div>
              <img width="100%" src="../assets/logo1.png"/>
            </div>
          </el-col>
          <el-col style="width: 20%">

          </el-col>
          <el-col>
            <el-row class="all">
              <el-col class="jump">
                <div v-if="this.flag !== true">
                  <el-link class="login" icon="el-icon-user-solid" :underline="false" @click="login">登录</el-link>
                  <span> | </span>
                  <el-link class="login" icon="el-icon-info" :underline="false" @click="register">注册</el-link>
                </div>
                <div v-else>
                  <el-link class="login" icon="el-icon-user-solid" :underline="false" @click="user">用户中心</el-link>
                  <span> | </span>
                  <el-link class="login" icon="el-icon-info" :underline="false" @click="exit">退出登录</el-link>
                </div>
              </el-col>
              <el-col class="search">
                <el-input style="width: 60%;" placeholder="请输入内容" v-model="searchInput" :maxlength="20" :minlength="0" :show-word-limit="true" :clearable="true">
                  <el-select style="width: 110px" slot="prepend" v-model="value" placeholder="请选择">
                    <el-option
                      v-for="item in options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                  <el-button slot="append" icon="el-icon-search" @click="handleSearch"></el-button>
                </el-input>
              </el-col>
            </el-row>
          </el-col>
        </el-row>
      </div>
    </div>
    <div>
    </div>
		<div style="width: 100%">
			<el-menu class="nav" :default-active="activeIndex" mode="horizontal" :router="true" @select="handleSelect">
			  <el-menu-item class="nav-text" index="/site/home">首页</el-menu-item>
			  <el-menu-item class="nav-text" index="/site/projectShow">志愿项目</el-menu-item>
			  <el-menu-item class="nav-text" index="/site/teamShow">志愿团队</el-menu-item>
			  <el-menu-item class="nav-text" index="/site/welfare">公益活动</el-menu-item>
			  <el-menu-item class="nav-text" index="/site/learn">学习雷锋</el-menu-item>
        <el-menu-item class="nav-text" index="/site/video">视频展示</el-menu-item>
			</el-menu>
		</div>
	</div>
</template>

<style scoped>
.top {
  display: flex;
  z-index: 10;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.header {
  display: flex;
  align-items: center;
  justify-content: center;
  background-size: cover;
  background-image: url(https://chinavolunteer.mca.gov.cn/site/static/img/header_bg.acdffe9.png);
}
.all {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
}
.jump  {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: right;
}
.login {
  font-weight: bold;
  font-size: larger;
  padding: 5px;
}
.search {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: right;
}
.nav {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
}
.nav-text {
  display: flex;
  flex-direction: row;
  justify-content: center;
  width: 150px;
  font-weight: bold;
  font-size: x-large;
}
</style>