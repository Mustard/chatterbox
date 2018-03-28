package com.github.mustard.chatterbox.msbot.client;

import com.github.mustard.chatterbox.msbot.domain.Activity;
import com.github.mustard.chatterbox.msbot.domain.Conversation;
import com.github.mustard.chatterbox.msbot.domain.CreateConversationResponse;
import com.github.mustard.chatterbox.msbot.domain.MSAAADAuthenticationResponse;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;

public class Gateway {

    private static final String DEFAULT_LOGIN_URL = "https://login.microsoftonline.com/botframework.com/oauth2/v2.0/token";
    private static final String DEFAULT_API_URL = "https://smba.trafficmanager.net/apis";

    private final String accessToken;
    private final JerseyClient jerseyClient;
    private final String loginURL;
    private final String apiURL;

    public Gateway(String accessToken) {
        this.accessToken = accessToken;
        this.jerseyClient = JerseyClientBuilder.createClient();
        this.loginURL = DEFAULT_LOGIN_URL;
        this.apiURL = DEFAULT_API_URL;
    }

    public Gateway(String accessToken, String loginURL, String apiURL) {
        this.accessToken = accessToken;
        this.jerseyClient = JerseyClientBuilder.createClient();
        this.loginURL = loginURL;
        this.apiURL = apiURL;
    }

    // https://docs.microsoft.com/en-us/azure/bot-service/rest-api/bot-framework-rest-connector-authentication#step-1-request-an-access-token-from-the-msaaad-v2-login-service
    public MSAAADAuthenticationResponse login(String appId, String appPassword) {

        Form form = new Form()
                .param("grant_type", "client_credentials")
                .param("client_id", appId)
                .param("client_secret", appPassword)
                .param("scope", "https://api.botframework.com/.default");

        Response response = jerseyClient.target(loginURL)
                .request(MediaType.APPLICATION_FORM_URLENCODED)
                .post(Entity.form(form));

        if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
            return response.readEntity(MSAAADAuthenticationResponse.class);
        }

        if (response.getStatusInfo().getFamily() != Response.Status.Family.CLIENT_ERROR) {
            // TODO throw auth error?
        }

        return null;

    }

    // https://docs.microsoft.com/en-us/azure/bot-service/rest-api/bot-framework-rest-connector-send-and-receive-messages#start-a-conversation
    public CreateConversationResponse startConversation(Conversation conversation) {
        return jerseyClient.target(apiURL)
                .path("/v3/conversations")
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .post(Entity.json(conversation), CreateConversationResponse.class);
    }

    // https://docs.microsoft.com/en-us/azure/bot-service/rest-api/bot-framework-rest-connector-send-and-receive-messages#send-the-reply
    public void replyInConversationToActivity(String conversationId, String replyToId, Activity activity) {
        jerseyClient.target(apiURL)
                .path("/v3/conversations/{conversationId}/activities/{activityId}")
                .resolveTemplate("conversationId", conversationId)
                .resolveTemplate("activityId", replyToId)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .post(Entity.json(activity));
    }

}
