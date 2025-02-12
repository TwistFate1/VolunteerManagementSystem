
<template> 
	<div>
	   <div class="video-container">
			   <div style="display: flex;overflow: hidden;" v-for="(item, index) in video1" :key="index">
				 <div style="flex: 1;width: 384px; margin-right:30px;">
					 <el-card :body-style="{ padding: '0px' }" style="border-radius: 10px; box-shadow: 5px 5px 5px 6px rgba(0,0,0,0.05);">
				   <video controls :src="prefix + (item.videoId) + suffix" style="width:384px;height:216px;">    
				   </video>
				   <div style="overflow: hidden;margin-left: 5%;margin-top: 0%;">   
					 <p style="overflow: hidden;margin-bottom: 0%;margin-top: 0%;font-size: 20px;height:52.8px">{{ item.introduction }}</p>
					   <p style="color: #73726E;margin-top: 0%;">#  {{ item.tag }}  {{ item.postDate }} </p> 
				   </div>
				   </el-card>
				   </div>
			   </div>
	   </div>
	   <div class="video-container">
			   <div style="display: flex;overflow: hidden;" v-for="(item, index) in video2" :key="index">
				 <div style="flex: 1;width: 384px; margin-right:30px;">
					 <el-card :body-style="{ padding: '0px' }" style="border-radius: 10px; box-shadow: 5px 5px 5px 6px rgba(0,0,0,0.05);">
				   <video controls :src="prefix + (item.videoId) + suffix" style="width:384px;height:216px;">    
				   </video>
				   <div style="overflow: hidden;margin-left: 5%;margin-top: 0%;">   
					 <p style="overflow: hidden;margin-bottom: 0%;margin-top: 0%;font-size: 20px;height:52.8px">{{ item.introduction }}</p>
					   <p style="color: #73726E;margin-top: 0%;">#  {{ item.tag }}  {{ item.postDate }} </p> 
				   </div>
				   </el-card>
				   </div>
			   </div>
			   
	   </div>
	   <div class="pagination-container" style="display: flex;align-items: center;justify-content: center;margin-bottom: 20px">
		   <!-- 分页组件 -->
		   <el-pagination @current-change="handleCurrentChange" :current-page="page"
			   :page-size="6" layout="total, prev, pager, next, jumper"
			   :total="total">
		   </el-pagination>
	   </div>
	</div>  
   </template>  
	 
   <script> 
	import  {getVideoInfo,getVideoFile} from '@/api/getData.js';
   export default {
		 data() {
		   return {
				prefix: 'http://localhost/video/',
				suffix: '.mp4',
			   video1:[
			   ],
			   video2:[],
			   pageForm1:{
				   "page":'1',
				   "limit":'3'
			   },
			   pageForm2:{
				   "page":'2',
				   "limit":'3'
			   },
			   total:''
		   }
		 },
		 methods: {
			 truncateTitle(item) {
				 console.log(item.introduction.length);
			   if (item.introduction && item.introduction.length > 35) {  
				 // 如果title存在且长度大于20，则截断并添加省略号  
				 return item.introduction.substring(0, 35) + '...';  
			   }  
			   // 如果title不存在或长度不大于20，则返回原始title  
			   return item.introduction || '';  
			 },
			 handleCurrentChange(val) {
				 // this.page = val;
				 console.log(val);
			   this.pageForm1.page=2*(val)-1;
			   this.pageForm2.page=2*val;
			   console.log(this.pageForm1);
			   console.log(this.pageForm2);
			   this.test();
			 },
			 test(){
				 getVideoInfo(this.pageForm1).then((res)=>{
					 console.log(res);
				   this.video1=res.result.videos;
				   this.total=res.result.total;
				   console.log(this.video1);
				   this.video1.forEach(item => {
					  item.introduction=this.truncateTitle(item);
					  console.log(item.introduction);
					  })
				 })
				 getVideoInfo(this.pageForm2).then((res)=>{
					 console.log(res);
					 this.video2=res.result.videos;
					 console.log(this.video2)
				   this.video2.forEach(item => {
					  item.introduction=this.truncateTitle(item);
					  console.log(item.introduction);
					  })
				 })
			 }
		   },
	   mounted() {
		   this.test();
	   },
	   }
   </script>  
	 
   <style scoped>  
   .video-container {  
	 margin-top: 3%;
	 display: flex;
   }     
   </style>