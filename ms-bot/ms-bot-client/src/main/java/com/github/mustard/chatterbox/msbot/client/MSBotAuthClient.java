package com.github.mustard.chatterbox.msbot.client;

import com.github.mustard.chatterbox.msbot.domain.MSAAADAuthenticationResponse;
import com.github.mustard.chatterbox.msbot.domain.OpenIDConfiguration;
import org.glassfish.jersey.client.JerseyClient;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

public class MSBotAuthClient {

    private static final String DEFAULT_LOGIN_URL = "https://login.microsoftonline.com/botframework.com/oauth2/v2.0/token";
    private static final String DEFAULT_OPENID_CONFIG_URL = "https://login.botframework.com/v1/.well-known/openidconfiguration";

    private final JerseyClient jerseyClient;
    private final String loginURL;
    private final String openIDConfigURL;

    public MSBotAuthClient() {
        this.jerseyClient = MSBotJerseyClientBuilder.makeClient();
        this.loginURL = DEFAULT_LOGIN_URL;
        this.openIDConfigURL = DEFAULT_OPENID_CONFIG_URL;
    }

    public MSBotAuthClient(String loginURL, String openIDConfigURL) {
        this.jerseyClient = MSBotJerseyClientBuilder.makeClient();
        this.loginURL = loginURL;
        this.openIDConfigURL = openIDConfigURL;
    }

    // https://docs.microsoft.com/en-us/azure/bot-service/rest-api/bot-framework-rest-connector-authentication#step-1-request-an-access-token-from-the-msaaad-v2-login-service
    public MSAAADAuthenticationResponse login(String appId, String appPassword) {

        Form form = new Form()
                .param("grant_type", "client_credentials")
                .param("client_id", appId)
                .param("client_secret", appPassword)
                .param("scope", "https://api.botframework.com/.default");

        return jerseyClient.target(loginURL)
                .request(MediaType.APPLICATION_FORM_URLENCODED)
                .post(Entity.form(form), MSAAADAuthenticationResponse.class);

//        if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
//            return response.readEntity(MSAAADAuthenticationResponse.class);
//        }

//        if (response.getStatusInfo().getFamily() != Response.Status.Family.CLIENT_ERROR) {
//            // TODO throw auth error?
//        }

//        return null;

    }

    public OpenIDConfiguration fetchOpenIDConfiguration() {
        return jerseyClient.target(openIDConfigURL)
                .request(MediaType.APPLICATION_JSON)
                .get(OpenIDConfiguration.class);

    }

}
