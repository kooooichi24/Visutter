import Vue from 'vue';
import VueRouter, { RouteConfig } from 'vue-router';
import Home from '../views/Home.vue';
import Heatmap from '../views/Heatmap.vue';
import Charts from '../views/Charts.vue';

Vue.use(VueRouter);

  const routes: Array<RouteConfig> = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/heatmap',
    name: 'Heatmap',
    component: Heatmap
  },
  {
    path: '/charts',
    name: 'Charts',
    component: Charts
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
