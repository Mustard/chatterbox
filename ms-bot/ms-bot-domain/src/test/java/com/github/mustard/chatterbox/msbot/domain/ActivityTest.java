package com.github.mustard.chatterbox.msbot.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mustard.chatterbox.msbot.MSBotObjectMapperFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {

    private final ObjectMapper objectMapper = MSBotObjectMapperFactory.createObjectMapper();

    @Test
    void shouldDeserialiseActivity() throws IOException {
        Activity activity = objectMapper.readValue(fixture("Activity.json"), Activity.class);

        assertEquals(activity.id, "bf3cc9a2f5de");
        assertNull(activity.action);
//        assertEquals(List<Attachment> attachments;
//        assertEquals(String attachmentLayout; // TODO enum?
        assertEquals(activity.channelId, "channel's name/id");
        assertEquals(activity.conversation.id, "abcd1234");
        assertEquals(activity.conversation.name, "conversation's name");
        assertEquals(activity.conversation.isGroup, false);
//        assertEquals(String code;
//        assertEquals(ChannelAccount from;
//        assertEquals(boolean historyDisclosed;
//        assertEquals(String id;
        // inputHint
        // locale
        // localTimestamp
        // membersAdded
        // membersRemoved
        // name
//        public final ChannelAccount recipient;
//        public final ConversationReference relatesTo;
//        public final String replyToId;
//        public final String serviceUrl;
        // speak
        // suggestedActions
        // summary
//        public final String text;
//        public final TextFormat textFormat;
        // timestamp
//        public final String topicName;
//        public final ActivityType type;
        // TODO rest of properties
    }

}