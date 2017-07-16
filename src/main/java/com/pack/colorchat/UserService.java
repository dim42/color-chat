package com.pack.colorchat;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class UserService {

    private Map<String, User> users = Collections.synchronizedMap(new HashMap<String, User>());

    public String addUser(String name, Color color) {
        synchronized (users) {
            users.values().stream().filter(user -> user.getName().equals(name)).findAny().ifPresent(user -> {
                throw new RuntimeException(name + " user already exists!");
            });
            String id = UUID.randomUUID().toString();
            users.put(id, new User(id, name, color));
            return id;
        }
    }

    public Collection<User> getUsers() {
        return users.values();
    }

    public User getUser(String userId) {
        return users.get(userId);
    }
}
