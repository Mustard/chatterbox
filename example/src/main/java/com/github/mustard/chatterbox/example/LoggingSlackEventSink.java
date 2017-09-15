package com.github.mustard.chatterbox.example;

import com.github.mustard.chatterbox.slack.SlackEventSink;
import com.github.mustard.chatterbox.slack.domain.event.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingSlackEventSink implements SlackEventSink {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingSlackEventSink.class);

    @Override
    public void onMessage(MessageEvent message) {
        LOG.info("onMessage channel={} user={} text={}",
                message.channel, message.user, message.text);
    }

}
