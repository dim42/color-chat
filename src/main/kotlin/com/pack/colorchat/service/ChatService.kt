package com.pack.colorchat.service

import com.pack.colorchat.model.Message
import com.pack.colorchat.model.User

const val KOTLIN_CHAT_SERVICE: String = "Kotlin chat service"

interface ChatService {
    fun addMessage(user: User, text: String)
    fun getMessages(): List<Message>
}
