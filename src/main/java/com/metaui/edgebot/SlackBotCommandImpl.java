package com.metaui.edgebot;

import com.metaui.edgebot.subcommands.*;
import com.slack.api.Slack;
import com.slack.api.model.Conversation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class SlackBotCommandImpl implements SlackBotCommand {
    private Map<String, SlackBotCommand> commands = new HashMap<>();

    public SlackBotCommandImpl(String slackBotName, Slack slack, SlackEngine engine, String token, Conversation homeChannel) {
        SlackBotContext context = new SlackBotContext(slackBotName, slack, engine, token, homeChannel);

        commands.put("exit", new ExitCommandImpl());
        commands.put("getprop", new GetPropertyCommandImpl());
        commands.put("listprops", new ListPropertiesCommandImpl());
        commands.put("listchannels", new ListChannelsCommandImpl());
        commands.put("listusers", new ListUsersCommandImpl());
    }

    @Override
    public String execute(SlackBotContext botContext, BotCommandContext commandContext) {
        if (commandContext.getCommandTokens().length > 0) {
            BotCommandContext subCommandContext = new BotCommandContext(commandContext.getUserName(),
                    Arrays.copyOfRange(commandContext.getCommandTokens(), 1, commandContext.getCommandTokens().length));

            for (Map.Entry<String, SlackBotCommand> command : commands.entrySet()) {
                if (command.getKey().equalsIgnoreCase(commandContext.getCommandTokens()[0])) {
                    return command.getValue().execute(botContext, subCommandContext);
                }
            }
            if (commandContext.getCommandTokens()[0].equalsIgnoreCase("help")) {
                StringBuilder out = new StringBuilder();
                for (String cmd : commands.keySet()) {
                    out.append(cmd).append("\n");
                }
                return "Available bot commands: \n" + out;
            }
        }

        return "Command missing or not recognized, \"/bot help\" for help";
    }
}
