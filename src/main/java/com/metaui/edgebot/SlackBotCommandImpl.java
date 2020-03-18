package com.metaui.edgebot;

import com.metaui.edgebot.subcommands.GetPropertyCommandImpl;

public class SlackBotCommandImpl implements SlackCommandInterface {
    public static String PREFIX = "/bot";

    @Override
    public String execute(String userName, String text) {
        String[] split = text.split(" ");

        if (split.length > 0) {
            StringBuilder subCommandText = new StringBuilder();

            for (int i = 1; i < split.length; i++) {
                if (i > 1) {
                    subCommandText.append(' ');
                }
                subCommandText.append(split[i]);
            }

            switch (split[0]) {
                case GetPropertyCommandImpl.PREFIX:
                    return new GetPropertyCommandImpl().execute(userName, subCommandText.toString());
                default:
                    return "Command not recognized, \"/bot help\" for help";
            }
        }

        return ":wave: Hello " + userName + ", your command was: " + text;
    }
}
