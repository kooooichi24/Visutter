<template>
  <div class="home">
    <Achievement />
    <Chart />
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import Achievement from '@/components/Achievement.vue';
import Chart from '@/components/Chart.vue';

export default Vue.extend({
  name: 'Home',
  components: {
    Achievement,
    Chart
  },
});
</script>
