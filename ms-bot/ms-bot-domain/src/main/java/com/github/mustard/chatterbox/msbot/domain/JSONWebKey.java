package com.github.mustard.chatterbox.msbot.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JSONWebKey {

    public final String keyType;
    public final String use;
    public final String keyOperations;
    public final String algorithm;
    public final String keyId;
    public final String x509URL;
    public final String x509CertificateChain;
    public final String x509CertificateThumbprint;

    public JSONWebKey(
            @JsonProperty("kty") String keyType,
            @JsonProperty("use") String use,
            @JsonProperty("key_ops") String keyOperations,
            @JsonProperty("alg") String algorithm,
            @JsonProperty("kid") String keyId,
            @JsonProperty("x5u") String x509URL,
            @JsonProperty("x5c") String x509CertificateChain,
            @JsonProperty("x5t") String x509CertificateThumbprint
    ) {
        this.keyType = keyType;
        this.use = use;
        this.keyOperations = keyOperations;
        this.algorithm = algorithm;
        this.keyId = keyId;
        this.x509URL = x509URL;
        this.x509CertificateChain = x509CertificateChain;
        this.x509CertificateThumbprint = x509CertificateThumbprint;
    }
}
