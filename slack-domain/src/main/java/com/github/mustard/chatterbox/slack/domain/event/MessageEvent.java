package com.github.mustard.chatterbox.slack.domain.event;

public class MessageEvent implements Event {

    public final String channel;
    public final String user;
    public final String text;

    public MessageEvent(
            String channel, String user, String text
    ) {
        this.channel = channel;
        this.user = user;
        this.text = text;
    }

}
