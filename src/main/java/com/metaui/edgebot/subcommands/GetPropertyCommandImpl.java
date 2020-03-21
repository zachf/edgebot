package com.metaui.edgebot.subcommands;

import com.metaui.edgebot.BotCommandContext;
import com.metaui.edgebot.SlackBotCommand;
import com.metaui.edgebot.SlackBotContext;

public class GetPropertyCommandImpl implements SlackBotCommand {
    private SlackBotContext botContext;

    public GetPropertyCommandImpl(SlackBotContext botContext) {
        this.botContext = botContext;
    }

    @Override
    public String execute(BotCommandContext context) {
        return System.getProperty(context.getCommandTokens()[0]);
    }
}
