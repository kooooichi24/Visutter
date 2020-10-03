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
      const today: string = moment().format('YYYY-MM-DD');
      const data: number[] =[];
      const labels: string[] = [];
      let currentCount = 0;

      // ツイート日をlabelsに代入する
      newTimeline.forEach(nt => {
        const createdAt = moment(nt.createdAt).format('YYYY-MM-DD');
        if (!labels.includes(createdAt)) {
          labels.push(createdAt);
        }
      })
      // 今日のツイートがない場合は、今日の日付を追加する
      if (!labels.includes(today)) {
        labels.push(today);
      }

      // ツイート数をdataに代入する
      labels.forEach(l => {
        currentCount += newTimeline.filter(nt => {
          return moment(l).isSame(moment(nt.createdAt), 'day');
        }).length;
        data.push(currentCount);
      })
      // 今日のツイートがない場合は、今日の日付に現在のツイート数を追加する
      if (!labels.includes(today)) {
        data.push(currentCount);
      }
      

      return {
        labels: labels,
        datasets: [
          {
            label: 'Tweet',
            data: data,
            borderColor: '#2196F3',
            borderWidth: 1,
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