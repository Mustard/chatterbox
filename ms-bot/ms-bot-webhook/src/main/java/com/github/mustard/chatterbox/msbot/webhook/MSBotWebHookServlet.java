package com.github.mustard.chatterbox.msbot.webhook;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mustard.chatterbox.msbot.MSBotObjectMapperFactory;
import com.github.mustard.chatterbox.msbot.domain.Activity;
import com.github.mustard.chatterbox.msbot.domain.MessageEvent;
import com.github.mustard.chatterbox.util.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MSBotWebHookServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(MSBotWebHookServlet.class);

    private final MSBotEventSink eventSink;
    private final ObjectMapper objectMapper;

    public MSBotWebHookServlet(MSBotEventSink eventSink) {
        this.eventSink = eventSink;
        this.objectMapper = MSBotObjectMapperFactory.createObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LOG.info("AUTH HEADER {}", req.getHeader("Authorization"));

        Activity activity;

        if (LOG.isDebugEnabled()) {
            String requestBody = IOUtil.toString(req.getInputStream());
            LOG.debug(requestBody);
            activity = objectMapper.readValue(requestBody, Activity.class);
        } else {
            activity = objectMapper.readValue(req.getInputStream(), Activity.class);
        }

//        eventSink.onMessage(new MessageEvent()); // TODO
        eventSink.onMessage(activity);

        resp.setStatus(200);
        resp.setContentType("text/plain");
    }

}
