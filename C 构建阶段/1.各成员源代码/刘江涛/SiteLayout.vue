<script setup>
  import Navigator from './Navigator.vue';
  import ContactUs from './ContactUs.vue';
  import Media from './Media.vue';
</script>

<script>
  export default {
    data() {
      return {
        cardWidth: '1260px'
      }
    },
    components: {
      Navigator,
      ContactUs,
      Media
    }
  }
</script>

<template>
  <div class="site">
    <Navigator></Navigator>
    <router-view style="min-height: 600px" :style="{ width: cardWidth }"></router-view>
    <Media></Media>
    <ContactUs></ContactUs>
  </div>
</template>

<style scoped>
.site {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
</style>