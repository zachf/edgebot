package com.metaui.edgebot.subcommands;

import com.metaui.edgebot.BotCommandContext;
import com.metaui.edgebot.SlackBotCommand;
import com.metaui.edgebot.SlackBotContext;
import com.slack.api.model.User;

public class ListUsersCommandImpl implements SlackBotCommand {
    private final SlackBotContext botContext;

    public ListUsersCommandImpl(SlackBotContext botContext) {
        this.botContext = botContext;
    }

    @Override
    public String execute(BotCommandContext context) {
        StringBuilder out = new StringBuilder();

        try {
            for (User user : botContext.getEngine().getUsers()) {
                if (user != null) {
                    out.append(user.getId()).append(": ").append(user.getName()).append('\n');
                }
            }
        } catch (Exception e) {
            out.append(e.getMessage());
            e.printStackTrace();
        }
        return out.toString();
    }
}
