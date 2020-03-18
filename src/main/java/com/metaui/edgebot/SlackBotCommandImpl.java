package com.metaui.edgebot;

public class SlackBotCommandImpl implements SlackCommandInterface {
    public static String PREFIX = "/bot";

    @Override
    public String execute(String userName, String text) {
        return ":wave: Hello " + userName + ", your command was: " + text;
    }
}
