<template>
  <div class="heatmap-page">
    <Heatmap />
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import Heatmap from '@/components/Heatmap.vue';

export default Vue.extend({
  name: 'HeatmapPage',
  components: {
    Heatmap,
  },
});
</script>
