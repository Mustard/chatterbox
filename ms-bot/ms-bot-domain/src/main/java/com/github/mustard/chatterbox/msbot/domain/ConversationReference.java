package com.github.mustard.chatterbox.msbot.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConversationReference {

    public final String activityId;
    public final ChannelAccount bot;
    public final String channelId;
    public final ConversationAccount conversation;
    public final String serviceUrl;
    public final ChannelAccount user;

    @JsonCreator
    public ConversationReference(
            @JsonProperty("activityId") String activityId,
            @JsonProperty("bot") ChannelAccount bot,
            @JsonProperty("channelId") String channelId,
            @JsonProperty("conversation") ConversationAccount conversation,
            @JsonProperty("serviceUrl") String serviceUrl,
            @JsonProperty("user") ChannelAccount user
    ) {
        this.activityId = activityId;
        this.bot = bot;
        this.channelId = channelId;
        this.conversation = conversation;
        this.serviceUrl = serviceUrl;
        this.user = user;
    }

}
