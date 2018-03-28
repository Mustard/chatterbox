package com.github.mustard.chatterbox.msbot.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateConversationResponse {

    public final String id;

    @JsonCreator
    public CreateConversationResponse(
            @JsonProperty("id") String id
    ) {
        this.id = id;
    }

}
