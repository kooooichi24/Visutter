<template>
  <v-container>
    <div class="chart-container">
      <line-chart :chart-data="datacollection" :options="options" :styles="chartStyles" />
    </div>
  </v-container>
</template>

<script lang="ts">
import Vue from 'vue';
import moment, { Moment } from 'moment';
import LineChart from './LineChart.vue'
import { ChartData, ChartOptions } from "chart.js";

type DataType = {
  datacollection: ChartData | null;
  options: ChartOptions | null;
  chartStyles: ChartStyles;
}

type ChartStyles = {
  width: string;
  height: string;
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
  name: 'ClimingChart',
  components: {
    LineChart
  },
  data(): DataType {
    return {
      datacollection: null,
      options: null,
      // チャートのスタイル: <canvas>のstyle属性として設定
      chartStyles: {
        height: "100%",
        width: "100%"
      }
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
      this.options = {
        scales: {
          xAxes: [{
            type: 'time',
            time: {
              unit: 'day',
              displayFormats: {
                day: 'YYYY-MM-DD'
              },
            },
            ticks: {
              maxRotation: 0,
              minRotation: 0,
              autoSkip: true,
              maxTicksLimit: 3 //値の最大表示数
            }
          }]
        },
        maintainAspectRatio: false,
        tooltips: {
          mode: 'index',
          intersect: false,
          callbacks: {
            title: (tooltipItem) => {
              return moment(tooltipItem[0].xLabel).format('YYYY-MM-DD');
            }
          }
        },
        hover: {
          mode: 'index',
          intersect: false
        },
        legend: {
          onClick: function () { return false }
        }
      }
    },
    getDataCollection(timeline: Tweet[]): ChartData {
      const newTimeline = [ ...timeline ].reverse();
      const data: number[] =[];
      const labels: Moment[] = [];
      let currentCount = 0;
      
      // ツイート日をlabelsに代入する
      newTimeline.forEach(nt => {
        const createdAt = moment(nt.createdAt, 'YYYY-MM-DD');
        
        // labelsにツイート日が存在していない場合追加する
        let isCreatedAt = false;
        labels.forEach(l => {
          if (moment(l).isSame(createdAt, 'day')) {
            isCreatedAt = true;
          }
        })
        if (!isCreatedAt) {
          labels.push(createdAt);
        }
      })

      // ツイート数をdataに代入する
      labels.forEach(l => {
        currentCount += newTimeline.filter(nt => {
          return moment(l).isSame(moment(nt.createdAt), 'day');
        }).length;
        data.push(currentCount);
      })

      return {
        labels: labels,
        datasets: [
          {
            label: 'Tweet',
            data: data,
            borderColor: '#2196F3',
            borderWidth: 2,
          }
        ]
      };
    },
  },
});
</script>

<style lang="scss">
.chart-container {
  /**
   * vue-chartjsで生成する<canvas>がabsoluteのため、
   * relateveを設定
   */
  position: relative;

  /**
   * chartStylesを設定しているので、
   * height/wightが有効になる
   */
  height: 40vh;
  margin: 0 auto;
}
</style>