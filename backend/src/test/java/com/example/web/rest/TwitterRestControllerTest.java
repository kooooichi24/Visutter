package com.example.web.rest;

import com.example.service.TwitterService;
import com.example.web.response.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import twitter4j.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DisplayName("エンドポイント /api/twt のコントローラクラス")
@ExtendWith(MockitoExtension.class)
class TwitterRestControllerTest {

    @InjectMocks
    TwitterRestController twitterRestController;

    @Mock
    TwitterService twitterService;

    @Test
    void test_スクリーンネームを基にTwitterのユーザ情報を取得する() throws TwitterException {

        Twitter twitter = TwitterFactory.getSingleton();
        User user = twitter.showUser("Fchandayo24");

        when(twitterService.showUser("Fchandayo24")).thenReturn(user);

        UserResponse actual = twitterRestController.getUserByScreenName("Fchandayo24");

        UserResponse expected = UserResponse.builder()
                .id(1212740416572772353L)
                .name("Fchan")
                .screenName("Fchandayo24")
                .description("")
                .followersCount(14)
                .friendsCount(359)
                .statusesCount(1141)
                .profileImageUrlHttps("https://pbs.twimg.com/profile_images/1336310404381769729/0q2P00bd_normal.jpg")
                .build();

        assertEquals(expected, actual);
    }
}
