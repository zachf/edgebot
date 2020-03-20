package com.metaui.edgebot;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.conversations.ConversationsHistoryRequest;
import com.slack.api.methods.request.conversations.ConversationsInfoRequest;
import com.slack.api.methods.request.conversations.ConversationsListRequest;
import com.slack.api.methods.request.users.UsersListRequest;
import com.slack.api.methods.response.conversations.ConversationsHistoryResponse;
import com.slack.api.methods.response.conversations.ConversationsInfoResponse;
import com.slack.api.methods.response.conversations.ConversationsListResponse;
import com.slack.api.methods.response.users.UsersListResponse;
import com.slack.api.model.Conversation;
import com.slack.api.model.ConversationType;
import com.slack.api.model.Message;
import com.slack.api.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlackEngine implements Runnable {

    private final Slack slack;
    private String token;
    private List<String> allowedChannels;

    SlackEngine(Slack slack, String token, String ... channelIds) {
        this.slack = slack;
        this.token = token;
        this.allowedChannels = Arrays.asList(channelIds);
    }

    public void run() {
        try {
            getUsers();
            getChannels(); // calls getMessages as it loops
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public List<Conversation> getChannels() throws IOException, SlackApiException {
        boolean keepGoing = true;
        String nextCursor = null;

        List<Conversation> allChannels = new ArrayList<>();
        if (!allowedChannels.isEmpty()) {
            for (String channelId : allowedChannels) {
                ConversationsInfoRequest ciReq = ConversationsInfoRequest.builder().token(token).channel(channelId).build();
                ConversationsInfoResponse ciResp = slack.methods().conversationsInfo(ciReq);
                allChannels.add(ciResp.getChannel());
            }
        } else {
            ConversationsListRequest clReq = ConversationsListRequest.builder().token(token).types(Arrays.asList(ConversationType.PUBLIC_CHANNEL)).build();
            while (keepGoing) {
                clReq.setCursor(nextCursor);
                ConversationsListResponse clResp = slack.methods().conversationsList(clReq);
                System.out.println("Adding " + clResp.getChannels().size() + " channels");
                allChannels.addAll(clResp.getChannels());

                nextCursor = clResp.getResponseMetadata().getNextCursor();
                if (nextCursor == null || nextCursor.isEmpty()) keepGoing = false;
            }
        }
        System.out.println("Got " + allChannels.size() + " channels");
        // sort by name
        allChannels.sort((Conversation c1, Conversation c2) -> c1.getName().compareTo(c2.getName()));

        return allChannels;
    }

    public List<User> getUsers() throws IOException, SlackApiException {
        boolean keepGoing = true;
        String nextCursor = null;

        List<User> allUsers = new ArrayList<>();
        UsersListRequest uReq = UsersListRequest.builder().token(token).build();
        while (keepGoing) {
            uReq.setCursor(nextCursor);
            UsersListResponse uResp = slack.methods().usersList(uReq);

            System.out.println("Adding " + uResp.getMembers().size() + " users");
            allUsers.addAll(uResp.getMembers());

            nextCursor = uResp.getResponseMetadata() == null ? null : uResp.getResponseMetadata().getNextCursor();
            if (nextCursor == null || nextCursor.isEmpty()) keepGoing = false;
        }
        System.out.println("Got " + allUsers.size() + " users");
        // alphabetical sort
        allUsers.sort((User u1, User u2) -> u1.getName().compareTo(u2.getName()));

        return allUsers;
    }

    public List<Message> getMessages(String channel) throws IOException, SlackApiException {
        boolean keepGoing = true;
        String nextCursor = null;
        long loopCount = 0;

        List<Message> allMessages = new ArrayList<>();
        ConversationsHistoryRequest chReq = ConversationsHistoryRequest.builder().token(token).channel(channel).build();
        while (keepGoing) {
            chReq.setCursor(nextCursor);
            ConversationsHistoryResponse chResp = slack.methods().conversationsHistory(chReq);

            System.out.println("Adding "+ (++loopCount) + "-" + chResp.getMessages().size() + " messages");
            allMessages.addAll(chResp.getMessages());

            nextCursor = chResp.getResponseMetadata() == null ? null : chResp.getResponseMetadata().getNextCursor();
            if (nextCursor == null || nextCursor.isEmpty()) keepGoing = false;

            //if (allMessages.size() >= 500) keepGoing = false;
        }
        //if (allMessages.size() < 500) return;

        System.out.println("Got " + allMessages.size() + " messages for " + channel);
        // sort by timestamp
        allMessages.sort((Message m1, Message m2) -> m1.getTs().compareTo(m2.getTs()));

        return allMessages;
    }

    private void pauseFor(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ie) {
            // ignore this
        }
    }

}