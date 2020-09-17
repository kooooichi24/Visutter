<template>
  <v-container>
    <v-row>
      <v-col
        v-for="card in tweetStatistics"
        :key="card.key"
        cols="12"
        sm="4"
        lg="6"
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
      this.calcLongestStreak(timeline);
      this.calcCurrentStreak(timeline);
    },
    calcTotalTweets(timeline: Tweet[]): void {
      // timelineを年月が早い順に変更する
      const newTimeline = [ ...timeline ].reverse();
      const firstDate: Moment = moment(newTimeline[0].createdAt, 'YYYY-MM-DD');
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
      // timelineを年月が早い順に変更する
      const newTimeline = [ ...timeline ].reverse();
      const firstDate: Moment = moment(newTimeline[0].createdAt, 'YYYY-MM-DD');
      let busiestDate = "";
      let busiestNum = 1;
      let count = 1;
      let streakCount = 1;

      for (let i = 0; i < newTimeline.length-1; i++) {
        const first: Moment = moment(newTimeline[i].createdAt, 'YYYY-MM-DD');
        const second: Moment = moment(newTimeline[i+1].createdAt, 'YYYY-MM-DD');
        if (first.diff(second, 'days') === 0) {
          count++;
          if (count > busiestNum) {
            busiestNum = count;
            busiestDate = moment.months(first.get('month')) + first.format(' DD, YYYY');
          }
        } else {
          count = 1;
          streakCount++;
        }
      }

      const busiestDay: CardType = {
        overline: "Busiest day",
        title: busiestNum + " tweets",
        subtitle: busiestDate,
      };

      const streakSum: CardType = {
        overline: "Streak Sum",
        title: streakCount + " days",
        subtitle: moment.months(firstDate.get('month')) + firstDate.format(' DD, YYYY') + " ー Today",
      };

      this.tweetStatistics.busiestDay = busiestDay;
      this.tweetStatistics.streakSum = streakSum;
    },
    calcLongestStreak(timeline: Tweet[]): void {
      // timelineを年月が早い順に変更する
      const newTimeline = [ ...timeline ].reverse();
      const firstDate: Moment = moment(newTimeline[0].createdAt, 'YYYY-MM-DD');
      let longestStartDate: Moment = firstDate;
      let longestEndDate: Moment = moment();
      let startDate: Moment = firstDate;
      let endDate: Moment = firstDate;
      let streakCount = 1;
      let longestStreakCount = 0;

      for (let i = 0; i < newTimeline.length-1; i++) {
        const first: Moment = moment(newTimeline[i].createdAt, 'YYYY-MM-DD');
        const second: Moment = moment(newTimeline[i+1].createdAt, 'YYYY-MM-DD');
        
        if (Math.abs(first.diff(second, 'days')) === 1) {
          streakCount++;
          endDate = second;

          // 連続日数を更新したとき
          if (streakCount > longestStreakCount) {  
            longestStreakCount = streakCount;
            longestStartDate = startDate;
            longestEndDate = endDate;
          }
        } else if (Math.abs(first.diff(second, 'days')) >= 2) {
          startDate = second;
          endDate = second;
          streakCount = 1;
        }
      }

      const longestStreak: CardType = {
        overline: "Longest Streak",
        title: longestStreakCount + " days",
        subtitle: moment.months(longestStartDate.get('month')) + longestStartDate.format(' DD, YYYY') + " ー " + moment.months(longestEndDate.get('month')) + longestEndDate.format(' DD, YYYY'),
      };
      this.tweetStatistics.longestStreak = longestStreak;
    },
    calcCurrentStreak(timeline: Tweet[]): void {
      let currentStartDate: Moment = moment(timeline[0].createdAt, 'YYYY-MM-DD');
      const currentEndDate: Moment = moment(timeline[0].createdAt, 'YYYY-MM-DD');
      let currentStreakCount = 1;

      // 新しいtweetから検索する
      for (let i = 0; i < timeline.length-1; i++) {
        const first: Moment = moment(timeline[i].createdAt, 'YYYY-MM-DD');
        const second: Moment = moment(timeline[i+1].createdAt, 'YYYY-MM-DD');
        
        if (Math.abs(first.diff(second, 'days')) === 0) {
          continue;
        }
        if (Math.abs(first.diff(second, 'days')) === 1) {
          currentStreakCount++;
          currentStartDate = second;
          continue;
        }
        if (Math.abs(first.diff(second, 'days')) >= 2) {
          break;
        }
      }

      const currentStreak: CardType = {
        overline: "Current Streak",
        title: currentStreakCount + " days",
        subtitle: moment.months(currentStartDate.get('month')) + currentStartDate.format(' DD, YYYY') + " ー " + moment.months(currentEndDate.get('month')) + currentEndDate.format(' DD, YYYY'),
      }
      this.tweetStatistics.currentStreak = currentStreak;
    }
  }
});
</script>
