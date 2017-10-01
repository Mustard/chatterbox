package com.github.mustard.chatterbox.example;

import com.github.mustard.chatterbox.msbot.domain.MessageEvent;
import com.github.mustard.chatterbox.msbot.webhook.MSBotEventSink;
import com.github.mustard.chatterbox.msbot.webhook.MSBotWebHookServlet;
import com.github.mustard.chatterbox.slack.dropwizard.ChatterboxSlackBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ExampleApp extends Application<Config> {

    public static void main(String[] args) throws Exception {
        new ExampleApp().run(args);
    }

    @Override
    public void run(Config conf, Environment env) throws Exception {

        env.servlets().addServlet("SlackServlet", new MSBotWebHookServlet(new MSBotEventSink() {
            @Override
            public void onMessage(MessageEvent message) {
            }
        }))
                .addMapping("/ms-bot-events");

    }

    @Override
    public void initialize(Bootstrap<Config> bootstrap) {
        bootstrap.addBundle(new ChatterboxSlackBundle(new LoggingSlackEventSink()));
    }

}
