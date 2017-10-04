package com.github.mustard.chatterbox.slack.client;

import com.palantir.roboslack.api.MessageRequest;
import com.palantir.roboslack.webhook.SlackWebHookService;
import com.palantir.roboslack.webhook.api.model.WebHookToken;
import com.palantir.roboslack.webhook.api.model.response.ResponseCode;

public class SlackClient {

    public void postMessage(String teamId, String botId, String key, String message) {

    }

    public static void main(String[] args) {

        WebHookToken token = WebHookToken.fromString("https://hooks.slack.com/services/T/B/O");

        MessageRequest msg = MessageRequest.builder()
                .username("chatterbox")
                .text("Hi Dan").build();

        ResponseCode response = SlackWebHookService.with(token)
                .sendMessage(msg);
    }

}
