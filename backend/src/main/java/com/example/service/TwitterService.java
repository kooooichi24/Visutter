package com.example.service;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

public interface TwitterService {

//    User verifyCredentials() throws TwitterException;

    ResponseList<Status> getAllUserTimeline(String screenName) throws TwitterException;
}
