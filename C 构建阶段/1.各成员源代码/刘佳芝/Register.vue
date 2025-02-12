<template>
	<div id="register-main">
		<el-container>
			<el-main class="main">
				<div class="registerform" >
					<!-- 地址栏 -->
					<div class="path">
							<el-breadcrumb separator-class="el-icon-arrow-right">
							  <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
							  <el-breadcrumb-item>注册</el-breadcrumb-item>
							</el-breadcrumb>
					</div>
					<!-- 表单内容 -->
					<div class="z">
						<el-tabs v-model="activeName" >
						    <el-tab-pane label="志愿者注册" name="first">
								<el-form ref="volunteer" :model="volunteer" :rules="volunteerRule" label-width="80px" style="width: 100%;">
								  <el-form-item label="账号"  prop="volunteerId" >
								    <el-input v-model="volunteer.volunteerId" placeholder="请输入账号"></el-input>
								  </el-form-item>
								  <el-form-item label="姓名"  prop="name">
								    <el-input v-model="volunteer.name" placeholder="请输入姓名"></el-input>
								  </el-form-item>
								  <el-form-item label="密码"  prop="password" >
								    <el-input v-model="volunteer.password" placeholder="请输入密码" show-password></el-input>
								  </el-form-item>
								  <el-form-item label="性别"  prop="gender">
								      <el-radio-group v-model="volunteer.gender">
								        <el-radio :label="1" >男</el-radio>
								        <el-radio :label="0" >女</el-radio>
								      </el-radio-group>
								    </el-form-item>
								  <el-form-item label="联系电话"  prop="phone">
								    <el-input v-model="volunteer.phone" placeholder="请输入联系方式" ></el-input>
								  </el-form-item>
								  <el-form-item label="身份证号"  prop="identity">
								    <el-input v-model="volunteer.identity" placeholder="请输入身份证号 "></el-input>
								  </el-form-item>
									<el-form-item label="民族"  prop="nation">
									  <el-input v-model="volunteer.nation" ></el-input>
									</el-form-item>
									<el-form-item label="邮箱" >
									  <el-input v-model="volunteer.email" ></el-input>
									</el-form-item>
									<el-form-item label="地区" >
									  <el-input v-model="volunteer.area" ></el-input>
									</el-form-item>
 		  
								</el-form>
								<div class="buttonbox">
									<el-button type="danger"  class="button" @click="onSubmit('volunteer')" >提交注册</el-button>
									
								</div>
							</el-tab-pane>
						    <el-tab-pane label="志愿团队注册" name="second">
								<el-form ref="volunteerTeam" :model="volunteerTeam" :rules="volunteerTeamRule" label-width="100px" style="width: 100%;">
									<el-form-item label="团队账号" prop="teamId">
									  <el-input v-model="volunteerTeam.teamId" placeholder="请输入账号"></el-input>
									</el-form-item>
									<el-form-item label="团队名称" prop="name">
									  <el-input v-model="volunteerTeam.name" placeholder="请输入团队名称"></el-input>
									</el-form-item>
									<el-form-item label="密码" prop="password">
									  <el-input v-model="volunteerTeam.password" placeholder="请输入密码" show-password></el-input>
									</el-form-item>
									<el-form-item label="团队联系人" prop="leader">
									  <el-input v-model="volunteerTeam.leader" ></el-input>
									</el-form-item>
									<el-form-item label="联系电话" prop="contact">
									  <el-input v-model="volunteerTeam.contact" ></el-input>
									</el-form-item>
									<el-form-item label="人数上限" prop="max">
									  <el-input v-model="volunteerTeam.max" ></el-input>
									</el-form-item>
								</el-form>
								<div class="buttonbox">
									<el-button type="danger"  class="button" @click="onSubmitVolunteerTeam('volunteerTeam')" >提交注册</el-button>
									
								</div>
							</el-tab-pane>
						    <!-- <el-tab-pane label="管理员注册" name="third">
								<el-form ref="admin" :model="admin" label-width="100px" style="width: 100%;">
									<el-form-item label="管理员账号" >
									  <el-input v-model="admin.adminId" placeholder="请输入账号"></el-input>
									</el-form-item>
									<el-form-item label="管理员密码" >
									  <el-input v-model="admin.password" placeholder="请输入密码"></el-input>
									</el-form-item>
								</el-form>
								<div class="buttonbox">
									<el-button type="danger"  class="button" @click="onSubmitadmin('admin')" >提交注册</el-button>
									
								</div>
							</el-tab-pane> -->
						  </el-tabs>
					</div>
					<!-- 提交按钮 -->
					<!-- <div class="buttonbox">
						<el-button type="danger"  class="button" @click="onSubmitVolunteer('volunteer')" >提交注册</el-button>
						
					</div> -->
					
				</div>
			</el-main>
		</el-container>
		

	</div>
</template>

<script>
	import {insertVolunteer} from '@/api/getData.js';
	import {insertVolunteerTeam} from '@/api/getData.js';
	// import {insertAdmin} from '@/api/getData.js';
	
	
	  export default {
	     data() {
	       return {
			 // showButtonVolunteer: false,
			 //  showButtonTeam:false,
			 //  showButtonAdmin:false,
	         volunteer: {
			   volunteerId:'',
	           name: '',
			   password:'',
			   gender:'',
			   phone:'',
			   identity:'',
			   nation:'',
			   email:'',
	           // area: [] 
			     area: ''
	         },
			 volunteerRule:{
				volunteerId:[
					{ required: true, message: '请输入账号', trigger: 'blur' },
					{ min: 6, max: 18, message: '长度在 6 到 18 个字符', trigger: 'blur' }
				],
				name: [
				        { required: true, message: '请输入姓名', trigger: 'blur' },
				        // { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
				    ],
				password:[
					{ required: true, message: '请输入密码', trigger: 'blur' },
					{ min: 6, max: 18, message: '长度在 6 到 18 个字符', trigger: 'blur' }
				],
				phone:[
					{ required: true, message: '请输入联系电话', trigger: 'blur' },
					{ min: 11, max: 11, message: '长度为11个字符', trigger: 'blur' }
				],
				identity:[
					{ required: true, message: '请输入身份证号码', trigger: 'blur' }
					// { min: 18, max: 18, message: '长度在为11个字符', trigger: 'blur' }
				],
				nation: [
				  { required: true, message: '请输入民族', trigger: 'change' }
				],
				gender:[
					 { required: true, message: '请选择性别', trigger: 'change' }
				]
			 },
			 volunteerTeam:{
				teamId:'',
				password:'',
				name:'',
				leader:'',
				contact:'',
				max:''
			 },
			 volunteerTeamRule:{
				 teamId:[
					 { required: true, message: '请输入账号', trigger: 'blur' },
					 { min: 6, max: 18, message: '长度在 6 到 18 个字符', trigger: 'blur' }
				 ],
				 password:[
					 { required: true, message: '请输入密码', trigger: 'blur' },
					 { min: 6, max: 18, message: '长度在 6 到 18 个字符', trigger: 'blur' }
				 ],
				 name:[
					         { required: true, message: '请输入姓名', trigger: 'blur' },
				 ],
				 leader:[
					 { required: true, message: '请输入负责人姓名', trigger: 'blur' },
				 ],
				 contact:[
					 { required: true, message: '请输入负责人联系电话', trigger: 'blur' },
					 { min: 11, max: 11, message: '长度为11个字符', trigger: 'blur' }
				 ],
				 max:[
					 { required: true, message: '请输入团队人数上线', trigger: 'blur' },
					
				 ]
			 },
			 admin:{
				 adminId:'',
				 password:''
			 },
			  activeName: 'first',
			  // options: regionDataPlus 
			  newsData : [],
			  news : {
			  	page : 1,
			  	limit : 4,
			  }
	       }
	     },
	     methods: {
			   onSubmitVolunteer(formName) {
			         this.$refs[formName].validate((valid) => {
			           if (valid) {
			             // alert('submit!');
						 insertVolunteer(this.volunteer).then((res)=>{
						 	console.log(res);
						 })
						 this.$message.success("注册成功，请登录");
						 this.$router.push({path: "login"})
			           } else {
						   this.$message.success("注册失败，请重新注册");
			           }
			         });
			       },
			onSubmitVolunteerTeam(formName){
			         this.$refs[formName].validate((valid) => {
			           if (valid) {
			             // alert('submit!');
						insertVolunteerTeam(this.volunteerTeam).then((res)=>{
							console.log(res);
											this.$message.success("注册成功，请登录");
											 this.$router.push({path: "login"})
						})
			           } else {
			              this.$message.success("注册失败，请重新注册");
			             return false;
			           }
			         });
			       },
			onSubmitadmin(formName){
			         this.$refs[formName].validate((valid) => {
			           if (valid) {
			             alert('submit!');
						insertAdmin(this.admin).then((res)=>{
							console.log(res);
											this.$message.success("注册成功，请登录");
											 this.$router.push({path: "login"})
						})
			           } else {
			             console.log('error submit!!');
			             return false;
			           }
			         });
			       },
				   
	  //      onSubmit() {
			//    console.log(this.volunteer)
			//    console.log('111')
	  //        console.log(this.activeName);
			 
			//  if(this.activeName=='first'){
			// 	 console.log('111')

			// 	 insertVolunteer(this.volunteer).then((res)=>{
			// 	 	console.log(res);
			// 	 })
			// 	 this.$message.success("注册成功，请登录");
			// 	 this.$router.push({path: "login"})
			//  }
			 
			// else if(this.activeName=='second'){
			// 	 console.log('222')
			// 	 insertVolunteerTeam(this.volunteerTeam).then((res)=>{
			// 	 	console.log(res);
			// 		this.$message.success("注册成功，请登录");
			// 		 this.$router.push({path: "login"})
			// 	 })
			//  }
			//  else{
			// 	 console.log('333')
			// 	 insertAdmin(this.admin).then((res)=>{
			// 	 	console.log(res);
			// 		this.$message.success("注册成功，请登录");
			// 		 this.$router.push({path: "login"})
			// 	 })
			//  }
			 
			 
	  //      }		   
	     }
		  
	   }
</script>

<style scoped>
	.main{
		/* background-image: url("../../assets/background1.png"); */
	}
	.path{
		margin-top: 50px;
		margin-bottom: 30px;
	}
	.infomation{
		background-color: white;
		padding: 40px;
		margin-bottom: 40px;
	}
	.registerform{
		
		/* margin-bottom: 30px; */
		width: 60%;
		margin: auto;
			/* position: absolute; */
		/* 	top: 0;
			left: 0;
			right: 0;
			bottom: 0; */
	}
	.buttonbox{
		display: flex;

		    justify-content: center; /* 子元素（按钮）靠右对齐 */  
		    align-items: center; /* 如果需要，可以在垂直方向上居中对齐子元素 */  
		    /* 其他样式，如宽度、高度、边距等，可以根据需要添加 */  
		margin-bottom: 30px;
		margin-top:10%

	}
	.button{
		 width:25%;
	}
</style>