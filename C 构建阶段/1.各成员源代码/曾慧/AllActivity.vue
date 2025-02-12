<template>
	<div>
	<el-card>
		<el-tabs type="border-card" v-model="activeName" >
			<el-tab-pane label="已发布" name="first">
				<el-table
					  :data="state1Activities"
					  border
					  :default-sort="{prop: 'postDate', order: 'descending'}"
					  style="width: 99%">
					  <el-table-column
					    prop="postDate"
					    label="发布日期"
					  	width="110"
						sortable
					    >
					   </el-table-column>
					  <el-table-column
					    prop="name"
					    label="名称"
						width="146"
					    >
					  </el-table-column>
					  <el-table-column
					    prop="location"
					    label="活动地点"
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
					  	width="80"
					    >
					  </el-table-column>
					  <el-table-column
					    prop="startDate"
					    label="开始日期"
						sortable
					  	width="110"
					    >
					  </el-table-column>
					  <el-table-column
					    prop="endDate"
					    label="结束日期"
						sortable
					  	width="110"
					    >
					  </el-table-column>
					</el-table>
			</el-tab-pane>	
			<el-tab-pane label="待审核" name="second">
				<el-table
				  :data="state0Activities"
				  border
				  :default-sort="{prop: 'postDate', order: 'descending'}"
				  style="width: 100%">
				  <el-table-column
				    prop="postDate"
				    label="发布日期"
				  	width="105"
					sortable
				    >
				   </el-table-column>
				  <el-table-column
				    prop="name"
				    label="名称"
					width="120"
				    >
				  </el-table-column>
				  <el-table-column
				    prop="location"
				    label="活动地点"
				  	width="130"
				    >
				  </el-table-column>
				  <el-table-column
				    prop="content"
				    label="活动内容"
				  	width="228"
				    >
				  </el-table-column>
				  <el-table-column
				    prop="numberVolunteers"
				    label="招募人数"
				  	width="80"
				    >
				  </el-table-column>
				  <el-table-column
				    prop="startDate"
				    label="开始日期"
					sortable
				  	width="105"
				    >
				  </el-table-column>
				  <el-table-column
				    prop="endDate"
				    label="结束日期"
					sortable
				  	width="105"
				    >
				  </el-table-column>
				  <el-table-column label="操作" width="145">
				        <template slot-scope="scope">
				          <el-button
				            size="mini"
				            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
				          <el-button
				            size="mini"
				            type="danger"
				            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
				        </template>
				      </el-table-column>
				</el-table>
				<el-drawer
				  title="编辑活动信息"
				  :before-close="handleClose"
				  :visible.sync="dialog"
				  direction="rtl"
				  custom-class="demo-drawer"
				  ref="drawer"
				  >
				  <div class="demo-drawer__content">
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
				        <el-button type="primary" @click="upddateForm('activity')">提交修改</el-button>
				        <el-button @click="back">取消</el-button>
				      </el-form-item>
				    </el-form>
				  </div>
				</el-drawer>
			</el-tab-pane>
			<el-tab-pane label="审核未通过" name="third">
				<el-table
				  :data="state2Activities"
				  border
				  :default-sort="{prop: 'postDate', order: 'descending'}"
				  style="width: 100%">
				  <el-table-column
				    prop="postDate"
				    label="发布日期"
				  	width="105"
					sortable
				    >
				   </el-table-column>
				  <el-table-column
				    prop="name"
				    label="名称"
					width="120"
				    >
				  </el-table-column>
				  <el-table-column
				    prop="location"
				    label="活动地点"
				  	width="130"
				    >
				  </el-table-column>
				  <el-table-column
				    prop="content"
				    label="活动内容"
				  	width="228"
				    >
				  </el-table-column>
				  <el-table-column
				    prop="numberVolunteers"
				    label="招募人数"
				  	width="80"
				    >
				  </el-table-column>
				  <el-table-column
				    prop="startDate"
				    label="开始日期"
					sortable
				  	width="105"
				    >
				  </el-table-column>
				  <el-table-column
				    prop="endDate"
				    label="结束日期"
					sortable
				  	width="105"
				    >
				  </el-table-column>
				  <el-table-column label="操作" width="145">
				        <template slot-scope="scope">
				          <el-button
				            size="mini"
				            @click="handleEdit2(scope.$index, scope.row)">编辑</el-button>
				          <el-button
				            size="mini"
				            type="danger"
				            @click="handleDelete2(scope.$index, scope.row)">删除</el-button>
				        </template>
				      </el-table-column>
				</el-table>
				<el-drawer
				  title="编辑活动信息"
				  :before-close="handleClose"
				  :visible.sync="dialog2"
				  direction="rtl"
				  custom-class="demo-drawer"
				  ref="drawer"
				  >
				  <div class="demo-drawer__content">
				    <el-form ref="activity2" :model="activity2" :rules="rules" label-width="80px" style="text-align: center;margin: 2%; margin-right: 30%;">
				      <el-form-item label="活动名称" prop="name">
				        <el-input v-model="activity2.name"></el-input>
				      </el-form-item>
				      <el-form-item label="活动地点" prop="location">
				        <el-input v-model="activity2.location"></el-input>
				      </el-form-item>
				      <el-form-item label="招募人数" prop="numberVolunteers">
				        <el-input v-model="activity2.numberVolunteers"></el-input>
				      </el-form-item>
				      <el-form-item label="开始日期" prop="startDate">
				        <el-col :span="11">
				    		<el-date-picker
				    		  v-model="activity2.startDate"
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
				      			  v-model="activity2.endDate"
				      			  type="date"
				      			  placeholder="选择日期"
				      			  format="yyyy 年 MM 月 dd 日"
				      			  value-format="yyyy-MM-dd"
				      			  style="width: 100%;">
				      			</el-date-picker>
				        </el-col>
				      </el-form-item>
				      <el-form-item label="活动内容" prop="content">
				        <el-input type="textarea" v-model="activity2.content"></el-input>
				      </el-form-item>
				      <el-form-item>
				        <el-button type="primary" @click="upddateForm2('activity2')">提交修改</el-button>
				        <el-button @click="back2">取消</el-button>
				      </el-form-item>
				    </el-form>
				  </div>
				</el-drawer>
			</el-tab-pane>
		</el-tabs>
	</el-card>
  </div>
</template>
<script>
import  {getActivityOfTeam,getVolunteerIdByActivityId,deleteActivity,UpdateActivity} from '@/api/getData.js';
 import ReleaseActivity from './ReleaseActivity.vue';
 import {
 	setStorage,
 	getStorage
 } from "@/utils/localStorage.js";
  export default {
	   inject: ['reload'],
	  components: {
	  	ReleaseActivity
	  },
    data() {
      return {
        activeName: 'first',
		team:{},
		teamId:{
			"teamId":''
		},
		activityId:{
			"activityId":'',
		},
		activitys:[],
		state0Activities:[],
		state1Activities:[],
		state2Activities:[],
		activity: {
			// teamId:'t001',
			// name:'',
			// location:'',
			// content:'',
			// numberVolunteers:'',
			// postDate:'',
			// startDate:'',
			// endDate:'',
		},
		activity2:{},
		dialog: false,
		dialog2:false,
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
		back(){
			this.dialog=false;
		},
		back2(){
			this.dialog2=false;
		},
		handleClose(done) {
		        this.$confirm('确认关闭？')
		          .then(_ => {
		            done();
		          })
		          .catch(_ => {});
		      },
		handleClose2(done) {
		        this.$confirm('确认关闭？')
		          .then(_ => {
		            done();
		          })
		          .catch(_ => {});
		      },
		test(){
			this.teamId.teamId=getStorage("teamId");
			console.log(this.teamId)
			getActivityOfTeam(this.teamId).then((res)=>{
				console.log(res);
				console.log(res.result);
				this.activitys=res.result;
				this.activitys.forEach(item => { 
				  if (item.state === 0) {  	
				    this.state0Activities.push(item);  
				  } else if (item.state === 1) {  
					  this.activityId.activityId=item.activityId;
					  getVolunteerIdByActivityId(this.activityId).then((res)=>{
						console.log(res);
					  	item.numberVolunteers=res.result.num1+'/'+item.numberVolunteers;
					  })
				    this.state1Activities.push(item);  
				  } else if (item.state === 2) {  
				    this.state2Activities.push(item);  
				  }  
				});
				console.log(this.state0Activities);
				console.log(this.state1Activities);
				console.log(this.state2Activities)
			})
			
		},
		handleEdit(index, row) {
			 this.activity=row;
			 this.dialog=true;
		        console.log(row);
		      },
		handleEdit2(index, row) {
			 this.activity2=row;
			 this.dialog2=true;
		        console.log(row);
		      },	  
		handleDelete(index, row) {
				this.activityId.activityId=row.activityId;
				console.log(this.activityId);
				deleteActivity(this.activityId).then((res)=>{
					console.log(res);
					if(res.code){
						this.$alert('删除成功', '提示', {
						          confirmButtonText: '确定',
								        });	
						this.state0Activities.splice(index,1);
					}
					else{
						this.reload();
						this.$alert('删除失败', '提示', {
						          confirmButtonText: '确定',
								        });							
					}
				})
		      },
			  handleDelete2(index, row) {
				this.activityId.activityId=row.activityId;
				console.log(this.activityId);
				deleteActivity(this.activityId).then((res)=>{
					console.log(res);
					if(res.code){
						this.$alert('删除成功', '提示', {
						          confirmButtonText: '确定',
								        });	
						this.state2Activities.splice(index,1);
					}
					else{
						this.reload();
						this.$alert('删除失败', '提示', {
						          confirmButtonText: '确定',
								        });							
					}
				})
		      },
		upddateForm(formName) {
			        this.$refs[formName].validate((valid) => {
			          if (valid) {
			            // alert('submit!');
						this.getTodayDate();
						console.log(this.activity);
						UpdateActivity(this.activity).then((res)=>{
							console.log(res);
							this.code = res.code
							if(this.code){
								this.$alert('修改成功', '提示', {
								          confirmButtonText: '确定',
										        });
								this.dialog=false;
							}
							else{
								this.$message("修改失败");
														
							}
						})
			          } else {
			            console.log('error submit!!');
			            return false;
			          }
			        });
			      },
			upddateForm2(formName) {
				        this.$refs[formName].validate((valid) => {
				          if (valid) {
				            // alert('submit!');
							this.getTodayDate();
							UpdateActivity(this.activity2).then((res)=>{
								console.log(res);
								this.code = res.code
								if(this.code){
									this.reload();
									this.$alert('修改成功', '提示', {
									          confirmButtonText: '确定',
											        });
									this.dialog2=false;
								}
								else{
									this.reload();
									this.$message("修改失败");
															
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
		this.test();
	},
  };
</script>