package com.example.service.impl;

import com.example.service.TwitterService;
import org.springframework.stereotype.Service;
import twitter4j.*;
import java.util.Map;

@Service
public class TwitterServiceImpl implements TwitterService {
    
    @Override
    public ResponseList<Status> getAllUserTimeline(String screenName) throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        User user = twitter.showUser(screenName);
        Integer statusesCount = user.getStatusesCount();
//        Map<String ,RateLimitStatus> rateLimitStatus = twitter.getRateLimitStatus();

        Integer page = 1;
        final Integer COUNT_MAX = 200; // Pagingを用いたgetUserTimelineの最大取得数は200件な仕様らしい
        ResponseList<Status> statuses = null;

        while (true) {
            Paging paging = new Paging(page, COUNT_MAX);
            try {
                System.out.println("page" + page);
                if (statuses == null) {
                    statuses = twitter.getUserTimeline(screenName, paging);
                } else {
                    statuses.addAll(twitter.getUserTimeline(screenName, paging));
                }
                page += 1;

                // 全件取得したら終了
                if (statuses.size() == statusesCount) {
                    break;
                }
            } catch(TwitterException twitterException) {
                // Rate Limit に引っかかった場合の処理
                // (メモ) status code も併せてチェックすべきか？！

                RateLimitStatus rateLimit = twitterException.getRateLimitStatus();
                int secondsUntilReset = rateLimit.getSecondsUntilReset();
                System.err.println("please wait for " + secondsUntilReset + " seconds");
//                System.err.println("Reset Time : " +  rateLimit.getResetTime());

                //(注) 120秒間，水増し・・getSecondsUntilReset() の返す値が負の
                //場合があり，それに対応するため
                // long waitTime = (long)(secondsUntilReset + 120) * 1000;
                long waitTime = (long)(3 * 1000); // 300秒待ち
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
