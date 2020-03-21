package com.metaui.edgebot;

import com.slack.api.methods.SlackApiException;
import com.slack.api.model.Conversation;
import com.slack.api.model.Message;
import com.slack.api.model.User;

import java.io.IOException;
import java.util.List;

public interface SlackQueryHelper {
    List<Conversation> getChannels() throws IOException, SlackApiException;

    List<User> getUsers() throws IOException, SlackApiException;

    List<Message> getMessages(String channel) throws IOException, SlackApiException;
}
