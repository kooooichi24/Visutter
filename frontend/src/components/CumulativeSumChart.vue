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
      const labels = this.getLabels(timeline);
      const data = this.getData(timeline);
      console.log(labels, data);
      
    },
    getLabels(timeline: Tweet[]): string[] {
      const newTimeline = [ ...timeline ].reverse();
      const firstTweetDate: Moment = moment(newTimeline[0].createdAt);
      const today: Moment = moment();

      const labels: string[] = [firstTweetDate.format('YYYY-MM-DD')];
      // 初めてツイートした日から今日までの日付を配列に挿入する
      for (let i = 1; i <= Math.abs(firstTweetDate.diff(today, 'days')); i++) {
        const iDate = firstTweetDate.clone();
        labels.push(iDate.add(i, 'd').format('YYYY-MM-DD'));
      }

      return labels;
    },
    getData(timeline: Tweet[]): number[] {
      const newTimeline = [ ...timeline ].reverse();
      const firstTweetDate: Moment = moment(newTimeline[0].createdAt);
      const today: Moment = moment();
      const data: number[] =[];
      let currentCount = 0;

      for (let i = 0; i <= Math.abs(firstTweetDate.diff(today, 'days')); i++) {
        const iDate = firstTweetDate.clone();
        iDate.add(i, 'd');
        currentCount += newTimeline.filter(nt => {
          iDate.isSame(moment(nt.createdAt, 'YYYY-MM-DD'), 'day');
        }).length;
        data.push(currentCount);
      }
      
      return data;
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