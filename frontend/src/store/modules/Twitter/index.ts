import { Module } from 'vuex';
import { TwitterState, RootState } from '@/store/types';
import getters from './getters';

const state: TwitterState = {
  tweet: {
    id: 1302758543519559700,
    text: "2020-09-06: 1 tweet",
    createdAt: "2020-09-07T09:00:15.000+09:00",
    favoriteCount: 0,
    retweetCount: 0
  }
}

export const twitter: Module<TwitterState, RootState> = {
  namespaced: true,
  state,
  getters
};