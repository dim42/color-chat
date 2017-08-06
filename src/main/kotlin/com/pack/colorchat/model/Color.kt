package com.pack.colorchat.model

import java.util.*

enum class Color(val id: String, val text: String) {
    GREEN("green", "Green"),
    RED("red", "Red"),
    YELLOW("yellow", "Yellow"),
    BLUE("blue", "Blue"),
    BLACK("black", "Black");


    companion object {

        private val VALUES: Map<String, Color>

        init {
            val colors = HashMap<String, Color>()
            Arrays.stream(values()).forEach { colors.put(it.id, it) }
            VALUES = Collections.unmodifiableMap(colors)
        }

        fun of(color: String): Color = VALUES.getOrDefault(color, BLACK)
    }
}
