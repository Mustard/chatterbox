package com.github.mustard.chatterbox.slack;

import com.github.mustard.chatterbox.slack.domain.event.MessageEvent;

import java.util.ArrayList;
import java.util.List;

public class SlackEventSinkStore implements SlackEventSink {

    public final List<MessageEvent> messages;

    public SlackEventSinkStore() {
        this.messages = new ArrayList<>();
    }

    public void clear() {
        messages.clear();
    }

    @Override
    public void onMessage(MessageEvent message) {
        messages.add(message);
    }
}
