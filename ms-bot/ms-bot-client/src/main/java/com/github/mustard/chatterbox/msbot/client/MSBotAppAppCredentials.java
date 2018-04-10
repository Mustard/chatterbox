package com.github.mustard.chatterbox.msbot.client;

public class MSBotAppAppCredentials implements MSBotAppCredentialsProvider {

    private final String appId;
    private final String appPassword;

    public MSBotAppAppCredentials(String appId, String appPassword) {
        this.appId = appId;
        this.appPassword = appPassword;
    }

    @Override
    public String getAppId() {
        return appId;
    }

    @Override
    public String getAppPassword() {
        return appPassword;
    }

}
