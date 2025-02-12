<script setup>

</script>

<script>
	// import放在setup里面生命周期函数无法调用
	import {
		getAllVolunteerActivitiesByVolunteerId,
		getVolunteerById,
		getAllTeamByVolunteerId,
		getVolunteerAvatar
	} from "@/api/getData.js";

	import {
		getStorage,
		setStorage,
		delStorage
	} from '@/utils/localStorage.js'; // 引入缓存操作方法

	export default {
		data() {
			return {
				volunteerInfo: {
					volunteerId: 'v001',
					password: '123',
					name: '张伟',
					gender: 0,
					nation: '',
					phone: '',
					email: '',
					identify: '',
					area: ''
				},
				volunteerTime: 0,
				activityNum: 0,
				teamNum: 0,
				volunteerAvatar: '',
				//默认头像
				defaultAvatar: require('@/assets/default.png'),
				contentWidth: '1060px',
				pwd: 'null',
				show: false,
				show2: false
			}
		},
		created () {
			this.checkLoginStatus();
		},
		methods: {
			checkLoginStatus() {
				const isLoggedIn = getStorage('volunteerId') != null;

				if (!isLoggedIn) {
					alert('您还没有登录，请先登录！');
					this.$router.push('/site/login');
				}
			},
			errorHandler() {
				// TODO
				console.log("请更换图像资源地址");
			},
			showHandler() {
				this.show = !this.show;
			},
			getVolunteerByIdTo() {
				console.log("getVolunteerByIdTo method called");
				//从缓存中获取志愿者ID
				const volunteerId = getStorage('volunteerId');
				// const volunteerId = this.volunteerInfo.volunteerId;
				console.log("volunteerId:", volunteerId);
				//请求志愿者信息
				getVolunteerById({
					volunteerId
				}).then(response => {
					this.volunteerInfo = response.result;
				}).catch(error => {
					console.error('获取志愿者信息失败:', error);
				});
				// 获取志愿者头像
				getVolunteerAvatar({
						volunteerId
					})
					.then((res) => {
						if (res) {
							const blob = new Blob([res], {
								type: 'image/png'
							});
							this.volunteerAvatar = URL.createObjectURL(blob);
						} else {
							console.log('No avatar found for this volunteer');
						}
					})
					.catch((error) => {
						console.error('获取志愿者头像失败:', error);
						// this.$message.error('无法获取志愿者头像');
					});
				getAllVolunteerActivitiesByVolunteerId({
					volunteerId
				}).then((res) => {
					if (res.result.totalServiceHours == null) {
						this.volunteerTime = 0;
					}	
					else {
						//志愿者总志愿时长
						this.volunteerTime = res.result.totalServiceHours;
					}
					//志愿者加入的志愿活动总数
					this.activityNum = res.result.num1;
					console.log("志愿时长：", this.volunteerTime);
					console.log("志愿活动数：", this.activityNum);
				});

				//请求志愿者加入的志愿团队数
				getAllTeamByVolunteerId({
					volunteerId
				}).then((res) => {
					this.teamNum = res.result.num1;
				});
			},

		},
		mounted() {
			console.log("Component mounted");
			this.getVolunteerByIdTo();
			this.show2 = true;
		}
	};
</script>

<template>
	<div :style="{ width: contentWidth }">
		<el-card>
			<div slot="header">
				<el-card>
					<el-container>
						<el-aside
							style="display: flex;flex-direction: column;justify-content: center;width: 40px;padding-top: 20px;padding-bottom: 20px">
							<div class="block">
								<el-avatar v-if="volunteerAvatar" fit="fill" :src="volunteerAvatar" @error="errorHandler"></el-avatar>
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
									{{ volunteerInfo.volunteerId }}
								</el-descriptions-item>
								<el-descriptions-item>
									<template slot="label">
										<i class="el-icon-user"></i>
										姓名
									</template>
									{{ volunteerInfo.name }}
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
							<el-container>
								<el-main class="dataIcon" style="width: 25%;height: 100%">
									<div>
										<img alt="志愿服务" title="总时长(小时)" src="../../../assets/time.png"></img>
									</div>
								</el-main>
								<el-aside style="width: 75%;height: 100%">
									<el-col style="height: 60%;width: 100%">
										<el-row style="height: 60%">
											<p class="data" style="margin-top: 20px; margin-bottom: 0%">
												{{ volunteerTime.toFixed(2) }}
											</p>
										</el-row>
										<el-row style="height: 90%">
											<div class="text" style="margin-top: 0%; margin-bottom: 0%">总时长(小时)
											</div>
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
							<el-container>
								<el-main class="dataIcon" style="width: 25%;height: 100%">
									<div>
										<img alt="志愿团队" title="加入志愿团队(个)" src="../../../assets/team.png"></img>
									</div>
								</el-main>
								<el-aside style="width: 75%;height: 100%">
									<el-col style="height: 60%;width: 100%">
										<el-row style="height: 60%">
											<p class="data" style="margin-top: 20px; margin-bottom: 0%">{{ teamNum }}
											</p>
										</el-row>
										<el-row style="height: 90%">
											<div class="text" style="margin-top: 0%; margin-bottom: 0%">加入志愿团队(个)</div>
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
							<el-container>
								<el-main class="dataIcon" style="width: 25%;height: 100%">
									<div>
										<img alt="志愿活动" title="参与志愿活动(个)" src="../../../assets/project.png"></img>
									</div>
								</el-main>
								<el-aside style="width: 75%;height: 100%">
									<el-col style="height: 60%;width: 100%">
										<el-row style="height: 60%">
											<p class="data" style="margin-top: 20px; margin-bottom: 0%">
												{{ activityNum }}
											</p>
										</el-row>
										<el-row style="height: 90%">
											<div class="text" style="margin-top: 0%; margin-bottom: 0%">参与志愿活动(个)</div>
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
	.infoCard {
		display: inline;
	}

	.dataIcon {
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