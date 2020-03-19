package com.metaui.edgebot;

public class BotCommandContext {
    private String userName;
    private String subCommandText;

    public BotCommandContext(String userName, String subCommandText) {
        this.userName = userName;
        this.subCommandText = subCommandText;
    }

    public String getUserName() {
        return userName;
    }

    public String getSubCommandText() {
        return subCommandText;
    }
}
