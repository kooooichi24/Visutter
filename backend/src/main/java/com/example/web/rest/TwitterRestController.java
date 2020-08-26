package com.example.web.rest;

import com.example.service.TwitterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import twitter4j.*;

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

    @GetMapping("/userTimeline")
    public ResponseList<Status> userTimeline() throws TwitterException {
        ResponseList<Status> statuses = twitterService.getAllUserTimeline();

        System.out.println("Showing user timeline.");
        for (Status status : statuses) {
            System.out.println(status.getUser().getName() + ":" + status.getText());
            System.out.println();
            System.out.println("-----------------------");
        }

        return statuses;
    }
}
