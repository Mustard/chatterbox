package com.github.mustard.chatterbox.msbot.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TextFormat {

    @JsonProperty("markdown") MARKDOWN,
    @JsonProperty("plain")PLAIN,
    @JsonProperty("xml")XML

}
