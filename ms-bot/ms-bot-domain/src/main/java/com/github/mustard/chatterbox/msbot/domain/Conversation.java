package com.github.mustard.chatterbox.msbot.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Conversation {

    public final ChannelAccount bot;
    public final boolean isGroup;
    public final List<ChannelAccount> members;
    public final String topicName;
    public final Activity activity;

    @JsonCreator
    public Conversation(
            @JsonProperty("bot") ChannelAccount bot,
            @JsonProperty("isGroup") boolean isGroup,
            @JsonProperty("members") List<ChannelAccount> members,
            @JsonProperty("topicName") String topicName,
            @JsonProperty("activity") Activity activity
    ) {
        this.bot = bot;
        this.isGroup = isGroup;
        this.members = members;
        this.topicName = topicName;
        this.activity = activity;
    }

}
