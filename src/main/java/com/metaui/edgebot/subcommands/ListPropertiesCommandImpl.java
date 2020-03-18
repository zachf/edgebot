package com.metaui.edgebot.subcommands;

import com.metaui.edgebot.SlackCommandInterface;

import java.util.Map;

public class ListPropertiesCommandImpl implements SlackCommandInterface {
    public static final String PREFIX = "listprops";

    @Override
    public String execute(String userName, String command) {
        StringBuilder out = new StringBuilder();
        for (String propName : System.getProperties().stringPropertyNames()) {
            out.append(propName).append(" ");
        }
        return out.toString();
    }
}
