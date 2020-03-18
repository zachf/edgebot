package com.metaui.edgebot.subcommands;

import com.metaui.edgebot.SlackCommandInterface;

public class GetPropertyCommandImpl implements SlackCommandInterface {
    public static final String PREFIX = "getprop";

    @Override
    public String execute(String userName, String command) {
        return System.getProperty(command);
    }
}
