package com.github.mustard.chatterbox.msbot.client;

import com.github.mustard.chatterbox.msbot.domain.MSAAADAuthenticationResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
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
            .willReturn(okJson("{\"foo\": \"bar\"}")));
//                .withRequestBody()

        MSAAADAuthenticationResponse response = gateway.login("APP_ID", "PASSWORD");

        System.out.println(wireMockServer);
    }
}