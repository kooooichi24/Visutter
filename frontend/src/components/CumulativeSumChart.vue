<template>
  <div class="small">
    <line-chart :chart-data="datacollection"></line-chart>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import moment, { Moment } from 'moment';
import LineChart from './LineChart.vue'
import { ChartData } from "chart.js";

type DataType = {
  datacollection: ChartData | null;
}

type Tweet = {
  id: number;
  text: string;
  createdAt: string;
  favoriteCount: number;
  retweetCount: number;
  isRetweeted: boolean;
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
    setDataCollection(timeline: Tweet[]): void {
      const newTimeline = [ ...timeline ].reverse();
      const labels: string[] = [];

      const startDate: string = moment(newTimeline[0].createdAt).format('YYYY-MM-DD');
      const endDate: string = moment(newTimeline[newTimeline.length-1].createdAt).format('YYYY-MM-DD');
      

      // newTimeline.forEach(t => {
      //   const createdAt = moment(t.createdAt, 'YYYY-MM-DD');
      //   if (!labels.includes(createdAt)) {
      //     labels.push(createdAt);
      //   }
      // });
      // console.log(labels);

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