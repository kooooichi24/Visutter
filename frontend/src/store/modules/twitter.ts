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

    return currentScreenNameTimeline[0].tweets;
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
  setTimeline: ({ commit }, screenName: string) => {
    return new Promise((resolve, reject) => {
      const isExists: boolean = state.timeline.some(el => {
        return el.screenName === screenName;
      });

      if (!isExists) {
        axios.get(`http://localhost:8080/api/twt/timeline?screenName=${screenName}`)
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
      }
    });
    // const isExists: boolean = state.timeline.some(el => {
    //   return el.screenName === screenName;
    // });
    // if (!isExists) {
    //   const timeline = await axios({ method: 'GET', url: `http://localhost:8080/api/twt/timeline?screenName=${screenName}` }).catch((error) => error)
    //   const payload: Timeline = {
    //     screenName: screenName,
    //     tweets: timeline.data
    //   }
    //   commit('addTimeline', payload);
    // }
  },
  searchScreenName: ({ commit }, screenName: string) => {
    return new Promise((resolve, reject) => {
      if (state.user.find(u => u.screenName === screenName) === undefined) {
        axios
          .get(`http://localhost:8080/api/twt/user?screenName=${screenName}`)
          .then(res => {
            commit('addUser', res.data);
            resolve(res.data);
          })
          .catch(err => {
            reject(err.response)
          })
      }
    });
    // if (state.user.find(u => u.screenName === screenName) === undefined) {
    //   // const user = await axios({ method: 'GET', url: `http://localhost:8080/api/twt/user?screenName=${screenName}` }).catch((error) => error)
    //   // commit('addUser', user.data);

    //   axios
    //     .get(`http://localhost:8080/api/twt/user?screenName=${screenName}`)
    //     .then(res => {
    //       commit('addUser', res.data);
    //     })
    //     .catch(err => {
    //       throw err;
    //     })
    //   // try {
    //   //   const user = await axios({ method: 'GET', url: `http://localhost:8080/api/twt/user?screenName=${screenName}` })
    //   // } catch(error) {
    //   //   return error.reponse.data.message;
    //   // }
    // }
  }
};


export const twitter: Module<TwitterState, RootState> = {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
};