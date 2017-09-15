package com.github.mustard.chatterbox.slack;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.StringEntity;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.jupiter.api.*;

import javax.ws.rs.core.MediaType;
import java.io.*;

import static com.google.common.base.Charsets.UTF_8;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Slack Web Hook Servlet")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SlackWebHookServletTest {

    private Server server;
    private SlackEventSinkStore eventSink = new SlackEventSinkStore();

    @BeforeAll
    void beforeAll() throws Exception {
        server = new Server(0);
        ServletHandler servletHandler = new ServletHandler();
        server.setHandler(servletHandler);
        servletHandler.addServletWithMapping(new ServletHolder(
                new SlackWebHookServlet(eventSink)), "/slack-events");
        server.start();
    }

    @AfterAll
    void afterAll() throws Exception {
        if (server != null) server.stop();
    }

    @AfterEach
    void afterEach() {
        eventSink.clear();
    }

    @Test
    @DisplayName("URL verification should return challenge")
    void shouldReturnChallenge() throws IOException {
        HttpResponse response = postEvent("url-check.json");
        assertThat(response.getStatusLine().getReasonPhrase()).isEqualTo("OK");
        assertThat(response.getLastHeader(CONTENT_TYPE).getValue()).contains(MediaType.TEXT_PLAIN);
        assertThat(response.getEntity().getContent()).hasSameContentAs(inputStreamFrom("ivBOsffuOApLqxhWskctgdvMPa00Nt1B9Zv8RzTx9ahBVfevfWPZ"));
    }

    @Test
    @DisplayName("Receive message")
    void should() throws IOException {
        HttpResponse response = postEvent("message.json");
        assertThat(response.getStatusLine().getReasonPhrase()).isEqualTo("OK");
        assertThat(response.getLastHeader(CONTENT_TYPE).getValue()).contains(MediaType.TEXT_PLAIN);
        assertThat(eventSink.messages.get(0).text).isEqualTo("Hi");
        assertThat(eventSink.messages.get(0).channel).isEqualTo("D72EA72LC");
        assertThat(eventSink.messages.get(0).user).isEqualTo("U72CEE1PC");
    }

    private int getPort() {
        return ((ServerConnector) server.getConnectors()[0]).getLocalPort();
    }

    private InputStream inputStreamFrom(String data) {
        return new ByteArrayInputStream(data.getBytes(UTF_8));
    }

    private HttpResponse postEvent(String jsonFixturePath) throws IOException {
        StringEntity stringEntity = new StringEntity(
                Resources.toString(getClass().getResource("/" + jsonFixturePath), UTF_8));
        return Request.Post("http://localhost:" + getPort() + "/slack-events")
                .setHeader(CONTENT_TYPE, APPLICATION_JSON)
                .body(stringEntity)
                .execute().returnResponse();
    }

}
