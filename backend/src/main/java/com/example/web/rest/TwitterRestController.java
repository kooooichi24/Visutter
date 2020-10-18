package com.example.web.rest;

import com.example.service.TwitterService;
import com.example.web.response.TimelineResponse;
import com.example.web.response.UserResponse;
import org.springframework.web.bind.annotation.*;

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
    public UserResponse user(@RequestParam("screenName") String screenName) throws TwitterException {
        User user = twitterService.showUser(screenName);
        UserResponse userResponse = new UserResponse(user);
        return userResponse;
    }

    @GetMapping("/timeline")
    public List<TimelineResponse> userTimeline(@RequestParam("screenName") String screenName) throws TwitterException {
        ResponseList<Status> statuses = twitterService.getAllUserTimeline(screenName);
        List<TimelineResponse> timelineResponseList = new ArrayList<>();

        for (Status status : statuses) {
            TimelineResponse timelineResponse = new TimelineResponse(status);
            timelineResponseList.add(timelineResponse);
        }

        return timelineResponseList;
    }
}
