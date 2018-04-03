package com.github.mustard.chatterbox.msbot.client;

import com.github.mustard.chatterbox.msbot.domain.MSAAADAuthenticationResponse;
import com.github.mustard.chatterbox.msbot.domain.OpenIDConfiguration;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class MSBotInMemoryAuthSession implements MSBotAuthSession {


    public enum Mode { LAZY, ASYNC_PREEMEPTIVE }

    private final MSBotAuthClient authClient;
    private final Mode mode;

    private final ReentrantLock openIdActionLock = new ReentrantLock();
    private final Duration openIdConfigCacheDuration;
    private LocalDateTime openIdConfigCachedTime;
    private OpenIDConfiguration openIDConfiguration;


    private MSAAADAuthenticationResponse authResponse;

    public MSBotInMemoryAuthSession(MSBotAuthClient authClient) {
        this.authClient = authClient;
        this.openIdConfigCacheDuration = Duration.ofDays(5L);
        this.mode = Mode.LAZY;
    }

    @Override
    public String getBearerAuthToken() {
        if (mode == Mode.LAZY) {
            lazyAuth();
        }
        if (mode == Mode.ASYNC_PREEMEPTIVE) {
            // TODO wait lock on auth id
        }
        return authResponse.accessToken;
    }

    private boolean hasOpenIdConfigCacheExpired() {
        synchronized (openIdActionLock) {
//        LocalDateTime expiry = LocalDateTime.now()

        }
        return false;
    }

    private void lazyAuth() {
        synchronized (openIdActionLock) {

        }
    }

}
