import { Module, GetterTree, ActionTree, MutationTree } from 'vuex';
import axios from 'axios';
import { TwitterState, RootState, Tweet, User } from '@/store/types';

const state: TwitterState = {
  tweets: [
    {
      id: 1302758543519559700,
      text: "2020-09-06: 1 tweet",
      createdAt: "2020-09-07T09:00:15.000+09:00",
      favoriteCount: 0,
      retweetCount: 0,
      isRetweeted: false,
    },
  ],
  timeline: [],
  user: [],
};

const getters: GetterTree<TwitterState, RootState> = {
  tweets: (state: TwitterState) => {
    return state.tweets;
  },
  timeline: (state: TwitterState) => {
    return state.timeline;
  },
  user: (state: TwitterState) => {
    return state.user;
  }
};

const mutations: MutationTree<TwitterState> = {
  addTweet: (state, tweet: Tweet) => {
    state.tweets.push(tweet);
  },
  setTimeline: (state, timeline: Tweet[]) => {
    state.timeline = timeline;
  },
  addUser: (state, user: User) => {
    state.user.push(user);
  }
}

const actions: ActionTree<TwitterState, RootState> = {
  addTweet: ({ commit }, tweet: Tweet) => {
    commit('addTweet', tweet);
  },
  setTimeline: async ({ commit }, screenName: string) => {
    const timeline = await axios({ method: 'GET', url: `http://localhost:8080/api/twt/timeline?screenName=${screenName}` }).catch((error) => error)
    commit('setTimeline', timeline.data);
  },
  searchScreenName: async ({ commit }, screenName: string) => {
    if (state.user.find(u => u.screenName === screenName) === undefined) {
      const user = await axios({ method: 'GET', url: `http://localhost:8080/api/twt/user?screenName=${screenName}` }).catch((error) => error)
      commit('addUser', user.data); 
    }
  }
};


export const twitter: Module<TwitterState, RootState> = {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
};