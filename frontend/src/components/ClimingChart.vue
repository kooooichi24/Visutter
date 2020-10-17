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

type Timeline = {
  screenName: string;
  tweets: Tweet[];
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
    const timelineByCurrentScreenName: Tweet[] = this.$store.getters["twitter/timelineByCurrentScreenName"];
    this.setDataCollection(timelineByCurrentScreenName);
  },
  watch: {
    "$store.state.twitter.timeline"() {
      const timelineByCurrentScreenName: Tweet[] = this.$store.getters["twitter/timelineByCurrentScreenName"];
      this.setDataCollection(timelineByCurrentScreenName);
    },
    /**
     * screenNameが変更された、かつ、vuexのtimelineにscreenNameのオブジェクトが格納されている状態で処理を行う
     * screenNameが変更されるシナリオは以下の2つ
     * 1. 検索バーにscreenNameが入力され、検索されたとき
     * 2. ナビゲーションバーの異なるユーザアイコンを選択したとき
     * 
     * シナリオ1の場合は、screenNameとtimelineの両方が変更される。
     * そのため、timelineにtwitterから取得してきたデータが格納された後に処理を行う必要がある
     */
    "$store.state.twitter.currentSearchScreenName"(newValue) {
      const isExists: boolean = this.$store.getters["twitter/timeline"].some((el: Timeline) => {
        return el.screenName === newValue;
      });

      if (isExists) {
        const timelineByCurrentScreenName: Tweet[] = this.$store.getters["twitter/timelineByCurrentScreenName"];
        this.setDataCollection(timelineByCurrentScreenName);
      }
    },
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