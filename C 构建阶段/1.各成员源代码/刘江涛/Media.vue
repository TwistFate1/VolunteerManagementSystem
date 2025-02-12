<script setup>

</script>

<script>

</script>

<template>
  <div class="media">
    <div style="display: flex;flex-direction: column;justify-content:right;align-items: right;z-index: 10;right: 60px;padding: 0%" border-radius="30px">
      <el-popover trigger="hover" placement="left-start">
        <div>
          <img src="../assets/QRcode.png">
        </div>
        <el-card slot="reference" shadow="hover">
          <i class="el-icon-coordinate"></i>
        </el-card>
      </el-popover>
      <el-popover trigger="hover" placement="left">
        <div>
          <img src="../assets/QRcode.png">
        </div>
        <el-card slot="reference" shadow="hover">
          <i class="el-icon-thumb"></i>
        </el-card>
      </el-popover>
      <el-popover trigger="hover" placement="left-end">
        <div>
          <img src="../assets/QRcode.png">
        </div>
        <el-card slot="reference" shadow="hover">
          <i class="el-icon-setting"></i>
        </el-card>
      </el-popover>
    </div>
    <el-backtop :bottom="150" :right="60"></el-backtop>
  </div>
</template>

<style scoped>
.media {
  position: fixed;
  z-index: 10;
  top: 15vw;
  right: 50px;
  display: flex;
  align-items: center;
  justify-content: right;
}
</style>