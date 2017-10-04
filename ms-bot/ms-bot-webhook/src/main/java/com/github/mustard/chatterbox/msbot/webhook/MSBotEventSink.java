package com.github.mustard.chatterbox.msbot.webhook;


import com.github.mustard.chatterbox.msbot.domain.MessageEvent;

public interface MSBotEventSink {

    void onMessage(MessageEvent message);

}
