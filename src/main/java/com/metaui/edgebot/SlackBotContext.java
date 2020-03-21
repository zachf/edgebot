package com.metaui.edgebot;

import com.slack.api.Slack;
import com.slack.api.model.Conversation;

public class SlackBotContext {
    private String slackBotName;
    private Slack slack;
    private SlackEngine engine;
    private String token;
    private Conversation homeChannel;

    public SlackBotContext(String slackBotName, Slack slack, SlackEngine engine, String token, Conversation homeChannel) {
        this.slackBotName = slackBotName;
        this.slack = slack;
        this.engine = engine;
        this.token = token;
        this.homeChannel = homeChannel;
    }

    public String getSlackBotName() {
        return slackBotName;
    }

    public Slack getSlack() {
        return slack;
    }

    public SlackEngine getEngine() {
        return engine;
    }

    public String getToken() {
        return token;
    }

    public Conversation getHomeChannel() {
        return homeChannel;
    }
}
