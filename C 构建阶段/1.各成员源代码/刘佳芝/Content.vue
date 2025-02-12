<script setup>

</script>

<script>
	import  {getNewsById} from '@/api/getData.js';

	
	  export default {
	      data() {
	        return {
				
				news:{
					"newsId":'',
					"teamId": '',
					"newsTime": '',
					"headline": '0',
					"state": '0',
					"title": '',
					"tag": '',
					// "value": undefined,
					"content":undefined,
					}
	        }
	      },
	      methods: {
	          aa(){
	            console.log(1234);
	        },
			getNewsByIdTo(){
				console.log(this.news.newsId)
				getNewsById(this.news).then((res)=>{
					console.log(res)
					this.news=res.result;
					console.log(this.news);
				})
			}
	      },
		  mounted(){
		          console.log(this.$route.params)/* 获取动态路由传值 */
		          this.news.newsId=this.$route.params.newsId
				  console.log("newsId")
				  console.log(this.news.newsId)
				 this.getNewsByIdTo();
		          // console.log(aid)
		          // 调用requestData()方法请求新闻内容
				 
		   
		      }
		
	    }

</script>

<template>
   <div class="news-page">  
   <!-- <div>
	   <el-button @click="getNewsByIdTo" type='danger'>
		   点击
	   </el-button>
   </div> -->
      <div class="title-container">  
        <h2>{{ news.title }}</h2>  
		 <div class="meta-info">
			 <el-row :gutter="5">
				 <el-col :span="12">
					 <span class="date-label">发布日期：</span>
					 <span >{{ news.newsTime }}</span>
				 </el-col>
				 <el-col :span="12">
					 <span class="author-label">标签：</span>
					 <span class="author">{{ news.tag }}</span>
				 </el-col>
			 </el-row>
		       <!-- <span class="date-label">发布日期：</span>
		        <span >{{ news.newsTime }}</span>  
		        <span class="author-label">标签：</span>  
		         <span class="author">{{ news.tag }}</span> -->
		      </div> 
      </div>  
      <div class="news-content" v-html="news.content"></div>  
    </div>
  
</template>

<style scoped>
	.news-page { 
		margin-top: 10%;
	  display: flex;  
	  flex-direction: column; /* 垂直布局 */  
	  align-items: center; /* 子项在交叉轴上的对齐方式（这里是水平居中） */  
	  width: 100%; /* 根据需要调整宽度 */  
	  max-width: 800px; /* 可选，限制最大宽度 */  
	  margin: 0 auto; /* 水平居中 */  
	  margin-top: 5%;
	  padding: 20px; /* 内边距 */  
	  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* 可选，添加阴影效果 */
	  border: 0.5px solid #F2C9BB; /* 红色边框，宽度为2px */
	}  
	  
	.title-container {  
	  text-align: center; /* 确保文本居中 */  
	  margin-bottom: 20px; /* 标题与下文之间的间距 */  
	}  
	  
	.date {  
	  margin: 0; /* 移除默认的margin */  
	  font-size: 16px; /* 可选，调整日期字体大小 */  
	  color: #666; /* 可选，调整日期字体颜色 */  
	}  
	  
	.news-content {  
	  /* 根据需要添加样式，例如字体大小、颜色等 */  
	  font-size: 14px;  
	  line-height: 1.6;  
	  color: #333;  
	}
	  .meta-info { 
		  height: 100%;
		margin-top: 10%;
	    text-align: center; /* 确保日期、作者标签和它们的值居中 */  
	    margin-bottom: 5%; /* 标题与下文之间的间距 */  
	  } 
	  .date-label, .author-label {  
	    /* 你可以给日期和作者的标签添加一些样式，比如字体加粗或颜色变化 */  
	   /* font-weight: bold; */  
	    color: #333; /* 深灰色字体 */  
	   /* margin-right: 100%; */ /* 在标签和值之间添加一些间距 */  
	  }  
	  .date{
		   /* argin-right: 1000px; */
		  font-size: 16px;
		  color: #666; /* 灰色字体 */ 
	  }  
	   .author {  
	    /* 日期和作者的样式，如果需要的话 */  
	    font-size: 16px;  
	    color: #666; /* 灰色字体 */  
	  }  

</style> 