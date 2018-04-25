package com.github.mustard.chatterbox.example.task;

import com.github.mustard.chatterbox.msbot.client.MSBotClient;
import com.github.mustard.chatterbox.msbot.domain.ChannelAccount;
import com.github.mustard.chatterbox.msbot.domain.Conversation;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMultimap;
import io.dropwizard.servlets.tasks.Task;

import java.io.PrintWriter;
import java.util.Optional;

/**
 * curl -X POST http://localhost:8881/tasks/start-conversation-msbot
 */
public class MSBotStartConversationTask extends Task {

    private final MSBotClient msBotClient;

    public MSBotStartConversationTask(MSBotClient msBotClient) {
        super("start-conversation-msbot");
        this.msBotClient = msBotClient;
    }

    @Override
    public void execute(ImmutableMultimap<String, String> parameters, PrintWriter output) throws Exception {
        String to = parameters.get("to").stream().findFirst().orElseThrow(() -> new IllegalStateException("Require to parameter"));
        output.println("Sending message to " + to);
//        msBotClient.startConversation(
//                new Conversation(
//                        new ChannelAccount(
//
//                        )
//                )
//        )
    }

}
