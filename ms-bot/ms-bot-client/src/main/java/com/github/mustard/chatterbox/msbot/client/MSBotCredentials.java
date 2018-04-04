package com.github.mustard.chatterbox.msbot.client;

public class MSBotCredentials implements MSBotCredentialsProvider {

    private final String appId;
    private final String appPassword;

    public MSBotCredentials(String appId, String appPassword) {
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
