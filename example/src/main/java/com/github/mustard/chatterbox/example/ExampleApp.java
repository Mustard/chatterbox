package com.github.mustard.chatterbox.example;

import com.github.mustard.chatterbox.example.config.Config;
import com.github.mustard.chatterbox.msbot.client.*;
import com.github.mustard.chatterbox.msbot.dropwizard.ChatterboxMSBotBundle;
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
    }

    @Override
    public void initialize(Bootstrap<Config> bootstrap) {
        bootstrap.addBundle(new ChatterboxSlackBundle(new LoggingSlackEventSink()));


        MSBotAppAppCredentials credentials = new MSBotAppAppCredentials("app", "password");
        MSBotAuthClient msBotAuthClient = new MSBotAuthClient();
        MSBotInMemoryAuthTokenProvider msBotInMemoryAuthTokenProvider = new MSBotInMemoryAuthTokenProvider(msBotAuthClient, credentials, MSBotInMemoryAuthTokenProvider.AuthMode.LAZY);
        MSBotClient msBotClient = new MSBotClient(msBotInMemoryAuthTokenProvider);

        EchoMSBotEventSink echoMSBotEventSink = new EchoMSBotEventSink(msBotClient);
        bootstrap.addBundle(new ChatterboxMSBotBundle(echoMSBotEventSink, msBotInMemoryAuthTokenProvider));
    }

}
