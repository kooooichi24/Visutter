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

type LabelsAndData = {

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
    this.$store.watch(
      (state, getters) => getters["twitter/timeline"],
      (newValue) => this.setDataCollection(newValue)
    );
  },
  methods: {
    setDataCollection(timeline: Tweet[]): void {
      this.datacollection = this.getDataCollection(timeline);
    },
    getDataCollection(timeline: Tweet[]): ChartData {
      const newTimeline = [ ...timeline ].reverse();
      const firstTweetDate: Moment = moment(newTimeline[0].createdAt);
      const today: Moment = moment();
      const data: number[] =[];
      const labels: string[] = [];
      let currentCount = 0;

      for (let i = 0; i <= Math.abs(firstTweetDate.diff(today, 'days')); i++) {
        let iDate = firstTweetDate.clone();
        
        // add i day
        iDate = moment(iDate.add(i, 'd'));
        labels.push(iDate.format('YYYY-MM-DD'));

        currentCount += newTimeline.filter(nt => {
          return iDate.isSame(moment(nt.createdAt, 'YYYY-MM-DD'), 'day');
        }).length;
        data.push(currentCount);
      }

      return {
        labels: labels,
        datasets: [
          {
            label: 'Tweet',
            backgroundColor: '#007900',
            data: data,
          }
        ]
      };
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