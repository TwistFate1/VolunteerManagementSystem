<script>
	import {
		getAllTeams,
		getTeamById,
		joinTeam,
		checkVolunteerTeam,
		getTeamAvatar
	} from '@/api/getData.js';
	import {
		Message
	} from 'element-ui'; // 引入 Element UI 的消息组件
	import router from '@/router'; // 引入 Vue Router 以便进行页面跳转
	import {
		getStorage,
		setStorage,
		delStorage
	} from '@/utils/localStorage.js'; // 引入缓存操作方法

	export default {
		name: 'VolunteerTeam',
		data() {
			return {
				page: 1,
				limit: 4,
				total: 0,
				teams: [],
				team: {
					name: ""
				},
				defaultAvatar: require('@/assets/default.png'),
				loading: true
			};
		},
		created () {
			if (this.$route.query.search) {
				this.team.name = this.$route.query.search;
			}
		},
		methods: {
			// 获取团队数据方法(模糊搜索)
			getTeams() {
				this.page = 1; // 搜索时始终从第一页开始显示
				this.getAllTeamsTo(); // 调用获取团队数据的方法
			},

			handleSizeChange(val) {
				this.limit = val;
				this.getAllTeamsTo();
			},
			handleCurrentChange(val) {
				this.page = val;
				this.getAllTeamsTo();
			},
			getTeamName() {
				this.page = 1;
				this.getAllTeamsTo();
			},
			getAllTeamsTo() {
				const params = {
					page: this.page,
					limit: this.limit,
					name: this.team.name
				};
				//传给后端进行分页
				getAllTeams(params).then((res) => {
					this.teams = res.result.teamList;
					this.total = res.result.total;

					let avatarLoadCount = 0;
					const totalTeams = this.teams.length;

					this.teams.forEach(team => {
						//判断团队人数状态
						console.log("团队id：", team.teamId, "已有人数：", team.memberCount, "最大人数：", team.max)
						if (team.memberCount < team.max) {
							this.$set(team, 'status', '未录满');
						} else {
							this.$set(team, 'status', '已录满');
						}
						const param = {
							volunteerId: getStorage('volunteerId'),
							// volunteerId: 'v001',
							teamId: team.teamId
						}

						//判断是否已加入
						if (param.volunteerId === null) {
							this.$set(team, 'joinOrNot', 0);
						} else {
							// console.log('Checking status for activity:', activity.activityId); // 添加调试信息
							checkVolunteerTeam(param).then((res) => {
								// console.log('res.code:', res.code, 'for activityId:', activity.activityId);
								if(res.result === 0){
									this.$set(team, 'joinOrNot', 0); // 待审核
								}
								else if (res.result === 1) {
									this.$set(team, 'joinOrNot', 1); //已加入
								} else if (res.result === 2){
									this.$set(team, 'joinOrNot', 2); // 被拒绝
								}
								else if (res.result === 3){
									this.$set(team, 'joinOrNot', 3); // 未加入
								}
							}).catch(() => {
								this.$set(team, 'joinOrNot', 0); // 处理错误情况，假设未加入
							});
						}

						// 获取团队头像
						getTeamAvatar({
								teamId: team.teamId
							})
							.then((res) => {
								if (res) {
									const blob = new Blob([res], {
										type: 'image/png'
									});
									this.$set(team, 'avatarReal', URL.createObjectURL(blob));
									// this.teamAvatar = URL.createObjectURL(blob);
								} else {
									this.$set(team, 'avatarReal', this.defaultAvatar);
									console.log('No avatar found for this team');
								}
							})
							.catch((error) => {
								this.$set(team, 'avatarReal', this.defaultAvatar);
								// console.error('获取团队头像失败:', error);
								// this.$message.error('无法获取团队头像');
							})
							.finally(() => {
								avatarLoadCount++;
								if (avatarLoadCount === totalTeams) {
									this.loading = false; // 当所有头像都加载完成后，设置 loading 为 false
								}
							});
					});
					if (totalTeams === 0) {
						this.loading = false; // 如果没有团队数据，立即设置 loading 为 false
					}
				}).catch(() => {
					this.loading = false; // 如果获取团队数据失败，立即设置 loading 为 false
				});
				

			},
			joinTeam(teamId) {
				const volunteerId = getStorage('volunteerId');
				//检查是否登录
				if (!volunteerId) {
					router.push('/login');
				} else {
					joinTeam({
						volunteerId,
						teamId
					}).then(res => {
						if (res.code === 1) {
							this.$message.success('申请加入成功');
						} else {
							this.$message.error('申请加入失败');
						}
					});
				}
			},
		},
		mounted() {
			this.getAllTeamsTo();
			// this.loading = false;
		}
	}
</script>

<template>
	<div>
		<!-- 搜索框 -->
		<div class="center-container-team">  
		    <div class="search-container-team">  
		        <el-input v-model="team.name" placeholder="请输入团队名称进行搜索" @change="getTeams">  
		            <el-button slot="append" icon="el-icon-search" @click="getTeams"></el-button>  
		        </el-input>  
		    </div>  
		</div>

		<!-- 卡片列表 -->
		<div class="card-container-team" v-if="teams != null" v-loading="loading">
			<el-card v-for="team in teams" :key="team.teamId" class="box-card-team">
				<!-- 卡片内容 -->
				<div slot="header" class="clearfix-team">
					<img :src="team.avatarReal" class="team-avatar">
					<span class="team-name">{{ team.name }}</span>
					<el-button v-if="team.status === '未录满' && team.joinOrNot === 3" style="float: right; padding: 3px 0"
						type="text" @click="joinTeam(team.teamId)">加入</el-button>
					<el-button v-else-if="team.status === '未录满' && team.joinOrNot === 1"
						style="float: right; padding: 3px 0" :disabled="true" type="text"
						@click="joinTeam(team.teamId)">已加入</el-button>
					<el-button v-else-if="team.status === '未录满' && team.joinOrNot === 2"
						style="float: right; padding: 3px 0" :disabled="true" type="text"
						@click="joinTeam(team.teamId)">被拒绝</el-button>
					<el-button v-else-if="team.status === '未录满' && team.joinOrNot === 0"
						style="float: right; padding: 3px 0" :disabled="true" type="text"
						@click="joinTeam(team.teamId)">待审核</el-button>
					<el-button v-else-if="team.status === '已录满'" style="float: right; padding: 3px 0" :disabled="true"
						type="text" @click="joinTeam(team.teamId)">已录满</el-button>
				</div>
				<div class="text item">
					<el-row>
						<el-col :span="24">
							<p><strong>🤵队伍联系人:</strong> {{ team.leader }}</p>
							<p><strong>📞联系电话:</strong> {{ team.contact }}</p>
							<el-divider></el-divider>
						</el-col>
						<el-col :span="24">
							<el-row
								style="display: flex; align-items: center;flex-direction: row;justify-content: space-between;">
								<el-col :span="12">
									<div
										style=" display: flex; align-items: center;flex-direction: column;justify-content: space-between;">
										<p
											style="display: flex; align-items: center;flex-direction: column;justify-content: space-around;">
											<strong>🏃‍♂️已有志愿者</strong><br> {{ team.memberCount }}人</p>
									</div>

								</el-col>
								<el-divider direction="vertical" style="height: 100%;"></el-divider>
								<el-col :span="12">
									<div
										style="display: flex; align-items: center;flex-direction: column;justify-content: space-around;">
										<p
											style="display: flex; align-items: center;flex-direction: column;justify-content: space-around;">
											<strong>🔝上限</strong><br> {{ team.max }}人</p>
									</div>
								</el-col>
							</el-row>

						</el-col>


					</el-row>

				</div>
			</el-card>
		</div>
		<div v-else>
			<p>正在加载团队数据...</p>
		</div>
		<div class="pagination-container-team">
			<!-- 分页组件 -->
			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page"
				:page-size="limit" :page-sizes="[4, 8, 10]" layout="total, sizes, prev, pager, next, jumper"
				:total="total">
			</el-pagination>
		</div>
	</div>
</template>

<style>
	.center-container-team {  
	    display: flex;  
	    justify-content: center;  
	    align-items: center; 
	    width: 100%; 
	    margin-bottom: 20px; 
	}  
	.search-container-team {
		display: flex;
		width: 35%;
	}

	.card-container-team {
		margin-top: 20px;
		display: flex;
		align-items: center;
		flex-direction: row;
		justify-content: space-around;
		padding: 0px;
		gap: 20px;
		/* 添加卡片之间的间隙 */
	}

	.box-card-team {
		width: calc(25% - 20px);
		/* 设置卡片宽度并考虑间隙 */
		margin-bottom: 20px;
		/* box-sizing: border-box; */
		border-radius: 5px;
	}

	.text {
		font-size: 14px;
	}

	.item {
		margin-bottom: 18px;
	}

	.clearfix-team {
		position: relative;
		padding: 10px;
		/* border-radius: 5px; */
		border-radius: 5px;
		margin-bottom: 10px;
		background-color: #F4F9FF;
	}

	.clearfix-team:before,
	.clearfix-team:after {
		display: table;
		content: "";
	}

	.clearfix-team:after {
		clear: both
	}

	.box-card-team:last-child {
		/* 最后一个卡片没有右边距 */
		margin-right: 0;
	}

	.pagination-container-team {
		text-align: center;
		margin-top: 30px;
		margin-bottom: 30px;
	}

	.team-avatar {
		width: 40px;
		height: 40px;
		border-radius: 50%;
		object-fit: cover;
		margin-right: 10px;
		vertical-align: middle;
	}

	.team-name {
		vertical-align: middle;
		font-size: 16px;
		font-weight: bold;
	}
</style>