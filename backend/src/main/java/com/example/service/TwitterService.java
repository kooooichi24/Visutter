package com.example.service;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

public interface TwitterService {
    void init() throws TwitterException;

    User verifyCredentials() throws TwitterException;

    ResponseList<Status> getAllUserTimeline() throws TwitterException;
}
