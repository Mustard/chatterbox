package com.github.mustard.chatterbox.msbot.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mustard.chatterbox.msbot.MSBotObjectMapperFactory;
import io.dropwizard.testing.FixtureHelpers;
import org.json.JSONException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeserialiseAndSerialiseParameterisedTest {

    private final ObjectMapper objectMapper = MSBotObjectMapperFactory.createObjectMapper();

    private static List<Arguments> fixtures() {
        return new ArrayList<Arguments>() {{
            add(Arguments.of("Activity.json", Activity.class));
        }};
    }

    @ParameterizedTest
    @MethodSource("fixtures")
    void shouldDeserialiseThenSerialise(String jsonFixture, Class claszz) throws IOException, JSONException {
        String json = FixtureHelpers.fixture(jsonFixture);
        Object object = objectMapper.readValue(json, claszz);
        String outputJSON = objectMapper.writeValueAsString(object);
        JSONAssert.assertEquals(json, outputJSON, false);
    }

}
