<script setup>

</script>

<script>
	import {
		getStorage
	} from '@/utils/localStorage.js';
	
	import {
		getAdminAvatar,
		getAdminById,
		getVolunteerNumber,
		getActivityNumber,
		getTeamNumber
	} from "@/api/getData.js";
	
	export default {
		data() {
			return {
				contentWidth: '1060px',
				adminInfo: {
					adminId: 'jyf',
					password: '',
					status: ''
				},
				show: false,
				time: 0.00,
				teamNum: 0,
				proNum: 0,
				grant: 0,
				adminAvatar: '',
				//默认头像
				defaultAvatar: require('@/assets/default.png'),
				show2: false,
				volunterrNum: '',
				activityNum:'',
				teamNum:''
			}
		},
		created () {
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
			getAdminAvatarTo() {
				const adminId = getStorage('adminId');
				// const adminId = this.adminInfo.adminId;
				getAdminById({
					adminId
				}).then((res) => {
					this.adminInfo = res.result;
				});
				// 获取管理员头像
				getAdminAvatar({
						adminId: this.adminInfo.adminId
					})
					.then((res) => {
						if (res) {
							const blob = new Blob([res], {
								type: 'image/png'
							});
							this.adminAvatar = URL.createObjectURL(blob);
						} else {
							console.log('No avatar found for this admin');
						}
					})
					.catch((error) => {
						console.error('获取管理员头像失败:', error);
						// this.$message.error('无法获取志愿者头像');
					});
					
				//获取三个总数
				getVolunteerNumber().then((res) => {
					if(res){
						this.volunterrNum = res.result;
					}
				}).catch((error) => {
					console.error('获取志愿者总数失败:', error);
				});
				
				getActivityNumber().then((res) => {
					if(res){
						this.activityNum = res.result;
					}
				}).catch((error) => {
					console.error('获取志愿活动总数失败:', error);
				});
				
				getTeamNumber().then((res) => {
					if(res){
						this.teamNum = res.result;
					}
				}).catch((error) => {
					console.error('获取志愿团队总数失败:', error);
				})
			},

			errorHandler() {
				// TODO
				console.log("请更换图像资源地址");
			},
			showHandler() {
				this.show = !this.show;
			}
		},
		mounted() {
			console.log("调用了mounted");
			this.getAdminAvatarTo();
			this.show2 = true;
		}
	};
</script>

<template>
	<div :style="{ width: contentWidth }">
		<el-card style="padding: 0%">
			<div slot="header">
				<el-card>
					<el-container class="content">
						<el-aside
							style="display: flex;flex-direction: column;justify-content: center;width: 40px;padding-top: 20px;padding-bottom: 20px">
							<div class="block">
								<el-avatar v-if="adminAvatar" fit="fill"
									src="adminAvatar"
									@error="errorHandler"></el-avatar>
								<el-avatar v-else fit="fill" :src="defaultAvatar" @error="errorHandler"></el-avatar>
							</div>
						</el-aside>
						<el-main>
							<el-descriptions :border="true" size="medium">
								<el-descriptions-item>
									<template slot="label">
										<i class="el-icon-postcard"></i>
										账号
									</template>
									{{adminInfo.adminId}}
								</el-descriptions-item>
								<el-descriptions-item>
									<template slot="label">
										<i class="el-icon-unlock"></i>
										密码
									</template>
									<div style="display: flex;align-items: center">
										<div v-if="show">{{ adminInfo.password}}</div>
										<div v-else>{{ '*'.repeat(this.adminInfo.password.length) }}</div>
										<el-button @click="showHandler" style="margin-left:auto" type="primary"
											icon="el-icon-view"></el-button>
									</div>
								</el-descriptions-item>
								<el-descriptions-item>
									<template slot="label">
										<i class="el-icon-user"></i>
										根管理员
									</template>
									<div v-if="grant === 0">{{ adminInfo.status === 0 ? '是' : '否' }}</div>
									<div v-else>否</div>
								</el-descriptions-item>
							</el-descriptions>
						</el-main>
					</el-container>
				</el-card>
			</div>
			<el-row :gutter="15" type="flex" justify="center">
				<el-col style="width:33.3%">
					<transition name="el-zoom-in-top">
						<el-card shadow="hover" v-show="show2">
							<el-container class="content">
								<el-main class="dataIcon" style="width: 25%;height: 100%">
									<div>
										<img alt="志愿者" title="总人数" src="../../../assets/team.png"></img>
									</div>
								</el-main>
								<el-aside style="width: 75%;height: 100%">
									<el-col style="height: 60%;width: 100%">
										<el-row style="height: 60%">
											<p class="data" style="margin-top: 20px; margin-bottom: 0%">
												{{ volunterrNum }}
											</p>
										</el-row>
										<el-row style="height: 90%">
											<div class="text" style="margin-top: 0%; margin-bottom: 0%">总人数</div>
										</el-row>
									</el-col>
								</el-aside>
							</el-container>
						</el-card>
					</transition>
				</el-col>
				<el-col style="width:33.3%">
					<transition name="el-zoom-in-top">
						<el-card shadow="hover" v-show="show2">
							<el-container class="content">
								<el-main class="dataIcon" style="width: 25%;height: 100%">
									<div>
										<img alt="志愿团队" title="总志愿团队(个)" src="../../../assets/team.png"></img>
									</div>
								</el-main>
								<el-aside style="width: 75%;height: 100%">
									<el-col style="height: 60%;width: 100%">
										<el-row style="height: 60%">
											<p class="data" style="margin-top: 20px; margin-bottom: 0%">{{ teamNum }}
											</p>
										</el-row>
										<el-row style="height: 90%">
											<div class="text" style="margin-top: 0%; margin-bottom: 0%">总志愿团队(个)</div>
										</el-row>
									</el-col>
								</el-aside>
							</el-container>
						</el-card>
					</transition>
				</el-col>
				<el-col style="width:33.3%">
					<transition name="el-zoom-in-top">
						<el-card shadow="hover" v-show="show2">
							<el-container class="content">
								<el-main class="dataIcon" style="width: 25%;height: 100%">
									<div>
										<img alt="志愿活动" title="总志愿活动(个)" src="../../../assets/project.png"></img>
									</div>
								</el-main>
								<el-aside style="width: 75%;height: 100%">
									<el-col style="height: 60%;width: 100%">
										<el-row style="height: 60%">
											<p class="data" style="margin-top: 20px; margin-bottom: 0%">{{ activityNum }}
											</p>
										</el-row>
										<el-row style="height: 90%">
											<div class="text" style="margin-top: 0%; margin-bottom: 0%">总志愿活动(个)</div>
										</el-row>
									</el-col>
								</el-aside>
							</el-container>
						</el-card>
					</transition>
				</el-col>
			</el-row>
		</el-card>
	</div>
</template>

<style scoped>
	.dataIcon {
		align-items: center;
		justify-content: center;
	}

	.content {
		height: auto;
		align-items: center;
		justify-content: center;
	}

	.data {
		color: black;
		font-weight: bold;
		font-size: xx-large;
	}

	.text {
		color: black;
		font-size: xx-small;
	}
</style>