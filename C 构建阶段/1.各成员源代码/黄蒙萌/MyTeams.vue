<script setup>
	import { Message } from 'element-ui';
</script>

<script>
	import {
		getAllTeamByVolunteerId,
		getTeamById,
		deleteApplyForTeam,
		getMembership
	} from "@/api/getData.js";
	import {
		getStorage,
		setStorage,
		delStorage
	} from '@/utils/localStorage.js'; // 引入缓存操作方法
	export default {
		data() {
			return {
				teamIds: [],
				//所有团队
				teams: [{
					applyState: ""
				}],
				//已加入
				teamsState1: [],
				//待审核
				teamsState0: [],
				confirmDialogVisibleQuit: false,
				confirmDialogVisibleCancel: false,
				//存储当前选择的团队id
				selectedTeamId: "",
				// 用于控制骨架屏的显示
				loading: true
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
			//获取志愿者加入的所有志愿团队
			getAllTeamByVolunteerIdTo() {
				const volunteerId = getStorage("volunteerId");
				// const volunteerId = 'v001';
				getAllTeamByVolunteerId({
						volunteerId
					}).then((res) => {
						this.teamIds = res.result.teams.map(team => team.teamId);
						console.log("团队ID：", this.teamIds);
						this.getTeamByIdTo();
					})
					.catch((error) => {
						console.error('获取团队ID失败:', error);
					});
			},
			//根据团队ID获取团队信息
			getTeamByIdTo() {
				// console.log("调用了getActivityByAIdTo");
				this.teams = []; // 清空teams数组
				const teamPromises = this.teamIds.map(teamId => {
					// console.log(`获取团队ID ${teamId} 的团队信息`);
					return getTeamById({
							teamId
						})
						.then((res) => {
							if (res.result) {
								this.teams.push(res.result);
								console.log("所有团队：", this.teams);
								const team = res.result;
								// console.log("teamId", teamId);
								const param = {
									volunteerId: getStorage('volunteerId'),
									// volunteerId: "v001",
									teamId: team.teamId
								};
								//获取志愿者申请每个团队的审核状态
								const applyStatePromise = getMembership(param)
									.then((res3) => {
										this.$set(team, 'applyState', res3.result);
									});
								return Promise.all([teamPromises, applyStatePromise]);
							} else {
								console.error(`团队ID ${teamId} 的团队信息返回为空`);
								return Promise.resolve(); // 返回一个已解决的Promise，以便继续处理下一个团队
							}
						})
						.catch((error) => {
							console.error(`获取团队ID ${teamId} 的团队信息失败:`, error);
							return Promise.resolve(); // 返回一个已解决的Promise，以便继续处理下一个团队
						});
				});
				//进行分类
				Promise.all(teamPromises)
					.then(() => {
						//所有团队信息获取完成后进行分类
						this.classifyActivities();
						console.log("分类后的团队信息：", this.teamsState1, this.teamsState0);
						this.loading = false; //加载完成，隐藏骨架屏
					})
					.catch((error) => {
						console.error('获取所有团队信息失败:', error);
						this.loading = false; //出错也需要隐藏骨架屏
					});
				// console.log("团队信息：", this.teams);
			},

			//将团队分为已加入和待审核
			classifyActivities() {
				this.teamsState1 = [];
				this.teamsState0 = [];
				this.teams.forEach(team => {
					if (team.applyState == 1) {
						this.teamsState1.push(team);
					} else if (team.applyState == 0) {
						this.teamsState0.push(team);
					}
				});
			},

			// 打开确认退出对话框
			openConfirmDialogQuit(teamId) {
				this.selectedTeamId = teamId;
				this.confirmDialogVisibleQuit = true;
			},
			// 打开确认撤销对话框
			openConfirmDialogCancel(teamId) {
				this.selectedTeamId = teamId;
				this.confirmDialogVisibleCancel = true;
			},

			//撤销参加团队申请
			deleteRecord() {
				const param = {
					volunteerId: getStorage('volunteerId'),
					// volunteerId: "v001",
					teamId: this.selectedTeamId
				};
				deleteApplyForTeam(param).then((res) => {
					if (res.code === 1) {
						this.$message.success('操作成功');
						this.getAllTeamByVolunteerIdTo(); // 重新获取团队列表
					} else {
						this.$message.error('操作失败');
					}
					this.confirmDialogVisibleCancel = false;
					this.confirmDialogVisibleQuit = false;
				})
			}
		},
		mounted() {
			this.getAllTeamByVolunteerIdTo();

		}
	}
</script>

<template>
	<div class="container">
		<el-card class="card">
			<el-tabs type="border-card">
				<el-tab-pane label="已加入">
					<!-- 骨架屏 -->
					<el-skeleton :loading="loading" animated>
						<template #template>
							<el-skeleton-item variant="text"></el-skeleton-item>
							<el-skeleton-item variant="text"></el-skeleton-item>
							<el-skeleton-item variant="text"></el-skeleton-item>
							<el-skeleton-item variant="text"></el-skeleton-item>
							<el-skeleton-item variant="text"></el-skeleton-item>
						</template>
						<!-- 表格 -->
						<el-table v-if="!loading" :data="teamsState1" style="width: 100%" :stripe="true">
							<el-table-column prop="teamId" label="团队编号" header-align="center"
								align="center"></el-table-column>
							<el-table-column prop="name" label="团队名称" header-align="center"
								align="center"></el-table-column>
							<el-table-column prop="leader" label="负责人" header-align="center"
								align="center"></el-table-column>
							<el-table-column prop="contact" label="联系电话" header-align="center"
								align="center"></el-table-column>
							<el-table-column label="操作" width="200" header-align="center" align="center">
								<template slot-scope="scope">
									<div class="button-container">
										<el-button type="danger" icon="el-icon-refresh-right" class="button"
											@click="openConfirmDialogQuit(scope.row.teamId)">退出</el-button>
									</div>
								</template>
							</el-table-column>
						</el-table>
					</el-skeleton>
				</el-tab-pane>
				<el-tab-pane label="待审核">
					<!-- 骨架屏 -->
					<el-skeleton :loading="loading" animated>
						<template #template>
							<el-skeleton-item variant="text"></el-skeleton-item>
							<el-skeleton-item variant="text"></el-skeleton-item>
							<el-skeleton-item variant="text"></el-skeleton-item>
							<el-skeleton-item variant="text"></el-skeleton-item>
							<el-skeleton-item variant="text"></el-skeleton-item>
						</template>
						<!-- 表格 -->
						<el-table v-if="!loading" :data="teamsState0" style="width: 100%" :stripe="true">
							<el-table-column prop="teamId" label="团队编号" header-align="center"
								align="center"></el-table-column>
							<el-table-column prop="name" label="团队名称" header-align="center"
								align="center"></el-table-column>
							<el-table-column prop="leader" label="负责人" header-align="center"
								align="center"></el-table-column>
							<el-table-column prop="contact" label="联系电话" header-align="center"
								align="center"></el-table-column>
							<el-table-column label="操作" width="200" header-align="center" align="center">
								<template slot-scope="scope">
									<div class="button-container">
										<el-button type="danger" icon="el-icon-refresh-right" class="button"
											@click="openConfirmDialogCancel(scope.row.teamId)">撤销</el-button>
									</div>
								</template>
							</el-table-column>
						</el-table>
					</el-skeleton>
				</el-tab-pane>
			</el-tabs>
		</el-card>
		<!-- 确认退出对话框 -->
		<el-dialog title="确认退出" :visible.sync="confirmDialogVisibleQuit" width="30%">
			<span>确定要退出该团队吗？</span>
			<span slot="footer" class="dialog-footer">
				<el-button @click="confirmDialogVisibleQuit = false">取消</el-button>
				<el-button type="danger" @click="deleteRecord">确定</el-button>
			</span>
		</el-dialog>

		<!-- 确认撤销对话框 -->
		<el-dialog title="确认撤销" :visible.sync="confirmDialogVisibleCancel" width="30%">
			<span>确定要撤销团队申请吗？</span>
			<span slot="footer" class="dialog-footer">
				<el-button @click="confirmDialogVisibleCancel = false">取消</el-button>
				<el-button type="danger" @click="deleteRecord">确定</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<style scoped>
	.container {
	  display: flex;
	  flex-direction: column;
	  height: 100%;
	}
	
	.card {
	  flex: 1;
	  display: flex;
	  flex-direction: column;
	  width: 1260	px;
	}
	.button-container {
		display: flex;
		justify-content: center;
		gap: 1px;
		/* Add gap between buttons */
	}

	.button {
		flex: 0 1 auto;
		/* Allow buttons to shrink and grow as needed */
		width: 35%;
		/* Adjust the width as needed */
		height: 30px;
		display: flex;
		align-items: center;
		justify-content: center;
		padding: 0;
		/* Remove default padding */
		text-align: center;
		/* Center text */
		line-height: 30px;
		/* Ensure text is vertically centered */
		margin: 0 2px;
		/* Add margin to control spacing */
	}

	.button:first-child {
		margin-left: 0;
	}

	.button:last-child {
		margin-right: 0;
	}
</style>