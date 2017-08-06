package com.pack.colorchat.impl

import com.pack.colorchat.model.Message
import com.pack.colorchat.model.User
import com.pack.colorchat.service.ChatService
import com.pack.colorchat.service.KOTLIN_CHAT_SERVICE
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.util.*

@Qualifier(KOTLIN_CHAT_SERVICE)
@Component
open class ChatServiceKtImpl : ChatService {
    private val messages: MutableList<Message> = Collections.synchronizedList(ArrayList())

    override fun addMessage(user: User, text: String) {
        messages.add(Message(user, text))
    }

    override fun getMessages(): List<Message> = messages
}

