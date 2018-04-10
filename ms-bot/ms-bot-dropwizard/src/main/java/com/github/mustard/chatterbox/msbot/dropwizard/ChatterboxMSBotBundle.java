package com.github.mustard.chatterbox.msbot.dropwizard;

import com.github.mustard.chatterbox.msbot.client.MSBotJWTKeyProvider;
import com.github.mustard.chatterbox.msbot.webhook.MSBotEventSink;
import com.github.mustard.chatterbox.msbot.webhook.MSBotWebHookServlet;
import io.dropwizard.Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ChatterboxMSBotBundle implements Bundle {

    private final MSBotEventSink msBoteventSink;
    private final MSBotJWTKeyProvider jwtKeyProvider;

    public ChatterboxMSBotBundle(MSBotEventSink msBoteventSink, MSBotJWTKeyProvider jwtKeyProvider) {
        this.msBoteventSink = msBoteventSink;
        this.jwtKeyProvider = jwtKeyProvider;
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }

    @Override
    public void run(Environment env) {
        env.servlets().addServlet("MSBotServlet", new MSBotWebHookServlet(msBoteventSink, jwtKeyProvider))
                .addMapping("/ms-bot-events");
    }

}
