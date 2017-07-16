package com.pack.colorchat;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Color {
    GREEN("green", "Green"),
    RED("red", "Red"),
    YELLOW("yellow", "Yellow"),
    BLUE("blue", "Blue"),
    BLACK("black", "Black");

    private static final Map<String, Color> VALUES;

    static {
        Map<String, Color> values = new HashMap<>();
        Arrays.stream(values()).forEach(color -> values.put(color.id, color));
        VALUES = Collections.unmodifiableMap(values);
    }

    private final String id;
    private final String text;

    Color(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public static Color of(String color) {
        return VALUES.getOrDefault(color, BLACK);
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
