package com.metaui.edgebot.subcommands;

import com.metaui.edgebot.BotCommandContext;
import com.metaui.edgebot.SlackBotContext;
import com.metaui.edgebot.SlackCommandInterface;

public class ListPropertiesCommandImpl implements SlackCommandInterface {
    public static final String PREFIX = "listprops";
    private final SlackBotContext botContext;

    public ListPropertiesCommandImpl (SlackBotContext botContext) {
        this.botContext = botContext;
    }

    @Override
    public String execute(BotCommandContext context) {
        StringBuilder out = new StringBuilder();
        for (String propName : System.getProperties().stringPropertyNames()) {
            out.append(propName).append('\n');
        }
        return out.toString();
    }

    @Override
    public String getPrefix() {
        return PREFIX;
    }

}
