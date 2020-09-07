<template>
  <div class="about">
    <h1>This is an about page</h1>
    <CalendarHeatmap />
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import axios from 'axios';
import CalendarHeatmap from '@/components/CalendarHeatmap.vue';

export default Vue.extend({
  name: 'Home',
  components: {
    CalendarHeatmap,
  },
  created(): void {
    axios
      .get("http://localhost:8080/api/twt/timeline")
      .then(res => {
        console.log(res.data);
      });

    console.log(this.$store.getters["twitter/tweets"]);
  }
});
</script>
