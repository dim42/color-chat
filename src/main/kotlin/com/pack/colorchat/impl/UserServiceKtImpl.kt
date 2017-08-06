package com.pack.colorchat.impl

import com.pack.colorchat.model.Color
import com.pack.colorchat.model.User
import com.pack.colorchat.service.Constants.KOTLIN_USER_SERVICE
import com.pack.colorchat.service.UserService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.util.*
import java.util.Collections.synchronizedMap

@Qualifier(KOTLIN_USER_SERVICE)
@Component
class UserServiceKtImpl : UserService {

    private val users = synchronizedMap(HashMap<String, User>())

    override fun addUser(name: String, color: Color): String {
        val id = UUID.randomUUID().toString()
        synchronized(users) {
            users.values.filter { it.name == name }.any { throw RuntimeException(name + " user already exists!") }
            users.put(id, User(id, name, color))
        }
        return id
    }

    override fun getUsers(): Collection<User> = users.values

    override fun getUser(userId: String): User = users[userId]!!
}
