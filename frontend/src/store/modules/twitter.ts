import { Module, GetterTree, ActionTree, MutationTree } from 'vuex';
import axios from 'axios';
import { TwitterState, RootState, Tweet, User } from '@/store/types';

const state: TwitterState = {
  timeline: [],
  user: [],
  timeline2: null,
};

const getters: GetterTree<TwitterState, RootState> = {
  timeline: (state: TwitterState) => {
    return state.timeline;
  },
  user: (state: TwitterState) => {
    return state.user;
  },
  timeline2: (state: TwitterState) => {
    return state.timeline2;
  },
};

const mutations: MutationTree<TwitterState> = {
  setTimeline: (state, timeline: Tweet[]) => {
    state.timeline = timeline;
  },
  addUser: (state, user: User) => {
    state.user.push(user);
  },
  addTimeline: (state, timeline: Tweet[]) => {
    // ユーザがtimelineに存在しない場合、追加
    // if ()
  }
}

const actions: ActionTree<TwitterState, RootState> = {
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