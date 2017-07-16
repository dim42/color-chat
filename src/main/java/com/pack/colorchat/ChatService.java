package com.pack.colorchat;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ChatService {

    private List<Message> messages = Collections.synchronizedList(new ArrayList<>());

    public void addMessage(User user, String text) {
        messages.add(new Message(user, text));
    }

    public List<Message> getMessages() {
        return messages;
    }
}
