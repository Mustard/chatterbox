package com.github.mustard.chatterbox.slack.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.mustard.chatterbox.slack.domain.event.Event;

public class EventMeta {

    public final String token;
    public final String teamId;
    public final String apiAppId;
    public final String eventId;
    public final Event event;
    // TODO date
    public final Integer eventTime;

    public EventMeta(
            @JsonProperty("token") String token,
            @JsonProperty("teamId") String teamId,
            @JsonProperty("apiAppId") String apiAppId,
            @JsonProperty("eventId") String eventId,
            @JsonProperty("event") Event event,
            @JsonProperty("eventTime") Integer eventTime
    ) {
        this.token = token;
        this.teamId = teamId;
        this.apiAppId = apiAppId;
        this.eventId = eventId;
        this.event = event;
        this.eventTime = eventTime;
    }

}
