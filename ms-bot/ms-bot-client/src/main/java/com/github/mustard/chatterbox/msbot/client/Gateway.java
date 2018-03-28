package com.github.mustard.chatterbox.msbot.client;

import com.github.mustard.chatterbox.msbot.domain.Activity;
import com.github.mustard.chatterbox.msbot.domain.Conversation;
import com.github.mustard.chatterbox.msbot.domain.CreateConversationResponse;
import com.github.mustard.chatterbox.msbot.domain.MSAAADAuthenticationResponse;
import org.glassfish.jersey.client.JerseyClient;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Gateway {

    private JerseyClient jerseyClient;
    private String accessToken;

    // https://docs.microsoft.com/en-us/azure/bot-service/rest-api/bot-framework-rest-connector-authentication#step-1-request-an-access-token-from-the-msaaad-v2-login-service
    private MSAAADAuthenticationResponse login(String appId, String appPassword) {

        Form form = new Form()
                .param("grant_type", "client_credentials")
                .param("client_id", appId)
                .param("client_secret", appPassword)
                .param("scope", "https://api.botframework.com/.default");

        Response response = jerseyClient.target("https://login.microsoftonline.com/botframework.com/oauth2/v2.0/token")
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
        return jerseyClient.target("/v3/conversations")
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .post(Entity.json(conversation), CreateConversationResponse.class);
    }

    // https://docs.microsoft.com/en-us/azure/bot-service/rest-api/bot-framework-rest-connector-send-and-receive-messages#send-the-reply
    public void replyInConversationToActivity(String conversationId, String replyToId, Activity activity) {
        jerseyClient.target("/v3/conversations/{conversationId}/activities/{activityId}")
                .resolveTemplate("conversationId", conversationId)
                .resolveTemplate("activityId", replyToId)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .post(Entity.json(activity));
    }

}
