<template>
  <div class="small">
    <line-chart :chart-data="datacollection"></line-chart>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import LineChart from './LineChart.vue'
import { ChartData } from "chart.js";

type DataType = {
  datacollection: ChartData | null;
}

export default Vue.extend({
  name: 'CumulativeSum',
  components: {
    LineChart
  },
  data(): DataType {
    return {
      datacollection: null
    }
  },
  mounted(): void {
    this.fillData()
  },
  methods: {
    fillData(): void {
      this.datacollection = {
        labels: [this.getRandomInt(), this.getRandomInt()],
        datasets: [
          {
            label: 'Tweets',
            backgroundColor: '#007900',
            data: [1, 2]
          },
        ]
      }
    },
    getRandomInt(): number {
      return Math.floor(Math.random() * (50 - 5 + 1)) + 5
    }
  }
});
</script>

<style lang="scss" scoped>
  .small {
    max-width: 600px;
    margin:  150px auto;
  }
</style>