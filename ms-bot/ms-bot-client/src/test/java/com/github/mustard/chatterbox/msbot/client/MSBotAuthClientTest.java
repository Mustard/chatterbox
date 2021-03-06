package com.github.mustard.chatterbox.msbot.client;

import com.github.mustard.chatterbox.msbot.domain.MSAAADAuthenticationResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.dropwizard.testing.FixtureHelpers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

class MSBotAuthClientTest {

    private static WireMockServer wireMockServer;
    private MSBotAuthClient client;

    @BeforeAll
    static void beforeAll() {
        wireMockServer = new WireMockServer(0);
        wireMockServer.start();
        // Wiremock Client
        configureFor("localhost", wireMockServer.port());
    }

    @BeforeEach
    void setUp() {
        String wireMockURL = "http://localhost:" + wireMockServer.port() + "/v1/.well-known/openidconfiguration";
        this.client = new MSBotAuthClient(wireMockURL);
    }

    @AfterAll
    static void afterAll() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @Test
    void shouldRetrieveOpenIdConfiguration() {
        String jwksURL = "http://localhost:" + wireMockServer.port() + "/v1/.well-known/keys";

        stubFor(get(urlEqualTo("/v1/.well-known/openidconfiguration"))
            .willReturn(okJson(FixtureHelpers.fixture("fixtures/OpenIdConfigurationResponse.json").replace("{{JWKS_URI}}", jwksURL))));

        stubFor(get(urlEqualTo("/v1/.well-known/keys"))
            .willReturn(okJson(FixtureHelpers.fixture("fixtures/JSONWebKeysResponse.json"))));

        MSBotAuthClient.OpenIdContainer response = client.fetchOpenIDConfiguration();

        assertEquals(response.openIDConfiguration.authorizationEndpoint, "https://invalid.botframework.com");

        // TODO assertions
    }


    @Test
    void shouldAuthenticate() {
        stubFor(post(urlEqualTo("/botframework.com/oauth2/v2.0/token"))
                .willReturn(okJson(FixtureHelpers.fixture("fixtures/SuccessfullAuthenticationResponse.json"))));

        String loginURL = "http://localhost:" + wireMockServer.port() + "/botframework.com/oauth2/v2.0/token";
        MSAAADAuthenticationResponse response = client.login(loginURL, new MSBotAppAppCredentials("APP_ID", "PASSWORD"));

        // TODO assert request was well formed
        // TODO handle and test auth failer (need to compare to real response)

        assertEquals(response.accessToken, "eyJhbGciOiJIUzI1Ni");
        assertEquals(response.tokenType, "Bearer");
        assertEquals(response.expiresIn, 3600L);
        assertEquals(response.extExpiresIn, 3600L);
    }

}