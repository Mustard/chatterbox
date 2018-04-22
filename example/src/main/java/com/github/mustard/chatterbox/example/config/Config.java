package com.github.mustard.chatterbox.example.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;

public class Config extends Configuration {

    @Valid
    public final ChatConfig chat;

    @JsonCreator
    public Config(
            @JsonProperty("chat") ChatConfig chat
    ) {
        this.chat = chat;
    }

}
