<template>
	<div>
	<el-card shadow="always" class="teammember">
	<!-- <div>
		<el-button type="danger" @click="getVolunteerByTeamIdTo1">点击</el-button>
	</div> -->
		 <el-tabs type="border-card">
		   <el-tab-pane label="项目成员">
			    <el-table
			       :data="tableDatastate1"
			       border
			       style="width: 100%">
			       <!-- <el-table-column
			         prop="volunteer_id"
			         label="账号">
			       </el-table-column> -->
			       <el-table-column
			         prop="name"
			         label="姓名"  width="100">
			       </el-table-column>
			       <el-table-column
			         prop="gender"
			         label="性别" width="50">
			       </el-table-column>
				   <el-table-column
				     prop="phone"
				     label="联系方式">
				   </el-table-column>
				   <el-table-column
				     prop="nation"
				     label="民族">
				   </el-table-column>
				   <el-table-column
				     prop="email"
				     label="邮箱" width="200">
				   </el-table-column>
				   <el-table-column
				     prop="area"
				     label="地区">
				   </el-table-column>
				   <el-table-column label="操作">
				         <template slot-scope="scope">
				           <!-- <el-button
				             size="mini"
				             @click="handleEdit(scope.$index, scope.row)">编辑</el-button> -->
				           <el-button
				             size="mini"
				             type="danger"
				             @click="handleDelete(scope.$index, scope.row)">删除</el-button>
				         </template>
				       </el-table-column>
			     </el-table>
		   </el-tab-pane>
		   <el-tab-pane label="待审核">
			   <el-table
			      :data="tableDatastate0"
			      border
			      style="width: 100%">
			      <!-- <el-table-column
			        prop="volunteer_id"
			        label="账号">
			      </el-table-column> -->
			      <el-table-column
			        prop="name"
			        label="姓名" width="100">
			      </el-table-column>
			      <el-table-column
			        prop="gender"
			        label="性别" width="50">
			      </el-table-column>
			      <el-table-column
			        prop="phone"
			        label="联系方式">
			      </el-table-column>
			      <el-table-column
			        prop="nation"
			        label="民族">
			      </el-table-column>
			      <el-table-column
			        prop="email"
			        label="邮箱" width="200">
			      </el-table-column>
			      <el-table-column
			        prop="area"
			        label="地区">
			      </el-table-column>
				  <el-table-column label="操作">
				        <template slot-scope="scope">
				          <el-button
				            size="mini"
				            @click="handleAgree(scope.$index, scope.row)">同意</el-button>
				          <el-button
				            size="mini"
				            type="danger"
				            @click="handleDisagree(scope.$index, scope.row)">拒绝</el-button>
				        </template>
				      </el-table-column>
			    </el-table>
		   </el-tab-pane>
		   <el-tab-pane label="已拒绝">
		   		<el-table
		   		   :data="tableDatastate2"
		   		   border
		   		   style="width: 100%">
		   		   <!-- <el-table-column
		   		     prop="volunteer_id"
		   		     label="账号">
		   		   </el-table-column> -->
		   		   <el-table-column
		   		     prop="name"
		   		     label="姓名" width="100">
		   		   </el-table-column>
		   		   <el-table-column
		   		     prop="gender"
		   		     label="性别"  width="50">
		   		   </el-table-column>
		   		   <el-table-column
		   		     prop="phone"
		   		     label="联系方式">
		   		   </el-table-column>
		   		   <el-table-column
		   		     prop="nation"
		   		     label="民族">
		   		   </el-table-column>
		   		   <el-table-column
		   		     prop="email"
		   		     label="邮箱"  width="200">
		   		   </el-table-column>
		   		   <el-table-column
		   		     prop="area"
		   		     label="地区">
		   		   </el-table-column>
		   		 </el-table>	   
		   </el-tab-pane>
		 </el-tabs>
	</el-card>
	</div>
</template>

<script>
	import  {getVolunteerByTeamId} from '@/api/getData.js';
	import {getVolunteerDetailByTeamId} from '@/api/getData.js';
	import {getVolunteerById} from '@/api/getData.js';
	import {deleteApplyForTeam} from '@/api/getData.js';
	import {updateMembership} from '@/api/getData.js';
	import {
		getStorage,
		setStorage,
		delStorage
	} from '@/utils/localStorage.js'; // 引入缓存操作方法
	
	
	 export default {
		 inject: ['reload'],
	      data() {
	        return {
				// team:{
				//   		"teamId":'t001'
				//   	 },
				tableDatastate0: [],
				tableDatastate1:[],
				tableDatastate2:[],
				memberList:[],
				tableDataMember:{},
				volunteer:{
					"teamId":'',
					"volunteerId":'',
					"state":'1'
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
			 
			getVolunteerByTeamIdTo(){
				this.volunteer.teamId = getStorage('teamId');
				console.log("获取缓存");
				console.log(this.volunteer.teamId);
				getVolunteerByTeamId(this.volunteer).then((res)=>{
					// console.log(res);
					// console.log(res.result);
					this.memberList=res.result.members;
					// console.log(this.memberList);
					this.memberList.forEach(item=>{
						if(item.state == 1){
							console.log(item.volunteerId);
							console.log("团队成员");
							this.volunteer.volunteerId=item.volunteerId;
							// console.log(this.volunteer.volunteerId);
							getVolunteerById(this.volunteer).then((res)=>{
								// console.log(res);
								// console.log(res.result);
								this.tableDataMember=res.result;
								// console.log(this.tableDataMember.gender);
								if(this.tableDataMember.gender==0){
									this.tableDataMember.gender="女"
									// console.log(this.tableDataMember.gender);
								}
								else{
									this.tableDataMember.gender="男"
									// console.log(this.tableDataMember.gender);
								}
								// console.log(this.tableDataMember);
								
								
								this.tableDatastate1.push(this.tableDataMember);
								
								// console.log(this.tableDatastate1);
								
								
								
							})
						}
						else if(item.state == 0){
							console.log(item.volunteerId);
							console.log("待审核")
							this.volunteer.volunteerId=item.volunteerId;
							console.log(this.volunteer.volunteerId);
							getVolunteerById(this.volunteer).then((res)=>{
								// console.log(res);
								// console.log(res.result);
								this.tableDataMember=res.result;
								if(this.tableDataMember.gender==0){
									this.tableDataMember.gender="女"
									// console.log(this.tableDataMember.gender);
								}
								else{
									this.tableDataMember.gender="男"
									// console.log(this.tableDataMember.gender);
								}
								console.log(this.tableDataMember);
								this.tableDatastate0.push(this.tableDataMember);
								console.log(this.tableDatastate0);
								
								
								
							})
						}else if(item.state == 2){
							
							console.log(item.volunteerId);
							console.log("已拒绝");
							this.volunteer.volunteerId=item.volunteerId;
							console.log(this.volunteer.volunteerId);
							getVolunteerById(this.volunteer).then((res)=>{
								// console.log(res);
								// console.log(res.result);
								this.tableDataMember=res.result;
								if(this.tableDataMember.gender==0){
									this.tableDataMember.gender="女"
									// console.log(this.tableDataMember.gender);
								}
								else{
									this.tableDataMember.gender="男"
									// console.log(this.tableDataMember.gender);
								}
								console.log(this.tableDataMember);
								this.tableDatastate2.push(this.tableDataMember);
								console.log(this.tableDatastate2);
								
								
								
							})
						}
					})
				})
			},
			getVolunteerByTeamIdTo1(){
				getVolunteerByTeamId(this.volunteer).then((res)=>{
					console.log(res);
				})
			},
			getVolunteerByIdTo(){
				getVolunteerById(this.volunteer).then((res)=>{
					console.log(res);
				})
			},
			getVolunteerDetailByTeamIdTo(){
				console.log("222");
				
				getVolunteerDetailByTeamId(this.volunteer).then((res)=>{
					console.log(res);
					console.log(res.result);
					this.tableData=res.result;
					console.log(this.tableData);
					console.log(this.tableData[0]);
					
				})
				// this.tableData.forEach(item => {
				// 				  if (item.state === 0) {  	
				// 				    this.state0Activities.push(item);  
				// 				  } else if (item.state === 1) {  
				// 					  this.activityId.activityId=item.activityId;
				// 					  getVolunteerIdByActivityId(this.activityId).then((res)=>{
				// 						console.log(res);
				// 					  	item.numberVolunteers=res.result.num1+'/'+item.numberVolunteers;
				// 					  })
				// 				    this.state1Activities.push(item);  
				// 				  } else if (item.state === 2) {  
				// 				    this.state2Activities.push(item);  
				// 				  }  
				// 				});
			},
			handleEdit(index, row) {
			        console.log(index, row);
					console.log(row.volunteerId)
			},
			handleDelete(index, row) {
				       this.$confirm('此操作将永久删除该成员,是否继续?', '提示', {
				          confirmButtonText: '确定',
				          cancelButtonText: '取消',
				          type: 'warning'
				        }).then(() => {  
							console.log(index, row);
							this.volunteer.volunteerId=row.volunteerId;
							deleteApplyForTeam(this.volunteer).then((res)=>{
								console.log(res);
								// this.$message("删除成功");
								// location.reload();
								this.tableDatastate1.splice(index,1);
							// this.reload();
				          this.$message({
				            type: 'success',
				            message: '删除成功!'
				          });
				        }).catch(() => {
				          this.$message({
				            type: 'info',
				            message: '已取消删除'
				          });          
				        });
						
			  //       console.log(index, row);
					// this.volunteer.volunteerId=row.volunteerId;
					// deleteApplyForTeam(this.volunteer).then((res)=>{
					// 	console.log(res);
					// 	this.$message("删除成功");
					// 	location.reload();
					})
					
			},
			handleAgree(index, row) {
			        console.log(index, row);
				this.$confirm('是否确认同意该成员加入团队?', '提示', {
								          confirmButtonText: '确定',
								          cancelButtonText: '取消',
								          type: 'info'
								        }).then(() => {  
											console.log(index, row);
											this.volunteer.volunteerId=row.volunteerId;
											this.volunteer.state='1';
											updateMembership(this.volunteer).then((res)=>{
												console.log(res);
											this.reload();
								          this.$message({
								            type: 'success',
								            message: '加入成功!'
								          });
								        }).catch(() => {
								          this.$message({
								            type: 'info',
								            message: '已取消'
								          });          
								        });
									})	

			},
			handleDisagree(index, row) {
			        console.log(index, row);
					this.$confirm('是否确认拒绝同意该成员加入团队?', '提示', {
									          confirmButtonText: '确定',
									          cancelButtonText: '取消',
									          type: 'warning'
									        }).then(() => {  
												console.log(index, row);
												this.volunteer.volunteerId=row.volunteerId;
												this.volunteer.state='2';
												updateMembership(this.volunteer).then((res)=>{
													console.log(res);
												this.reload();
									          this.$message({
									            type: 'success',
									            message: '已拒绝'
									          });
									        }).catch(() => {
									          this.$message({
									            type: 'info',
									            message: '已取消'
									          });          
									        });
										})	
			}
	      },
		  mounted() {
		  		this.getVolunteerByTeamIdTo();
		  	}
	    }
</script>

<style>
</style>