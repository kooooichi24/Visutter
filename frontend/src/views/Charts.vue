<template>
  <div class="charts-page">
    <ClimingChart />
    <DailyEffortChart />
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import ClimingChart from '../components/ClimingChart.vue';
import DailyEffortChart from '../components/DailyEffortChart.vue';

export default Vue.extend({
  name: 'ChartsPage',
  components: {
    ClimingChart,
    DailyEffortChart 
  },
});
</script>
