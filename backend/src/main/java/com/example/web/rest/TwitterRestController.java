package com.example.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api/twt")
public class TwitterRestController {

    private Twitter twitter;

    @PostConstruct
    private void ctor() {
        this.twitter = new TwitterFactory().getInstance();
    }

    @GetMapping("/user")
    public User user() throws Exception {
        User user = twitter.verifyCredentials();

        String userName = user.getName();
        String displayName = user.getScreenName();
        int fav = user.getFavouritesCount();

        System.out.println("username: " + userName);
        System.out.println("displayName: " + displayName);
        System.out.println("fav: " + fav);

        return user;
    }
}
