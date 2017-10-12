package com.github.mustard.chatterbox.example;

import com.github.mustard.chatterbox.msbot.domain.MessageEvent;
import com.github.mustard.chatterbox.msbot.webhook.MSBotEventSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingMSBotEventSink implements MSBotEventSink {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingMSBotEventSink.class);

    @Override
    public void onMessage(MessageEvent message) {
        LOG.info("onMessage ", message);
    }

}
