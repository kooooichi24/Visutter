<template>
  <v-card
    class="pa-2 grey darken-4"
    outlined
    flat
  >
    <v-list-item three-line>
      <v-list-item-content>
        <div class="overline">Total</div>
        <v-list-item-title class="headline mb-1">{{ total }} tweets</v-list-item-title>
        <v-list-item-subtitle>Jan 1 ー Today</v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>
  </v-card>
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
