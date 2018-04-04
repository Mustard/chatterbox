package com.github.mustard.chatterbox.example;

import com.github.mustard.chatterbox.msbot.client.MSBotAuthClient;
import com.github.mustard.chatterbox.msbot.client.MSBotClient;
import com.github.mustard.chatterbox.msbot.client.MSBotCredentials;
import com.github.mustard.chatterbox.msbot.client.MSBotInMemoryAuthTokenProvider;
import com.github.mustard.chatterbox.msbot.domain.Activity;
import com.github.mustard.chatterbox.msbot.domain.ActivityType;
import com.github.mustard.chatterbox.msbot.domain.TextFormat;
import com.github.mustard.chatterbox.msbot.webhook.MSBotEventSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingMSBotEventSink implements MSBotEventSink {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingMSBotEventSink.class);

    private MSBotClient client;

    public LoggingMSBotEventSink() {
        MSBotCredentials credentials = new MSBotCredentials("app_id", "app_password");
        MSBotInMemoryAuthTokenProvider authTokenProvider = new MSBotInMemoryAuthTokenProvider(new MSBotAuthClient(), credentials, MSBotInMemoryAuthTokenProvider.AuthMode.EAGER);
        this.client = new MSBotClient(authTokenProvider);
    }

    @Override
    public void onMessage(Activity activity) {
        LOG.info("onMessage ", activity);
        if (activity.type == ActivityType.MESSAGE) {
            client.replyToMessageActivity(activity, "Echo " + activity.text, TextFormat.PLAIN);
        }
    }

}
