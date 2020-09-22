<template>
  <div>
    This is chart components.
    <LineChart />
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import LineChart from '@/components/LineChart.vue'

export default Vue.extend({
  name: 'Chart',
  components: {
    LineChart
  },
});
</script>
