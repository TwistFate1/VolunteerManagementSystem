<script setup>

</script>

<script>
	import * as echarts from 'echarts';
	import {
		getHoursPerDayForYear
	} from '@/api/getData.js'
	import {
		getStorage,
		setStorage,
		delStorage
	} from '@/utils/localStorage.js'; // 引入缓存操作方法
	export default {
		name: 'TimeHeatMap',
		data() {
			return {
        volunteerId: null,
				year: '2024',
        pick: '',
				myChart: null,
				data: [],
				heatMapWidth: '950px',
				heatMapHeight: '250px'
			}
		},
		mounted() {
      this.volunteerId = getStorage('volunteerId');
			this.getVirtualData();
		},
		methods: {
			getVirtualData() {
				const params = {
					volunteerId: this.volunteerId,
					year: parseInt(this.year)
				};

				// 清空旧数据，准备接收新数据
				this.data = [];

				getHoursPerDayForYear(params).then((res) => {
					// 直接赋值，以确保Vue可以检测到数组的变化
					this.data = res.result.map(item => {
						return [item.date, item.time];
					});
					// 确保所有数据都添加后再调用绘制函数
					this.drawHeatMap();
				}).catch((error) => {
					console.error("获取热力图数据失败：", error);
					this.$message.error('获取热力图数据失败');
				});
			},
			drawHeatMap() {
				var option;

        if (this.myChart == null) {
          this.myChart = echarts.init(document.getElementById('main'));
        }
				
				option = {
					title: {
						top: 30,
						left: 'center',
						text: '志愿服务贡献图'
					},
					tooltip: {},
					visualMap: {
						min: 0,
						max: 40,
						type: 'piecewise',
						orient: 'horizontal',
						left: 'center',
						top: 65
					},
					calendar: {
						top: 120,
						left: 30,
						right: 30,
						cellSize: ['auto', 13],
						range: this.year,
						itemStyle: {
							borderWidth: 0.5
						},
						yearLabel: {
							show: false
						}
					},
					series: {
						type: 'heatmap',
						coordinateSystem: 'calendar',
						data: this.data
					}
				};
				this.myChart.setOption(option);
			},
      handleChange () {
        // Mon Jan 01 2024 00:00:00 GMT+0800 (中国标准时间)
        // 转化
        var date = new Date(this.pick);

        this.year = date.getFullYear();
        // 更新数据
        this.getVirtualData();
      }
		}
	}
</script>

<template>
	<div>
		<el-card>
      <div class="block">
        <el-date-picker v-model="pick" type="year" placeholder="选择年" @change="handleChange"></el-date-picker>
        <el-tag size="medium">年</el-tag>
        <el-tag type="success">{{ this.volunteerId }}</el-tag>
        <el-tag size="medium">志愿者</el-tag>
      </div>
			<div id="main" style="display: flex;height: auto" :style="{ width: heatMapWidth,height: heatMapHeight }">
			</div>
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
  .block {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: left;
  }
</style>