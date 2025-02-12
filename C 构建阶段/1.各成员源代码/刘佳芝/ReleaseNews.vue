<template>
	<div>
		<!-- <div>
			<el-button @click="aaa"></el-button>
		</div> -->
		<el-card>

			<el-card>
				<div slot="header" class="clearfix">
					<span style="color: red; font-weight: bold; font-size: 20px;">新闻内容</span>

				</div>
				<el-form ref="news" :model="news" :rules="rules" label-width="100px">
					<el-form-item label="新闻标题" prop="title">
						<el-input v-model="news.title"></el-input>
					</el-form-item>
					<el-form-item label="发布时间" prop="newsTime">
						<el-date-picker v-model="news.newsTime" type="date" placeholder="选择日期">
						</el-date-picker>
					</el-form-item>
					<el-form-item label="标签选择" prop="tag">
						<el-select v-model="news.tag" placeholder="请选择新闻标签">
							<el-option v-for="tag in serviceTags" :key="tag.value" :label="tag.label"
								:value="tag.value">
								</el-option>
						</el-select>
					</el-form-item>

				</el-form>
			</el-card>
			<el-card>
				<!-- <h2 style="color: darkred;">新闻正文</h2> -->
				<div slot="header" class="clearfix">
					<span style="color: red; font-weight: bold; font-size: 20px;">新闻正文</span>

				</div>
				<el-form ref="news" :model="news" :rules="rules">
					<el-form-item class="editMember"
						style="display: flex; justify-content: center; align-items: center;" prop="content">
						<!-- <div style="height: 100%; width: 100%; display: flex; justify-content: center; align-items: center;">  -->
						<Tinymce :height="200" v-model="news.content" style="max-width: 100%;"></Tinymce>
						<!-- </div> -->
					</el-form-item>
					<!-- <el-form-item class="editMember">
					  <Tinymce :height="200" v-model="news.value"></Tinymce>
				</el-form-item> -->
				</el-form>
				<el-divider></el-divider>
				<div class="button-container"
					style="display: flex; justify-content: flex-end; margin-top: 0;width: 100%;">
					<el-button type="danger" style="width: 15%;" @click="Onsubmit('news')">提交</el-button>
				</div>

				<!-- <div class="editMember">
			  <p class="editMember-title">Tinymce</p>
			  <Tinymce :height="200" v-model="news.value"></Tinymce>
			</div> -->

			</el-card>


		</el-card>





	</div>

</template>

<script>
	
	import  {uploadNews} from '@/api/getData.js';
	import {
		getStorage,
		setStorage,
		delStorage
	} from '@/utils/localStorage.js';
	import Tinymce from '../../../views/Tinymce.vue';
	export default {
		components: {
			Tinymce,
		},
		data() {
			return {
				 serviceTags: [  
				        { value: '环保志愿服务', label: '环保志愿服务' },  
				        { value: '助老助残志愿服务', label: '助老助残志愿服务' },  
				        { value: '支教助学志愿服务', label: '支教助学志愿服务' },  
				        { value: '社区志愿服务', label: '社区志愿服务' },  
				        { value: '医疗救助志愿服务', label: '医疗救助志愿服务' },  
				        { value: '应急救援志愿服务', label: '应急救援志愿服务' },  
				        { value: '文化传承志愿服务', label: '文化传承志愿服务' },  
				        { value: '法律援助志愿服务', label: '法律援助志愿服务' },  
				        { value: '心理健康志愿服务', label: '心理健康志愿服务' },  
				        { value: '科技创新志愿服务', label: '科技创新志愿服务' }  
				      ],

				news: {
					"teamId": '',
					"newsTime": '',
					"headline": '0',
					"state": '0',
					"title": '',
					"tag": '',
					"content": undefined,
				},
				rules: {
					title: [{
						required: true,
						message: '请输入新闻标题',
						trigger: 'blur'
					}, ],
					newsTime: [{
						type: 'date',
						required: true,
						message: '请选择日期',
						trigger: 'change'
					}],
					tag: [{
						required: true,
						message: '请选择标签',
						trigger: 'change'
					}],
					content: [{
						required: true,
						message: '请输入新闻内容',
						trigger: 'blur'
					}, ]

				}
			};
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
			editorNews() {
				this.news.teamId = getStorage('teamId');
				console.log(this.news.teamId)
			},
			// 提交内容
			Onsubmit(formName) {
				console.log("提交")
				this.$refs[formName].validate((valid) => {
					if (valid) {
						
						// alert('submit!');
						this.$confirm('是否确认提交?', '提示', {
							confirmButtonText: '确定',
							cancelButtonText: '取消',
							type: 'warning'
						}).then(() => {
							console.log(this.formName)
							console.log(this.news)
							
							uploadNews(this.news).then((res)=>{
								console.log(res)
							});
							this.$message({
								type: 'info',
								message: '提交成功'
							});
							location.reload ();
							
							// this.reload();
						}).catch(() => {
							this.$message({
								type: 'info',
								message: '已取消'
							});
						});
					} else {
						console.log('error submit!!');
						return false;
					}
				});

			},

			aaa() {
				this.news.teamId = getStorage('teamId');
				console.log("团队账号是")
				console.log(this.news.teamId)
			},
			// 保存富文本内容-保存字符串
			add() {

				const params = `this.tiny?.value`;
			},
			edit() {
				// 接口回显保存html
				// this.tiny.value = res.data|| ''
			},
		},
		mounted() {
			this.editorNews();
		}
	}
</script>
<style scoped>
	.clearfix:before,
	.clearfix:after {
		display: table;
		content: "";
	}

	.clearfix:after {
		clear: both
	}
</style>