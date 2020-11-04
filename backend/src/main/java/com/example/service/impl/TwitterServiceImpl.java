package com.example.service.impl;

import com.example.service.TwitterService;
import org.springframework.stereotype.Service;
import twitter4j.*;

@Service
public class TwitterServiceImpl implements TwitterService {
    @Override
    public User showUser(String screenName) throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        return twitter.showUser(screenName);
    }

    @Override
    public ResponseList<Status> getAllUserTimeline(String screenName) throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        User user = twitter.showUser(screenName);
        Integer statusesCount = user.getStatusesCount();
        Integer beforeStatusSize = 0;

        Integer page = 1;
        final Integer COUNT_MAX = 200; // Pagingを用いたgetUserTimelineの最大取得数は200件な仕様らしい
        ResponseList<Status> statuses = null;

        while (true) {
            Paging paging = new Paging(page, COUNT_MAX);
            try {
                if (statuses == null) {
                    statuses = twitter.getUserTimeline(screenName, paging);
                } else {
                    statuses.addAll(twitter.getUserTimeline(screenName, paging));
                }
                page += 1;

                /**
                 * ユーザーのツイートを全て取得したかどうかの判定
                 * Twitterの仕様上、ユーザのツイートにはリツイートも含む
                 * しかし、ユーザーがリツイートしたアカウントが鍵垢の場合は、本APIでは取得不可である
                 * つまり、ユーザーの全てのツイートを本APIで取得することは実現不可能である
                 * そのため、取得ツイート数が変化しないこと確認できたならば、取得可能なツイートを全て取得できたこととする
                 *
                 * 比較対象
                 * - 取得後のツイート数
                 * - 取得前のツイート数
                 * */
                if (statuses.size() == beforeStatusSize) {
                    break;
                } else {
                    beforeStatusSize = statuses.size();
                }

            } catch(TwitterException twitterException) {
                // Rate Limit に引っかかった場合の処理
                RateLimitStatus rateLimit = twitterException.getRateLimitStatus();
                int secondsUntilReset = rateLimit.getSecondsUntilReset();
                System.err.println("please wait for " + secondsUntilReset + " seconds");
                System.err.println("Reset Time : " +  rateLimit.getResetTimeInSeconds());

                long waitTime = (long)(300 * 1000); // 300秒待ち
                try {
                    Thread.sleep(waitTime);
                } catch(Exception e){
                    e.printStackTrace();
                }

                continue;
            } catch(Exception e){
                e.printStackTrace();
            }

        }

        return statuses;
    }
}
