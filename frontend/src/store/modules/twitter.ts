import { Module, GetterTree, ActionTree, MutationTree } from 'vuex';
import axios from 'axios';
import { TwitterState, RootState, Tweet, User, Timeline } from '@/store/types';

const state: TwitterState = {
  timeline: [],
  user: [],
  timeline2: [],
  currentSearchScreenName: '',
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
  currentSearchScreenName: (state: TwitterState) => {
    return state.currentSearchScreenName;
  }
};

const mutations: MutationTree<TwitterState> = {
  setTimeline: (state, timeline: Tweet[]) => {
    state.timeline = timeline;
  },
  addUser: (state, user: User) => {
    state.user.push(user);
  },
  addTimeline: (state, timeline: Timeline) => {
    state.timeline2.push(timeline);
  },
  setCurrentSearchScreenName: (state, screenName: string) => {
    state.currentSearchScreenName = screenName;
  }
}

const actions: ActionTree<TwitterState, RootState> = {
  setTimeline: async ({ commit }, screenName: string) => {
    const timeline = await axios({ method: 'GET', url: `http://localhost:8080/api/twt/timeline?screenName=${screenName}` }).catch((error) => error)
    commit('setTimeline', timeline.data);


    const isExists: boolean = state.timeline2.some(el => {
      return el.screenName === screenName;
    });
    if (!isExists) {
      const timeline2 = await axios({ method: 'GET', url: `http://localhost:8080/api/twt/timeline?screenName=${screenName}` }).catch((error) => error)
      const payload: Timeline = {
        screenName: screenName,
        tweets: timeline2.data
      }
      commit('addTimeline', payload);
    }
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