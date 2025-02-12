<script setup>
	import {
		ref
	} from 'vue';
	import {
		Message
	} from 'element-ui';
</script>

<script>
	import {
		getAllVolunteerActivitiesByVolunteerId,
		getActivityByActivityId,
		getTeamById,
		getVolunteerServiceHoursPerActivity,
		getServiceRecord,
		deleteApplyForActivity,
		getVolunteerById
	} from "@/api/getData.js";
	import {
		getStorage,
		setStorage,
		delStorage
	} from '@/utils/localStorage.js'; // 引入缓存操作方法
	export default {
		data() {
			return {
				activityIds: [],
				//所有活动
				activities: [{
					applyState: ""
				}],
				//已加入
				activitiesState1: [{
					onGoing: "",
					teamName: ""
				}],
				//待审核
				activitiesState0: [],
				confirmDialogVisible: false,
				//存储当前选择的活动id
				selectedActivityId: null,
				// 用于控制骨架屏的显示
				loading: true,
				//证书模态框控制
				certificateDialogVisible: false,
				certificateImage: "",

				volunteerName: ""
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
			//根据志愿者Id获取志愿者信息
			getVolunteerByIdTo() {
				const volunteerId = getStorage('volunteerId');
				// const volunteerId = "v001";
				getVolunteerById({
					volunteerId
				}).then(response => {
					this.volunteerName = response.result.name;
				}).catch(error => {
					console.error('获取志愿者信息失败:', error);
				});
			},

			//获取志愿者加入的所有志愿活动
			getAllVolunteerActivitiesByVolunteerIdTo() {
				const volunteerId = getStorage('volunteerId');
				// const volunteerId = 'v001';
				getAllVolunteerActivitiesByVolunteerId({
						volunteerId
					}).then((res) => {
						this.activityIds = res.result.records.map(record => record.activityId);
						console.log("活动ID：", this.activityIds);
						this.getActivityByAIdTo();
					})
					.catch((error) => {
						console.error('获取活动ID失败:', error);
					});

			},
			//根据活动ID获取活动信息
			getActivityByAIdTo() {
				// console.log("调用了getActivityByAIdTo");
				this.activities = []; // 清空activities数组
				const currentDate = new Date().toISOString().split('T')[0]; // 获取当前日期并格式化
				const activityPromises = this.activityIds.map(activityId => {
					// console.log(`获取活动ID ${activityId} 的活动信息`);
					return getActivityByActivityId({
							activityId
						})
						.then((res) => {
							if (res.result) {
								this.activities.push(res.result);
								console.log("所有活动：", this.activities);

								const activity = res.result;
								//判断进行状态
								const startDate = new Date(activity.startDate).toISOString().split('T')[0];
								const endDate = new Date(activity.endDate).toISOString().split('T')[0];

								if (currentDate < startDate) {
									this.$set(activity, 'onGoing', '未开始');
								} else if (currentDate > endDate) {
									this.$set(activity, 'onGoing', '已结束');
								} else {
									this.$set(activity, 'onGoing', '进行中');
								};

								const teamId = activity.teamId;
								// console.log("teamId", teamId);
								//通过teamId获取负责团队名称

								const teamPromise = getTeamById({
										teamId
									})
									.then((res1) => {
										this.$set(activity, 'teamName', res1.result.name);
									})
									.catch((error) => {
										console.error(`获取团队Id ${activity.teamId} 的信息失败:`, error);
									});
								//获取每个活动的志愿时长
								const param = {
									volunteerId: getStorage('volunteerId'),
									// volunteerId: "v001",
									activityId: activity.activityId
								};
								const serviceHoursPromise = getVolunteerServiceHoursPerActivity(param)
									.then((res2) => {
										this.$set(activity, 'time', res2.result);
									});

								//获取志愿者申请每个活动的审核状态
								const applyStatePromise = getServiceRecord(param)
									.then((res3) => {
										this.$set(activity, 'applyState', res3.result.state);
									});
								return Promise.all([teamPromise, serviceHoursPromise, applyStatePromise]);
							} else {
								console.error(`活动ID ${activityId} 的活动信息返回为空`);
								return Promise.resolve(); // 返回一个已解决的Promise，以便继续处理下一个活动
							}
						})
						.catch((error) => {
							console.error(`获取活动ID ${activityId} 的活动信息失败:`, error);
							return Promise.resolve(); // 返回一个已解决的Promise，以便继续处理下一个活动
						});
				});
				//进行分类
				Promise.all(activityPromises)
					.then(() => {
						//所有活动信息获取完成后进行分类
						this.classifyActivities();
						console.log("分类后的活动信息：", this.activitiesState1, this.activitiesState0);
						this.loading = false; //加载完成，隐藏骨架屏
					})
					.catch((error) => {
						console.error('获取所有活动信息失败:', error);
						this.loading = false; //出错也需要隐藏骨架屏
					});
				// console.log("活动信息：", this.activities);
			},

			//将活动分为已加入和待审核
			classifyActivities() {
				this.activitiesState1 = [];
				this.activitiesState0 = [];
				this.activities.forEach(activity => {
					if (activity.applyState == 1) {
						this.activitiesState1.push(activity);
					} else if (activity.applyState == 0) {
						this.activitiesState0.push(activity);
					}
				});
			},

			// 打开确认修改对话框
			openConfirmDialog(activityId) {
				this.selectedActivityId = activityId;
				this.confirmDialogVisible = true;
			},

			//格式化日期
			formatDate(dateString) {
				const date = new Date(dateString);
				const year = date.getFullYear();
				const month = date.getMonth() + 1; // getMonth() 返回的月份从 0 开始
				const day = date.getDate();
				return `${year}年${month}月${day}日`;
			},

			//撤销参加活动申请
			deleteRecord() {
				const param = {
					volunteerId: getStorage('volunteerId'),
					// volunteerId: "v001",
					activityId: this.selectedActivityId
				};
				deleteApplyForActivity(param).then((res) => {
					if (res.code === 1) {
						this.$message.success('撤销成功');
						this.getAllVolunteerActivitiesByVolunteerIdTo(); // 重新获取活动列表
					} else {
						this.$message.error('撤销失败');
					}
					this.confirmDialogVisible = false;
				})
			},
			
			//绘制证书
			drawCertificate(activity, callback) {
				// 获取 <canvas> 元素
				const canvas = document.getElementById('certificateCanvas');
				// 获取 2D 绘图上下文
				const ctx = canvas.getContext('2d');

				// 清空画布
				ctx.clearRect(0, 0, canvas.width, canvas.height);

				// 绘制证书背景（假设有一个背景图）
				const background = new Image();
				background.src = require('@/assets/certificate.png');
				background.onload = () => {
					ctx.drawImage(background, 0, 0, canvas.width, canvas.height);

					// 绘制志愿者姓名
					ctx.font = '46px Arial';
					ctx.fillStyle = 'black';
					ctx.fillText(`${this.volunteerName}`, 200, 840);

					// 绘制志愿活动信息
					ctx.font = '40px Arial';
					ctx.fillText(`${this.formatDate(activity.startDate)}`, 290, 1030);
					ctx.fillText(`${this.formatDate(activity.endDate)}`, 630, 1030);
					ctx.fillText(`${activity.name}`, 200, 1110);

					// 绘制志愿时长
					ctx.fillText(`${activity.time} `, 380, 1190);

					// 绘制当前日期
					ctx.font = '34px Arial';
					const currentDate = new Date().toISOString().split('T')[0];
					ctx.fillText(`${this.formatDate(currentDate)} `, 145, 1535);
					// 绘制单位
					ctx.fillText(`重庆大学志愿者服务队`, 610, 1535);

					// 加载并绘制公章
					const overlayImage = new Image();
					overlayImage.src = require('@/assets/seal.png');
					overlayImage.onload = () => {
						ctx.drawImage(overlayImage, 650, 1400, 280, 280); // 调整位置和大小

						// 执行回调函数
						callback(canvas);
					};
					overlayImage.onerror = (error) => {
						console.error("叠加公章加载错误:", error);

						// 即使叠加图像加载失败，也执行回调函数
						callback(canvas);
					};
				};
				background.onerror = (error) => {
					console.error("图像加载错误:", error);
				};
			},
			//查看志愿证书
			viewCertificate(activity) {
				this.drawCertificate(activity, (canvas) => {
					// 显示证书图片
					this.certificateImage = canvas.toDataURL('image/png');
					this.certificateDialogVisible = true;
				});
			},
			//下载志愿证书
			downloadCertificate(activity) {
				this.drawCertificate(activity, (canvas) => {
					// 触发下载
					// 动态创建一个 <a> 元素（超链接）
					const link = document.createElement('a');
					// 将图片嵌入到 href 属性
					link.href = canvas.toDataURL('image/png');
					link.download = '我的志愿证书.png';
					link.click();
				});
			}
		},
		mounted() {
			this.getVolunteerByIdTo();
			this.getAllVolunteerActivitiesByVolunteerIdTo();
			this.confirmDialogVisible = false;
		}
	}
</script>

<template>
	<div>
		<el-card>
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
						<el-table v-if="!loading" :data="activitiesState1" style="width: 100%" :stripe="true">
							<!-- <el-table-column prop="activityId" label="" width="20" header-align="center"
							align="center"></el-table-column> -->
							<el-table-column prop="name" label="活动名称" width="150" header-align="center"
								align="center"></el-table-column>
							<el-table-column prop="location" label="地址" width="120" header-align="center"
								align="center"></el-table-column>
							<el-table-column prop="content" label="活动内容" width="180" header-align="center"
								align="center"></el-table-column>
							<el-table-column prop="teamName" label="负责团队" width="110" header-align="center"
								align="center"></el-table-column>
							<el-table-column prop="onGoing" label="活动状态" header-align="center" align="center">
								<template slot-scope="scope">
									<span class="status-text">
										<i :class="{
							                    'el-icon-time': scope.row.onGoing === '未开始',
							                    'el-icon-circle-check': scope.row.onGoing === '进行中',
							                    'el-icon-circle-close': scope.row.onGoing === '已结束'
							                }"></i>
										{{ scope.row.onGoing }}
									</span>
								</template>
							</el-table-column>
							<el-table-column prop="time" label="志愿时长/小时" width="120" header-align="center"
								align="center">
								<template slot-scope="scope">
									{{ scope.row.time ? scope.row.time : '-' }}
								</template>
							</el-table-column>
							<el-table-column label="志愿证书" width="200" header-align="center" align="center">
								<template slot-scope="scope">
									<div class="button-container">
										<el-button icon="el-icon-search" class="button" type="success"
											@click="viewCertificate(scope.row)">查看</el-button>
										<el-button type="primary" icon="el-icon-download" class="button"
											@click="downloadCertificate(scope.row)">下载</el-button>
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
						<el-table v-if="!loading" :data="activitiesState0" style="width: 100%" :stripe="true">
							<el-table-column prop="activityId" label="活动编号" width="80" header-align="center"
								align="center"></el-table-column>
							<el-table-column prop="name" label="活动名称" width="150" header-align="center"
								align="center"></el-table-column>
							<el-table-column prop="location" label="地址" width="150" header-align="center"
								align="center"></el-table-column>
							<el-table-column prop="content" label="活动内容" width="200" header-align="center"
								align="center"></el-table-column>
							<el-table-column prop="teamName" label="负责团队" header-align="center"
								align="center"></el-table-column>
							<el-table-column label="操作" width="200" header-align="center" align="center">
								<template slot-scope="scope">
									<div class="button-container">
										<el-button type="danger" icon="el-icon-refresh-right" class="button"
											@click="openConfirmDialog(scope.row.activityId)">撤销</el-button>
									</div>
								</template>
							</el-table-column>
						</el-table>
					</el-skeleton>
				</el-tab-pane>
			</el-tabs>
		</el-card>

		<!-- 确认撤销对话框 -->
		<el-dialog title="确认撤销" :visible.sync="confirmDialogVisible" width="30%">
			<span>确定要撤销活动申请吗？</span>
			<span slot="footer" class="dialog-footer">
				<el-button @click="confirmDialogVisible = false">取消</el-button>
				<el-button type="danger" @click="deleteRecord">确定</el-button>
			</span>
		</el-dialog>

		<!-- 志愿证书模态框 -->
		<el-dialog title="志愿证书" :visible.sync="certificateDialogVisible" width="50%">
			<img :src="certificateImage" alt="志愿证书" style="width: 100%; height: auto;">
			<span slot="footer" class="dialog-footer">
				<el-button @click="certificateDialogVisible = false">关闭</el-button>
			</span>
		</el-dialog>

		<canvas id="certificateCanvas" width="1080" height="1920" style="display: none;"></canvas>

	</div>
</template>

<style scoped>
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