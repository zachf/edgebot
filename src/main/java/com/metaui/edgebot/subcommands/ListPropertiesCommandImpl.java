package com.metaui.edgebot.subcommands;

import com.metaui.edgebot.BotCommandContext;
import com.metaui.edgebot.SlackCommandInterface;
import com.metaui.edgebot.SlackEngine;

import java.util.Map;

public class ListPropertiesCommandImpl implements SlackCommandInterface {
    public static final String PREFIX = "listprops";

    public ListPropertiesCommandImpl(SlackEngine engine) {
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
