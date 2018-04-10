package com.github.mustard.chatterbox.msbot.client;

import com.github.mustard.chatterbox.msbot.domain.JSONWebKey;
import com.github.mustard.chatterbox.msbot.domain.MSAAADAuthenticationResponse;
import com.github.mustard.chatterbox.msbot.domain.OpenIDConfiguration;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

import static java.time.LocalDateTime.now;

public class MSBotInMemoryAuthTokenProvider implements MSBotAuthTokenProvider, MSBotJWTKeyProvider {


    public enum AuthMode {LAZY, EAGER}

    private final MSBotAuthClient authClient;
    private final AuthMode authMode;
    private final MSBotAppCredentialsProvider credentialsProvider;
    private final ReentrantLock authActionLock = new ReentrantLock();

    private final ReentrantLock openIdActionLock = new ReentrantLock();
    private final Duration openIdConfigCacheDuration;
    private LocalDateTime openIdConfigCachedTime;
    private OpenIDConfiguration openIDConfiguration;
    private List<JSONWebKey> openIDJWTKeyList;
    private final ExecutorService executorService;

    private MSAAADAuthenticationResponse authResponse;
    private LocalDateTime authResponseExpiry;

    public MSBotInMemoryAuthTokenProvider(MSBotAuthClient authClient, MSBotAppCredentialsProvider credentialsProvider, AuthMode authMode) {
        this.authClient = authClient;
        this.credentialsProvider = credentialsProvider;
        this.openIdConfigCacheDuration = Duration.ofDays(5L);
        this.authMode = authMode;
        this.executorService = createExecutorService(authMode);
    }

    private ExecutorService createExecutorService(AuthMode authMode) {
        if (authMode == AuthMode.LAZY) {
            return currentThreadExecutorService();
        }

        return Executors.newSingleThreadExecutor();
    }

    @Override
    public String getBearerAuthToken() {
        checkOpenIdConfig();
        Future<?> authDone = checkAuthentication();

        if (authResponse == null) {
            try {
                authDone.get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        return authResponse.accessToken;
    }


    @Override
    public Optional<JSONWebKey> getKey(String keyId) {
        getBearerAuthToken(); // TODO this is just to check we have fetched

        openIdActionLock.lock();
        try {
            return openIDJWTKeyList.stream()
                    .filter(jsonWebKey -> keyId.equals(jsonWebKey.keyId))
                    .findFirst();
        } finally {
            openIdActionLock.unlock();
        }
    }


    private Future<?> checkAuthentication() {
        if (openIDConfiguration == null) {
            try {
                checkOpenIdConfig().get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        return executorService.submit(() ->{
            authActionLock.lock();
            try {
                if (authResponse == null || authResponseExpiry == null || authResponseExpiry.minusMinutes(5).isBefore(now())) {
                    authResponse = authClient.login(MSBotAuthClient.DEFAULT_OPENID_AUTH_URL, credentialsProvider);
                    authResponseExpiry = now().plusSeconds(authResponse.expiresIn);
                }
            } finally {
                authActionLock.unlock();
            }
        });

    }

    private Future<?> checkOpenIdConfig() {
        return executorService.submit(() -> {
            openIdActionLock.lock();
            try {
                if (openIDConfiguration == null || openIdConfigCachedTime == null || openIdConfigCachedTime.plus(openIdConfigCacheDuration).isBefore(now())) {
                    MSBotAuthClient.OpenIdContainer openIdContainer = authClient.fetchOpenIDConfiguration();
                    this.openIDConfiguration = openIdContainer.openIDConfiguration;
                    this.openIDJWTKeyList = openIdContainer.keyList;
                    this.openIdConfigCachedTime = now();
                }
            } finally {
                openIdActionLock.unlock();
            }
        });
    }

    private static ExecutorService currentThreadExecutorService() {
        ThreadPoolExecutor.CallerRunsPolicy callerRunsPolicy = new ThreadPoolExecutor.CallerRunsPolicy();
        return new ThreadPoolExecutor(0, 1, 0L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), callerRunsPolicy) {
            @Override
            public void execute(Runnable command) {
                callerRunsPolicy.rejectedExecution(command, this);
            }
        };
    }

}
