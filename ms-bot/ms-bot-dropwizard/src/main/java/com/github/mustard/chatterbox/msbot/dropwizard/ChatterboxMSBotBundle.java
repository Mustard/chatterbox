package com.github.mustard.chatterbox.msbot.dropwizard;

import com.github.mustard.chatterbox.msbot.webhook.MSBotEventSink;
import com.github.mustard.chatterbox.msbot.webhook.MSBotWebHookServlet;
import io.dropwizard.Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ChatterboxMSBotBundle implements Bundle {

    private final MSBotEventSink msBoteventSink;

    public ChatterboxMSBotBundle(MSBotEventSink msBoteventSink) {
        this.msBoteventSink = msBoteventSink;
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }

    @Override
    public void run(Environment env) {
        env.servlets().addServlet("SlackServlet", new MSBotWebHookServlet(msBoteventSink))
                .addMapping("/ms-bot-events");
    }

}
