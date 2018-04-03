package com.github.mustard.chatterbox.msbot.client;

import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

public class MSBotJerseyClientBuilder {

    static JerseyClient makeClient() {
        return JerseyClientBuilder.createClient()
                .register(JacksonFeature.class);
    }

}
