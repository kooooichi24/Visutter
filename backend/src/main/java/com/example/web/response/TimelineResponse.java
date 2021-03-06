package com.example.web.response;

import twitter4j.Status;
import java.util.Date;

public class TimelineResponse {
    private Long id;
    private String text;
    private Date createdAt;
    private Integer favoriteCount;
    private Integer retweetCount;
    private Boolean isRetweeted;

    public TimelineResponse(Status status) {
        this.id = status.getId();
        this.text = status.getText();
        this.createdAt = status.getCreatedAt();
        this.favoriteCount = status.getFavoriteCount();
        this.retweetCount = status.getRetweetCount();
        this.isRetweeted = status.isRetweeted();
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public Integer getRetweetCount() {
        return retweetCount;
    }

    public Boolean getIsRetweeted() {
        return isRetweeted;
    }
}
