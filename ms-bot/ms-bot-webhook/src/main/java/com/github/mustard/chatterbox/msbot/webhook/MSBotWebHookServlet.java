package com.github.mustard.chatterbox.msbot.webhook;

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


public class MSBotWebHookServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(MSBotWebHookServlet.class);

    private final MSBotEventSink eventSink;

    public MSBotWebHookServlet(MSBotEventSink eventSink) {
        this.eventSink = eventSink;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestBody = inputStreamToString(req.getInputStream());

        LOG.info("AUTH HEADER {}", req.getHeader("Authorization"));

        LOG.info("MS BOT request \n\n\n{}\n\n\n", requestBody);
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
