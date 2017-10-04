package com.github.mustard.chatterbox.slack;

import com.github.mustard.chatterbox.slack.domain.event.MessageEvent;

public interface SlackEventSink {

    void onMessage(MessageEvent message);

}
