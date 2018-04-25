package com.github.mustard.chatterbox.example;

import com.github.mustard.chatterbox.example.config.Config;
import com.github.mustard.chatterbox.example.task.MSBotStartConversationTask;
import com.github.mustard.chatterbox.msbot.client.*;
import com.github.mustard.chatterbox.msbot.dropwizard.ChatterboxMSBotBundle;
import com.github.mustard.chatterbox.slack.dropwizard.ChatterboxSlackBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ExampleApp extends Application<Config> {

    private final MSBotClient msBotClient;
    private final MSBotInMemoryAuthTokenProvider msBotInMemoryAuthTokenProvider;

    public ExampleApp() {
        MSBotAppAppCredentials credentials = new MSBotAppAppCredentials("app", "password");
        MSBotAuthClient msBotAuthClient = new MSBotAuthClient();
        this.msBotInMemoryAuthTokenProvider = new MSBotInMemoryAuthTokenProvider(msBotAuthClient, credentials, MSBotInMemoryAuthTokenProvider.AuthMode.LAZY);
        this.msBotClient = new MSBotClient(msBotInMemoryAuthTokenProvider);
    }

    public static void main(String[] args) throws Exception {
        new ExampleApp().run(args);
    }

    @Override
    public void run(Config conf, Environment env) throws Exception {

        env.admin().addTask(new MSBotStartConversationTask(msBotClient));

    }

    @Override
    public void initialize(Bootstrap<Config> bootstrap) {
        bootstrap.addBundle(new ChatterboxSlackBundle(new LoggingSlackEventSink()));

        EchoMSBotEventSink echoMSBotEventSink = new EchoMSBotEventSink(msBotClient);
        bootstrap.addBundle(new ChatterboxMSBotBundle(echoMSBotEventSink, msBotInMemoryAuthTokenProvider));
    }

}
