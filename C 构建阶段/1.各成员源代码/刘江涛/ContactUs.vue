<script setup>

</script>

<script>
  export default {
    data() {
      return {
        cardWidth: '1260px',
        TEL: {
          Email: 'siteexample@qq.com',
          QQ: '1101191205',
          PhoneNumber: '+86 135 XXXX XXXX'
        }
      }
    }
  }
</script>

<template>
  <div class="footer" style="width: 100%">
    <div style="height: 100%" :style="{ width: cardWidth }">
      <el-container style="height: 100%">
        <el-main style="width: 60%">
          <el-divider content-position="center">友情链接</el-divider>
          <el-row class="link" style="width: 100%">
            <el-col class="link">
              <el-link href="https://www.cqu.edu.cn">
                <span class="link-text">重庆大学</span>
              </el-link>
            </el-col>
            <el-col class="link">
              <el-link>
                <span class="link-text">中国志愿服务网</span>
              </el-link>
            </el-col>
            <el-col class="link">
              <el-link href="http://www.chinacharityfederation.org">
                <span class="link-text">中华慈善总会</span>
              </el-link>
            </el-col>
            <el-col class="link">
              <el-link href="http://www.chinesevolunteer.org">
                <span class="link-text">中华志愿者协会</span>
              </el-link>
            </el-col>
          </el-row>
          <el-divider content-position="center">技术支持</el-divider>
          <el-row class="link" style="width: 100%">
            <el-col class="link">
              <el-link href="https://element.eleme.cn">
                <span class="link-text">ElementUI</span>
              </el-link>
            </el-col>
            <el-col class="link">
              <el-link href="https://echarts.apache.org">
                <span class="link-text">Echarts</span>
              </el-link>
            </el-col>
            <el-col class="link">
              <el-link href="https://cn.vuejs.org">
                <span class="link-text">Vue.js</span>
              </el-link>
            </el-col>
            <el-col class="link">
              <el-link href="https://mybatis.net.cn">
                <span class="link-text">MyBatis</span>
              </el-link>
            </el-col>
            <el-col class="link">
              <el-link href="https://docs.spring.io">
                <span class="link-text">SpringBoot</span>
              </el-link>
            </el-col>
          </el-row>
          <el-divider content-position="center">联系我们</el-divider>
          <el-row class="contact" style="width: 100%">
            <el-col class="contact">
              <div v-if="TEL.Email != null">
                <div class="link-text">官方邮箱: {{ TEL.Email }}</div>
              </div>
            </el-col>
            <el-col class="contact" >
              <div v-if="TEL.QQ != null">
                <div class="link-text">官方QQ: {{ TEL.QQ }}</div>
              </div>
            </el-col>
            <el-col class="contact">
              <div v-if="TEL.PhoneNumber != null">
                <div class="link-text">客服电话: {{ TEL.PhoneNumber }}</div>
              </div>
            </el-col>
          </el-row>
        </el-main>
        <el-aside style="display: flex;flex-direction:column;align-items: center;justify-content: center">
          <div>
            <img width="100%" src="../assets/QRcode.png"/>
          </div>
        </el-aside>
      </el-container>
    </div>
  </div>
</template>

<style scoped>
.footer {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 270px;
  background-color: rgb(0, 0, 0);
}
.link {
  display: flex;
  align-items: center;
  justify-content: center;
}
.contact {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
}
.link-text {
  color: rgb(255, 255, 255);
  font-weight: bold;
  font-size: medium;
}
</style>