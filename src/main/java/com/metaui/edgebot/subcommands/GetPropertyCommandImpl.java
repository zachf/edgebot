package com.metaui.edgebot.subcommands;

import com.metaui.edgebot.BotCommandContext;
import com.metaui.edgebot.SlackCommandInterface;

public class GetPropertyCommandImpl implements SlackCommandInterface {
    public static final String PREFIX = "getprop";

    @Override
    public String execute(BotCommandContext context) {
        return System.getProperty(context.getCommandTokens()[0]);
    }

    @Override
    public String getPrefix() {
        return PREFIX;
    }

}
