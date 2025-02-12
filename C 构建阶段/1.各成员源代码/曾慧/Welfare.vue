<template>
	<div>
		<div class="container">
			<div>
				<el-carousel class="public_benefit_img">
				      <el-carousel-item v-for="(item, index) in carouselList" :key="index">
						<a :href="item.url" target="_blank">
						<img :src="item.image"class="carousel-image">
						</a>  
				      </el-carousel-item>
				</el-carousel>
			</div>
			<div>
				<h1 style="padding-left: 5%;">新闻资讯</h1>
				<div class="container1">
					<el-card class="pub_video">
						<el-carousel style="padding-top: 5%;padding-bottom: 5%;">
						      <el-carousel-item v-for="(item, index) in carouselList1" :key="index" style="padding-bottom: 0px;">
								<a :href="item.url" target="_blank">
								<img :src="item.image"class="carousel-image1">
								</a>  
						      </el-carousel-item>
						</el-carousel>
					</el-card>
					<div class="pub_news">
						<div style="display: flex;flex:1;margin-bottom: 1%;">
							<div style="flex:2;background-color: aliceblue;margin-right: 2%;">
								<p style="font-size: 20px; text-align: center;margin-bottom: 0%;">26</p>
								<p style="font-size: 16px; text-align: center;margin-top: 0%;">2024-6</p>
							</div>
							<div style="flex:8; ">
								<el-link href="http://www.chinacharityfederation.org/nv.html?nid=d6fb7c04-2185-46ed-9ca6-820657117f0f">
									<p style="font-size: 15px;margin: 0%;color: black;">
										慈善注入父亲节 —— 中国柒牌联手中华慈善总会向四省空巢老人捐赠价值2041万余元服装
									</p>
									<p style="font-size: 12px;margin-left: 2%; color:#838B8B;">
										提及父亲节，仿佛总是很“低调”的存在。但这并非出于人们对父亲的漠视，而是源于父爱以及对父亲之爱的深邃、内敛与沉厚。
									</p>
								</el-link>
								
								
							</div>
						</div>
						<div style="display: flex;flex:1;margin-bottom: 1%;">
							<div style="flex:2;background-color: aliceblue;margin-right: 2%;">
								<p style="font-size: 20px; text-align: center;margin-bottom: 0%;">25</p>
								<p style="font-size: 16px; text-align: center;margin-top: 0%;">2024-6</p>
							</div>
							<div style="flex:8; ">
								<el-link href="http://www.chinacharityfederation.org/nv.html?nid=23dd34ae-4431-4202-8d8d-a07588a9aa3a" style="color: black;">
									<p style="font-size: 15px;margin: 0%;">
										推动党纪学习教育走深走实 坚决做到学纪知纪明纪守纪一体贯通 ——中华慈善总会党委召开专题党课
									</p>
									<p style="font-size: 12px;margin-left: 2%;margin-bottom: 1%; margin-top: 1%; color:#838B8B;">
										根据党中央在全党开展党纪学习教育的部署，按照民政部部管社会组织综合党委关于党组织书记讲党课的有关要求和《中华慈善总会党委党纪学习教育实施方案》
									</p>
								</el-link>
								
							</div>
						</div>
							
						<div style="display: flex;flex:1;margin-bottom: 1%;">
							<div style="flex:2;background-color: aliceblue;margin-right: 2%;">
								<p style="font-size: 20px; text-align: center;margin-bottom: 0%;">2</p>
								<p style="font-size: 16px; text-align: center;margin-top: 0%;">2024-7</p>
							</div>
							<div style="flex:8; ">
								<el-link href="http://www.chinacharityfederation.org/nv.html?nid=98a72c2e-2eb7-40b0-85ed-aba62f839ecb" style="color: black;">
								<p style="font-size: 15px;">
									铜陵市用“六助”共筑慈善之城
								</p>
								<p style="font-size: 12px;margin-left: 2%; color:#838B8B;">
									近年来，安徽省铜陵市民政局多方联动、凝心聚力，围绕慈善“六助” —— 助童、助老、助困、助残、助业和助美，共同执笔勾画铜陵城市美好建设同心圆。
								</p>
								</el-link>
							</div>
						</div>
						<div style="display: flex;flex:1;">
							<div style="flex:2;background-color: aliceblue;margin-right: 2%;">
								<p style="font-size: 20px; text-align: center;margin-bottom: 0%;">26</p>
								<p style="font-size: 16px; text-align: center;margin-top: 0%;">2024-6</p>
							</div>
							<div style="flex:8;">
								<el-link href="http://www.chinacharityfederation.org/nv.html?nid=000399f9-4d36-421f-8d11-dac0fe08dc86" style="color: black;">
									<p style="font-size: 15px;">
										共创美好生活 爱心助残进行时
									</p>
									<p style="font-size: 12px;margin-left: 2%; color:#838B8B;">
										助残志愿服务项目大赛、残障人士就业创业培训、慈善草原行助残公益行动……爱心助残没有终点。近期，多地围绕助残主题开展丰富活动。
									</p>
								</el-link>
							
							</div>
						</div>
						
					</div>
				</div>
			</div>
			<h1 style="padding-left: 5%;">公益项目</h1>
			<el-card style="height: 100%; background-color: aliceblue;margin-bottom: 3%">
				<div class="public_benefit">
					<div class="pub_content" v-for="(item, index) in public_benefit_list" :key="index" >
						<div class="pub_content1">
							<a :href="item.url" target="_blank">
							<img :src="item.image"class="public_benefit_img">  
							</a> 
							<div style="text-align: center;margin-top: 2%;">{{item.title}}</div>
						</div>
						
					</div>				
				</div>
				<div class="public_benefit">
					<div class="pub_content" v-for="(item, index) in public_benefit_list1" :key="index" >
						<div class="pub_content1">
							<a :href="item.url" target="_blank">
							<img :src="item.image"class="public_benefit_img">  
							</a> 
							<div style="text-align: center;margin-top: 2%;">{{item.title}}</div>
						</div>
						
					</div>				
				</div>
			</el-card>
			
			
			
		</div>
		
	</div>
</template>

<script>
	
	export default {
	  data() {
	    return {
			url: 'https://chinavolunteer.mca.gov.cn/site/static/img/header_bg.acdffe9.png',
			activeIndex:"/publicBenefit",
			carouselList:[
				{
					image:'https://res-img.n.gongyibao.cn/uploads/a01358c5-befc-4f81-9c85-48b42fa78b35/20200721/cb668a7dcc8c4ded899d334c1c4fe58e.jpg',
					url:'http://www.chinacharityfederation.org/pv.html?id=77f5a9f6-da33-4e91-8470-9825a8b50341'
				},
				{
					image:'https://res-img.n.gongyibao.cn/uploads/a01358c5-befc-4f81-9c85-48b42fa78b35/20221201/39dbccbcb8e94ceca3d7114477c21b65.jpg',
					url:'http://www.chinacharityfederation.org/pv.html?id=08f3e1e4-ad5c-4093-b2b5-8601e7391e83'
				},
				{
					image:'https://res-img.n.gongyibao.cn/uploads/a01358c5-befc-4f81-9c85-48b42fa78b35/20210304/dbfe266534264fbfa9861b6d9741025a.jpg',
					url:'http://www.chinacharityfederation.org/pv.html?id=bbcb063b-17be-4cf7-a5da-11cec770119e'
				},
				{
					image:'https://res-img.n.gongyibao.cn/uploads/a01358c5-befc-4f81-9c85-48b42fa78b35/20200713/fb234a00a0374e4088e2eb18b99a00b7.png',
					url:'http://www.chinacharityfederation.org/pv.html?id=4e7d72cf-8a30-4512-b478-54b56cc7c89c'
				}
			],
			carouselList1:[
				{
					image:'https://res-img.n.gongyibao.cn/uploads/a01358c5-befc-4f81-9c85-48b42fa78b35/20240614/3b54ee8ca5ec46e28307054f2c2e4642.jpg',
					url:'http://www.chinacharityfederation.org/nv.html?nid=d6fb7c04-2185-46ed-9ca6-820657117f0f',
					tirle:'慈善注入父亲节 '
				},
				{
					image:'https://res-img.n.gongyibao.cn/uploads/a01358c5-befc-4f81-9c85-48b42fa78b35/20240605/9ba2585f919d42a986a215dc416eeb24.jpg',
					url:'http://www.chinacharityfederation.org/nv.html?nid=3f7e8b99-ca4c-4e91-9503-aac4bbb24dba',
					title:'关爱儿童助成长 真情慰问暖童心'
					
				},
				{
					image:'https://res-img.n.gongyibao.cn/uploads/a01358c5-befc-4f81-9c85-48b42fa78b35/20240625/17b98d5092d34cabbbdf5831a1b778f5.jpg',
					url:'http://www.chinacharityfederation.org/nv.html?nid=23dd34ae-4431-4202-8d8d-a07588a9aa3a',
					title:'推动党纪学习教育走深走实'
				}
				
			],
			public_benefit_list:[
				{
					image:'http://file.chinacharityfederation.org/Image/20170605/20170605104948_8014.jpg',
					title:'为了明天，关爱儿童项目',
					url:'http://www.chinacharityfederation.org/pv.html?id=6e9bf96c-6697-453f-8d10-b90a37ccd2a5'
				},
				{
					image:'https://res-img.n.gongyibao.cn/uploads/a01358c5-befc-4f81-9c85-48b42fa78b35/20201021/7e16c8dee4e946828bba8e5efcf6dcd8.jpg',
					title:'一张纸献爱心行动',
					url:'http://www.chinacharityfederation.org/pv.html?id=54bd0034-82ef-4f29-8555-2c677f531730'
				},
				{
					image:'https://res-img.n.gongyibao.cn/uploads/a01358c5-befc-4f81-9c85-48b42fa78b35/20220601/6bab0ff253e84eae96e0d8e44fc9b25f.jpg',
					title:'先心病儿童救助行动',
					url:'http://www.chinacharityfederation.org/pv.html?id=35842a4c-c3e4-4a72-abd9-4f6f386f55f0'
				},
				{
					image:'https://file-chinacharityfederation-org.n.gongyibao.cn/Image/20191030/20191030151348_9712.jpg',
					title:'“慈善情暖万家”扶弱济困项目',
					url:'http://www.chinacharityfederation.org/pv.html?id=002450c5-477a-4f6b-9550-17ff9dabf656'
				}
				
			],
			public_benefit_list1:[
				{
					image:'https://res-img.n.gongyibao.cn/uploads/a01358c5-befc-4f81-9c85-48b42fa78b35/20231129/41c50cbb84d34f90b58716729d004121.jpg',
					title:'儿童公益音乐会',
					url:'http://www.chinacharityfederation.org/pv.html?id=37c6ad46-a0ec-4843-a345-d4d483a9a318'
				},
				{
					image:'https://file-chinacharityfederation-org.n.gongyibao.cn/Image/20170803/20170803150441_8412.JPG',
					title:'贫困家庭包虫病患者救助行动',
					url:'http://www.chinacharityfederation.org/pv.html?id=19065397-3224-463d-80e0-112740ef7a2c'
				},
				{
					image:'https://file-chinacharityfederation-org.n.gongyibao.cn/Image/20160325/20160325092036_3732.jpg',
					title:'慈善文化进校园项目',
					url:'http://www.chinacharityfederation.org/pv.html?id=4e7d72cf-8a30-4512-b478-54b56cc7c89c'
				},
				{
					image:'http://file.chinacharityfederation.org/Image/20141020/20141020174101_7307.jpg',
					title:'慈善一元捐行动',
					url:'http://www.chinacharityfederation.org/pv.html?id=1d063bef-9296-4177-9c54-5a48f2eb16a1'
				}
			]
	    };
	  },
	  methods: {
	
	  }
	};
</script>

<style scoped>
	.container{
		margin-top: 2%;
	}
	.carousel-image{
		width: 100%;
	}
	.carousel-image1{
		height: 100%;
	}
	.container1{
		display: flex;
		height: 100%;
		margin-top: 3%;
	}
	.pub_video{
		flex: 4;
		background-color: aliceblue;
		height: 50%;
		margin-right: 2%;
		
	}
	.pub_news{
		flex: 5;
		height: 50%;
		display: flex;
		flex-direction: column;
	}
	.public_benefit{
		display: flex;
		height: 34%;
		padding-left: 3%;
		background-color: aliceblue;
	}
	.pub_title{
		flex:1;
		margin-right: 2%;
	}
	.pub_content{
		margin-top: 1%;
		flex:2;
		margin-right: 2%;
		margin-bottom: 1%;
		display: flex;
	}
	.pub_content1{
		flex:1;
		margin-right: 2%;
		background-color: white;
	
	}
	.public_benefit_img{
		width: 100%;
		height: 80%;
	}
</style>