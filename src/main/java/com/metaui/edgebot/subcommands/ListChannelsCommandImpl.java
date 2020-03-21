package com.metaui.edgebot.subcommands;

import com.metaui.edgebot.BotCommandContext;
import com.metaui.edgebot.SlackBotCommand;
import com.metaui.edgebot.SlackBotContext;
import com.slack.api.model.Conversation;

public class ListChannelsCommandImpl implements SlackBotCommand {
    private SlackBotContext botContext;

    public ListChannelsCommandImpl(SlackBotContext botContext) {
        this.botContext = botContext;
    }

    @Override
    public String execute(BotCommandContext context) {
        StringBuilder out = new StringBuilder();

        try {
            for (Conversation conversation : botContext.getEngine().getChannels()) {
                if (conversation != null) {
                    out.append(conversation.getId()).append(": ").append(conversation.getName()).append('\n');
                }
            }
        } catch (Exception e) {
            out.append(e.getMessage());
            e.printStackTrace();
        }
        return out.toString();
    }
}
