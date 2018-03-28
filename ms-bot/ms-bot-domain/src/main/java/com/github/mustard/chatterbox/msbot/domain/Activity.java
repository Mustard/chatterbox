package com.github.mustard.chatterbox.msbot.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


// https://docs.microsoft.com/en-us/azure/bot-service/rest-api/bot-framework-rest-connector-api-reference#activity-object
public class Activity {

    public final String action;
    public final List<Attachment> attachments;
    public final String attachmentLayout; // TODO enum?
    public final String channelId;
    public final ConversationAccount conversation;
    public final String code;
    public final ChannelAccount from;
    public final boolean historyDisclosed;
    public final String id;
    // inputHint
    // locale
    // localTimestamp
    // membersAdded
    // membersRemoved
    // name
    public final ChannelAccount recipient;
    public final ConversationReference relatesTo;
    public final String replyToId;
    public final String serviceUrl;
    // speak
    // suggestedActions
    // summary
    public final String text;
    public final TextFormat textFormat;
    // timestamp
    public final String topicName;
    public final ActivityType type;


    @JsonCreator
    public Activity(
            @JsonProperty("action") String action,
            @JsonProperty("attachments") List<Attachment> attachments,
            @JsonProperty("attachmentLayout") String attachmentLayout,
            @JsonProperty("channelId") String channelId,
            @JsonProperty("conversation") ConversationAccount conversation,
            @JsonProperty("code") String code,
            @JsonProperty("from") ChannelAccount from,
            @JsonProperty("historyDisclosed") boolean historyDisclosed,
            @JsonProperty("id") String id,
            @JsonProperty("recipient") ChannelAccount recipient,
            @JsonProperty("relatesTo") ConversationReference relatesTo,
            @JsonProperty("replyToId") String replyToId,
            @JsonProperty("serviceUrl") String serviceUrl,
            @JsonProperty("text") String text,
            @JsonProperty("textFormat") TextFormat textFormat,
            @JsonProperty("topicName") String topicName,
            @JsonProperty("type") ActivityType type
    ) {
        this.action = action;
        this.attachments = attachments;
        this.attachmentLayout = attachmentLayout;
        this.channelId = channelId;
        this.conversation = conversation;
        this.code = code;
        this.from = from;
        this.historyDisclosed = historyDisclosed;
        this.id = id;
        this.recipient = recipient;
        this.relatesTo = relatesTo;
        this.replyToId = replyToId;
        this.serviceUrl = serviceUrl;
        this.text = text;
        this.textFormat = textFormat;
        this.topicName = topicName;
        this.type = type;
    }

}
