package com.github.mustard.chatterbox.msbot.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ActivityType {

    @JsonProperty("message") MESSAGE,
    @JsonProperty("conversationUpdate") CONVERSATION_UPDATE,
    @JsonProperty("contactRelationUpdate") CONTACT_RELATION_UPDATE,
    @JsonProperty("typing") TYPING,
    @JsonProperty("ping") PING,
    @JsonProperty("deleteUserData") DELETE_USER_DATA,
    @JsonProperty("endOfConversation") END_OF_CONVERSATION

}
