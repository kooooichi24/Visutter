<template>
  <v-app id="inspire">
    <v-navigation-drawer
      v-model="drawer"
      app
      clipped
    >
      <v-list dense :disabled="neverSearch">
        <v-list-item-group v-model="model" mandatory color="blue lighten-2">
          <v-list-item
            v-for="item in items"
            :key="item.text"
            link
            @click="handlePath(item.path)"
          >
            <v-list-item-action>
              <v-icon>{{ item.icon }}</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>
                {{ item.text }}
              </v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
        <v-subheader class="mt-4 grey--text text--darken-1">SUBSCRIPTIONS</v-subheader>
        <v-list>
          <v-list-item
            v-for="item in items2"
            :key="item.text"
            link
            @click="changeUser(item.screenName)"
          >
            <v-list-item-avatar>
              <img
                :src="item.profileImageUrlHttps"
                alt=""
              >
            </v-list-item-avatar>
            <v-list-item-title v-text="item.screenName"></v-list-item-title>
          </v-list-item>
        </v-list>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar
      app
      clipped-left
      dense
    >
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title class="mr-12 align-center">
        <span class="title">Visutter</span>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-row
        align="center"
        style="max-width: 650px"
      >
        <v-text-field
          v-model="searchBy"
          :append-icon-cb="() => {}"
          placeholder="Search..."
          single-line
          append-icon="mdi-magnify"
          color="white"
          hide-details
          @keydown.enter="searchByUser(searchBy)"
          @click:append="searchByUser(searchBy)"
        ></v-text-field>
      </v-row>
    </v-app-bar>

    <v-main>
      <v-container class="alert">
        <v-row justify="end">
          <v-col
            cols="12"
            xs="12"
            sm="4"
            xl="3"
          >
            <v-alert
              v-model="alert"
              dismissible
              type="error"
              transition="slide-x-reverse-transition"
            >User Not Found</v-alert>
          </v-col>
        </v-row>
      </v-container>
      
      <!-- ルートアウトレット -->
      <!-- ルートとマッチしたコンポーネントがここへ描画されます -->
      <router-view></router-view>
    </v-main>
  </v-app>
</template>

<script lang="ts">
import Vue from 'vue';

export type DataType = {
  searchBy: string | null;
  drawer: boolean | null;
  items: Items[];
  items2: Items2[];
  alert: boolean;
  model: number;
}

export type Items = {
  icon: string;
  text: string;
  path: string;
}

export type Items2 = {
  profileImageUrlHttps: string;
  screenName: string;
}

export default Vue.extend({
  name: 'App',
  data(): DataType {
    return {
      searchBy: null,
      drawer: null,
      items: [
        { icon: 'mdi-view-dashboard-outline', text: 'Home', path: '/' },
        { icon: 'mdi-view-comfy', text: 'Achievement', path: '/achievement' },
        { icon: 'mdi-chart-multiline', text: 'Progress Charts', path: '/charts' },
      ],
      items2: [
      ],
      alert: false,
      model: 0, // ナビゲーションバー選択用
    };
  },
  computed: {
    neverSearch(): boolean {
      return this.$store.getters["twitter/timeline"].length === 0 && this.$store.getters["twitter/user"].length === 0;
    }
  },
  watch: {
    $route (to, from) {
      this.model = this.items.findIndex(item => item.path === from.path);
    }
  },
  created(): void {
    this.$vuetify.theme.dark = true
  },
  mounted(): void {
    this.$store.watch(
      (state, getters) => getters["twitter/user"],
      (newValue) => {
        this.items2 = newValue
      }
    );

    this.model = this.items.findIndex(item => item.path === this.$route.path);
  },
  methods: {
    searchByUser(searchBy: string): void {
      if (searchBy !== null) {
        this.$store.dispatch('twitter/searchByScreenName', searchBy)
          .then(() => {
            this.$store.commit('twitter/setCurrentSearchScreenName', searchBy);
          })
          .catch(() => {
            this.alert = true;
            // 5秒後にalertを非表示にする
            setTimeout(() => {
              this.alert = false
            }, 5000);
          })
      }
    },
    handlePath(path: string): void {
      // Error: NavigationDuplicated を回避するためにパスが変更する際のみpushする
      if (this.$route.path !== path) {
        this.$router.push(path);
      }
    },
    changeUser(screenName: string): void {
      this.$store.commit('twitter/setCurrentSearchScreenName', screenName);
    }
  },
});
</script>

<style lang="scss" scoped>
.alert {
  position: fixed;
  right: 0;
  bottom: 0;
  z-index: 100;
}
</style>