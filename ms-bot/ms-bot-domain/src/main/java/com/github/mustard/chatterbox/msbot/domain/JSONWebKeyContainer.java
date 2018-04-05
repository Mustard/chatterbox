package com.github.mustard.chatterbox.msbot.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class JSONWebKeyContainer {

    public final List<JSONWebKey> keys;

    @JsonCreator
    public JSONWebKeyContainer(
            @JsonProperty("keys") List<JSONWebKey> keys
    ) {
        this.keys = keys;
    }

}
