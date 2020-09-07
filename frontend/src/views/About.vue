<template>
  <div class="about">
    <h1>This is an about page</h1>
    <v-btn rounded color="primary" dark @click="getTimeline" >Home</v-btn>
    <CalendarHeatmap />
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import CalendarHeatmap from '@/components/CalendarHeatmap.vue';

export default Vue.extend({
  name: 'Home',
  components: {
    CalendarHeatmap,
  },
  created(): void {
    this.$store.dispatch('twitter/setTimeline')
  },
  methods: {
    getTimeline(): void {
      console.log(this.$store.getters["twitter/timeline"]);
    }
  }
});
</script>
