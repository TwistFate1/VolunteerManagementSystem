<script setup>

</script>

<script>
	// import放在setup里面生命周期函数无法调用
	import {
		getAllVolunteerActivitiesByVolunteerId,
		getVolunteerById,
		getAllTeamByVolunteerId,
		getVolunteerByTeamId,
		getTeamById,
		getActivityNumByTeamId,
		getTeamTotalTime,
		getTeamAvatar
	} from "@/api/getData.js";

	import {
		getStorage,
		setStorage,
		delStorage
	} from '@/utils/localStorage.js'; // 引入缓存操作方法

	export default {
		data() {
			return {
				contentWidth: '1060px',
				id: 'admin',
				pwd: 'null',
				show: false,

				teamInfo: {
					teamId: 't001',
					password: '',
					name: '',
					leader: '',
					contact: '',
					introduction: '',
					avatar: ''
				},
				memberNum: 0,
				activityNum: 0,
				totalTime: 0,
				// url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
				team: {},
				teamAvatar: '',
				//默认头像
				defaultAvatar: require('@/assets/default.png'),
				show2: false
			}
		},
		created () {
			this.checkLoginStatus();
		},
		methods: {
			checkLoginStatus() {
				const isLoggedIn = getStorage('teamId') != null;

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
			// 获取支援团队所有信息
			getTeamInfo() {
				this.teamInfo.teamId = getStorage('teamId');
				console.log("得到缓存");
				console.log(this.teamInfo.teamId);

				console.log("getTeamInfo调用")
				// 得到团队信息
				this.getTeamByIdTo();
				// 得到团队人数
				this.getVolunteerByTeamIdTo();
				// 得到团队活动数量
				this.getActivityNumByTeamIdTo();
				// 得到总时长
				this.getTeamTotalTimeTo();
				// 获取志愿团队头像
				this.getTeamAvatarTo();

			},
			// 得到团队信息
			getTeamByIdTo() {
				getTeamById(this.teamInfo).then(res => {
					console.log(res.result);
					this.team = res.result;
					console.log(this.team);
					console.log(this.team.name);
					this.teamInfo.name = this.team.name;
				})


			},
			// 得到团队人数
			getVolunteerByTeamIdTo() {

				getVolunteerByTeamId(this.teamInfo).then(res => {
					console.log(res.result.num1);
					this.memberNum = res.result.num1;
				})
			},
			// 得到团队活动数量
			getActivityNumByTeamIdTo() {

				getActivityNumByTeamId(this.teamInfo).then(res => {
					console.log(res);
					this.activityNum = res.result;
					console.log(this.activityNum);

				})
			},
			getTeamTotalTimeTo() {
				getTeamTotalTime(this.teamInfo).then(res => {
					console.log(res.result);
					this.totalTime = res.result;
				})
			},
			getTeamAvatarTo(){
				const teamId = getStorage('teamId');
				// const teamId = this.teamInfo.teamId;
				// 获取志愿者头像
				getTeamAvatar({
						teamId
					})
					.then((res) => {
						if (res) {
							const blob = new Blob([res], {
								type: 'image/png'
							});
							this.teamAvatar = URL.createObjectURL(blob);
						} else {
							console.log('No avatar found for this team');
						}
					})
					.catch((error) => {
						console.error('获取志愿团队头像失败:', error);
						// this.$message.error('无法获取志愿者头像');
					});
			}
			// getVolunteerByIdTo() {
			// 	console.log("getVolunteerByIdTo method called");
			// 	//从缓存中获取志愿者ID
			// 	// const volunteerId = getStorage('volunteerId');
			// 	const volunteerId = this.volunteerInfo.volunteerId;
			// 	console.log("volunteerId:", volunteerId);
			// 	//请求志愿者信息
			// 	getVolunteerById({
			// 		volunteerId
			// 	}).then(response => {
			// 		this.volunteerInfo = response.result;
			// 	}).catch(error => {
			// 		console.error('获取志愿者信息失败:', error);
			// 	});
			// 	getAllVolunteerActivitiesByVolunteerId({volunteerId}).then((res)=>{
			// 		//志愿者总志愿时长
			// 		this.volunteerTime = res.result.totalServiceHours;
			// 		//志愿者加入的志愿活动总数
			// 		this.activityNum = res.result.num1;
			// 		console.log("志愿时长：",this.volunteerTime);
			// 		console.log("志愿活动数：",this.activityNum);
			// 	});

			// 	//请求志愿者加入的志愿团队数
			// 	getAllTeamByVolunteerId({volunteerId}).then((res) => {
			// 		this.teamNum = res.result.num1;
			// 	});
			// },
		},
		mounted() {
			console.log("Component mounted");
			this.getTeamInfo();
			this.show2 = true;
		}
	};
</script>

<template>
	<div :style="{ width: contentWidth }">
		<el-card>
			<!-- <div>
				<el-button @click="getTeamByIdTo" type="danger">
				</el-button>
			</div> -->
			<!-- 头部 -->
			<div slot="header">
				<el-card>
					<el-container>
						<el-aside
							style="display: flex;flex-direction: column;justify-content: center;width: 40px;padding-top: 20px;padding-bottom: 20px">
							<div class="block">
								<el-avatar v-if="teamAvatar" fit="fill" :src="teamInfo.avatar" @error="errorHandler"></el-avatar>
								<el-avatar v-else fit="fill" :src="defaultAvatar" @error="errorHandler"></el-avatar>
							</div>
						</el-aside>
						<el-main>
							<el-descriptions :border="true" size="medium">
								<el-descriptions-item>
									<template slot="label">
										<i class="el-icon-postcard"></i>
										团队账号
									</template>
									{{ teamInfo.teamId }}
								</el-descriptions-item>
								<el-descriptions-item>
									<template slot="label">
										<i class="el-icon-user"></i>
										团队名称
									</template>
									{{ teamInfo.name }}
								</el-descriptions-item>
							</el-descriptions>
						</el-main>
					</el-container>
					<!-- <el-row  :gutter="30">
						<el-col :span="3">
							<div class="block">
								<img src="../../../assets/avatar.png" style="width: 100%; height: 100%;">
								
							</div>
						</el-col >
						<el-col :span="21">
							<el-row >
								<div ><h2>欢迎回来，{{ teamInfo.name }}</h2></div>

							</el-row>
							<el-row :gutter="0">
								<div style="margin-top: 0%;">
									<el-col :span="2.8">
										<div style="color: darkgray;">团队账号:&nbsp;</div>
									</el-col>
									<el-col :span="2">
										<div style="color: darkgray;">{{ teamInfo.teamId }}</div>
									</el-col>	
								</div>
							</el-row> -->
					<!-- <el-row>
								<div style=" margin-bottom: 3%;">
									 <p style="color: darkgray;"><strong>团队简介：</strong>&nbsp;{{teamInfo.introduction}}</p>

								</div>
							</el-row> -->
					<!-- </el-col>
					</el-row> -->
				</el-card>
			</div>
			<!-- 展示内容 -->
			<el-row :gutter="15" type="flex" justify="center">
				<el-col style="width:33.3%">
					<transition name="el-zoom-in-top">
						<el-card shadow="hover" v-show="show2">
							<el-container>
								<el-main class="dataIcon" style="width: 25%;height: 100%">
									<div>
										<img alt="团队人数" title="人数(人)" src="../../../assets/team.png"></img>
										<!-- src="../../assets/time.png" -->
									</div>
								</el-main>
								<el-aside style="width: 75%;height: 100%">
									<el-col style="height: 60%;width: 100%">
										<el-row style="height: 60%">
											<p class="data" style="margin-top: 20px; margin-bottom: 0%">
												{{ memberNum }}
												<!-- .toFixed(2) -->
											</p>
										</el-row>
										<el-row style="height: 90%">
											<div class="text" style="margin-top: 0%; margin-bottom: 0%">总人数(人)
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
										<img alt="活动数量" title="发布志愿活动(个)" src="../../../assets/project.png"></img>
										<!-- src="../../assets/team.png" -->
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
											<div class="text" style="margin-top: 0%; margin-bottom: 0%">发布志愿活动(个)</div>
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
										<img alt="团队时长" title="总时长(小时)" src="../../../assets/time.png"></img>
										<!-- src="../../assets/project.png" -->
									</div>
								</el-main>
								<el-aside style="width: 75%;height: 100%">
									<el-col style="height: 60%;width: 100%">
										<el-row style="height: 60%">
											<p class="data" style="margin-top: 20px; margin-bottom: 0%">{{ totalTime }}
											</p>
										</el-row>
										<el-row style="height: 90%">
											<div class="text" style="margin-top: 0%; margin-bottom: 0%">总时长(小时)</div>
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
	.block {
		margin-top: 10%;
		height: 100%;
		width: 100%;
	}

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