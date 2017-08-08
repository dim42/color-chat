package com.pack.colorchat.rest

import com.pack.colorchat.model.Color.*
import com.pack.colorchat.service.ChatService
import com.pack.colorchat.service.Constants.ADD_MESSAGE_PATH
import com.pack.colorchat.service.Constants.ADD_USER_PATH
import com.pack.colorchat.service.Constants.CHAT_PATH
import com.pack.colorchat.service.Constants.CHAT_VIEW
import com.pack.colorchat.service.Constants.INDEX_VIEW
import com.pack.colorchat.service.Constants.KOTLIN_USER_SERVICE
import com.pack.colorchat.service.Constants.USER_ID_PARAM
import com.pack.colorchat.service.Constants.USER_ID_SESS_ATTR
import com.pack.colorchat.service.KOTLIN_CHAT_SERVICE
import com.pack.colorchat.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.POST
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.SessionAttribute
import javax.annotation.PostConstruct
import javax.servlet.http.HttpSession

@Controller
class ChatControllerKt @Autowired constructor(@Value("\${application.message:Welcome!}")
                                              private val message: String,
                                              @Value("\${test.chat:true}")
                                              private val testChat: Boolean,
                                              @Qualifier(KOTLIN_USER_SERVICE)
                                              val userService: UserService,
                                              @Qualifier(KOTLIN_CHAT_SERVICE)
                                              val chatService: ChatService) {

    @PostConstruct
    fun init() {
        if (testChat) {
            val id1 = userService.addUser("test user1", GREEN)
            val id2 = userService.addUser("test user2", RED)
            chatService.addMessage(userService.getUser(id1), "t1 fdsf dsfvse<br/>2fsdf")
            chatService.addMessage(userService.getUser(id2), "test2 fvdsd")
        }
    }

    @RequestMapping("/")
    fun home(map: MutableMap<String, Any>): String {
        map.put("message", message)
        map.put("colors", values())
        return INDEX_VIEW
    }

    @RequestMapping("/" + ADD_USER_PATH)
    fun addUser(@RequestParam("name") name: String, @RequestParam("color") color: String, httpSession: HttpSession): String {
        checkUser(name)
        val id = userService.addUser(name, Companion.of(color))
        httpSession.setAttribute(USER_ID_SESS_ATTR, id)
        return "redirect:" + CHAT_PATH
    }

    @RequestMapping("/" + CHAT_PATH)
    fun chat(map: MutableMap<String, Any>, @SessionAttribute(name = USER_ID_SESS_ATTR, required = false) userId: String): String {
        checkUser(userId)
        map.put("user", userService.getUser(userId))
        map.put("users", userService.getUsers())
        map.put("messages", chatService.getMessages())
        return CHAT_VIEW
    }

    @RequestMapping(path = arrayOf("/" + ADD_MESSAGE_PATH), method = arrayOf(POST))
    fun addMessage(@RequestParam(USER_ID_PARAM) userId: String, @RequestParam("message") message: String): String {
        checkUser(userId)
        chatService.addMessage(userService.getUser(userId), message)
        return "redirect:" + CHAT_PATH
    }

    private fun checkUser(userId: String?) {
        if (userId == null || userId.trim { it <= ' ' }.isEmpty()) {
            throw IllegalArgumentException("User is empty!")
        }
    }
}
