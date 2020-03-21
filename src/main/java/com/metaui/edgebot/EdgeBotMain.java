/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.metaui.edgebot;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.jetty.SlackAppServer;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.conversations.ConversationsJoinRequest;
import com.slack.api.methods.response.conversations.ConversationsJoinResponse;
import com.slack.api.model.Conversation;
import com.slack.api.util.http.SlackHttpClient;

import java.util.Date;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EdgeBotMain {
    public static void main(String[] args) throws Exception {
        // App expects env variables (SLACK_BOT_TOKEN, SLACK_SIGNING_SECRET)
        System.setProperty("org.eclipse.jetty.LEVEL","DEBUG");

        String botToken = System.getProperty("SLACK_BOT_TOKEN");
        String signingSecret = System.getProperty("SLACK_SIGNING_SECRET");
        String webhookUrl = System.getProperty("SLACK_WEBHOOK_URL");

        Properties properties = new Properties();
        properties.load(EdgeBotMain.class.getResourceAsStream("/edgebot.properties"));

        String slackBotName = properties.getProperty("slackbot.name");
        String slackBotChannel = properties.getProperty("slackbot.channel");

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
        slackConfig.setProxyUrl(webhookUrl);
        SlackHttpClient client = new SlackHttpClient();
        client.setConfig(slackConfig);
        Slack slack = Slack.getInstance(client);
        SlackEngine engine = new SlackEngine(slack, botToken);

        Conversation homeChannel = null;

        for (Conversation conversation : engine.getChannels()) {
            if (conversation != null) {
                System.out.println(conversation.getId() + ": " + conversation.getName());

                if (conversation.getName().equals(slackBotChannel)) {
                    ConversationsJoinResponse response = slack.methods().conversationsJoin(
                            ConversationsJoinRequest.builder().token(botToken).channel(conversation.getId()).build());
                    System.out.println(response);

                    slack.methods().chatPostMessage(ChatPostMessageRequest.builder().token(botToken).
                            channel(conversation.getId()).text(slackBotName + " is starting up, the time is " + new Date()).build());

                    homeChannel = conversation;
                }
            }
        }

        final SlackBotCommand botCommand = SlackBotCommand.newInstance(slack, engine, botToken, homeChannel);
        app.command("/bot", (req, ctx) -> {
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