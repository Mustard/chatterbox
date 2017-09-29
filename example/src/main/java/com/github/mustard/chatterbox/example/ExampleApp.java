package com.github.mustard.chatterbox.example;

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
    }

}
