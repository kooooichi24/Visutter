<template>
  <div class="home">
    <Achievement />
    <RandomChart />
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import Achievement from '@/components/Achievement.vue';
import RandomChart from '@/components/RandomChart.vue';

export default Vue.extend({
  name: 'Home',
  components: {
    Achievement,
    RandomChart
  },
});
</script>
