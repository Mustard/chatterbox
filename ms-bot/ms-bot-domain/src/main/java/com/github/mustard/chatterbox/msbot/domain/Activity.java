package com.github.mustard.chatterbox.msbot.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.EnumDeserializer;

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
    public final String timestamp; // TODO temporal type
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
            @JsonProperty("timestamp") String timestamp,
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
        this.timestamp = timestamp;
        this.topicName = topicName;
        this.type = type;
    }

    public static ActivityBuilder activityBuilder() {
        return new ActivityBuilder();
    }

    public static final class ActivityBuilder {
        public String action;
        public List<Attachment> attachments;
        public String attachmentLayout; // TODO enum?
        public String channelId;
        public ConversationAccount conversation;
        public String code;
        public ChannelAccount from;
        public boolean historyDisclosed;
        public String id;
        // inputHint
        // locale
        // localTimestamp
        // membersAdded
        // membersRemoved
        // name
        public ChannelAccount recipient;
        public ConversationReference relatesTo;
        public String replyToId;
        public String serviceUrl;
        // speak
        // suggestedActions
        // summary
        public String text;
        public TextFormat textFormat;
        public String timestamp; // TODO temporal type
        public String topicName;
        public ActivityType type;

        private ActivityBuilder() {
        }

        public ActivityBuilder action(String action) {
            this.action = action;
            return this;
        }

        public ActivityBuilder attachments(List<Attachment> attachments) {
            this.attachments = attachments;
            return this;
        }

        public ActivityBuilder attachmentLayout(String attachmentLayout) {
            this.attachmentLayout = attachmentLayout;
            return this;
        }

        public ActivityBuilder channelId(String channelId) {
            this.channelId = channelId;
            return this;
        }

        public ActivityBuilder conversation(ConversationAccount conversation) {
            this.conversation = conversation;
            return this;
        }

        public ActivityBuilder code(String code) {
            this.code = code;
            return this;
        }

        public ActivityBuilder from(ChannelAccount from) {
            this.from = from;
            return this;
        }

        public ActivityBuilder historyDisclosed(boolean historyDisclosed) {
            this.historyDisclosed = historyDisclosed;
            return this;
        }

        public ActivityBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ActivityBuilder recipient(ChannelAccount recipient) {
            this.recipient = recipient;
            return this;
        }

        public ActivityBuilder relatesTo(ConversationReference relatesTo) {
            this.relatesTo = relatesTo;
            return this;
        }

        public ActivityBuilder replyToId(String replyToId) {
            this.replyToId = replyToId;
            return this;
        }

        public ActivityBuilder serviceUrl(String serviceUrl) {
            this.serviceUrl = serviceUrl;
            return this;
        }

        public ActivityBuilder text(String text) {
            this.text = text;
            return this;
        }

        public ActivityBuilder textFormat(TextFormat textFormat) {
            this.textFormat = textFormat;
            return this;
        }

        public ActivityBuilder timestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ActivityBuilder topicName(String topicName) {
            this.topicName = topicName;
            return this;
        }

        public ActivityBuilder type(ActivityType type) {
            this.type = type;
            return this;
        }

        public Activity build() {
            return new Activity(action, attachments, attachmentLayout, channelId, conversation, code, from, historyDisclosed, id, recipient, relatesTo, replyToId, serviceUrl, text, textFormat, timestamp, topicName, type);
        }
    }
}
