package com.github.mustard.chatterbox.msbot.webhook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mustard.chatterbox.msbot.MSBotObjectMapperFactory;
import com.github.mustard.chatterbox.msbot.domain.Activity;
import com.github.mustard.chatterbox.msbot.domain.MessageEvent;
import com.github.mustard.chatterbox.util.IOUtil;
import com.github.mustard.chatterbox.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;

import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;


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

        resp.setContentType("text/plain");

        String authHeader = StringUtil.trimToEmpty(req.getHeader("Authorization"));
        String authBearerToken = authHeader.replaceFirst("^Bearer ", "");

        if (authHeader.isEmpty()) {
            LOG.warn("No Authorization header in request");
            resp.setStatus(HTTP_FORBIDDEN);
            return;
        }

        if (authBearerToken.isEmpty()) {
            LOG.warn("No Authorization Bearer token in request");
            resp.setStatus(HTTP_FORBIDDEN);
            return;
        }

        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(authBearerToken);
        } catch (JsonProcessingException jpEx) {
            LOG.warn("Authorization Bearer token is not valid JSON");
            resp.setStatus(HTTP_FORBIDDEN);
            return;
        }

        System.out.println(jsonNode);


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

        resp.setStatus(HTTP_OK);
    }

}
