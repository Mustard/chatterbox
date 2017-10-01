package com.github.mustard.chatterbox.msbot.webhook;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;


public class MSBotWebHookServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(MSBotWebHookServlet.class);

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new ParameterNamesModule())
            .disable(FAIL_ON_UNKNOWN_PROPERTIES);

    private final MSBotEventSink eventSink;

    public MSBotWebHookServlet(MSBotEventSink eventSink) {
        this.eventSink = eventSink;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestBody = inputStreamToString(req.getInputStream());

        LOG.info("MS BOT request {}", requestBody);
        resp.setStatus(200);
        resp.setContentType("text/plain");

//        objectMapper.readreq.getInputStream().
//        eventSink.
    }

    private String inputStreamToString(InputStream inputStream) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int result = bis.read();
        while(result != -1) {
            buf.write((byte) result);
            result = bis.read();
        }
        return buf.toString(StandardCharsets.UTF_8.name());
    }

}
