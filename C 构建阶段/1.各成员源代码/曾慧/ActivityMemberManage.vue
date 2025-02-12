<template>
	<div>
	<el-card>
		<el-card>
		<el-table
			  :data="state1Activities"
			  :default-sort="{prop: 'endDate', order: 'descending'}"
			  style="width: 100%">
			  <el-table-column
			    prop="endDate"
			    label="结束日期"
			  	width="110"
				sortable
			    >	
			   </el-table-column>
			  <el-table-column
			    prop="name"
			    label="名称"
				width="170"
			    >
			  </el-table-column>
			  <el-table-column
			    prop="location"
			    label="活动地点"
			  	width="160"
			    >
			  </el-table-column>
			  <el-table-column
			    prop="content"
			    label="活动内容"
			  	width="270"
			    >
			  </el-table-column>
			  <el-table-column
			    prop="numberVolunteers"
			    label="招募人数"
			  	width="100"
			    >
			  </el-table-column>
			  <el-table-column label="操作" width="100">
			        <template slot-scope="scope">
			          <el-button
			            size="mini"
						type="primary"
			            @click="handleEdit(scope.$index, scope.row)">管理</el-button>
			        </template>
			      </el-table-column>
			</el-table>
			<el-drawer
			  title="人员管理"
			  :visible.sync="table"
			  direction="rtl"
			  size="50%">
			      <el-table
			      		:data="teamMember"
			      		  border
			      		  style="width: 100%">
			      		  <el-table-column
			      		    prop="volunteerId"
			      		    label="账号"
							width="100">
			      		  </el-table-column>
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
			      		  <el-table-column label="操作">
			      		        <template slot-scope="scope">
			      		          <el-button
			      		            size="medium"
									type="danger"
			      		            @click="deleteMember(scope.$index, scope.row)">删除</el-button>
			      		        </template>
			      		</el-table-column>
			    </el-table>
			</el-drawer>
		</el-card>
	</el-card>
	</div>
</template>

<script>
	import  {getActivityOfTeam,getVolunteerIdByActivityId,
	getVolunteerDetailByActivityId,getVolunteerServiceHoursPerActivity,
	updataVolunteerServiceRecord,deleteApplyForActivity} from '@/api/getData.js';
	import {
		setStorage,
		getStorage
	} from "@/utils/localStorage.js";
  export default {
    data() {
      return {
        state1Activities:[],
		activitys:[],
		teamId:{
			"teamId":''
		},
		serviceHours:'',
		activityId:{
			"activityId":'',
		},
		serviceForm:{
			"activityId":'',
			"volunteerId":''
		},
		deleteActivityMemberForm:{
			"activityId":'',
			"volunteerId":'',
		},
		todayDate:'',
		table:false,
		teamMember:[],
		endDate1:'',
        
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
		test(){
			console.log(111)
			this.teamId.teamId=getStorage("teamId");
			getActivityOfTeam(this.teamId).then((res)=>{
				console.log(res);
				this.activitys=res.result;
				this.activitys.forEach(item => { 
				  if (item.state === 1) {  
					// 将endDate字符串转换为Date对象  
					let endDate1 = new Date(item.endDate);  
					// 获取当前日期  
					let today = new Date();
					if (endDate1 >= today) {  
					       this.activityId.activityId=item.activityId;
					         getVolunteerIdByActivityId(this.activityId).then((res)=>{
					       	console.log(res);
					         	item.numberVolunteers=res.result.num1+'/'+item.numberVolunteers;
					         })
					       this.state1Activities.push(item);  
					    }  
				    
				  }  
				});
				console.log(this.state1Activities);
			})
			
		},
		handleEdit(index,row){
			console.log(row);
			this.activityId.activityId=row.activityId;
			this.table=true;
			getVolunteerDetailByActivityId(this.activityId).then((res)=>{
				console.log(res);
				this.teamMember=res.result;
				this.teamMember.forEach(item =>{
					console.log(item.gender)
					if(item.gender === 1){
						item.gender = "男"
						console.log("男")
					}
					else if(item.gender === 0){
						item.gender = "女"
						console.log("女")
					}
				})
			})
		},
		deleteMember(index,row){
			this.deleteActivityMemberForm.activityId=this.activityId.activityId;
			this.deleteActivityMemberForm.volunteerId=row.volunteerId;
			this.$confirm('此操作将永久删除该成员, 是否继续?', '提示', {
			          confirmButtonText: '确定',
			          cancelButtonText: '取消',
			          type: 'warning'
			        }).then(() => {
			          deleteApplyForActivity(this.deleteActivityMemberForm).then((res)=>{
			          	console.log(res);
			          	if(res.code){
			          		this.$alert('志愿者删除成功', '提示', {
			          		          confirmButtonText: '确定',
			          				        });
						    this.teamMember.splice(index, 1);
			          	}
			          	
			          })
			        }).catch(() => {
			          this.$alert('已取消删除', '提示', {
			                    confirmButtonText: '确定',
			          		        });          
			        });
			
			
		},
		update(index,row){
			if(row.add===false)
			{
				this.updateHoursForm.activityId=this.activityId.activityId;
				this.updateHoursForm.volunteerId=row.volunteerId;
				this.updateHoursForm.serviceHours=row.serviceHours;
				console.log(this.updateHoursForm)
				updataVolunteerServiceRecord(this.updateHoursForm).then((res)=>{
					console.log(res);
					this.$alert('志愿时长录入成功', '提示', {
					          confirmButtonText: '确定',
							        });
				})
				this.teamMember[index].add=true;
			}
			
		}
    },
	mounted(){
		this.test();
		
	}
  }
</script>

<style>
</style>