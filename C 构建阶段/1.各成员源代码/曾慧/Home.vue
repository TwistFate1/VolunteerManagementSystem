<template>
	<div style="width: 100%">
		<div class="container">
			<div>
				<div class="container1">
					<el-card :body-style="{ padding: '0px' }" class="carousel-container">  
					     <!-- <el-carousel>
					           <el-carousel-item v-for="(item, index) in carouselList" :key="index">
					             <img :src="item.image" alt="carousel image" class="carousel-image">
					           </el-carousel-item>
					         </el-carousel> --> 
							 <HomeShow style="margin-bottom: 3%;margin-top: 0%;"></HomeShow>
					</el-card > 
					
					
					<div class="civilizedXX">
						<el-card style="">
					  <h2 style="margin-left: 5%; margin-top: 1%; margin-bottom: 2%;">文明先锋</h2>
					  <div style="" v-for="(item, index) in news_list" :key="index">
						  <el-link :href="'/site/Content/'+item.newsId" target="_blank" style="margin-left: 4%; margin-top: 1%;" >
						  	<li style="font-size: 18px;">
						  	<span>{{item.title}}</span>
						  	<span style="margin-left: 1vw; font-size: 15px;">{{item.newsTime}}</span>
						  	</li>
						  </el-link>
					  </div>
					  <div style="margin-top: 4%;">  
					  </div>
					  </el-card>
					</div>
				</div>
			</div>
			<div class="bigScreen">
				<DataMap></DataMap>
			</div>
			<el-card style="margin-top: 3%;background-color: #DFEDFB;" :body-style="{ padding: '0px' }">
				<div class="public_benefit">
					<div class="pub_title">
						<h1 style="text-align: center;">公</h1>
						<h1 style="text-align: center;">益</h1>
						<h1 style="text-align: center;">活</h1>
						<h1 style="text-align: center;">动</h1>
					</div>
					<div class="pub_content">
							<div class="pub_content1">
								<el-card :body-style="{ padding: '0px' }" style="height: 90%;">
									<a href="http://www.chinacharityfederation.org/pv.html?id=6e9bf96c-6697-453f-8d10-b90a37ccd2a5" target="_blank">
										<div>
											<img src="../../assets/Learn1.jpg" class="public_benefit_img"></img>
										</div>
									</a> 
									<div style="text-align: center;margin-top: 2%;margin-bottom: 2%;">为了明天，关爱儿童项目</div>
								</el-card>
							</div>
							<div class="pub_content1">
								<el-card :body-style="{ padding: '0px' }" style="height: 90%;">
									<a href="http://www.chinacharityfederation.org/pv.html?id=54bd0034-82ef-4f29-8555-2c677f531730" target="_blank">
										<div>
											<img src="../../assets/Learn2.jpg" class="public_benefit_img"></img>
										</div>
									</a> 
									<div style="text-align: center;margin-top: 2%;margin-bottom: 2%;">一张纸献爱心行动</div>
								</el-card>
							</div>
							<div class="pub_content1">
								<el-card :body-style="{ padding: '0px' }" style="height: 90%;">
									<a href="http://www.chinacharityfederation.org/pv.html?id=35842a4c-c3e4-4a72-abd9-4f6f386f55f0" target="_blank">
										<div>
											<img src="../../assets/Learn3.jpg" class="public_benefit_img"></img>
										</div>
									</a> 
									<div style="text-align: center;margin-top: 2%;margin-bottom: 2%;">先心病儿童救助行动</div>
								</el-card>
							</div>
							<div class="pub_content1">
								<el-card :body-style="{ padding: '0px' }" style="height: 90%;">
									<a href="http://www.chinacharityfederation.org/pv.html?id=002450c5-477a-4f6b-9550-17ff9dabf656" target="_blank">
										<div>
											<img src="../../assets/Learn4.jpg" class="public_benefit_img"></img>
										</div>
									</a> 
									<div style="text-align: center;margin-top: 2%;margin-bottom: 2%;">慈善情暖万家”扶弱济困项目</div>
								</el-card>
							</div>
					</div>
											
				</div>
			</el-card>
			
			<el-card  class="leifengCard" >
				<div class="leifeng">
					<div class="lei_title">
						<h1 style="text-align: center; margin-top: 20%;">学</h1>
						<h1 style="text-align: center;">雷</h1>
						<h1 style="text-align: center;">锋</h1>
					</div>
					<div class="lei_content1">
						<div v-for="(item, index) in leifeng_list" :key="index">
							<el-link :href="item.url" target="_blank" :underline="false" class="leifeng_link">
								<li style="font-size: 20px; margin-top: 0%; color: black;">{{item.title}}</li>
							</el-link>
						</div>
					</div>
					<div class="lei_content2">
						<img :src="lei_img"class="lei_img_cls">
					</div>
				</div>
				
			</el-card>
			
		</div>
	</div>
	
</template>
<script>
	import {getAllNews} from '@/api/getData.js';
	import {volunteerLogin} from '@/api/getData.js';
	import DataMap from '../echarts/DataMap.vue';
	import HomeShow from '../../page/home/display/HomeShow.vue';
  export default {
	components: {
		DataMap,
		HomeShow
	},
    data() {
      return {
		  //'https://chinavolunteer.mca.gov.cn/site/static/img/header_bg.acdffe9.png'
		url: '../../assets/Learn1.jpg',
		activeIndex:"/",
		input:'',
		news_list:{},
		news_split:{
			"page":1,
			"limit":8
		},
		//../../assets/Learn1.jpg
		test_image:'https://chinavolunteer.mca.gov.cn/nvsiwebsite/XLFZYFW/newsDetail?name=xlf_top&catlevel=99&newsid=0de4b58d1681401881e6706bd63ef932',
		volunteer_param:{
			"password":"pass123",
			"volunteer_id":"v001"
		},
		lei_img:'https://img.tukuppt.com/bg_grid/00/04/90/As4lfntefE.jpg!/fh/350',
		carouselList: [
		  {  
		    image: 'https://chinavolunteer.mca.gov.cn/site/static/img/header_bg.acdffe9.png' // 替换为真实的图片地址  
		  },
		  {
		    image: 'https://chinavolunteer.mca.gov.cn/site/static/img/header_bg.acdffe9.png' // 替换为真实的图片地址  
		  },
		],
		
		leifeng_list:[
			{
				title:'习近平对深入开展学雷锋活动作出重要指示',
				url:'https://chinavolunteer.mca.gov.cn/nvsiwebsite/XLFZYFW/newsDetail?name=xlf_top&catlevel=99&newsid=0de4b58d1681401881e6706bd63ef932'
			},
			{
				title:'跟着总书记感受雷锋精神的时代内涵与力量',
				url:'https://chinavolunteer.mca.gov.cn/nvsiwebsite/XLFZYFW/newsDetail?name=xlf_top&catlevel=99&newsid=5983c80d1e22418c830629001ee3b732'
			},
			{
				title:'让学雷锋在新时代青少年中蔚然成风',
				url:'http://www.qstheory.cn/dukan/hqwg/2024-02/28/c_1130083105.htm'
			},
			{
				title:'学习弘扬雷锋精神，争做新时代好青年',
				url:'https://dangjian.gmw.cn/2024-03/12/content_37198930.htm'
			},
			{
				title:'雷锋精神绽放新光芒',
				url:'http://politics.people.com.cn/n1/2024/0305/c1001-40188739.html'
			},
			{
				title:'学习雷锋好榜样',
				url:'http://dangshi.people.com.cn/n1/2021/0907/c436975-32219868.html'
			},
			{
				title:'弘扬“雷锋精神”：新时代青年如何做？',
				url:'https://jcxg.njmu.edu.cn/2023/0422/c7859a236704/page.htm'
			},
			{
				title:'雷锋精神，一座永不褪色的丰碑',
				url:'http://politics.people.com.cn/n1/2023/0224/c1001-32630155.html'
			}
			
		]
		
      };
    },
    methods: {
		truncateTitle(item) {
			console.log(item.title.length);
		  if (item.title && item.title.length > 25) {  
		    // 如果title存在且长度大于20，则截断并添加省略号  
		    return item.title.substring(0, 25) + '...';  
		  }  
		  // 如果title不存在或长度不大于20，则返回原始title  
		  return item.title || '';  
		},
		getAllNewsTo(){
			getAllNews(this.news_split).then((res)=>{
				console.log(res);
				this.news_list=res.result.newsList;
				console.log(this.news_list);
				this.news_list.forEach(item => {
				   item.title=this.truncateTitle(item);
				   console.log(item.title);
				   })
			})
		},
		volunteerLoginTo(){
			volunteerLogin(this.volunteer_param).then((res)=>{
				console.log(res);
			})
		},
    },
	mounted() {
		this.getAllNewsTo();
	}
  };
</script>

<style scoped>
	.container{
		margin-top: 2%;
	}
	.container1{
	  display: flex;  
	  justify-content: space-between; 
	  
	}  
	.carousel-container {  
	  flex: 4; /* 占据可用空间的一半（或根据需要调整） */  
	  margin-right: 4%; /* 可选，用于增加左右两块内容之间的间距 */  
	  background-color: white;
	  height: 100%;
	  margin-top: 1%;
	  margin-right: 2%;
	}  
	  
	.civilizedXX {  
	  flex: 5; /* 占据可用空间的一半（或根据需要调整） */  
	  margin-top: 0%;
	  height: 100%;
	  background-image: url('../../assets/CivilizedBackground.jpg');
	} 
	
	.bigScreen{
		height: 90%;
		margin-top: 3%;
	}
	.public_benefit{
		display: flex;
		margin-top: 1%;
		margin-bottom: 0%;
		/* background-color: white; */
		/* #DFEDFB */
	}
	.pub_title{
		flex:1;
		margin-top: 0%;
		margin-bottom: 0%;
		margin-right: 0%;
	}
	.pub_content{
		margin-top: 1%;
		flex:9;
		margin-right: 2%;
		margin-bottom: 0%;
		display: flex;
	}
	.pub_content1{
		flex:2;
		margin-right: 2%;
	}
	.public_benefit_img{
		width: 100%; 
	}
	.leifengCard{
		margin-top: 3%;
		margin-bottom: 20px;
		background-color: antiquewhite;
		background-image: url('../../assets/LearnBackground2.jpg');
		background-size: cover;
	}
	.leifeng{
		display: flex;
		height: 40%;
		margin-top: 1%;
	}
	.leifeng_link{
		margin-left: 2%; 
		margin-top: 1% ;
	}
	.lei_title{
		flex:2;
		margin-right: 5%;
	}
	.lei_content1{
		flex:7;
		margin-top: 1%;
	}
	.lei_content2{
		flex:8;
		margin-top: 1%;
	}
	.lei_img_cls{
		width: 100%;
	}
	
</style>
