package com.github.mustard.chatterbox.msbot.client;

import com.github.mustard.chatterbox.msbot.domain.JSONWebKey;
import com.github.mustard.chatterbox.msbot.domain.JSONWebKeyContainer;
import com.github.mustard.chatterbox.msbot.domain.MSAAADAuthenticationResponse;
import com.github.mustard.chatterbox.msbot.domain.OpenIDConfiguration;
import org.glassfish.jersey.client.JerseyClient;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import java.util.List;

public class MSBotAuthClient {

    public static final String DEFAULT_OPENID_CONFIG_URL = "https://login.botframework.com/v1/.well-known/openidconfiguration";
    public static final String DEFAULT_OPENID_AUTH_URL = "https://login.microsoftonline.com/botframework.com/oauth2/v2.0/token";

    private final JerseyClient jerseyClient;
    private final String openIDConfigURL;

    public MSBotAuthClient() {
        this.jerseyClient = MSBotJerseyClientBuilder.makeClient();
        this.openIDConfigURL = DEFAULT_OPENID_CONFIG_URL;
    }

    public MSBotAuthClient(String openIDConfigURL) {
        this.jerseyClient = MSBotJerseyClientBuilder.makeClient();
        this.openIDConfigURL = openIDConfigURL;
    }

    // https://docs.microsoft.com/en-us/azure/bot-service/rest-api/bot-framework-rest-connector-authentication#step-1-request-an-access-token-from-the-msaaad-v2-login-service
    public MSAAADAuthenticationResponse login(String url, MSBotCredentialsProvider credentialsProvider) {

        Form form = new Form()
                .param("grant_type", "client_credentials")
                .param("client_id", credentialsProvider.getAppId())
                .param("client_secret", credentialsProvider.getAppPassword())
                .param("scope", "https://api.botframework.com/.default");

        return jerseyClient.target(url)
                .request(MediaType.APPLICATION_FORM_URLENCODED)
                .post(Entity.form(form), MSAAADAuthenticationResponse.class);
    }

    public OpenIdContainer fetchOpenIDConfiguration() {
        OpenIDConfiguration openIDConfiguration = jerseyClient.target(openIDConfigURL)
                .request(MediaType.APPLICATION_JSON)
                .get(OpenIDConfiguration.class);

        JSONWebKeyContainer jsonWebKeyContainer = jerseyClient.target(openIDConfiguration.jwksURI)
                .request(MediaType.APPLICATION_JSON)
                .get(JSONWebKeyContainer.class);

        return new OpenIdContainer(openIDConfiguration, jsonWebKeyContainer.keys);

    }

    public static class OpenIdContainer {
        public final OpenIDConfiguration openIDConfiguration;
        public final List<JSONWebKey> keyList;

        public OpenIdContainer(OpenIDConfiguration openIDConfiguration, List<JSONWebKey> keyList) {
            this.openIDConfiguration = openIDConfiguration;
            this.keyList = keyList;
        }
    }

}
