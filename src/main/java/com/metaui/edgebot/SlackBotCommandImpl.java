package com.metaui.edgebot;

import com.metaui.edgebot.subcommands.ExitCommandImpl;
import com.metaui.edgebot.subcommands.GetPropertyCommandImpl;
import com.metaui.edgebot.subcommands.ListPropertiesCommandImpl;

import java.util.ArrayList;
import java.util.List;

public class SlackBotCommandImpl implements SlackCommandInterface {
    public static String PREFIX = "/bot";

    private List<SlackCommandInterface> commands = new ArrayList<>();

    public SlackBotCommandImpl() {
        commands.add(new ExitCommandImpl());
        commands.add(new GetPropertyCommandImpl());
        commands.add(new ListPropertiesCommandImpl());
    }

    @Override
    public String execute(BotCommandContext commandContext) {
        String[] split = commandContext.getSubCommandText().split(" ");

        if (split.length > 0) {
            StringBuilder subCommandText = new StringBuilder();

            for (int i = 1; i < split.length; i++) {
                if (i > 1) {
                    subCommandText.append(' ');
                }
                subCommandText.append(split[i]);
            }

            BotCommandContext subCommandContext = new BotCommandContext(commandContext.getUserName(), subCommandText.toString());

            for (SlackCommandInterface commandInterface : commands) {
                if (commandInterface.getPrefix().equals(split[1])) {
                    commandInterface.execute(subCommandContext);
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
