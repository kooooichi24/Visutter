<template>
  <div>
    this is statistics tweets component.
    <TotalTweets :total="tweetStatistics.totalTweets" />
  </div>
</template>

<script lang="ts">
import Vue from 'vue';
import moment, { Moment } from 'moment';
import TotalTweets from '@/components/Statistics/TotalTweets.vue';

export type DataType = {
  tweetStatistics: TweetStatistics;
}

export type TweetStatistics = {
  totalTweets: number | null;
  mostFavoriteCount: number | null;
  mostRetweetCount: number | null;
  longestStreak: Streak;
  currentStreak: Streak;
  streakSum: Streak;
  busiestDay: Streak;
}

export type Streak = {
  days: number | null;
  term: string;
}

export type Tweet = {
  id: number;
  text: string;
  createdAt: string;
  favoriteCount: number;
  retweetCount: number;
  isRetweeted: boolean;
}

export type MostCount = {
  favNum: number;
  retweetNum: number;
};

export default Vue.extend({
  name: 'StatisticsTweets',
  components: {
    TotalTweets
  },
  data(): DataType {
    return {
      tweetStatistics: {
        totalTweets: null,
        mostFavoriteCount: 0,
        mostRetweetCount: null,
        longestStreak: {
          days: null,
          term: ""
        },
        currentStreak: {
          days: null,
          term: ""
        },
        streakSum: {
          days: null,
          term: ""
        },
        busiestDay: {
          days: null,
          term: ""
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
      this.tweetStatistics.totalTweets = timeline.length;
    },
    calcMostCount(timeline: Tweet[]): void {
      let favNum = 0;
      let retweetNum = 0; 

      timeline.forEach(tweet => {
        if (tweet.favoriteCount > favNum) {
          favNum = tweet.favoriteCount;
        }
        if (tweet.isRetweeted === false && tweet.retweetCount > retweetNum) {
          retweetNum = tweet.retweetCount;
          console.log(tweet.createdAt, tweet.text, tweet.favoriteCount, tweet.retweetCount, tweet.isRetweeted);
        }
      })
      
      this.tweetStatistics.mostFavoriteCount = favNum;
      this.tweetStatistics.mostRetweetCount = retweetNum;
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

      this.tweetStatistics.busiestDay = {
        days: busiestNum,
        term: busiestDate
      }
    }
  }
});
</script>
