package com.metaui.edgebot;

import com.slack.api.Slack;
import com.slack.api.model.Conversation;

public interface SlackBotCommand {
    String execute(BotCommandContext context);

    static SlackBotCommand newInstance(String slackBotName, Slack slack, SlackEngine engine, String token, Conversation homeChannel) {
        return new SlackBotCommandImpl(slackBotName, slack, engine, token, homeChannel);
    }
}
