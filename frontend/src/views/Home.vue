<template>
  <div class="home">
    <Achievement />
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import Achievement from '@/components/Achievement.vue';

export default Vue.extend({
  name: 'Home',
  components: {
    Achievement,
  },
});
</script>
