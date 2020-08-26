package com.example.service.impl;

import com.example.service.TwitterService;
import org.springframework.stereotype.Service;
import twitter4j.*;

@Service
public class TwitterServiceImpl implements TwitterService {

    @Override
    public User verifyCredentials() throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        return twitter.verifyCredentials();
    }

    @Override
    public ResponseList<Status> getAllUserTimeline() throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();

        User user = twitter.verifyCredentials();
        Integer statusesCount = user.getStatusesCount();
        Integer page = 1;
        Integer count = 200; // Pagingを用いたgetUserTimelineの最大取得数は200件な仕様らしい

        ResponseList<Status> statuses = null;

        for (int i = 0; i < statusesCount/count + 1; i++) {
            Paging paging = new Paging(page, count);

            if (statuses == null) {
                statuses = twitter.getUserTimeline(paging);
            } else {
                statuses.addAll(twitter.getUserTimeline(paging));
            }
            page += 1;
        }

        return statuses;
    }
}