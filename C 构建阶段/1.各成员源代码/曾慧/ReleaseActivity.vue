<template>
	<div>
	<el-card>
		<el-card>
			<el-form ref="activity" :model="activity" :rules="rules" label-width="80px" style="text-align: center;margin: 2%; margin-right: 30%;">
			  <el-form-item label="活动名称" prop="name">
			    <el-input v-model="activity.name"></el-input>
			  </el-form-item>
			  <el-form-item label="活动地点" prop="location">
			    <el-input v-model="activity.location"></el-input>
			  </el-form-item>
			  <el-form-item label="招募人数" prop="numberVolunteers">
			    <el-input v-model="activity.numberVolunteers"></el-input>
			  </el-form-item>
			  <el-form-item label="开始日期" prop="startDate">
			    <el-col :span="11">
					<el-date-picker
					  v-model="activity.startDate"
					  type="date"
					  placeholder="选择日期"
					  format="yyyy 年 MM 月 dd 日"
					  value-format="yyyy-MM-dd"
					  style="width: 100%;">
					</el-date-picker>
			    </el-col>
			  </el-form-item>
			  <el-form-item label="结束日期" prop="endDate">
			    <el-col :span="11">
			  					<el-date-picker
			  					  v-model="activity.endDate"
			  					  type="date"
			  					  placeholder="选择日期"
			  					  format="yyyy 年 MM 月 dd 日"
			  					  value-format="yyyy-MM-dd"
			  					  style="width: 100%;">
			  					</el-date-picker>
			    </el-col>
			  </el-form-item>
			  <el-form-item label="活动内容" prop="content">
			    <el-input type="textarea" v-model="activity.content"></el-input>
			  </el-form-item>
			  <el-form-item>
			    <el-button type="primary" @click="submitForm('activity')">立即创建</el-button>
			    <el-button @click="resetForm">重置</el-button>
			  </el-form-item>
			</el-form>
		</el-card>
	</el-card>
	</div>
</template>

<script>
	import  {insertActivity} from '@/api/getData.js';
	import {
		setStorage,
		getStorage
	} from "@/utils/localStorage.js";
  export default {
    data() {
      return {
        activity: {
			teamId:'',
			name:'',
			location:'',
			content:'',
			numberVolunteers:'',
			postDate:'',
			startDate:'',
			endDate:'',
        },
		rules: {
		          name: [
		            { required: true, message: '请输入活动名称', trigger: 'blur' }
		          ],
		          location: [
		            { required: true, message: '请输入活动地点', trigger: 'blur' }
		          ],
		          startDate: [
		            { required: true, message: '请选择日期', trigger: 'change' }
		          ],
				  endDate: [
				    { required: true, message: '请选择日期', trigger: 'change' }
				  ],
				  numberVolunteers: [
				    { required: true, message: '请输入招募人数', trigger: 'blur' }
				  ],
				  content: [
				    { required: true, message: '请输入活动内容', trigger: 'blur' }
				  ],
		        }
      }
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
		submitForm(formName) {
		        this.$refs[formName].validate((valid) => {
		          if (valid) {
		            // alert('submit!');
					this.getTodayDate();
					this.activity.teamId=getStorage("teamId");
					console.log(this.activity);
					insertActivity(this.activity).then((res)=>{
						console.log(res);
						this.code = res.code
						if(this.code){
							this.$alert('创建成功', '提示', {
							          confirmButtonText: '确定',
									        });	
							this.resetForm();
						}
						else{
							this.$alert('创建失败', '提示', {
							          confirmButtonText: '确定',
									        });
													
						}
					})
		          } else {
		            console.log('error submit!!');
		            return false;
		          }
		        });
		      },
	  resetForm() {
	    this.$refs['activity'].resetFields();
	    },
	  getTodayDate() {  
	      const today = new Date();  
	      // 获取年、月、日  
	      const year = today.getFullYear();  
	      let month = today.getMonth() + 1; // 月份是从0开始的，所以要加1  
	      let day = today.getDate();  
	    
	      // 确保月份和日期是两位数  
	      month = month < 10 ? '0' + month : month.toString();  
	      day = day < 10 ? '0' + day : day.toString();  
	    
	      // 拼接成字符串  
	      this.activity.postDate = `${year}-${month}-${day}`; 
		  console.log(this.activity.postDate)
	  }
    },
	mounted() {
		this.getTodayDate();
	}
  }
</script>

<style>
	
</style>