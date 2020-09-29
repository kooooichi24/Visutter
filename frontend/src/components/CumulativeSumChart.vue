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
    };
  },
  mounted(): void {
    this.fillData();
    this.$store.watch(
      (state, getters) => getters["twitter/timeline"],
      (newValue) => this.setDataCollection(newValue)
    );
  },
  methods: {
    setDataCollection(timeline: any): void {
      console.log(timeline);
    },
    fillData(): void {
      this.datacollection = {
        labels: ['1/1', '1/2', '1/3', '1/4'],
        datasets: [
          {
            label: 'Tweet',
            backgroundColor: '#007900',
            data: [0, 1, 2, 3]
          },
        ],
      };
    },
    getRandomInt(): number {
      return Math.floor(Math.random() * (50 - 5 + 1)) + 5
    },
  },
});
</script>

<style lang="scss" scoped>
  .small {
    max-width: 600px;
    margin:  150px auto;
  }
</style>