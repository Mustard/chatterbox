package com.github.mustard.chatterbox.msbot.client;

import com.github.mustard.chatterbox.msbot.domain.*;
import org.glassfish.jersey.client.JerseyClient;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;

import static com.github.mustard.chatterbox.msbot.domain.ActivityType.MESSAGE;

public class MSBotClient {

    private static final String DEFAULT_API_URL = "https://smba.trafficmanager.net/apis";

    private final JerseyClient jerseyClient;
    private final String apiURL;
    private final MSBotAuthTokenProvider authSession;

    public MSBotClient(MSBotAuthTokenProvider authSession) {
        this.authSession = authSession;
        this.jerseyClient = MSBotJerseyClientBuilder.makeClient();
        this.apiURL = DEFAULT_API_URL;
    }

    public MSBotClient(MSBotAuthTokenProvider authSession, String apiURL) {
        this.authSession = authSession;
        this.jerseyClient = MSBotJerseyClientBuilder.makeClient(); //JerseyClientBuilder.createClient().register(JacksonJsonProvider.class);
        this.apiURL = apiURL;
    }


    // https://docs.microsoft.com/en-us/azure/bot-service/rest-api/bot-framework-rest-connector-send-and-receive-messages#start-a-conversation
    public CreateConversationResponse startConversation(Conversation conversation) {
        return jerseyClient.target(apiURL)
                .path("/v3/conversations")
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authSession.getBearerAuthToken())
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
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authSession.getBearerAuthToken())
                .post(Entity.json(activity));

        System.out.println(response);
    }

}
