package com.example.service.impl;

import com.example.service.TwitterService;
import org.springframework.stereotype.Service;
import twitter4j.*;

import javax.annotation.PostConstruct;

@Service
public class TwitterServiceImpl implements TwitterService {

    @Override
    @PostConstruct
    public void init() throws TwitterException {
        ResponseList<Status> statuses = getAllUserTimeline();

        System.out.println("Showing user timeline.");
        for (Status status : statuses) {
            System.out.println(status.getUser().getName() + ":" + status);
            System.out.println();
            System.out.println("-----------------------");
        }
    }

    @Override
    public User verifyCredentials() throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        return twitter.verifyCredentials();
    }

    @Override
    public ResponseList<Status> getAllUserTimeline() throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        User user = verifyCredentials();
        Integer statusesCount = user.getStatusesCount();

        Integer page = 1;
        final Integer COUNT_MAX = 200; // Pagingを用いたgetUserTimelineの最大取得数は200件な仕様らしい
        ResponseList<Status> statuses = null;

        while (true) {
            Paging paging = new Paging(page, COUNT_MAX);
            if (statuses == null) {
                statuses = twitter.getUserTimeline(paging);
            } else {
                statuses.addAll(twitter.getUserTimeline(paging));
            }
            page += 1;

            // 全件取得したら終了
            if (statuses.size() == statusesCount) {
                break;
            }
        }

        return statuses;
    }
}
