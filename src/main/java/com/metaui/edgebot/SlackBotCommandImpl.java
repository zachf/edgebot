package com.metaui.edgebot;

import com.metaui.edgebot.subcommands.ExitCommandImpl;
import com.metaui.edgebot.subcommands.GetPropertyCommandImpl;
import com.metaui.edgebot.subcommands.ListPropertiesCommandImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlackBotCommandImpl implements SlackCommandInterface {
    public static String PREFIX = "/bot";

    private List<SlackCommandInterface> commands = new ArrayList<>();

    public SlackBotCommandImpl(SlackEngine engine) {
        commands.add(new ExitCommandImpl(engine));
        commands.add(new GetPropertyCommandImpl(engine));
        commands.add(new ListPropertiesCommandImpl(engine));
    }

    @Override
    public String execute(BotCommandContext commandContext) {
        if (commandContext.getCommandTokens().length > 0) {
            BotCommandContext subCommandContext = new BotCommandContext(commandContext.getUserName(),
                    Arrays.copyOfRange(commandContext.getCommandTokens(), 1, commandContext.getCommandTokens().length));

            for (SlackCommandInterface command : commands) {
                if (command.getPrefix().equals(commandContext.getCommandTokens()[0])) {
                    return command.execute(subCommandContext);
                }
            }
        }

        return "Command not recognized, \"/bot help\" for help";
    }

    @Override
    public String getPrefix() {
        return "/bot";
    }
}
