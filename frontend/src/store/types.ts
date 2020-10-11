export interface RootState {
  version: string;
}

export interface TwitterState {
  timeline: Tweet[];
  user: User[];
}

export interface Tweet {
  id: number
  text: string
  createdAt: string
  favoriteCount: number
  retweetCount: number
  isRetweeted: boolean
}

export interface User {
  id: number
  name: string
  screenName: string
  description: string
  followersCount: number
  friendsCount: number
  statusesCount: number
  profileImageUrlHttps: string
}