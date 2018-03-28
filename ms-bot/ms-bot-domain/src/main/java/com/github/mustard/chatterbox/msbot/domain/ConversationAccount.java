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

}
