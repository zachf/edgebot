package com.metaui.edgebot;

public interface SlackBotCommand {
    String execute(SlackBotContext botContext, BotCommandContext botCommandContext);
}
