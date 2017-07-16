package com.pack.colorchat;

public class User {

    private final String id;
    private final String name;
    private final Color color;

    public User(String id, String name, Color color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color.getId();
    }
}
