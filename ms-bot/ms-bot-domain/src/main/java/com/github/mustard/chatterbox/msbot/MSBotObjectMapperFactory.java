package com.github.mustard.chatterbox.msbot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class MSBotObjectMapperFactory {

    public static ObjectMapper createObjectMapper() {
        return new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .disable(FAIL_ON_UNKNOWN_PROPERTIES);
    }

}
