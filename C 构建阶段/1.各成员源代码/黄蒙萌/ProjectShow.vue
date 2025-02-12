<script>
	import {
		cities
	} from '@/data/cities.js';
	import {
		getAllActivities,
		getTeamById,
		joinActivity,
		checkVolunteerActivity
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
		inject: ['reload'],
		name: 'VolunteerActivity',
		data() {
			return {
				page: 1,
				limit: 4,
				total: 0,
				cities: cities,
				districts: [],
				status: '',
				category: '',
				activities: [],
				activity: {
					name: "",
					teamName: "",
					joinOrNot: 0,
					locations: ""
				},
				finishColor: "#A9A9A9",
				underprogressColor: "#5cb87a",
				startColor: "#ff0000",
				loading: true,
				selectedCity: ''
			};
		},
		created () {
			if (this.$route.query.search) {
				this.activity.name = this.$route.query.search;
			}
		},
		methods: {
			// 获取活动数据方法
			getActivities() {
				this.page = 1; // 搜索时始终从第一页开始显示
				this.getAllActivitiesTo(); // 调用获取活动数据的方法
			},

			handleSizeChange(val) {
				this.limit = val;
				this.getAllActivitiesTo();
			},
			handleCurrentChange(val) {
				this.page = val;
				this.getAllActivitiesTo();
			},
			getActivityName() {
				this.page = 1;
				this.getAllActivitiesTo();
			},
			getAllActivitiesTo() {
				const params = {
					page: this.page,
					limit: this.limit,
					name: this.activity.name,
					location: `${this.selectedCity}${this.activity.locations}`,
					// status: this.activity.status, //未开始、进行中、已结束
					sort: this.activity.sort
				};
				console.log("params", params);
				//传给后端进行分页
				getAllActivities(params).then((res) => {
					// console.log('响应数据:', res); // 打印整个响应
					// console.log('活动数据:', res.result.activities); // 打印活动数据
					this.activities = res.result.activities;
					this.total = res.result.total;
					const currentDate = new Date();

					let avatarLoadCount = 0;
					const totalActivities = this.activities.length;

					// 遍历活动数据
					this.activities.forEach(activity => {

						//获取每个活动的 teamId
						// console.log('activity.teamId',activity.teamId)
						this.getTeamById({
							teamId: activity.teamId
						}).then(res => {
							// console.log('团队名称:',res.result.name);
							// 将团队信息赋值给 activity 对象,使用 Vue.set 保证 reactivity 系统能检测到新属性
							this.$set(activity, 'teamName', res.result.name);

							const startDate = new Date(activity.startDate);
							const endDate = new Date(activity.endDate);

							if (currentDate < startDate) {
								this.$set(activity, 'status', '未开始');
							} else if (currentDate > endDate) {
								this.$set(activity, 'status', '已结束');
							} else {
								this.$set(activity, 'status', '进行中');
								const percent = ((currentDate - startDate) / (endDate -
									startDate) * 100).toFixed(1); // 保留四位小数
								this.$set(activity, 'underprogress', parseFloat(
									percent)); // 将字符串转换为浮点数
							}
						});

						const param = {
							volunteerId: getStorage('volunteerId'),
							// volunteerId: 'v001',
							activityId: activity.activityId
						}
						//判断是否已经加入该活动
						if (param.volunteerId == '') {
							this.$set(activity, 'joinOrNot', 0);
						} else {
							// console.log('Checking status for activity:', activity.activityId); // 添加调试信息
							checkVolunteerActivity(param).then((res) => {
								if (res.result === 0) {
									this.$set(activity, 'joinOrNot', 0); //待审核
								} else if (res.result === 1) {
									this.$set(activity, 'joinOrNot', 1); //已加入
								} else if (res.result === 2) {
									this.$set(activity, 'joinOrNot', 2); // 被拒绝
								} else if (res.result === 3) {
									this.$set(activity, 'joinOrNot', 3); // 未加入
								}
							}).catch(() => {
								this.$set(activity, 'joinOrNot', 0); // 处理错误情况，假设未加入
							}).finally(() => {
								avatarLoadCount++;
								if (avatarLoadCount === totalActivities) {
									this.loading = false;
								}
							});
						}
					});
					if (totalActivities === 0) {
						this.loading = false;
					}
				}).catch(() => {
					this.loading = false;
				});
			},
			// 根据 teamId 获取 teamName 的方法
			getTeamById(teamId) {
				return getTeamById(teamId).then(res => {
					return res;
				});
			},
			joinActivity(activityId) {
				const volunteerId = getStorage('volunteerId');
				//检查是否登录
				if (!volunteerId) {
					router.push('/site/login');
				} else {
					joinActivity({
						volunteerId,
						activityId
					}).then(res => {
						if (res.code === 1) {
							this.$message.success('申请加入成功');
							// this.reload();
							this.getAllActivitiesTo();
						} else {
							this.$message.error('申请加入失败');
						}
					});
				}
			},
			handleCityChange() {
				const selectedCity = this.cities.find(city => city.name === this.selectedCity);
				this.districts = selectedCity ? selectedCity.districts : [];
				this.activity.locations = '';
			},
			// 获取活动状态对应的 class
			getHeaderClass(activity) {
				switch (activity.status) {
					case '未开始':
						return activity.joinOrNot === 0 ? 'not-started' : 'joined';
					case '进行中':
						return 'in-progress';
					case '已结束':
						return 'ended';
					default:
						return '';
				}
			}
		},
		mounted() {
			this.getAllActivitiesTo();
			// this.loading = false;
			// setStorage('volunteerId','v001');
		}
	}
</script>


<template>
	<div>
		<!-- <pre>{{activities}}</pre> -->
		<!-- 搜索框 -->
		<div class="center-container-activity">
			<div class="search-container-activity">
				<el-input v-model="activity.name" placeholder="请输入活动名称进行搜索" @change="getActivities">
					<el-button slot="append" icon="el-icon-search" @click="getActivities"></el-button>
				</el-input>
			</div>
		</div>

		<!-- 筛选条件 -->
		<div class="center-container-activity">
			<div class="filter-container-activity">
				<el-select v-model="activity.sort" placeholder="请选择活动类别" @change="getActivities">
					<el-option label="" value=""></el-option>
					<el-option label="教育类" value="教育类"></el-option>
					<el-option label="安全类" value="安全类"></el-option>
					<el-option label="服务类" value="服务类"></el-option>
				</el-select>
				<el-select v-model="selectedCity" placeholder="请选择城市" @change="handleCityChange">
					<el-option label="" value=""></el-option>
					<el-option v-for="city in cities" :key="city.name" :label="city.name"
						:value="city.name"></el-option>
				</el-select>
				<el-select v-model="activity.locations" placeholder="请选择区" @change="getActivities">
					<el-option label="" value=""></el-option>
					<el-option v-for="district in districts" :key="district" :label="district"
						:value="district"></el-option>
				</el-select>
			</div>
		</div>


		<!-- 卡片列表 -->
		<div class="card-container-activity" v-if="activities != null" v-loading="loading">
			<el-card v-for="activity in activities" :key="activity.activityId" class="box-card-activity">
				<!-- 卡片内容 -->
				<div slot="header" :class="getHeaderClass(activity)" class="clearfix-activity">
					<span>{{ activity.name }}</span>
					<el-button v-if="activity.status === '未开始' && activity.joinOrNot === 3"
						style="float: right; padding: 3px 0" type="text"
						@click="joinActivity(activity.activityId)">加入</el-button>
					<el-button v-else-if="activity.status === '未开始' && activity.joinOrNot === 1"
						style="float: right; padding: 3px 0" type="text" :disabled="true"
						@click="joinActivity(activity.activityId)">已加入</el-button>
					<el-button v-else-if="activity.status === '未开始' && activity.joinOrNot === 2"
						style="float: right; padding: 3px 0" type="text" :disabled="true"
						@click="joinActivity(activity.activityId)">被拒绝</el-button>
					<el-button v-else-if="activity.status === '未开始' && activity.joinOrNot === 0"
						style="float: right; padding: 3px 0" type="text" :disabled="true"
						@click="joinActivity(activity.activityId)">待审核</el-button>
					<el-button v-else-if="activity.status === '进行中'" :disabled="true"
						style="float: right; padding: 3px 0" type="text"
						@click="joinActivity(activity.activityId)">活动已开始</el-button>
					<el-button v-else="activity.status === '已结束'" :disabled="true" style="float: right; padding: 3px 0"
						type="text" @click="joinActivity(activity.activityId)">活动已结束</el-button>
				</div>

				<!-- 卡片内容 -->
				<el-row class="text item">
					<el-col :span="24">
						<p><strong>👪活动团队:</strong> {{ activity.teamName }}</p>
						<p><strong>🚩活动地点:</strong> {{ activity.location }}</p>
						<p><strong>📖内容:</strong> {{ activity.content }}</p>
						<el-divider style="margin: 8px 0; margin-bottom: 5px;"></el-divider>
					</el-col>

					<el-col :span="24">
						<el-row
							style="height: 70px; display: flex; align-items: center;flex-direction: row;justify-content: space-between;">
							<el-col :span="12">
								<div
									style=" display: flex; align-items: center;flex-direction: column;justify-content: space-between;">
									<p
										style="display: flex; align-items: center;flex-direction: column;justify-content: space-around;">
										<strong>📅开始</strong><br>{{ activity.startDate }}
									</p>
								</div>
							</el-col>
							<el-divider direction="vertical" style="height: 100%;"></el-divider>
							<el-col :span="12">
								<div
									style=" display: flex; align-items: center;flex-direction: column;justify-content: space-between;">
									<p
										style="display: flex; align-items: center;flex-direction: column;justify-content: space-around;">
										<strong>📆结束</strong><br>{{ activity.endDate }}
									</p>
								</div>
							</el-col>
						</el-row>

					</el-col>

					<el-col :span="24">
						<el-divider style="margin: 8px 0;"></el-divider>
						<el-row
							style="display: flex; align-items: center;flex-direction: row;justify-content: space-between;">
							<el-col :span="8">
								<div
									style="font-size: 16px; display: flex; align-items: center;flex-direction: row;justify-content: space-between;">
									<el-tag v-if="activity.sort === '教育类'" effect="light">{{ activity.sort }}</el-tag>
									<el-tag v-else-if="activity.sort === '服务类'" type="success"
										effect="light">{{ activity.sort }}</el-tag>
									<el-tag v-else-if="activity.sort === '安全类'" type="warning"
										effect="light">{{ activity.sort }}</el-tag>
								</div>
							</el-col>
							<el-divider direction="vertical"></el-divider>
							<el-col :span="8">
								<div
									style="display: flex; align-items: center;flex-direction: column;justify-content: space-between; font-size: 16px;">
									🏃‍♂️{{ activity.numberVolunteers }}人
								</div>
							</el-col>
							<el-divider direction="vertical"></el-divider>
							<el-col :span="8">
								<div v-if="activity.status === '未开始'"
									style="display: flex; align-items: center;flex-direction: column;justify-content: space-between;">
									🟡{{ activity.status }}
								</div>
								<div v-else-if="activity.status === '进行中'"
									style="display: flex; align-items: center;flex-direction: column;justify-content: space-between;">
									🟢{{ activity.status }}
								</div>
								<div v-else-if="activity.status === '已结束'"
									style="display: flex; align-items: center;flex-direction: column;justify-content: space-between;">
									🔴️{{ activity.status }}
								</div>
							</el-col>
						</el-row>
					</el-col>
				</el-row>

				<!-- 进度条 -->
				<div class="progress-container">
					<el-row type="flex" justify="center">
						<el-progress v-if="activity.status === '未开始'" :percentage="0" :color="startColor"
							style="width: 250px;">
							<span slot="format">未开始</span>
						</el-progress>
						<el-progress v-else-if="activity.status === '已结束'" :percentage="100" :color="finishColor"
							style="width: 250px;">
							<span slot="format">已结束</span>
						</el-progress>
						<el-progress v-else-if="activity.status === '进行中'" :percentage="activity.underprogress"
							:color="underprogressColor" style="width: 250px;">
							<span slot="format">进行中</span>
						</el-progress>
					</el-row>
				</div>
			</el-card>
		</div>
		<div v-else>
			<p>正在加载活动数据...</p>
		</div>
		<div class="pagination-container-activity">
			<!-- 分页组件 -->
			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page"
				:page-size="limit" :page-sizes="[4, 8, 10]" layout="total, sizes, prev, pager, next, jumper"
				:total="total">
			</el-pagination>
		</div>

	</div>
</template>


<style>
	.center-container-activity {
		display: flex;
		justify-content: center;
		align-items: center;
		width: 100%;
		margin-bottom: 20px;
	}

	.search-container-activity {
		display: flex;
		width: 35%;
	}

	.filter-container-activity {
		display: flex;
		gap: 20px;
	}

	.card-container-activity {
		display: flex;
		flex-wrap: wrap;
		justify-content: flex-start;
		padding: 0px;
		gap: 20px;
		/* 添加卡片之间的间距 */
		margin-left: 10px;
	}

	.box-card-activity {
		width: calc(25% - 20px);
		margin-bottom: 20px;
	}

	.not-started {
		background-color: #ffffe0;
	}

	.joined {
		background-color: #ffffcc;
	}

	.in-progress {
		background-color: #ccffcc;
	}

	.ended {
		background-color: #f0f0f0;
	}

	.text {
		font-size: 14px;
	}

	.item {
		margin-bottom: 18px;
	}

	.clearfix-activity {
		position: relative;
		padding: 10px;
		border-radius: 5px;
		margin-bottom: 10px;
	}

	.clearfix-activity:before,
	.clearfix-activity:after {
		display: table;
		content: "";
	}

	.clearfix-activity:after {
		clear: both
	}

	.box-card-activity:last-child {
		/* 最后一个卡片没有右边距 */
		/* margin-right: 0; */
	}

	.pagination-container-activity {
		text-align: center;
		margin-top: 30px;
		margin-bottom: 30px;
	}

	.progress-container {
		display: flex;
		justify-content: center;
		width: 100%;
		margin-bottom: 15px;
		margin-top: 10px;
	}

	/* 	.progress-container {
		position: relative;
		width: 100%;
		margin-top: 10px;
	} */

	.progress-container>>>.el-progress {
		width: 100%;
	}

	.progress-container>>>.el-progress-bar__outer {
		height: 26px;
		/* width: 70%; */
		/* 调整宽度以居中 */
		margin: auto;
		/* 居中对齐 */
		width: 100%;
	}

	.progress-container>>>.el-progress-bar__inner {
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.progress-container>>>.el-progress-bar__innerText {
		position: absolute;
		right: 0;
		margin-right: 200px;
		/* 调整百分比数字距离进度条末端的距离 */
	}

	.el-tag {
		margin-left: 7px;
	}
</style>