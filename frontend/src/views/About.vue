<template>
  <div class="about">
    <h1>This is an about page</h1>
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
});
</script>
