<template>
  <div class="charts-page">
    <ClimingChart :datacollection="datacollectionCliming" />
    <DailyEffortChart :datacollection="datacollectionDaillyEffort" />
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import moment from 'moment';
import ClimingChart from '../components/ClimingChart.vue';
import DailyEffortChart from '../components/DailyEffortChart.vue';
import { ChartData } from "chart.js";

type DataType = {
  datacollectionCliming: ChartData | null;
  datacollectionDaillyEffort: ChartData | null;
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

type ThinedOutDataAndLabel = {
  thinedOutData: number[];
  thinedOutLabels: string[];
}

type DataCollections = {
  datacollectionCliming: ChartData;
  datacollectionDaillyEffort: ChartData;
}

export default Vue.extend({
  name: 'ChartsPage',
  components: {
    ClimingChart,
    DailyEffortChart 
  },
  data(): DataType {
    return {
      datacollectionCliming: null,
      datacollectionDaillyEffort: null,
    };
  },
  mounted(): void {
    const timelineByCurrentScreenName: Tweet[] = this.$store.getters["twitter/timelineByCurrentScreenName"];
    
    const datacollections = this.convertTimelineToClimingAndDailyEffortData(timelineByCurrentScreenName);
    this.datacollectionCliming = datacollections.datacollectionCliming;
    this.datacollectionDaillyEffort = datacollections.datacollectionDaillyEffort;
  },
  watch: {
    "$store.state.twitter.timeline"() {
      const timelineByCurrentScreenName: Tweet[] = this.$store.getters["twitter/timelineByCurrentScreenName"];
      
      const datacollections = this.convertTimelineToClimingAndDailyEffortData(timelineByCurrentScreenName);
      this.datacollectionCliming = datacollections.datacollectionCliming;
      this.datacollectionDaillyEffort = datacollections.datacollectionDaillyEffort;
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
        
        const datacollections = this.convertTimelineToClimingAndDailyEffortData(timelineByCurrentScreenName);
        this.datacollectionCliming = datacollections.datacollectionCliming;
        this.datacollectionDaillyEffort = datacollections.datacollectionDaillyEffort;
      }
    },
  },
  methods: {
    /**
     * タイムラインのツイート情報 を ClimingとDailyEffort用のChart.jsのデータ構造に変換するメソッド。
     * 
     * @param {Tweet[]} timeline タイムラインの配列。
     * @return {DataCollections} ClimingのChart.jsのデータ と DailyEffortのChart.jsのデータ。
     */
    convertTimelineToClimingAndDailyEffortData(timeline: Tweet[]): DataCollections {
      const newTimeline = [ ...timeline ].reverse();

      const dataDailyEffortObject: { [key: string]: number} = {};
      const dataClimingObject: { [key: string]: number} = {};
      let labels: string[] = [];
      let climingCount = 0;

      newTimeline.forEach(nt => {
        const createdAt = moment(nt.createdAt).format('YYYY-MM-DD');
        climingCount += 1;
        dataClimingObject[createdAt] = climingCount;
        
        if (Object.prototype.hasOwnProperty.call(dataDailyEffortObject, createdAt)) {
          dataDailyEffortObject[createdAt] += 1;
        } else {
          dataDailyEffortObject[createdAt] = 1;

          labels.push(createdAt);
        }
      });

      let dataDailyEffort = Object.values(dataDailyEffortObject);
      let dataCliming = Object.values(dataClimingObject);

      if (dataCliming.length > 300) {
        const thinedOutDataClimingAndLabel: ThinedOutDataAndLabel = this.thinOutDataAndLabel(dataCliming, labels);
        const thinedOutDataDailyEffortAndLabel: ThinedOutDataAndLabel = this.thinOutDataAndLabel(dataDailyEffort, labels);
        dataCliming = thinedOutDataClimingAndLabel.thinedOutData;
        labels = thinedOutDataClimingAndLabel.thinedOutLabels;

        dataDailyEffort = thinedOutDataDailyEffortAndLabel.thinedOutData;
      }

      const datacollectionCliming = {
        labels: labels,
        datasets: [
          {
            label: 'Tweet',
            data: dataCliming,
            borderColor: '#2196F3',
            borderWidth: 2,
          }
        ]
      };

      const datacollectionDaillyEffort = {
        labels: labels,
        datasets: [
          {
            label: 'Tweet',
            data: dataDailyEffort,
            borderColor: '#2196F3',
            borderWidth: 2,
          }
        ]
      }
      return {
        datacollectionCliming,
        datacollectionDaillyEffort
      };
    },

    /**
     * データとラベルを間引く関数。
     * 条件: Tweet数が300以上の場合は間引く。
     * 
     * @param {number[]} data ある1日のツイート数の配列。
     * @param {string[]} labels ツイート日の配列。
     * @return {ThinedOutDataAndLabel}
     */
    thinOutDataAndLabel(data: number[], labels: string[]): ThinedOutDataAndLabel {
      const thinedOutData = data.reverse().filter((d, i) => {
        return i===0 || i===data.length-1 || i%7===0;
      }).reverse();
      const thinedOutLabels = labels.reverse().filter((l, i) => {
        return i===0 || i===labels.length-1 || i%7===0;
      }).reverse();
      return { thinedOutData, thinedOutLabels };
    },
  }
});
</script>
