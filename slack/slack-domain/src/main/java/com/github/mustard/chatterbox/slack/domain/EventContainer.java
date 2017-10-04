package com.github.mustard.chatterbox.slack.domain;

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
            String type, String token, String teamId, String apiAppId,
            String eventId, String challenge, Event event, Integer eventTime
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
