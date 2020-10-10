<template>
  <div class="home">
    <Heatmap />
    <Achievement />
    <ClimingChart />
    <DailyEffortChart />
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import Heatmap from '@/components/Heatmap.vue';
import Achievement from '@/components/Achievement.vue';
import ClimingChart from '@/components/ClimingChart.vue';
import DailyEffortChart from '@/components/DailyEffortChart.vue';

export default Vue.extend({
  name: 'Home',
  components: {
    Heatmap,
    Achievement,
    ClimingChart,
    DailyEffortChart
  },
});
</script>
