package com.example.web.response;

import twitter4j.User;

public class UserResponse {
    private Long id;
    private String name;
    private String screenName;
    private String description;
    private Integer followersCount;
    private Integer friendsCount;
    private Integer statusesCount;
    private String profileImageUrlHttps;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.screenName = user.getScreenName();
        this.description = user.getDescription();
        this.followersCount = user.getFollowersCount();
        this.friendsCount = user.getFriendsCount();
        this.statusesCount = user.getStatusesCount();
        this.profileImageUrlHttps = user.getProfileImageURLHttps();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getDescription() {
        return description;
    }

    public Integer getFollowersCount() {
        return followersCount;
    }

    public Integer getFriendsCount() {
        return friendsCount;
    }

    public Integer getStatusesCount() {
        return statusesCount;
    }

    public String getProfileImageUrlHttps() {
        return profileImageUrlHttps;
    }
}
