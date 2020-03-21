package com.metaui.edgebot.subcommands;

import com.metaui.edgebot.BotCommandContext;
import com.metaui.edgebot.SlackBotCommand;
import com.metaui.edgebot.SlackBotContext;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;

import java.io.IOException;
import java.util.Date;

public class ExitCommandImpl implements SlackBotCommand {
    private SlackBotContext botContext;

    public ExitCommandImpl(SlackBotContext botContext) {
        this.botContext = botContext;
    }

    @Override
    public String execute(BotCommandContext commandContext) {
        new Thread(() -> {
            try {
                botContext.getSlack().methods().chatPostMessage(ChatPostMessageRequest.builder().token(botContext.getToken()).
                        channel(botContext.getHomeChannel().getId()).text(botContext.getSlackBotName() + " is exiting, the time is " + new Date()).build());

                Thread.sleep(1000);
                System.exit(0);
            } catch (InterruptedException | SlackApiException | IOException e) {
                e.printStackTrace();
            }
        }).start();
        return "Bot is exiting";
    }
}
