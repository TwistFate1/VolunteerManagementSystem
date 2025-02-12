<script>
	import * as echarts from 'echarts';

	export default {
		name: 'ServiceHours',
		props: {
			hoursData: {
				type: Object,
				required: true
			},
			years: {
				type: Array,
				required: true
			}
		},
		data() {
			return {
				chart: null,
				selectedYear: this.years.length > 0 ? this.years[this.years.length-1] : ''
			};
		},
		watch: {
			selectedYear: 'updateChart',
			hoursData: 'updateChart'
		},
		mounted() {
			this.initChart();
		},
		methods: {
			initChart() {
				const chartDom = this.$refs.chart;
				this.chart = echarts.init(chartDom);

				const option = {
					title: {
						text: '累计志愿时长/小时',
						left: 'center'
					},
					tooltip: {
						trigger: 'axis',
						axisPointer: {
							type: 'shadow'
						}
					},
					xAxis: {
						type: 'category',
						data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
					},
					yAxis: {
						type: 'value'
					},
					series: [{
						name: '累计时长',
						type: 'bar',
						barWidth: '70%',
						itemStyle: {
							color: '#55aaff',
							borderRadius: [5, 5, 0, 0] // 柱子的圆角  
						},
						data: this.hoursData[this.selectedYear] || []
					}]
				};

				this.chart.setOption(option);
			},
			//选择年份
			updateChart() {
				if (this.chart) {
					const option = {
						series: [{
							data: this.hoursData[this.selectedYear] || []
						}]
					};
					this.chart.setOption(option);
				}
			}
		}
	};
</script>


<template>
	<div>
		<el-card>
			<el-select v-model="selectedYear" placeholder="选择年份" @change="updateChart" style="width: 120px;">
				<el-option v-for="year in years" :key="year" :label="year" :value="year" />
			</el-select>
			<div ref="chart" style="width: 100%; height: 400px;"></div>
		</el-card>
	</div>
</template>


<style scoped>
	.el-card {
		margin: 20px;
		border-radius: 8px;
		overflow: hidden;
		/* 防止内容溢出 */
	}

	/* 如果需要，可以为图表容器添加一些边距或填充 */
	.el-card>div {
		margin: 20px;
	}
</style>