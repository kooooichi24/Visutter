<template>
  <div class="achievement">
    <StatisticsTweets />
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import StatisticsTweets from './Statistics/StatisticsTweets.vue';

export default Vue.extend({
  name: 'Achievement',
  components: {
    StatisticsTweets,
  },
});
</script>
