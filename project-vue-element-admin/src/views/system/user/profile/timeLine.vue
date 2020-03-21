<template>
  <div class="block">
    <el-timeline>
      <el-timeline-item v-for="(item,index) of logs" :key="index" :timestamp="item.createTime" placement="top">
        <el-card>
          <p><el-icon class="el-icon-link" />  登录ip：{{ item.ip }} </p>
          <p><el-icon class="el-icon-location-outline" />  登录时间：{{ item.createTime }} </p>
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<script>
export default {
  props: {
    logs: {
      type: Array
    }
  },
  data() {
    return {
    }
  }
}
</script>
<style lang="scss" scoped>
  .el-card.is-always-shadow {
    box-shadow: none;
  }
  .el-card {
    border: 1px solid #f1f1f1;
    border-radius: 2px;
  }
</style>
