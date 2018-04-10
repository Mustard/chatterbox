package com.github.mustard.chatterbox.msbot.webhook;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mustard.chatterbox.msbot.MSBotObjectMapperFactory;
import com.github.mustard.chatterbox.msbot.client.MSBotAppAppCredentials;
import com.github.mustard.chatterbox.msbot.client.MSBotJWTKeyProvider;
import com.github.mustard.chatterbox.msbot.domain.Activity;
import com.github.mustard.chatterbox.msbot.domain.JSONWebKey;
import com.github.mustard.chatterbox.util.IOUtil;
import com.github.mustard.chatterbox.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.time.Duration;
import java.util.Base64;
import java.util.Optional;

import static java.net.HttpURLConnection.*;


public class MSBotWebHookServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(MSBotWebHookServlet.class);

    private final MSBotEventSink eventSink;
    private final MSBotJWTKeyProvider jwtKeyProvider;
    private final ObjectMapper objectMapper;

    public MSBotWebHookServlet(MSBotEventSink eventSink, MSBotJWTKeyProvider jwtKeyProvider) {
        this.eventSink = eventSink;
        this.jwtKeyProvider = jwtKeyProvider;
        this.objectMapper = MSBotObjectMapperFactory.createObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/plain");

        Optional<DecodedJWT> decodedJWT = authenticateRequest(req, resp);

        if (!decodedJWT.isPresent()) return;

        Activity activity;

        if (LOG.isDebugEnabled()) {
            String requestBody = IOUtil.toString(req.getInputStream());
            LOG.debug(requestBody);
            activity = objectMapper.readValue(requestBody, Activity.class);
        } else {
            activity = objectMapper.readValue(req.getInputStream(), Activity.class);
        }

        eventSink.onMessage(activity);

        resp.setStatus(HTTP_OK);
    }

    private Optional<DecodedJWT> authenticateRequest(HttpServletRequest req, HttpServletResponse resp) {
        String authHeader = StringUtil.trimToEmpty(req.getHeader("Authorization"));
        String authBearerToken = authHeader.replaceFirst("^Bearer ", "");

        if (authHeader.isEmpty()) {
            LOG.warn("No Authorization header in request");
            resp.setStatus(HTTP_FORBIDDEN);
            return Optional.empty();
        }

        if (authBearerToken.isEmpty()) {
            LOG.warn("No Authorization Bearer token in request");
            resp.setStatus(HTTP_FORBIDDEN);
            return Optional.empty();
        }

        DecodedJWT decodedJWT = JWT.decode(authBearerToken);

        RSAKeyProvider keyProvider = new RSAKeyProvider() {
            @Override
            public RSAPublicKey getPublicKeyById(String keyId) {
                Optional<JSONWebKey> key = jwtKeyProvider.getKey(keyId);
                if (!key.isPresent()) {
                    // TODO what do?
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return null;
                }
                try {
                    // https://github.com/auth0/jwks-rsa-java/blob/master/src/main/java/com/auth0/jwk/Jwk.java#L171
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    JSONWebKey jwk = key.get();
                    BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(jwk.n));
                    BigInteger exponent = new BigInteger(1, Base64.getUrlDecoder().decode(jwk.e));
                    return (RSAPublicKey) keyFactory.generatePublic(new RSAPublicKeySpec(modulus, exponent));
//                    String keyDataString = key.get().x509CertificateChain.get(0);
//                    byte[] keyData = Base64.getDecoder().decode(keyDataString.getBytes(StandardCharsets.UTF_8));
//                    return (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(keyData));
                } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public RSAPrivateKey getPrivateKey() {
                return null;
            }

            @Override
            public String getPrivateKeyId() {
                return null;
            }
        };

        Algorithm algorithm;
        switch (decodedJWT.getAlgorithm()) {
            case "RS256":
                algorithm = Algorithm.RSA256(keyProvider);
                break;
            default:
                resp.setStatus(HTTP_BAD_REQUEST);
                LOG.error("Unknown algorithm {}", decodedJWT.getAlgorithm());
                return Optional.empty();
        }

        JWTVerifier jwtVerifier = JWT.require(algorithm)
                .acceptLeeway(Duration.ofMinutes(5).getSeconds())
                .withIssuer("https://api.botframework.com")
                // .withAudience(ap) TODO verify audience if the app id
                .build();

        jwtVerifier.verify(authBearerToken);
        return Optional.of(decodedJWT);
    }

}
