import { Module, GetterTree, ActionTree, MutationTree } from 'vuex';
import axios from 'axios';
import { TwitterState, RootState, User, Timeline } from '@/store/types';

const state: TwitterState = {
  user: [],
  timeline: [],
  currentSearchScreenName: '',
};

const getters: GetterTree<TwitterState, RootState> = {
  user: (state: TwitterState) => {
    return state.user;
  },
  timeline: (state: TwitterState) => {
    return state.timeline;
  },
  currentSearchScreenName: (state: TwitterState) => {
    return state.currentSearchScreenName;
  },
  timelineByCurrentScreenName: (state: TwitterState) => {
    const currentScreenNameTimeline = state.timeline.filter(tl => {
      return tl.screenName === state.currentSearchScreenName;
    });

    return currentScreenNameTimeline.length > 0 ? currentScreenNameTimeline[0].tweets : [];
  }
};

const mutations: MutationTree<TwitterState> = {
  addUser: (state, user: User) => {
    state.user.push(user);
  },
  addTimeline: (state, timeline: Timeline) => {
    state.timeline.push(timeline);
  },
  setCurrentSearchScreenName: (state, screenName: string) => {
    state.currentSearchScreenName = screenName;
  }
}

const actions: ActionTree<TwitterState, RootState> = {
  searchByScreenName: ({ commit }, screenName: string) => {
    return new Promise((resolve, reject) => {
      // 入力されたスクリーンネームが検索済みか判定
      const isUserExists: boolean = state.user.some(u => u.screenName === screenName);
      const isTimelineExists: boolean = state.timeline.some(el => el.screenName === screenName);

      // 未検索の場合
      if (!isUserExists && !isTimelineExists) {
        // userを検索
        axios.get(`/api/twt/user?screenName=${screenName}`)
          .then(res => {
            commit('addUser', res.data);
            resolve(res.data);
          })
          .catch(err => {
            reject(err.response)
          })
        
        // timelineを検索
        axios.get(`/api/twt/timeline?screenName=${screenName}`)
          .then(res => {
            const payload: Timeline = {
              screenName: screenName,
              tweets: res.data
            };
            commit('addTimeline', payload);
            resolve(res.data);
          })
          .catch(err => {
            reject(err.response);
          })
      } else {
        resolve();
      }
    });
  },
};


export const twitter: Module<TwitterState, RootState> = {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
};