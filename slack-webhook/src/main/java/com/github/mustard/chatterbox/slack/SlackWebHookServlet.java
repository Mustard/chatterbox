package com.github.mustard.chatterbox.slack;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SlackWebHookServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        JsonNode json = objectMapper.readTree(req.getInputStream());

        if (json.has("challenge")) {
            String challenge = json.get("challenge").asText();
            resp.setStatus(200);
            resp.setContentType("text/plain");
            resp.getWriter().write(challenge);
            return;
        }



        super.doPost(req, resp);
    }

}
