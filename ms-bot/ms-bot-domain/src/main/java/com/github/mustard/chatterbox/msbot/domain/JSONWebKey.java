package com.github.mustard.chatterbox.msbot.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;

// https://tools.ietf.org/html/rfc7517
public class JSONWebKey {

    public final String keyType;
    public final String use;
    public final String keyOperations;
    public final String algorithm;
    public final String keyId;
    public final String n;
    public final String e;
    public final Set<String> endorsements;
    public final String x509URL;
    public final List<String> x509CertificateChain;
    public final String x509CertificateThumbprint;

    public JSONWebKey(
            @JsonProperty("kty") String keyType,
            @JsonProperty("use") String use,
            @JsonProperty("key_ops") String keyOperations,
            @JsonProperty("alg") String algorithm,
            @JsonProperty("kid") String keyId,
            @JsonProperty("n") String n,
            @JsonProperty("e") String e,
            @JsonProperty("endorsements") Set<String> endorsements,
            @JsonProperty("x5u") String x509URL,
            @JsonProperty("x5c") List<String> x509CertificateChain,
            @JsonProperty("x5t") String x509CertificateThumbprint
    ) {
        this.keyType = keyType;
        this.use = use;
        this.keyOperations = keyOperations;
        this.algorithm = algorithm;
        this.keyId = keyId;
        this.n = n;
        this.e = e;
        this.endorsements = endorsements;
        this.x509URL = x509URL;
        this.x509CertificateChain = x509CertificateChain;
        this.x509CertificateThumbprint = x509CertificateThumbprint;
    }
}
