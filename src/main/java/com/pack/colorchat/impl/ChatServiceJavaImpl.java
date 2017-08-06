package com.pack.colorchat.impl;

import com.pack.colorchat.service.ChatService;
import com.pack.colorchat.model.Message;
import com.pack.colorchat.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ChatServiceJavaImpl implements ChatService {

    private List<Message> messages = Collections.synchronizedList(new ArrayList<>());

    public void addMessage(User user, String text) {
        messages.add(new Message(user, text));
    }

    public List<Message> getMessages() {
        return messages;
    }
}
