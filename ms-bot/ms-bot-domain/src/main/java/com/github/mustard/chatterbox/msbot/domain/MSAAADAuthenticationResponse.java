package com.github.mustard.chatterbox.msbot.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// https://docs.microsoft.com/en-us/azure/bot-service/rest-api/bot-framework-rest-connector-authentication#step-2-obtain-the-jwt-token-from-the-msaaad-v2-login-service-response
public class MSAAADAuthenticationResponse {

    public final String tokenType;
    public final long expiresIn;
    public final long extExpiresIn;
    public final String accessToken;

    // for what ever reason possibly to comply with OAuth spec? this response is snake case
    @JsonCreator
    public MSAAADAuthenticationResponse(
            @JsonProperty("token_type") String tokenType,
            @JsonProperty("expires_in") long expiresIn,
            @JsonProperty("ext_expires_in") long extExpiresIn,
            @JsonProperty("access_token") String accessToken
    ) {
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.extExpiresIn = extExpiresIn;
        this.accessToken = accessToken;
    }

}
