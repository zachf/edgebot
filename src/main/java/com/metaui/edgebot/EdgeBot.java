/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.metaui.edgebot;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.jetty.SlackAppServer;
import com.slack.api.model.Conversation;
import com.slack.api.util.http.SlackHttpClient;
import okhttp3.OkHttpClient;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EdgeBot {
    public static void main(String[] args) throws Exception {
        // App expects env variables (SLACK_BOT_TOKEN, SLACK_SIGNING_SECRET)
        System.setProperty("org.eclipse.jetty.LEVEL","DEBUG");

        String botToken = System.getProperty("SLACK_BOT_TOKEN");
        String signingSecret = System.getProperty("SLACK_SIGNING_SECRET");

        if (botToken == null || signingSecret == null) {
            System.err.println("Missing token or secret");
            System.exit(1);
        }

        Handler fh = new FileHandler("/tmp/edgebot.log");
        Logger.getLogger("").addHandler(fh);
        Logger.getLogger("com.metaui").setLevel(Level.FINEST);

        AppConfig config = new AppConfig();
        config.setSingleTeamBotToken(botToken);
        config.setSigningSecret(signingSecret);
        App app = new App(config);

        SlackConfig slackConfig = new SlackConfig();
        slackConfig.setProxyUrl("https://hooks.slack.com/services/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");
        SlackHttpClient client = new SlackHttpClient();
        client.setConfig(slackConfig);
        Slack slack = Slack.getInstance(client);
        SlackEngine engine = new SlackEngine(slack,botToken, "edgebot-test");
        engine.getChannels();

        for (Conversation conversation : engine.getChannels()) {
            System.out.println(conversation.getId() + ": " + conversation.getName());
        }

        final SlackBotCommandImpl botCommand = new SlackBotCommandImpl(engine);
        app.command(SlackBotCommandImpl.PREFIX, (req, ctx) -> {
            System.out.println(req);
            return ctx.ack(botCommand.execute(new BotCommandContext(req.getPayload().getUserName(),
                    req.getPayload().getText().split(" "))));
        });

//        SlackHttpClient client = new SlackHttpClient();
//        Response response = client.postJsonBody("", ConversationsListRequest.builder().token("token").cursor("test")
//                .excludeArchived(true).build().toString());
//
//        System.out.println(response);


        SlackAppServer server = new SlackAppServer(app);
        server.start(); // http://localhost:3000/slack/events
    }
}
