<template>
  <v-container>
    <v-row>
      <v-col
        v-for="card in tweetStatistics"
        :key="card.key"
        cols="12"
        sm="4"
        lg="3"
      >
        <StatisticsCard :card="card" />
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import Vue from 'vue';
import moment, { Moment } from 'moment';
import StatisticsCard from '@/components/Statistics/StatisticsCard.vue';

export type DataType = {
  tweetStatistics: TweetStatistics;
}

export type TweetStatistics = {
  totalTweets: CardType;
  mostFavoriteCount: CardType;
  mostRetweetCount: CardType;
  longestStreak: CardType;
  currentStreak: CardType;
  streakSum: CardType;
  busiestDay: CardType;
}


export type CardType = {
  overline: string;
  title: string;
  subtitle: string;
}

export type Tweet = {
  id: number;
  text: string;
  createdAt: string;
  favoriteCount: number;
  retweetCount: number;
  isRetweeted: boolean;
}

export default Vue.extend({
  name: 'StatisticsTweets',
  components: {
    StatisticsCard,
  },
  data(): DataType {
    return {
      tweetStatistics: {
        totalTweets: {
          overline: "",
          title: "",
          subtitle: "",
        },
        mostFavoriteCount: {
          overline: "",
          title: "",
          subtitle: "",
        },
        mostRetweetCount: {
          overline: "",
          title: "",
          subtitle: "",
        },
        longestStreak: {
          overline: "",
          title: "",
          subtitle: "",
        },
        currentStreak: {
          overline: "",
          title: "",
          subtitle: "",
        },
        streakSum: {
          overline: "",
          title: "",
          subtitle: "",
        },
        busiestDay: {
          overline: "",
          title: "",
          subtitle: "",
        },
      }
    };
  },
  mounted(): void {
    this.$store.watch(
      (state, getters) => getters["twitter/timeline"],
      (newValue) => this.calcStatistics(newValue)
    );
  },
  methods: {
    calcStatistics(timeline: Tweet[]): void {
      this.calcTotalTweets(timeline);
      this.calcMostCount(timeline);
      this.calcStreak(timeline);
    },
    calcTotalTweets(timeline: Tweet[]): void {
      const firstDate: Moment = moment(timeline.slice(-1)[0].createdAt, 'YYYY-MM-DD');
      const totalTweets: CardType = {
        overline: "Total",
        title: timeline.length + " tweets",
        subtitle: moment.months(firstDate.get('month')) + firstDate.format(' DD, YYYY') + " ー Today",
      }

      this.tweetStatistics.totalTweets = totalTweets;
    },
    calcMostCount(timeline: Tweet[]): void {
      let favNum = 0;
      let favDate: Moment = moment();
      let retweetNum = 0;
      let retweetDate: Moment = moment();

      timeline.forEach(tweet => {
        if (tweet.favoriteCount > favNum) {
          favNum = tweet.favoriteCount;
          favDate = moment(tweet.createdAt, 'YYYY-MM-DD');
        }
        if (tweet.isRetweeted === false && tweet.retweetCount > retweetNum) {
          retweetNum = tweet.retweetCount;
          retweetDate = moment(tweet.createdAt, 'YYYY-MM-DD');
        }
      })
      
      const mostFavoriteCount: CardType = {
        overline: "Most Fav",
        title: favNum + " fav",
        subtitle: moment.months(favDate.get('month')) + favDate.format(' DD, YYYY')
      }

      const mostRetweetCount: CardType = {
        overline: "Most Retweet",
        title: favNum + " retweet",
        subtitle: moment.months(retweetDate.get('month')) + retweetDate.format(' DD, YYYY')
      }

      this.tweetStatistics.mostFavoriteCount = mostFavoriteCount;
      this.tweetStatistics.mostRetweetCount = mostRetweetCount;
    },
    calcStreak(timeline: Tweet[]): void {
      let busiestDate = "";
      let busiestNum = 0;
      let count = 0;
      
      for (let i = 0; i < timeline.length-1; i++) {
        const first: Moment = moment(timeline[i].createdAt, 'YYYY-MM-DD');
        const second: Moment = moment(timeline[i+1].createdAt, 'YYYY-MM-DD');
        if (first.diff(second, 'days') === 0) {
          count += 1;
          if (count > busiestNum) {
            busiestNum = count;
            busiestDate = moment.months(first.get('month')) + first.format(' DD, YYYY');
          }
        } else {
          count = 0;
        }
      }

      const busiestDay: CardType = {
        overline: "Busiest day",
        title: busiestNum + " tweets",
        subtitle: busiestDate
      }

      this.tweetStatistics.busiestDay = busiestDay
    }
  }
});
</script>
