package com.example.web.rest;

import com.example.service.TwitterService;
import com.example.web.response.TimelineResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/twt")
public class TwitterRestController {
    private TwitterService twitterService;
    public TwitterRestController(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    @GetMapping("/user")
    public User user() throws Exception {
        User user = twitterService.verifyCredentials();

        String userName = user.getName();
        String displayName = user.getScreenName();
        int fav = user.getFavouritesCount();

        System.out.println("username: " + userName);
        System.out.println("displayName: " + displayName);
        System.out.println("fav: " + fav);

        return user;
    }

    // TwitterServiceImplで@PostConstructを用いて起動時に実行する形式に変更（ひとまずね）
    @GetMapping("/userTimeline")
    public List<TimelineResponse> userTimeline() throws TwitterException {
        ResponseList<Status> statuses = twitterService.getAllUserTimeline();
        List<TimelineResponse> timelineResponseList = new ArrayList<>();

        System.out.println("Showing user timeline.");
        for (Status status : statuses) {
            TimelineResponse timelineResponse = new TimelineResponse(status);
            timelineResponseList.add(timelineResponse);
        }

        return timelineResponseList;
    }
}
