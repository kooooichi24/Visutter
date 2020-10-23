<template>
  <div class="home">
    <HomeCard />
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import HomeCard from '@/components/HomeCard.vue';

export default Vue.extend({
  name: 'Home',
  components: {
    HomeCard,
  },
});
</script>
