package com.github.mustard.chatterbox.slack;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.github.mustard.chatterbox.slack.domain.EventContainer;
import com.github.mustard.chatterbox.slack.domain.event.MessageEvent;
import com.github.mustard.chatterbox.util.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE;


public class SlackWebHookServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(SlackWebHookServlet.class);

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new ParameterNamesModule())
            .setPropertyNamingStrategy(SNAKE_CASE)
            .disable(FAIL_ON_UNKNOWN_PROPERTIES);

    private final SlackEventSink eventSink;

    public SlackWebHookServlet(SlackEventSink eventSink) {
        this.eventSink = eventSink;
    }

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        EventContainer eventContainer;

        if (LOG.isDebugEnabled()) {
            String requestBody = IOUtil.toString(req.getInputStream());
            LOG.debug(requestBody);
            eventContainer = objectMapper.readValue(requestBody, EventContainer.class);
        } else {
            eventContainer = objectMapper.readValue(req.getInputStream(), EventContainer.class);
        }

        switch (eventContainer.type) {
            case "event_callback":
                handleEvent(eventContainer, resp);
                break;
            case "url_verification":
                handleUrlVerification(eventContainer, resp);
                break;
        }

    }

    private void handleUrlVerification(EventContainer eventContainer, HttpServletResponse resp) throws IOException {
        resp.setStatus(200);
        resp.setContentType("text/plain");
        resp.getWriter().write(eventContainer.challenge);
    }

    private void handleEvent(EventContainer eventContainer, HttpServletResponse resp) {
        resp.setStatus(200);
        resp.setContentType("text/plain");
        if (eventContainer.event instanceof MessageEvent) {
            eventSink.onMessage((MessageEvent) eventContainer.event);
        }
    }

}
