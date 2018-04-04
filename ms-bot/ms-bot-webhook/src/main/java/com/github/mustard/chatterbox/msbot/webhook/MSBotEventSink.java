package com.github.mustard.chatterbox.msbot.webhook;


import com.github.mustard.chatterbox.msbot.domain.Activity;

public interface MSBotEventSink {

    void onMessage(Activity activity);

}
