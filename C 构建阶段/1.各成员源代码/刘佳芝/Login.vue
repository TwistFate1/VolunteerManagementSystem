<template>
	
	<!-- <div id="login-main"> -->
	            <el-container class="el-container">
	                <el-main class="main">
						<div class="background">
							<el-row class="path">
								<div >
									<el-col :span='24' class='col3'>
										<el-breadcrumb separator-class="el-icon-arrow-right">
										  <el-breadcrumb-item :to="{ path: 'home' }">首页</el-breadcrumb-item>
										  <el-breadcrumb-item>登录</el-breadcrumb-item>
										</el-breadcrumb>
									</el-col>
								</div>
							</el-row>
							<el-row :gutter="20" type='flex'>
							    <el-col :span='16' class='picture'>
									<img src="../../assets/login.png" style="width: 100%; height: 100%;">
								</el-col>
							    <el-col :span='8' class='login-location'>
									<div class="col-right" style="width: 100%; height: 100%;">
										<div class="login-form2" style="width: 100%;">
											<div class="login-title">欢迎登录</div>
											<div class="login-form">
												<el-form :model="ruleForm"  ref="ruleForm" class="demo-ruleForm">
													<el-form-item prop="userid" >
														<el-input type="text" v-model="ruleForm.username" placeholder="请输入账号"><i slot="prefix" class="el-input__icon el-icon-user-solid"></i></el-input>
													</el-form-item>
													<el-form-item prop="password" >
														<el-input type="password" show-password placeholder="请输入密码" v-model="ruleForm.password"><i slot="prefix" class="el-input__icon el-icon-lock"></i></el-input>
													</el-form-item>
												</el-form>
												<el-form :model="identityForm" >
													 <el-form-item >
													    <el-select v-model="identityForm.identity" placeholder="请选择登录身份" class="identity" ><i slot="prefix" class="el-input__icon el-icon-user-solid"></i>
													      <el-option label="志愿者" value="1"></el-option>
													      <el-option label="志愿团队" value="2"></el-option>
														  <el-option label="管理员" value="3"></el-option>
													    </el-select>
													</el-form-item>
													<!-- <a href="javascript:void(0)">忘记密码?</a> -->
													<el-form-item >
														<div>
															<el-button  @click="submitForm" type="danger" class="login-button">登录</el-button>
															<el-button   @click="goToRegister" type="danger" class="register-button">注册</el-button>
														</div>
																	
													</el-form-item>
												</el-form>
											</div>
										</div>
											
										
									</div>
									<div style="clear:both"></div>
								</el-col>
							</el-row> 
						</div>
	                </el-main>
	            </el-container>
	    <!-- </div> -->

</template>

<script>
	import  {volunteerLogin} from '@/api/getData.js';
	import  {volunteerTeamLogin} from '@/api/getData.js';
	import  {adminLogin} from '@/api/getData.js';
	
	import {
		setStorage,
		getStorage
	} from "@/utils/localStorage.js";
	export default {
		data() {
			return {
				ruleForm: {
					"username": '',
					"password": ''
					
				},
				volunteer:{
					"volunteer_id":'',
					"password": ''
					
				},
				volunteerTeam:{
					"teamId":'',
					"password": ''
				},
				admin:{
					"adminId":'',
					"password": ''

				},
				
				identityForm:{
					identity:''
				},
				code : 0,
				userdata : {},
				teamdata :{},
			}
		},
		methods: {
			
			submitForm(){
				if(this.identityForm.identity==1){
					this.volunteer.volunteer_id=this.ruleForm.username;
					this.volunteer.password=this.ruleForm.password;
					volunteerLogin(this.volunteer).then((res)=>{
						console.log(res);
						this.userdata = res.result;
						this.code = res.code
						if(this.code){
							setStorage("volunteerId", res.result.volunteerId)
							setStorage("code", res.code)
							this.$message.success("登录成功");
							this.$router.push({path: "volunteer"})	
						}
						else{
							this.$message("账号或者密码错误");
													
						}
					})
					
				}
				else if(this.identityForm.identity==2){
					this.volunteerTeam.teamId=this.ruleForm.username;
					this.volunteerTeam.password=this.ruleForm.password;
					volunteerTeamLogin(this.volunteerTeam).then((res)=>{
						console.log(res);
						this.teamdata = res.result;
						this.code = res.code
						if(this.code){
							setStorage("teamId", res.result.teamId)
							setStorage("code", res.code)
							this.$message.success("登录成功");
							this.$router.push({path: "team/teamInfoContent"})	
						}
						else{
							this.$message("账号或者密码错误");
													
						}
					})
				}
				else if(this.identityForm.identity==3){
					this.admin.adminId=this.ruleForm.username;
					this.admin.password=this.ruleForm.password;
					console.log(this.admin);
					console.log(this.identityForm.identity);
					adminLogin(this.admin).then((res)=>{
						console.log(res);
						this.teamdata = res.result;
						this.code = res.code
						if(this.code){
							setStorage("adminId", res.result.adminId)
							setStorage("code", res.code)
							this.$message.success("登录成功");
							this.$router.push({path: "admin/infoContent"})	
						}
						else{
							this.$message("账号或者密码错误");
													
						}
					})
				}
				else{
					this.$message("请选择登录身份");
				}
				
				
			},
		goToRegister() {
			      this.$router.push('register');   
			    } 
		},
		
		mounted() {
	
		}
	}
</script>

<style scoped>
	/* body {
		margin: 0px auto;
	} */
	.background{
		/* background:url("../../assets/background.png");
		  width:100%;
		  height:100%;
		  position:fixed;
		  background-size:100% 100%; */
	}
	.el-container{
		
		width: 60%;
		margin: auto;
			top: 0;
			left: 0;
			right: 0;
			bottom: 0;
	}
	.identity{
		width:100%;
	}
	.main{
		/* background:url("../../assets/background1.png"); */
		  width:100%;
		  height:100%;
		 /* position:fixed; */
		  background-size:100% 100%;
	}
	.path{
		margin-bottom: 50px;
	}
	.row-login{
		 display: flex;
	}
	.login-title {
		color: darkred;
		font-size: 30px;
		font-weight: bolder;
		margin-bottom: 50px;
		text-align: center;
	}
	.button{
		/* display: flex; */
		width:100%;
		
	}
	.login-button{  
			width: 45%;
			margin-right: 2%;
			
	    }   
	.register-button {  
			width: 45%;
			margin-left: 2%;
			
	}
	.col-right{
		display: flex;  
		align-items: center; 
		
	}
	
	/* justify-content: center; */ 
</style>