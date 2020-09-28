<template>
  <div class="home">
    <Achievement />
    <CumulativeSumChart />
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import Achievement from '@/components/Achievement.vue';
import CumulativeSumChart from '@/components/CumulativeSumChart.vue';

export default Vue.extend({
  name: 'Home',
  components: {
    Achievement,
    CumulativeSumChart
  },
});
</script>
