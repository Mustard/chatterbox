package com.github.mustard.chatterbox.slack.dropwizard;

import com.github.mustard.chatterbox.slack.SlackEventSink;
import com.github.mustard.chatterbox.slack.SlackWebHookServlet;
import io.dropwizard.Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ChatterboxSlackBundle implements Bundle {

    private final SlackEventSink slackEventSink;

    public ChatterboxSlackBundle(SlackEventSink slackEventSink) {
        this.slackEventSink = slackEventSink;
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
    }

    @Override
    public void run(Environment env) {
        env.servlets().addServlet("SlackServlet", new SlackWebHookServlet(slackEventSink))
                .addMapping("/slack-events");
    }

}
