package com.github.mustard.chatterbox.msbot.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Attachment {

    public final String contentType;
    public final String contentUrl;
    public final Object content; // TODO
    public final String name;
    public final String thumbnailUrl;

    @JsonCreator
    public Attachment(
            @JsonProperty("contentType") String contentType,
            @JsonProperty("contentUrl") String contentUrl,
            @JsonProperty("content") Object content,
            @JsonProperty("name") String name,
            @JsonProperty("thumbnailUrl") String thumbnailUrl
    ) {
        this.contentType = contentType;
        this.contentUrl = contentUrl;
        this.content = content;
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
    }

}
