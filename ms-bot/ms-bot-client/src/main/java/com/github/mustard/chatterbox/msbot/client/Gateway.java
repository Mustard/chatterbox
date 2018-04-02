package com.github.mustard.chatterbox.msbot.client;

import com.github.mustard.chatterbox.msbot.domain.*;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;

import static com.github.mustard.chatterbox.msbot.domain.ActivityType.MESSAGE;

public class Gateway {

    private static final String DEFAULT_LOGIN_URL = "https://login.microsoftonline.com/botframework.com/oauth2/v2.0/token";
    private static final String DEFAULT_API_URL = "https://smba.trafficmanager.net/apis";

    private final String accessToken;
    private final JerseyClient jerseyClient;
    private final String loginURL;
    private final String apiURL;

    public Gateway(String accessToken) {
        this.accessToken = accessToken;
        this.jerseyClient = makeClient();
        this.loginURL = DEFAULT_LOGIN_URL;
        this.apiURL = DEFAULT_API_URL;
    }

    public Gateway(String accessToken, String loginURL, String apiURL) {
        this.accessToken = accessToken;
        this.jerseyClient = makeClient(); //JerseyClientBuilder.createClient().register(JacksonJsonProvider.class);
        this.loginURL = loginURL;
        this.apiURL = apiURL;
    }

    private JerseyClient makeClient() {
        return JerseyClientBuilder.createClient()
                .register(JacksonFeature.class);
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

    // https://docs.microsoft.com/en-us/azure/bot-service/rest-api/bot-framework-rest-connector-send-and-receive-messages#start-a-conversation
    public CreateConversationResponse startConversation(Conversation conversation) {
        return jerseyClient.target(apiURL)
                .path("/v3/conversations")
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .post(Entity.json(conversation), CreateConversationResponse.class);
    }

    // https://docs.microsoft.com/en-us/azure/bot-service/rest-api/bot-framework-rest-connector-send-and-receive-messages#send-the-reply
    public void replyToMessageActivity(Activity replyToActivity, String text, TextFormat textFormat) {

        if (replyToActivity.type != MESSAGE) {
            throw new IllegalStateException("Can only reply to activity events with type = \"message\"");
        }

        ConversationAccount conversationAccount = new ConversationAccount(
                replyToActivity.conversation.id, replyToActivity.conversation.isGroup, null
        );

        Activity activity = Activity.activityBuilder()
                .type(MESSAGE)
                .conversation(conversationAccount)
                .replyToId(replyToActivity.id)
                .text(text)
                .textFormat(textFormat)
                .build();

        Response response = jerseyClient.target(replyToActivity.serviceUrl)
                .path("/v3/conversations/{conversationId}/activities/{activityId}")
                .resolveTemplate("conversationId", replyToActivity.conversation.id)
                .resolveTemplate("activityId", replyToActivity.id)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .post(Entity.json(activity));

        System.out.println(response);
    }

}
