package com.metaui.edgebot;

public class BotCommandContext {
    private String userName;
    private String[] commandTokens;

    public BotCommandContext(String userName, String[] commandTokens) {
        this.userName = userName;
        this.commandTokens = commandTokens;
    }

    public String getUserName() {
        return userName;
    }

    public String[] getCommandTokens() {
        return commandTokens;
    }
}
