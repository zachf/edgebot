/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.metaui.edgebot;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.jetty.SlackAppServer;

public class EdgeBot {
    public static void main(String[] args) throws Exception {
        // App expects env variables (SLACK_BOT_TOKEN, SLACK_SIGNING_SECRET)
        AppConfig config = new AppConfig();
        config.setSingleTeamBotToken("xoxb-1002667100565-1005402728624-zCLqgHbIyiyNgZSP9Vl0PHiS");
        config.setSigningSecret("f0ffc785b30f02e795694f7ceef827b9");
        App app = new App(config);

        app.command("/bot", (req, ctx) -> {
            return ctx.ack(":wave: Hello!");
        });

        SlackAppServer server = new SlackAppServer(app);
        server.start(); // http://localhost:3000/slack/events
    }
}