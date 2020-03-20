package com.metaui.edgebot.subcommands;

import com.metaui.edgebot.BotCommandContext;
import com.metaui.edgebot.SlackCommandInterface;
import com.metaui.edgebot.SlackEngine;
import com.slack.api.model.Conversation;

import java.io.StringWriter;

public class ListChannelsCommandImpl implements SlackCommandInterface {
    public static final String PREFIX = "listchannels";
    private SlackEngine engine;

    public ListChannelsCommandImpl(SlackEngine engine) {
        this.engine = engine;
    }

    @Override
    public String execute(BotCommandContext context) {
        StringBuilder out = new StringBuilder();

        try {
            for (Conversation conversation : engine.getChannels()) {
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

    @Override
    public String getPrefix() {
        return PREFIX;
    }

}
