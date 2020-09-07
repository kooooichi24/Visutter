export interface RootState {
  version: string;
}

export interface TwitterState {
  tweets: Tweet[];
  timeline: Tweet[];
}

export interface Tweet {
  id: number
  text: string
  createdAt: string
  favoriteCount: number
  retweetCount: number
}