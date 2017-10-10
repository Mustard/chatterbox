
# chatterbox

Easily build chat bots for Slack and other messaging platforms (coming soon) 

[![Build Status](https://travis-ci.org/Mustard/chatterbox.svg?branch=master)](https://travis-ci.org/Mustard/chatterbox)

## Slack

#### Modules

`slack-domain` 
Domain objects (DTOs) for Slack

`slack-webhook` 
Servlet to recieve Slack HTTP events (what slack calles outgoing webhooks)

`slack-dropwizard` 
Dropwizard bundle to register all Slack servlets



## MS Bot Framework

The [MS Bot Framework](https://dev.botframework.com/) supports many chat platforms 
what it calls [channels](https://docs.microsoft.com/en-us/bot-framework/portal-configure-channels)


Register here

https://dev.botframework.com/bots/new

Choose existing bot

#### Modules

`ms-bot-domain`
Domain objects (DTOs) for the MS Bot Framework

`ms-bot-webhook`
Servlet to recieve MS Bot Framework events