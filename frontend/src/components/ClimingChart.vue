<template>
  <v-container>
    <div class="chart-container">
      <line-chart :chart-data="datacollection" :options="options" :styles="chartStyles" />
    </div>
  </v-container>
</template>

<script lang="ts">
import Vue, { PropType } from 'vue';
import moment from 'moment';
import LineChart from './LineChart.vue'
import { ChartData, ChartOptions } from "chart.js";

type DataType = {
  options: ChartOptions;
  chartStyles: ChartStyles;
}

type ChartStyles = {
  width: string;
  height: string;
}

export default Vue.extend({
  name: 'ClimingChart',
  components: {
    LineChart
  },
  props: {
    datacollection: Object as PropType<ChartData>,
  },
  data(): DataType {
    return {
      options: {
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
      },
      // チャートのスタイル: <canvas>のstyle属性として設定
      chartStyles: {
        height: "100%",
        width: "100%"
      }
    };
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