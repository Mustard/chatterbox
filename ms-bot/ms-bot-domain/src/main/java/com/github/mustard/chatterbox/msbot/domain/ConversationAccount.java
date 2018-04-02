package com.github.mustard.chatterbox.msbot.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConversationAccount {

    public final String id;
    public final boolean isGroup;
    public final String name;

    @JsonCreator
    public ConversationAccount(
            @JsonProperty("id") String id,
            @JsonProperty("isGroup") boolean isGroup,
            @JsonProperty("name") String name
    ) {
        this.id = id;
        this.isGroup = isGroup;
        this.name = name;
    }

    private ConversationAccount(ConversationAccountBuilder builder) {
        this(
                builder.id,
                builder.isGroup,
                builder.name
        );
    }

    public static ConversationAccountBuilder conversationAccountBuilder() {
        return new ConversationAccountBuilder();
    }

    public static class ConversationAccountBuilder {
        private String id;
        private boolean isGroup;
        private String name;

        private ConversationAccountBuilder() {
        }

        public ConversationAccountBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ConversationAccountBuilder isGroup(boolean isGroup) {
            this.isGroup = isGroup;
            return this;
        }

        public ConversationAccountBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ConversationAccount build() {
            return new ConversationAccount(this);
        }

    }

}
