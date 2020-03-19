package com.metaui.edgebot.subcommands;

import com.metaui.edgebot.BotCommandContext;
import com.metaui.edgebot.SlackCommandInterface;

public class ExitCommandImpl implements SlackCommandInterface {
    public static final String PREFIX = "exit";

    @Override
    public String execute(BotCommandContext context) {
        System.exit(0);
        return "Bot is exiting";
    }
}
