<template>
  <div>
    this is total tweets component.
    <div>total</div>
    <div>{{ total }}</div>
    <div>tweets</div>
    <v-card
      class="pa-2 grey darken-4"
      outlined
      flat
    >
      One of three columns
    </v-card>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';

export default Vue.extend({
  name: 'TotalTweets',
  props: {
    total: Number,
  },
});
</script>
