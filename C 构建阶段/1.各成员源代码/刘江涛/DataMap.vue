<script setup>
  import * as echarts from 'echarts';
  import axios from 'axios';
</script>

<script>
  export default {
    name: 'DataMap',
    data () {
      return {
        mapSize: {
          height: '580px',
          width: '700px'
        },
        dataWidth: '480px',

        myChart: null,
        name: '全国',
        detail: [ '实名志愿者总数(人)', '志愿队伍总数(个)', '志愿项目总数(个)', '服务时间总数(小时)', '记录时间人数(人)'],
        myMap: {
          '全国': [ '2.37亿', '135万', '1236万', '532600万', '8666万' ],
          '北京市': [ '679万', '10万', '48万', '56516万', '251万'],
          '天津市': [ '207万', '2282', '8130', '1616万', '9万'],
          '河北省': [ '1206万', '7万', '24万', '13864万', '415万'],
          '山西省': [ '1206万', '7万', '24万', '13864万', '415万'],
          '内蒙古自治区': [ '262万', '2万', '8万', '3223万', '57万'],
          '辽宁省': [ '734万', '3万', '10万', '3280万', '250万'],
          '吉林省': [ '287万', '2万', '3万', '849万', '29万'],
          '黑龙江省': [ '450万', '5052', '8031', '395万', '4万'],
          '上海市': [ '557万', '4万', '40万', '52617万', '367万'],
          '江苏省': [ '2357万', '12万', '282万', '157483万', '1273万'],
          '浙江省': [ '947万', '5257万', '6万', '694万', '10万'],
          '安徽省': [ '1316万', '6万', '56万', '15863万', '699万'],
          '福建省': [ '775万', '9万', '129万', '20902万', '361万'],
          '江西省': [ '578万', '6711万', '7万', '5681万', '63万'],
          '山东省': [ '1748万', '11万', '45万', '28599万', '675万'],
          '河南省': [ '1361万', '8万', '65万', '24436万', '399万'],
          '湖北省': [ '1107万', '5万', '27万', '7173万', '320万'],
          '湖南省': [ '813万', '7万', '18万', '637万', '45万'],
          '广东省': [ '1327万', '8万', '115万', '7220万', '104万'],
          '广西壮族自治区': [ '1093万', '6万', '43万', '14479万', '624万'],
          '海南省': [ '136万', '1万', '10万', '2932万', '51万'],
          '重庆市': [ '709万', '5万', '25万', '13032万', '246万'],
          '四川省': [ '1524万', '7万', '35万', '14931万', '653万'],
          '贵州省': [ '912万', '5万', '75万', '22570万', '598万'],
          '云南省': [ '872万', '6万', '59万', '31801万', '463万'],
          '西藏自治区': [ '14万', '3042', '6108', '112万', '2万'],
          '陕西省': [ '448万', '4万', '29万', '12510万', '185万'],
          '甘肃省': [ '384万', '4万', '19万', '8445万', '148万'],
          '青海省': [ '65万', '7387', '1万', '394万', '12万'],
          '宁夏回族自治区': [ '148万', '9698', '24万', '8381万', '71万'],
          '新疆维吾尔自治区': [ '360万/27万', '3万/3718', '18万/2万', '5236万/453万', '123万/7万'],
          '台湾省': [ '暂无数据', '暂无数据', '暂无数据', '暂无数据', '暂无数据'],
          '香港特别行政区': [ '暂无数据', '暂无数据', '暂无数据', '暂无数据', '暂无数据'],
          '澳门特别行政区': [ '暂无数据', '暂无数据', '暂无数据', '暂无数据', '暂无数据'],
        }
      }
    },
    mounted () {
      this.drawMap();
    },
    methods: {
      debounce (func, wait) {
        let timeout;
        return function() {
            let context = this, args = arguments;
            clearTimeout(timeout);
            timeout = setTimeout(() => {
                func.apply(context, args);
            }, wait);
        };
      },
      drawMap () {
        var ROOT_PATH = 'https://geo.datav.aliyun.com/areas_v3/bound/100000_full.json';
        var option;

        this.myChart = echarts.init(document.getElementById('main'));

        this.myChart.showLoading();
        axios({
          method: 'get',
          url: ROOT_PATH,
          responseType: 'json'
        })
        .then((cnJson) => {
          this.myChart.hideLoading();
          echarts.registerMap('CN', cnJson.data);
          option = {
            title: {
              text: '全国各地志愿者数量',
              subtext: '数据来源中国志愿服务网(部分区域暂无数据)',
              sublink: 'https://chinavolunteer.mca.gov.cn/',
              left: 'right'
            },
            tooltip: {
              trigger: 'item',
              showDelay: 0,
              transitionDuration: 0.2
            },
            visualMap: {
              left: 'right',
              min: 0,
              max: 2500,
              inRange: {
                color: [
                  '#313695',
                  '#4575b4',
                  '#74add1',
                  '#abd9e9',
                  '#e0f3f8',
                  '#ffffbf',
                  '#fee090',
                  '#fdae61',
                  '#f46d43',
                  '#d73027',
                  '#a50026'
                ]
              },
              text: ['High', 'Low'],
              calculable: true,
              realtime: true
            },
            toolbox: {
              show: true,
              //orient: 'vertical',
              left: 'left',
              top: 'top',
              feature: {
                dataView: { readOnly: true },
                restore: {},
                saveAsImage: {}
              }
            },
            series: [
              {
                name: '全国各地志愿者数量(单位：万人)',
                type: 'map',
                roam: true,
                scaleLimit: {
                  min: 1,
                  max: 25
                },
                map: 'CN',
                label: {
                  show: true,
                  color: 'inherit',
                  fontSize: 12
                },
                emphasis: {
                  label: {
                    show: true
                  }
                },
                data: [
                  { name: "北京市", value: 679 },
                  { name: "天津市", value: 207 },
                  { name: "河北省", value: 1206 },
                  { name: "山西省", value: 444 },
                  { name: "内蒙古自治区", value: 262 },
                  { name: "辽宁省", value: 734 },
                  { name: "吉林省", value: 287 },
                  { name: "黑龙江省", value: 450 },
                  { name: "上海市", value: 557 },
                  { name: "江苏省", value: 2357 },
                  { name: "浙江省", value: 947 },
                  { name: "安徽省", value: 1316 },
                  { name: "福建省", value: 775 },
                  { name: "江西省", value: 578 },
                  { name: "山东省", value: 1748 },
                  { name: "河南省", value: 1361 },
                  { name: "湖北省", value: 1107 },
                  { name: "湖南省", value: 813 },
                  { name: "广东省", value: 1327 },
                  { name: "广西壮族自治区", value: 1093 },
                  { name: "海南省", value: 136 },
                  { name: "重庆市", value: 709 },
                  { name: "四川省", value: 1524 },
                  { name: "贵州省", value: 912 },
                  { name: "云南省", value: 872 },
                  { name: "西藏自治区", value: 14 },
                  { name: "陕西省", value: 448 },
                  { name: "甘肃省", value: 384 },
                  { name: "青海省", value: 65 },
                  { name: "宁夏回族自治区", value: 384 },
                  { name: "新疆维吾尔自治区", value: 360 },
                  { name: "台湾省", value: 0 },
                  { name: "香港特别行政区", value: 0 },
                  { name: "澳门特别行政区", value: 0 }
                ]
              }
            ]
          };
          this.myChart.setOption(option);

          this.myChart.on('mouseover', (series) => {
            this.name = series.name;
          });

          this.myChart.on('mouseout', () => {
            this.name = '全国';
          });

          const debouncedResize = this.debounce(() => {
            this.myChart.resize({
                animation: {
                    duration: 0.02,
                    easing: true
                }
            });
            this.myChart.setOption(option);
          }, 0);

          window.addEventListener('resize', debouncedResize);
        })
        .catch(function (error) {
          console.log(error);
        });
      }
    },
  }
</script>

<template>
  <div class="mid">
    <el-card>
      <div slot="header">
        <div>
          <span class="title">全国(各地)志愿服务数据统计</span>
        </div>
      </div>
      <div>
        <el-container class="card">
          <el-main>
            <div id="main" style="display: flex" :style="{ height: mapSize.height, width: mapSize.width }"></div>
          </el-main>
          <el-aside class="info" :style="{ height: mapSize.height,  width: dataWidth }">
            <el-card style="height: 99%;width: 100%">
              <div slot="header">
                <div class="info-header">
                  <div class="info-name">{{ name }}</div>
                  <span class="infoText">志愿服务数据统计</span>
                </div>
              </div>
              <div class="card" style="height: 20%;padding: 10px" v-for="(item, index) in myMap[name]" :key="index">
                <el-container>
                  <el-main class="icon" style="width: 16%;height: 100%;padding-top: 7px">
                    <div>
                    <img alt="志愿服务" title="中国志愿服务网" src="../../assets/icon.png"></img>
                    </div>
                  </el-main>
                  <el-aside style="width: 84%;height: 100%">
                    <el-col style="height: 60%;width: 100%;padding: 0%; margin: 0%">
                      <el-row style="height: 60%">
                        <p class="data" style="margin-top: 0%; margin-bottom: 0%">{{ item }}</p>
                      </el-row>
                      <el-row style="height: 90%">
                        <div class="detail-text" style="margin-top: 0%; margin-bottom: 0%">{{ detail[index] }}</div>
                      </el-row>
                    </el-col>
                  </el-aside>
                </el-container>
              </div>
            </el-card>
          </el-aside>
        </el-container>
      </div>
    </el-card>
  </div>
</template>

<style scoped>
.mid {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-left: auto;
  margin-right: auto;
  box-sizing: content-box;
}
.title {
  color: #cd0000;
  text-align: center;
  font-weight: bold;
  font-size: larger;
}
.info {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: auto;
  margin-bottom: auto;
}
.info-header {
  display: flex;
  justify-content: center;
  align-items: center;
}
.info-name {
  color: #cd0000;
  text-align: left;
  font-weight: bold;
  font-size: x-large;
}
.infoText {
  text-align: left;
  font-weight: bold;
  font-size: x-large;
}
.icon {
  justify-content: center;
  padding-top: 1.8%;
}
.card {
  justify-content: left;
  align-items: center;
}
.data {
  color: #cd0000;
  font-weight: bold;
  font-size: xx-large;
}
.detail-text {
  color: black;
  font-size: medium;
  font-weight: bold;
}
</style>