<template>
  <v-app id="inspire">
    <v-navigation-drawer
      v-model="drawer"
      app
      clipped
    >
      <v-list dense>
        <v-list-item-group v-model="model" mandatory color="blue lighten-2">
          <v-list-item
            v-for="(item, i) in items"
            :key="item.text"
            :class='{ disabled: neverSearch && i!=0 }'
            link
            :disabled="neverSearch && i!=0"
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
        <v-subheader class="mt-4 grey--text text--lighten-5">SUBSCRIPTIONS</v-subheader>
        <v-list flat>
          <v-list-item-group v-model="activedUserNum" mandatory color="blue lighten-3">
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
          </v-list-item-group>
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
  activedUserNum: number;
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

interface User {
  id: number;
  name: string;
  screenName: string;
  description: string;
  followersCount: number;
  friendsCount: number;
  statusesCount: number;
  profileImageUrlHttps: string;
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
      activedUserNum: 0,
    };
  },
  computed: {
    neverSearch(): boolean {
      return this.$store.getters["twitter/timeline"].length === 0 && this.$store.getters["twitter/user"].length === 0;
    }
  },
  watch: {
    $route (to) {
      this.model = this.items.findIndex(item => item.path === to.path);
    },
    "$store.state.twitter.user"() {
      const user: User[] = this.$store.getters["twitter/user"];
      this.items2 = user;
    },
  },
  created(): void {
    this.$vuetify.theme.dark = true;

    // 本当はNavigation Guardを利用したいが、
    // vue-routerとtypescriptの相性が悪く自分の力では実現できないため、対応策として。
    if (this.$route.path != '/') {
      this.$router.push('/');
    }
  },
  mounted(): void {
    this.items2 = this.$store.getters["twitter/user"];
    this.model = this.items.findIndex(item => item.path === this.$route.path);
  },
  methods: {
    searchByUser(searchBy: string): void {
      if (searchBy !== null) {
        this.$store.dispatch('twitter/searchByScreenName', searchBy)
          .then(() => {
            this.$store.commit('twitter/setCurrentSearchScreenName', searchBy);
            this.changePath();
            this.bindNavUserSelected(searchBy);
          })
          .catch(() => {
            this.alert = true;
            // 5秒後にalertを非表示にする
            // setTimeoutごとのIDによりthis.alertがfalseになったら、setTimeoutを中断する処理を書く
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
    /**
     * 現在のパスがルートのときにユーザ検索を行った時に、パスをリダイレクトする関数
     * ユーザー検索時に呼び出されることを想定
     * 
     * ルーとパス以外の検索は、カレントパスからは遷移しない
     */
    changePath(): void {
      const currentPath: string = this.$route.path;
      if (currentPath === '/') {
        this.$router.push('/achievement');
      }
    },
    changeUser(screenName: string): void {
      this.$store.commit('twitter/setCurrentSearchScreenName', screenName);
    },
    /**
     * 検索に応じて、ナビゲーションバーのユーザーアイコンのアクティブ条件をバインドする
     */
    bindNavUserSelected(screenName: string): void {
      this.activedUserNum = this.items2.findIndex(i => i.screenName === screenName);
    }
  },
});
</script>

<style lang="scss" scoped>
.v-toolbar__title {
  padding-left: 10px !important;
}

.alert {
  position: fixed;
  right: 0;
  bottom: 0;
  z-index: 100;
}

.v-list-item.disabled {
  .v-list-item__action {
    .theme--dark.v-icon {
      color: rgba(255, 255, 255, 0.5) !important;
    }
  }
}
</style>