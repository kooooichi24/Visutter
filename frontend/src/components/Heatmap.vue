<template>
  <v-container>
    <calendar-heatmap :values="values" :endDate="endDate" :range-color="rangeColor" :max="max" />
  </v-container>
</template>

<!-- vue-calendar-heatmapが型定義ファイルが用意されていないため、jsファイルとして扱う -->
<script>
import Vue from 'vue';
import { CalendarHeatmap } from 'vue-calendar-heatmap';
import moment from 'moment';

export default Vue.extend({
  name: 'Heatmap',
  components: {
    CalendarHeatmap
  },
  data() {
    return {
      values: [],
      endDate: moment().format('YYYY-MM-DD'),
      rangeColor: ['#E0E0E0', '#BBDEFB', '#64B5F6', '#1E88E5', '#0D47A1'],
      max: 20,
    }
  },
  mounted() {
    const timelineByCurrentScreenName = this.$store.getters["twitter/timelineByCurrentScreenName"];
    this.setValues(timelineByCurrentScreenName);
  },
  watch: {
    "$store.state.twitter.timeline"() {
      const timelineByCurrentScreenName = this.$store.getters["twitter/timelineByCurrentScreenName"];
      this.setValues(timelineByCurrentScreenName);
    },
    "$store.state.twitter.currentSearchScreenName"(newValue) {
      const isExists = this.$store.getters["twitter/timeline"].some(el => {
        return el.screenName === newValue;
      });

      if (isExists) {
        const timelineByCurrentScreenName = this.$store.getters["twitter/timelineByCurrentScreenName"];
        this.setValues(timelineByCurrentScreenName);
      }
    }
  },
  methods: {
    setValues(timeline) {
      // 初期化
      this.values = [];
      
      const newTimeline = [ ...timeline ].reverse();
      newTimeline.forEach(nt => {
        const createdAt = moment(nt.createdAt).format('YYYY-MM-DD');
        
        // valuesにツイート日が存在しているか判定する
        let isCreatedAt = false;
        this.values.forEach(v => {
          if (v.date === createdAt) {
            isCreatedAt = true;

            // ツイート数をインクリメント
            v.count += 1;
          }
        })

        // 存在しない場合
        if (!isCreatedAt) {
          this.values.push({
            date: createdAt,
            count: 1,
          });
        }
      })
    }
  }
})
</script>

<style lang="scss" scoped>
</style>