package com.metaui.edgebot.subcommands;

import com.metaui.edgebot.BotCommandContext;
import com.metaui.edgebot.SlackBotCommand;
import com.metaui.edgebot.SlackBotContext;

public class ListPropertiesCommandImpl implements SlackBotCommand {
    private final SlackBotContext botContext;

    public ListPropertiesCommandImpl (SlackBotContext botContext) {
        this.botContext = botContext;
    }

    @Override
    public String execute(BotCommandContext context) {
        StringBuilder out = new StringBuilder();
        for (String propName : System.getProperties().stringPropertyNames()) {
            out.append("- ").append(propName).append('\n');
        }
        return out.toString();
    }
}
