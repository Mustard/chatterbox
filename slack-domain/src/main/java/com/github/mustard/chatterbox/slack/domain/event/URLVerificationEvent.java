package com.github.mustard.chatterbox.slack.domain.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class URLVerificationEvent implements Event {

    public final String challenge;

    @JsonCreator
    public URLVerificationEvent(
            @JsonProperty("challenge") String challenge
    ) {
        this.challenge = challenge;
    }

}
