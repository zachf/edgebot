package com.metaui.edgebot;

import com.slack.api.Slack;
import com.slack.api.model.Conversation;

public class SlackBotContext {
    private final String slackBotName;
    private final Slack slack;
    private final SlackQueryHelper queryHelper;
    private final String token;
    private final Conversation homeChannel;

    public SlackBotContext(String slackBotName, Slack slack, SlackQueryHelper queryHelper, String token, Conversation homeChannel) {
        this.slackBotName = slackBotName;
        this.slack = slack;
        this.queryHelper = queryHelper;
        this.token = token;
        this.homeChannel = homeChannel;
    }

    public String getSlackBotName() {
        return slackBotName;
    }

    public Slack getSlack() {
        return slack;
    }

    public SlackQueryHelper getQueryHelper() {
        return queryHelper;
    }

    public String getToken() {
        return token;
    }

    public Conversation getHomeChannel() {
        return homeChannel;
    }
}
