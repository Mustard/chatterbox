package com.github.mustard.chatterbox.msbot.client;

import com.github.mustard.chatterbox.msbot.domain.*;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.dropwizard.testing.FixtureHelpers;
import org.junit.Ignore;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;


class MSBotClientTest {

    private static WireMockServer wireMockServer;
    private MSBotClient client;

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
        this.client = new MSBotClient(new MSBotAuthSession() {
            @Override
            public String getBearerAuthToken() {
                return "ACCESS_TOKEN";
            }
        }, wireMockURL + "/botframework.com/oauth2/v2.0/token");
    }

    @AfterAll
    static void afterAll() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
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

        client.replyToMessageActivity(inbound, "Reply Message", TextFormat.PLAIN);

    }
}