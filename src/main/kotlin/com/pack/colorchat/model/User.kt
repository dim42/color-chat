package com.pack.colorchat.model

class User(val id: String, val name: String, private val color: Color) {

    fun getColor(): String = color.id
}
