package com.github.mustard.chatterbox.example.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;

public class ChatConfig {

    @Valid
    public final SlackConfig slack;

    @Valid
    public final MSBotConfig msBot;

    @JsonCreator
    public ChatConfig(
            @JsonProperty("slack") SlackConfig slack,
            @JsonProperty("msBot") MSBotConfig msBot
    ) {
        this.slack = slack;
        this.msBot = msBot;
    }

}
