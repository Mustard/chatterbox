package com.github.mustard.chatterbox.msbot.client;

import com.github.mustard.chatterbox.msbot.domain.JSONWebKey;

import java.util.Optional;

public interface MSBotJWTKeyProvider {

    Optional<JSONWebKey> getKey(String keyId);

}
