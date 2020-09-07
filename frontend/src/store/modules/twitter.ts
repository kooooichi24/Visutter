import { Module, GetterTree } from 'vuex';
import { TwitterState, RootState } from '@/store/types';

const state: TwitterState = {
  tweet: {
    id: 1302758543519559700,
    text: "2020-09-06: 1 tweet",
    createdAt: "2020-09-07T09:00:15.000+09:00",
    favoriteCount: 0,
    retweetCount: 0
  }
}

const getters: GetterTree<TwitterState, RootState> = {
  tweets: (state: TwitterState) => {
    return state.tweet;
  },
};

export const twitter: Module<TwitterState, RootState> = {
  namespaced: true,
  state,
  getters
};