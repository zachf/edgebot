package com.metaui.edgebot.subcommands;

import com.metaui.edgebot.BotCommandContext;
import com.metaui.edgebot.SlackCommandInterface;

public class ExitCommandImpl implements SlackCommandInterface {
    public static final String PREFIX = "exit";

    @Override
    public String execute(BotCommandContext context) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    //ignore
                }
                System.exit(0);
            }
        }.start();
        return "Bot is exiting";
    }
}
