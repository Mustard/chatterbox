package com.github.mustard.chatterbox.example;

import com.github.mustard.chatterbox.msbot.client.*;
import com.github.mustard.chatterbox.msbot.domain.Activity;
import com.github.mustard.chatterbox.msbot.domain.ActivityType;
import com.github.mustard.chatterbox.msbot.domain.TextFormat;
import com.github.mustard.chatterbox.msbot.webhook.MSBotEventSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoMSBotEventSink implements MSBotEventSink {

    private static final Logger LOG = LoggerFactory.getLogger(EchoMSBotEventSink.class);

    private final MSBotClient client;

    public EchoMSBotEventSink(MSBotClient msBotClient) {
        this.client = msBotClient;
    }

    @Override
    public void onMessage(Activity activity) {
        LOG.info("onMessage ", activity);
        if (activity.type == ActivityType.MESSAGE) {
            client.replyToMessageActivity(activity, "Echo " + activity.text, TextFormat.PLAIN);
        }
    }

}
