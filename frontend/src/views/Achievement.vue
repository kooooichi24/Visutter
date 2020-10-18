<template>
  <div class="achievement-page">
    <Achievement />
    <Heatmap />
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import Achievement from '@/components/Achievement.vue';
import Heatmap from '@/components/Heatmap.vue';

export default Vue.extend({
  name: 'AchievementPage',
  components: {
    Achievement,
    Heatmap,
  },
});
</script>
