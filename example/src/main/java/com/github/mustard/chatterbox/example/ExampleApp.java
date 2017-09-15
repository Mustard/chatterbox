package com.github.mustard.chatterbox.example;

import com.github.mustard.chatterbox.slack.SlackWebHookServlet;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class ExampleApp extends Application<Config> {

    public static void main(String[] args) throws Exception {
        new ExampleApp().run(args);
    }

    @Override
    public void run(Config conf, Environment env) throws Exception {
        env.servlets().addServlet("SlackServlet", new SlackWebHookServlet(new LoggingSlackEventSink()))
                .addMapping("/slack-events");
    }

}
