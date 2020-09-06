package com.example.web.rest;

import com.example.service.TwitterService;
import com.example.web.response.TimelineResponse;
import com.example.web.response.UserResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/twt")
public class TwitterRestController {
    private TwitterService twitterService;
    public TwitterRestController(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    @GetMapping("/user")
    public UserResponse user() throws Exception {
        User user = twitterService.verifyCredentials();

        UserResponse userResponse = new UserResponse(user);
        return userResponse;
    }

    @GetMapping("/timeline")
    public List<TimelineResponse> userTimeline() throws TwitterException {
        ResponseList<Status> statuses = twitterService.getAllUserTimeline();
        List<TimelineResponse> timelineResponseList = new ArrayList<>();

        for (Status status : statuses) {
            TimelineResponse timelineResponse = new TimelineResponse(status);
            timelineResponseList.add(timelineResponse);
        }

        return timelineResponseList;
    }
}
