package com.pack.colorchat.impl;

import com.pack.colorchat.model.Color;
import com.pack.colorchat.model.User;
import com.pack.colorchat.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class UserServiceJavaImpl implements UserService {

    private final Map<String, User> users = Collections.synchronizedMap(new HashMap<String, User>());

    @Override
    public String addUser(String name, Color color) {
        String id = UUID.randomUUID().toString();
        synchronized (users) {
            users.values().stream().filter(user -> user.getName().equals(name)).findAny().ifPresent(user -> {
                throw new RuntimeException(name + " user already exists!");
            });
            users.put(id, new User(id, name, color));
        }
        return id;
    }

    @Override
    public Collection<User> getUsers() {
        return users.values();
    }

    @Override
    public User getUser(String userId) {
        return users.get(userId);
    }
}
