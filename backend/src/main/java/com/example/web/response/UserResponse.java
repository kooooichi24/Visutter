package com.example.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import twitter4j.User;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
}
