package com.pack.colorchat.service

import com.pack.colorchat.model.Color
import com.pack.colorchat.model.User

interface UserService {
    fun addUser(name: String, color: Color): String
    fun getUsers(): Collection<User>
    fun getUser(userId: String): User
}
