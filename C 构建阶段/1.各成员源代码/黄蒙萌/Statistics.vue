<script>
	import ServiceHours from '@/page/volunteer/statistics/components/ServiceHours.vue';
	import Percent from '@/page/volunteer/statistics/components/Percent.vue';
	import TimeCard from '@/page/volunteer/statistics/components/TimeCard.vue';
	import TimeHeatMap from '@/page/volunteer/statistics/components/TimeHeatMap.vue';
	import axios from 'axios';
	import {
		getServiceHourStatistics,
		getPercentOfVolunteerServiceHours,
		getHoursPerDayForYear
	} from '@/api/getData.js'
	import {
		getStorage,
		setStorage,
		delStorage
	} from '@/utils/localStorage.js'; // 引入缓存操作方法

	export default {
		components: {
			ServiceHours,
			Percent,
			TimeCard,
			TimeHeatMap
		},
		data() {
			return {
				//柱状图
				years: ['2020', '2021', '2022', '2023', '2024'],
				volunteerHours: {},
				//环状图
				percent: '',
				time:''
			};
		},
		methods: {
			fetchVolunteerHours() {
				// const volunteerId = getStorage("volunteerId");
				const volunteerId = 'v002';
				//获取志愿者每年每月累计志愿时长
				getServiceHourStatistics({
					volunteerId
				}).then((res) => {
					if (res && res.result) {
						// console.log("res.result.volunteerHours:", res.result.volunteerHours);
						this.volunteerHours = res.result.volunteerHours;
					} else {
						console.error("Unexpected response structure:", res);
						this.$message.error('查询数据失败');
					}
				}).catch((error) => {
					console.error("API call failed:", error);
					this.$message.error('查询数据失败');
				});
				//获取志愿者超过多少人
				getPercentOfVolunteerServiceHours({
					volunteerId
				}).then((res) => {
					if (res && res.result) {
						console.log("res.result.percent:", res.result.percent);
						this.percent = res.result.percent.toString();
						this.time = res.result.time.toString();
						console.log("this.time:",this.time);
					} else {
						console.error("Unexpected response structure:", res);
						this.$message.error('查询数据失败');
					}
				}).catch((error) => {
					console.error("API call failed:", error);
					this.$message.error('查询数据失败');
				});
			},

		},
		mounted() {
			this.fetchVolunteerHours();
		}
	};
</script>

<template>
	<div>
		<el-card>
			<el-row>
				<el-col :span="18">
					<ServiceHours :hoursData="volunteerHours" :years="years" />
				</el-col>
				<el-col :span="6" style="display: flex; flex-direction: column; justify-content: space-between; height: 100%;">
					<el-row style="display: flex; align-items: center;">
						<Percent :percent="percent"/>
					</el-row>
					<el-row style="display: flex; align-items: center;">
						<TimeCard :time="time"></TimeCard>
					</el-row>
				</el-col>
			</el-row>
			<el-row style="display: flex;align-items: center">
				<TimeHeatMap></TimeHeatMap>
			</el-row>
		</el-card>
	</div>
</template>

<style scoped>
</style>