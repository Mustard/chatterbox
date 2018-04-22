package com.github.mustard.chatterbox.example.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MSBotConfig {

    public final String appId;
    public final String password;

    @JsonCreator
    public MSBotConfig(
            @JsonProperty("appId") String appId,
            @JsonProperty("password") String password
    ) {
        this.appId = appId;
        this.password = password;
    }

}
