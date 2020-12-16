package com.example.web.rest;

import com.example.service.TwitterService;
import com.example.web.response.TimelineResponse;
import com.example.web.response.UserResponse;
import org.springframework.web.bind.annotation.*;

import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

/**
 * TwitterへのAPIリクエストをハンドリングするエンドポイント
 */
@CrossOrigin
@RestController
@RequestMapping("/api/twt")
public class TwitterRestController {
    private TwitterService twitterService;
    public TwitterRestController(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    /**
     * スクリーンネームを基にTwitterのユーザ情報を取得するコントローラーメソッド。
     *
     * @param screenName スクリーンネーム
     * @return UserResponse UserResponseインスタンス
     * @throws TwitterException TwitterAPIの呼び出しに失敗したときにスローされる例外クラス。
     */
    // TODO: HTTPを意識したAPIのレスポンスを実現したい。
    @GetMapping("/user")
    public UserResponse getUserByScreenName(@RequestParam("screenName") String screenName) throws TwitterException {
        User user = twitterService.showUser(screenName);
        return new UserResponse(user);
    }

    /**
     * スクリーンネームを基にTwitterユーザのタイムラインを取得するコントローラーメソッド。
     *
     * @param screenName スクリーンネーム
     * @return List<TimelineResponse> TimelineResponseのリストインスタンス
     * @throws TwitterException TwitterAPIの呼び出しに失敗したときにスローされる例外クラス。
     */
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
