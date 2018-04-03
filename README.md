
# Chatterbox [![Build Status](https://travis-ci.org/Mustard/chatterbox.svg?branch=master)](https://travis-ci.org/Mustard/chatterbox)

Easily build chat bots for Slack, Facebook, Skype and other messaging platforms 

## Getting Started

Chatterbox has been built to work well with [Dropwizard](www.dropwizard.io) however each module is packaged as its own jar with minimal dependencies typically just the servlet api and can therefore be used in other environments   

To get started with Chatterbox and Dropwizard check out the `example` directory

## Supported Platforms

| Platform | Module | 
| -------- | -------|
| Slack    | `slack` |
| Skype    | `ms-bot` |
| Facebook | `ms-bot` |


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