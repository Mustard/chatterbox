package com.github.mustard.chatterbox.example;

import com.github.mustard.chatterbox.slack.SlackWebHookServlet;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class ExampleApp extends Application<Config> {

    public static void main(String[] args) throws Exception {
        new ExampleApp().run(args);
    }

    // API Token
    // xoxb-240349962339-ZYa1npaeSiLJab8fxwB4vHzq

    @Override
    public void run(Config conf, Environment env) throws Exception {
        env.servlets().addServlet("SlackServlet", new SlackWebHookServlet())
                .addMapping("/slack-events");
    }

}
