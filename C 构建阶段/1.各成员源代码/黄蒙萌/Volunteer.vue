<script setup>

</script>

<script>
	export default {
		data() {
			return {
				menuWidth: '200px',
				contentWidth: '1060px',
				key: 'admin/infoContent',
				url: 'https://chinavolunteer.mca.gov.cn/site/static/img/header_bg.acdffe9.png',
			}
		},
		methods: {
			handleSelect(key, indexPath) {
				// 确保在跳转前在挂载点下，即 '/'
				console.log(key, indexPath);
				this.key = key;
				this.$router.push('/');
			},
		},
	};
</script>

<template>

		<!-- 图片只是用于观察效果,整合时删除 -->
		<!-- <div class="header">
			<el-image :src="url" class="header-image"></el-image>
		</div> -->
		
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
								<el-menu-item index="volunteer/informationV">基本信息</el-menu-item>
								<el-menu-item index="volunteer/informationVEdit">信息修改</el-menu-item>
							</el-menu-item-group>
						</el-submenu>
						<el-submenu index="activity">
							<template slot="title">
								<i class="el-icon-s-flag"></i>
								<span slot="title">志愿活动</span>
							</template>
							<el-menu-item-group>
								<span slot="title">活动</span>
								<el-menu-item index="volunteer/myActivities">我的活动</el-menu-item>
							</el-menu-item-group>
						</el-submenu>
						<el-submenu index="team">
							<template slot="title">
								<i class="el-icon-s-promotion"></i>
								<span slot="title">志愿团队</span>
							</template>
							<el-menu-item-group>
								<span slot="title">团队</span>
								<el-menu-item index="volunteer/myTeams">我的团队</el-menu-item>
							</el-menu-item-group>
						</el-submenu>
						<el-submenu index="statistics">
							<template slot="title">
								<i class="el-icon-s-management"></i>
								<span slot="title">志愿成就</span>
							</template>
							<el-menu-item-group>
								<span slot="title">成就</span>
								<el-menu-item index="volunteer/statistics">数据概览</el-menu-item>
							</el-menu-item-group>
						</el-submenu>
					</el-menu>
				</el-aside>
				<el-main :style="{ width:contentWidth }" style="padding: 0%">
					<!-- <div v-if="key === 'admin/infoContent'">
		        <AdminInfoContent></AdminInfoContent>
		      </div> -->
					<router-view></router-view>
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
</style>