package com.example.web.rest;

import com.example.service.TwitterService;
import com.example.web.response.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import twitter4j.TwitterException;

import static org.mockito.Mockito.when;

@DisplayName("エンドポイント /api/twt のコントローラクラス")
@WebMvcTest(TwitterRestController.class)
@AutoConfigureMockMvc
class TwitterRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    // TODO @Mock と @MockBean の違いを把握する https://www.baeldung.com/java-spring-mockito-mock-mockbean
    @MockBean
    TwitterService twitterService;

    @Test
    void test_aa() throws TwitterException {
        UserResponse userResponse = UserResponse.builder()
                .id(10000L)
                .name("Fchandayo24")
                .screenName("Fchan")
                .description("kooooichi24's English learning account")
                .followersCount(10)
                .friendsCount(100)
                .statusesCount(10)
                .profileImageUrlHttps("https://localhost:8080/link")
                .build();
        
        when(twitterService.showUser("Fchandayo24")).thenReturn(userResponse);


    }
}
