<script>
	import * as echarts from 'echarts';

	export default {
		name: "Percent",
		props: {
			percent: {
				type: String,
				required: true
			}
		},
		data() {
			return {
				number: 0,
				show2: false
			};
		},
		watch: {
			percent: 'initChart'
		},
		methods: {
			initChart() {
				const chartDom = this.$refs.chart;
				if(this.chart == null) {
					this.chart = echarts.init(chartDom);
				}

				if(this.percent) {
					this.number = parseInt(this.percent);
				}

				const option = {
					title: {
						text: `${this.number}%`,
						left: 'center',
						top: 'center'
					},
					series: [{
						type: 'pie',
						data: [{
							value: this.number,
							name: '超越',
							itemStyle: {
								// color: '#55aaff',
								shadowBlur: 4, // 阴影的模糊大小  
								shadowColor: 'rgba(0, 0, 0, 0.5)', // 阴影颜色  
								shadowOffsetX: 0, // 阴影水平偏移  
								shadowOffsetY: 0 // 阴影垂直偏移 
							},
							label: {
								show: false
							}
						}, {
							value: (100 - this.number),
							name: '剩余',
							itemStyle: {
								color: '#f0f5ff',
							},
							tooltip: {
								show: false
							},
							label: {
								show: false
							}
						}],
						radius: ['50%', '78%'],
						// backgroundColor: '#ffffff'
					}]
				};

				this.chart.setOption(option);
			}
		},
		mounted() {
			this.initChart();
			this.show2 = true;
		}
	}
</script>

<template>
	<div>
		<el-card>
			<transition name="el-zoom-in-top" v-show="show2">
				<el-col>
					<el-row>
						<div class="text"> 您已超过✔️
						</div>
					</el-row>
					<el-row>
						<div ref="chart" style="width: 100%; height: 180px;"></div>
					</el-row>
					<el-row>
						<div class="text2">志愿者
						</div>
					</el-row>
				</el-col>
			</transition>
		</el-card>
	</div>
</template>

<style scoped>
	.text {
		color: black;
		font-size: 17px;
		margin-bottom: 0px;
	}

	.text2 {
		color: black;
		font-size: 15px;
		margin-bottom: 0px;
		display: flex;
		justify-content: right;
	}

	.el-card {
		margin-top: 20px;
		margin-bottom: 20px;
		border-radius: 8px;
		overflow: hidden;
		/* 防止内容溢出 */
		width: 243px;
		height: 268px;
	}
</style>