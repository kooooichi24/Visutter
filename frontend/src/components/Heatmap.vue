<template>
  <v-container>
    <calendar-heatmap :values="[{ date: '2018-9-21', count: 3 }, { date: '2018-9-22', count: 6 }]" :endDate="endDate" :range-color="['#E0E0E0', '#BBDEFB', '#64B5F6', '#1E88E5', '#0D47A1']" />
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
      rangeColor: [],
      max: 20,

    }
  }
})
</script>

<style lang="scss" scoped>
</style>