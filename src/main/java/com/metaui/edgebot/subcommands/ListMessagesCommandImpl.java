package com.metaui.edgebot.subcommands;

import com.metaui.edgebot.BotCommandContext;
import com.metaui.edgebot.SlackBotCommand;
import com.metaui.edgebot.SlackBotContext;
import com.slack.api.model.Conversation;
import com.slack.api.model.Message;

public class ListMessagesCommandImpl implements SlackBotCommand {
    @Override
    public String execute(SlackBotContext botContext, BotCommandContext botCommandContext) {
        StringBuilder out = new StringBuilder();

        try {
            for (Conversation conversation : botContext.getQueryHelper().getChannels()) {
                if (conversation != null) {
                    if (conversation.getName().equals(botCommandContext.getCommandTokens()[0])) {
                        for (Message message : botContext.getQueryHelper().getMessages(conversation.getId())) {
                            if (message != null) {
                                out.append(message.getUsername()).append(": ").append(message.getText()).append('\n');
                            }
                        }
                        return out.toString();
                    }
                }
            }
            return "Conversation not found";
        } catch (Exception e) {
            out.append(e.getMessage());
            e.printStackTrace();
        }
        return out.toString();
    }
}
