package com.metaui.edgebot.subcommands;

import com.metaui.edgebot.BotCommandContext;
import com.metaui.edgebot.SlackCommandInterface;

public class ExitCommandImpl implements SlackCommandInterface {
    public static final String PREFIX = "exit";

    @Override
    public String execute(BotCommandContext context) {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                System.exit(0);
            } catch (InterruptedException e) {
                //ignore
            }
        }).start();
        return "Bot is exiting";
    }

    @Override
    public String getPrefix() {
        return PREFIX;
    }
}
