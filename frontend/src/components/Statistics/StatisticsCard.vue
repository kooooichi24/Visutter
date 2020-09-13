<template>
  <v-card
    class="pa-2 grey darken-4"
    outlined
    flat
  >
    <v-list-item three-line>
      <v-list-item-content>
        <div class="overline">{{ card.overline }}</div>
        <v-list-item-title class="headline mb-1">{{ card.title }}</v-list-item-title>
        <v-list-item-subtitle>{{ card.subtitle }}</v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>
  </v-card>
</template>

<script lang="ts">
import Vue, { PropType } from 'vue';

export type CardType = {
  overline: string;
  title: string;
  subtitle: string;
}

export default Vue.extend({
  name: 'StatisitcsCard',
  props: {
    card: Object as PropType<CardType>,
  },
});
</script>
