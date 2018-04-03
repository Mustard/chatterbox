package com.github.mustard.chatterbox.msbot.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class OpenIDConfiguration {

    public final String issuer;
    public final String authorizationEndpoint;
    public final String jwksURI;
    public final Set<String> idTokenAlgValuesSupported;
    public final Set<String> tokenEndpointAuthMethodsSupported;

    @JsonCreator
    public OpenIDConfiguration(
            @JsonProperty("issuer") String issuer,
            @JsonProperty("authorization_endpoint") String authorizationEndpoint,
            @JsonProperty("jwks_uri") String jwksURI,
            @JsonProperty("id_token_signing_alg_values_supported") Set<String> idTokenAlgValuesSupported,
            @JsonProperty("token_endpoint_auth_methods_supported") Set<String> tokenEndpointAuthMethodsSupported
    ) {
        this.issuer = issuer;
        this.authorizationEndpoint = authorizationEndpoint;
        this.jwksURI = jwksURI;
        this.idTokenAlgValuesSupported = idTokenAlgValuesSupported;
        this.tokenEndpointAuthMethodsSupported = tokenEndpointAuthMethodsSupported;
    }
}
