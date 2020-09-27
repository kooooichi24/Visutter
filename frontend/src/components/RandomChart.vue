<template>
  <div class="small">
    <line-chart :chart-data="datacollection"></line-chart>
    <button @click="fillData()">Randomize</button>
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
  name: 'RandomChart',
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
            label: 'Data One',
            backgroundColor: '#007900',
            data: [this.getRandomInt(), this.getRandomInt()]
          },
          {
            label: 'Data One',
            backgroundColor: '#f87979',
            data: [this.getRandomInt(), this.getRandomInt()]
          }
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