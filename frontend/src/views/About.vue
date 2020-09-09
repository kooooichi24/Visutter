<template>
  <div class="about">
    <h1>This is an about page</h1>
    <v-btn rounded color="primary" dark @click="getTimeline" >Home</v-btn>
    <CalendarHeatmap />
    <TotalTweets />
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import CalendarHeatmap from '@/components/CalendarHeatmap.vue';
import TotalTweets from '@/components/Statics/TotalTweets.vue';

export default Vue.extend({
  name: 'Home',
  components: {
    CalendarHeatmap,
    TotalTweets,
  },
  created(): void {
    this.$store.dispatch('twitter/setTimeline')
  },
  // mounted(): void {
  //   this.$store.watch(
  //     (state, getters) => getters["twitter/timeline"],
  //     (newValue) => console.log("vuex state is changed", newValue)
  //   );
  // },
  methods: {
    getTimeline(): void {
      console.log(this.$store.getters["twitter/timeline"]);
    }
  }
});
</script>
