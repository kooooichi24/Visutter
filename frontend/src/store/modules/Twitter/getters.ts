import { GetterTree } from 'vuex';
import { TwitterState, RootState } from '@/store/types';

const getters: GetterTree<TwitterState, RootState> = {
  tweets: (state: TwitterState) => {
    return state.tweet;
  },
};

export default getters;