<template>
	<div>
	<el-card>
	<el-card>
	<el-table
	   :data="ActivityVolunteerList"
	   border
	   style="width: 100%">
	   <el-table-column
	     prop="volunteer.volunteerId"
	     label="申请人账号">
	   </el-table-column>
	   <el-table-column
	     prop="volunteer.name"
	     label="姓名" width="100">
	   </el-table-column>
	   <el-table-column
	     prop="volunteer.gender"
	     label="性别" width="50">
	   </el-table-column>
	   <el-table-column
	     prop="volunteer.phone"
	     label="联系方式">
	   </el-table-column>
	   <el-table-column
	     prop="activity.activityId"
	     label="活动id">
	   </el-table-column>
	   <el-table-column
	     prop="activity.name"
	     label="活动名称" width="200">
	   </el-table-column>
					  <el-table-column label="操作">
					        <template slot-scope="scope">
					          <el-button
					            size="mini"
								type="primary"
					            @click="handleAgree(scope.$index, scope.row)">同意</el-button>
					          <el-button
					            size="mini"
					            type="danger"
					            @click="handleDisagree(scope.$index, scope.row)">拒绝</el-button>
					        </template>
					      </el-table-column>
	</el-table>
	</el-card>
	</el-card>
	</div>
</template>

<script>
	import {
		setStorage,
		getStorage
	} from "@/utils/localStorage.js";
	import{getVolunteerACtivityByTeamId,getVolunteerById,getActivityByActivityId,
	updataVolunteerServiceRecord}from '@/api/getData.js';
	export default {
		inject: ['reload'],
	  data() {
	    return {
			teamId:{
				teamId:''
			},
			activities:[],
			volunteer:{},
			ActivityVolunteerList:[],
			ActivityVolunteerItem:{
				activity:{},
				volunteer:{}
			},
			AgreeForm:{
				activityId:'',
				volunteerId:'',
				state:'1',
				serviceHours:'0'
			},
			DisagreeForm:{
				activityId:'',
				volunteerId:'',
				state:'2',
				serviceHours:'0'
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
		  test() {  
		      this.teamId.teamId = getStorage("teamId"); // 假设这里返回的是团队的ID  
		      getVolunteerACtivityByTeamId(this.teamId).then((res) => {  
		          this.activities = res.result.result;  
		          console.log(this.activities);  
		    
		          this.ActivityVolunteerList = []; // 初始化列表  
		    
		          this.activities.forEach(item => {  
		              if (item.volunteerId.length === 0) {  
		                  console.log("无数据");  
		              } else {  
		                  const activityId={
							  "activityId":'',
						  }; // 使用局部变量 
						   activityId.activityId=item.activityId;
		                  getActivityByActivityId(activityId).then((activityRes) => {  
		                      const activityVolunteerItem = { // 创建新对象  
		                          activity: activityRes.result,  
		                          volunteer: null  
		                      }; 
							   const volunteerId={
							   		 "volunteerId":'',
							   }; // 使用局部变量 
							   volunteerId.volunteerId=item.volunteerId;
		                      getVolunteerById(volunteerId).then((volunteerRes) => {  
		                          activityVolunteerItem.volunteer = volunteerRes.result;  
		                          console.log(activityVolunteerItem.volunteer);
								  if(activityVolunteerItem.volunteer.gender==='1'){
									  activityVolunteerItem.volunteer.gender='男';
								  }
								  else{
									  activityVolunteerItem.volunteer.gender='女';
								  }
		                          this.ActivityVolunteerList.push(activityVolunteerItem);  
		                      }).catch(error => console.error('Error fetching volunteer:', error));  
		                  }).catch(error => console.error('Error fetching activity:', error));  
		              }  
		          });  
		    
		          console.log("List:", this.ActivityVolunteerList);  
		      }).catch(error => console.error('Error fetching activities:', error));  
		  },
		  handleAgree(index, row) {
		    console.log(index, row);
			this.$confirm('是否同意该成员加入活动?', '提示', {
			          confirmButtonText: '确定',
			          cancelButtonText: '取消',
			          type: 'warning'
			        }).then(() => {
			          this.AgreeForm.volunteerId=row.volunteer.volunteerId;
			          this.AgreeForm.activityId=row.activity.activityId;
			          console.log(this.AgreeForm)
			          updataVolunteerServiceRecord(this.AgreeForm).then((res)=>{
						    console.log(res.code)
							if(res.code){
								console.log("aaa")
								this.reload();	
								this.$message({
								  type: 'success',
								  message: '已同意'
								});
								 	
							}
							else{
								this.reload();
								this.$alert('该志愿者加入活动失败', '提示', {
								          confirmButtonText: '确定',
										        });
								
							}
						})
			        }).catch(() => {
			          	      this.$message({
			          	        type: 'info',
			          	        message: '已取消'
			          	      });        
			        });
		  },
		  handleDisagree(index, row) {
		          console.log(index, row);
		  	this.$confirm('是否拒绝该志愿者加入活动?', '提示', {
		  		confirmButtonText: '确定',
		  		      cancelButtonText: '取消',
		  		      type: 'warning'
		  		    }).then(() => {  
		  				this.DisagreeForm.volunteerId=row.volunteer.volunteerId;
		  				this.DisagreeForm.activityId=row.activity.activityId;
		  				console.log(this.DisagreeForm)
		  				updataVolunteerServiceRecord(this.DisagreeForm).then((res)=>{
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
	  	this.test();
	  },
	}
</script>

<style>
</style>