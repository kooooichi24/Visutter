<template>
  <v-container>
    <v-row>
      <v-col
        v-for="card in readMe"
        :key="card.key"
        cols="12"
        sm="12"
        lg="12"
        xl="12"
      >
        <v-card
          class="pa-1 grey darken-3"
          elevation="10"
          outlined
        >
          <v-card-title>{{ card.title }}</v-card-title>
          <v-card-subtitle>{{ card.subtitle }}</v-card-subtitle>
          <!-- <v-card-text class="py-0">{{ card.text }}</v-card-text>
          <v-card-text class="pt-0">{{ card.text }}</v-card-text> -->
          <v-card-text
            v-for="(text, i) in card.describe"
            :key="text"
            :class="[i===0 ? 'pb-0' : 'pt-0']">{{ text }}</v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import Vue from 'vue';

type DataType = {
  readMe: ReadMe;
}

type ReadMe = {
  visutter: CardType;
  usage: CardType;
  importantNotice: CardType;
}

type CardType = {
  title: string;
  subtitle: string;
  describe: string[];
}


export default Vue.extend({
  name: 'HomeCard',
  components: {},
  data(): DataType {
    return {
      readMe: {
        visutter: {
          title: "Visutter",
          subtitle: "びじゅったー",
          describe: [
            "ツイート情報をダッシュボード形式で可視化するアプリ",
          ],
        },
        usage: {
          title: "Usage",
          subtitle: "使い方",
          describe: [
            "1. アカウント名で右上の検索バーから検索する。",
            "2. ナビゲーションバーで統計情報やチャート形式で確認",
          ],
        },
        importantNotice: {
          title: "Important Notice",
          subtitle: "注意事項",
          describe: [
            "TwitterAPIの仕様上、取得可能なツイート数に上限があります。",
            "3200ツイート以上のアクティブユーザ（ツイ廃）の方は、直近3200ツイートまでの情報となります。",
          ],
        },
      }
    };
  },
});
</script>
