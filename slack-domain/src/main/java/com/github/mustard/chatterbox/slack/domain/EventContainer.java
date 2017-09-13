package com.github.mustard.chatterbox.slack.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.mustard.chatterbox.slack.domain.event.Event;

public class EventContainer {

    public final String type;
    public final String token;
    public final String teamId;
    public final String apiAppId;
    public final String eventId;
    public final String challenge;
    public final Event event;
    // TODO date
    public final Integer eventTime;

    public EventContainer(
            @JsonProperty("type") String type,
            @JsonProperty("token") String token,
            @JsonProperty("teamId") String teamId,
            @JsonProperty("apiAppId") String apiAppId,
            @JsonProperty("eventId") String eventId,
            @JsonProperty("challenge") String challenge,
            @JsonProperty("event") Event event,
            @JsonProperty("eventTime") Integer eventTime
    ) {
        this.type = type;
        this.token = token;
        this.teamId = teamId;
        this.apiAppId = apiAppId;
        this.eventId = eventId;
        this.challenge = challenge;
        this.event = event;
        this.eventTime = eventTime;
    }

}
