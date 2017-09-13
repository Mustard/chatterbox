package com.github.mustard.chatterbox.slack.domain.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageEvent implements Event {

    public final String channel;
    public final String user;
    public final String text;

    @JsonCreator
    public MessageEvent(
            @JsonProperty("channel") String channel,
            @JsonProperty("user") String user,
            @JsonProperty("text") String text
    ) {
        this.channel = channel;
        this.user = user;
        this.text = text;
    }

}
