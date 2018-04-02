package com.github.mustard.chatterbox.msbot.client;

import com.github.mustard.chatterbox.msbot.domain.*;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.dropwizard.testing.FixtureHelpers;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;


@Ignore
class GatewayTest {

    private static WireMockServer wireMockServer;
    private Gateway gateway;

    @BeforeAll
    static void beforeAll() {
        wireMockServer = new WireMockServer(0);
        wireMockServer.start();
        // Wiremock Client
        configureFor("localhost", wireMockServer.port());
    }

    @BeforeEach
    void setUp() {
        String wireMockURL = "http://localhost:" + wireMockServer.port();
        this.gateway = new Gateway("ACCESS_TOKEN", wireMockURL + "/botframework.com/oauth2/v2.0/token", wireMockURL);
    }

    @AfterAll
    static void afterAll() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @Test
    @Ignore
    void shouldAuthenticate() {
        stubFor(post(urlEqualTo("/botframework.com/oauth2/v2.0/token"))
                .willReturn(okJson(FixtureHelpers.fixture("fixtures/SuccessfullAuthenticationResponse.json"))));

        MSAAADAuthenticationResponse response = gateway.login("APP_ID", "PASSWORD");

        // TODO assert request was well formed
        // TODO handle and test auth failer (need to compare to real response)

        assertEquals(response.accessToken, "eyJhbGciOiJIUzI1Ni");
        assertEquals(response.tokenType, "Bearer");
        assertEquals(response.expiresIn, 3600L);
        assertEquals(response.extExpiresIn, 3600L);
    }

    @Test
    void shouldReplyToMessageActivity() {

        Activity inbound = Activity.activityBuilder()
                .type(ActivityType.MESSAGE)
                .serviceUrl("http://localhost:" + wireMockServer.port())
                .conversation(ConversationAccount.conversationAccountBuilder()
                        .id("conversation_id").build())
                .id("activity_id")
                .build();

        stubFor(post(urlEqualTo("/v3/conversations/conversation_id/activities/activity_id"))
                // TODO real captured response
                .willReturn(okJson(FixtureHelpers.fixture("fixtures/SuccessfullAuthenticationResponse.json"))));

        gateway.replyToMessageActivity(inbound, "Reply Message", TextFormat.PLAIN);

    }
}