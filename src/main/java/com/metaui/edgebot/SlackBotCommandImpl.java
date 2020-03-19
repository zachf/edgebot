package com.metaui.edgebot;

import com.metaui.edgebot.subcommands.ExitCommandImpl;
import com.metaui.edgebot.subcommands.GetPropertyCommandImpl;
import com.metaui.edgebot.subcommands.ListPropertiesCommandImpl;

public class SlackBotCommandImpl implements SlackCommandInterface {
    public static String PREFIX = "/bot";

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

            switch (split[0]) {
                case GetPropertyCommandImpl.PREFIX:
                    return new GetPropertyCommandImpl().execute(subCommandContext);
                case ListPropertiesCommandImpl.PREFIX:
                    return new ListPropertiesCommandImpl().execute(subCommandContext);
                case ExitCommandImpl.PREFIX:
                    return new ExitCommandImpl().execute(subCommandContext);
            }
        }

        return "Command not recognized, \"/bot help\" for help";
    }
}
