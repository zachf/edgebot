package com.metaui.edgebot.subcommands;

import com.metaui.edgebot.BotCommandContext;
import com.metaui.edgebot.SlackBotCommand;
import com.metaui.edgebot.SlackBotContext;
import com.slack.api.model.Conversation;

public class ListChannelsCommandImpl implements SlackBotCommand {
    @Override
    public String execute(SlackBotContext botContext, BotCommandContext botCommandContext) {
        StringBuilder out = new StringBuilder();

        try {
            for (Conversation conversation : botContext.getQueryHelper().getChannels()) {
                if (conversation != null) {
                    out.append("- ").append(conversation.getId()).append(": ").append(conversation.getName()).append('\n');
                }
            }
        } catch (Exception e) {
            out.append(e.getMessage());
            e.printStackTrace();
        }
        return out.toString();
    }
}
