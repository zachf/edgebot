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
