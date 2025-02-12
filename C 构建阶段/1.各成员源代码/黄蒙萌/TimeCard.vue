<script>
	import * as echarts from 'echarts';

	export default {
		name: "TimeCard",
		props: {
			time: {
				type: String,
				required: true
			}
		},
		data() {
			return {
				show2: false,
				numericTime: 0
			};
		},
		methods: {},
		mounted() {
			console.log("inner time:",this.time);
			this.show2 = true;
			// this.numericTime = parseFloat(this.time); // 将字符串时间转换为浮点数 
			// console.log("numericTime:",this.numericTime);
		}
	}
</script>
<template>
	<div>
		<el-card class="el-card1">
			<transition name="el-zoom-in-top">
				<el-card shadow="hover" v-show="show2">
					<el-col>
						<el-row>
							<div class="text">总时长(小时)
							</div>
						</el-row>
						<el-row>
							<p class="data">
								🎉{{ parseFloat(time).toFixed(1) }}
							</p>
						</el-row>
					</el-col>
				</el-card>
			</transition>
		</el-card>
	</div>
</template>


<style scoped>
	.data {
		color: black;
		font-weight: bold;
		font-size: xx-large;
		display: flex;
		align-items: center;
		flex-direction: column;
		justify-content: space-around;
	}

	.text {
		color: black;
		font-size: 16px;
		margin-bottom: 0px;
	}

	.el-card1 {
		width: 243px;
		margin: 0px;
		border-radius: 8px;
		overflow: hidden;
		/* 防止内容溢出 */
		/* height: 230px; */
		
	}

</style>