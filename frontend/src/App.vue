<template>
  <v-app id="inspire">
    <v-navigation-drawer
      v-model="drawer"
      app
      clipped
    >
      <v-list dense>
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
      <div class="alert">
        <v-alert
          v-model="alert"
          dismissible
          type="error"
          transition="slide-x-reverse-transition"
        >User Not Found</v-alert>
      </div>
      
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
        { icon: 'mdi-view-dashboard-outline', text: 'Achievement', path: '/' },
        { icon: 'mdi-chart-multiline', text: 'Progress Charts', path: '/charts' },
        { icon: 'mdi-view-comfy', text: 'Heatmap', path: '/heatmap' },
      ],
      items2: [
      ],
      alert: false,
    };
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
  },
  methods: {
    searchByUser(searchBy: string): void {
      if (searchBy !== null) {
        // this.$store.dispatch('twitter/setTimeline', searchBy);
        this.$store.dispatch('twitter/searchScreenName', searchBy)
          .then()
          .catch(() => {
            this.alert = true;

            // 5秒後にalertを非表示にする
            setTimeout(() => {
              this.alert = false
            }, 5000);
          })
        // this.$store.commit('twitter/setCurrentSearchScreenName', searchBy);
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
.v-main {
  position: relative;
}
.alert {
  width: 30%;
  position: absolute;
  right: 10px;
  bottom: 0;
}
</style>