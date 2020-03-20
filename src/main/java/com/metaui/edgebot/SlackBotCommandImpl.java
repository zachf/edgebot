package com.metaui.edgebot;

import com.metaui.edgebot.subcommands.*;
import com.slack.api.Slack;
import com.slack.api.model.Conversation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlackBotCommandImpl implements SlackCommandInterface {
    public static String PREFIX = "/bot";

    private List<SlackCommandInterface> commands = new ArrayList<>();

    public SlackBotCommandImpl(Slack slack, SlackEngine engine, String token, Conversation homeChannel) {
        SlackBotContext context = new SlackBotContext(slack, engine, token, homeChannel);

        commands.add(new ExitCommandImpl(context));
        commands.add(new GetPropertyCommandImpl(context));
        commands.add(new ListPropertiesCommandImpl(context));
        commands.add(new ListChannelsCommandImpl(context));
        commands.add(new ListUsersCommandImpl(context));
    }

    @Override
    public String execute(BotCommandContext commandContext) {
        if (commandContext.getCommandTokens().length > 0) {
            BotCommandContext subCommandContext = new BotCommandContext(commandContext.getUserName(),
                    Arrays.copyOfRange(commandContext.getCommandTokens(), 1, commandContext.getCommandTokens().length));

            for (SlackCommandInterface command : commands) {
                if (command.getPrefix().equalsIgnoreCase(commandContext.getCommandTokens()[0])) {
                    return command.execute(subCommandContext);
                }
            }
            if (commandContext.getCommandTokens()[0].equalsIgnoreCase("help")) {
                StringBuilder out = new StringBuilder();
                for (SlackCommandInterface cmd : commands) {
                    out.append(cmd.getPrefix()).append("\n");
                }
                return "Available bot commands: \n" + out;
            }
        }

        return "Command missing or not recognized, \"/bot help\" for help";
    }

    @Override
    public String getPrefix() {
        return "/bot";
    }
}
