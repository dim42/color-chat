package com.pack.colorchat.web

import com.pack.colorchat.model.Color.Companion
import com.pack.colorchat.model.Color.GREEN
import com.pack.colorchat.model.Color.RED
import com.pack.colorchat.model.Color.values
import com.pack.colorchat.service.ChatService
import com.pack.colorchat.service.Constants.ADD_MESSAGE_PATH
import com.pack.colorchat.service.Constants.ADD_USER_PATH
import com.pack.colorchat.service.Constants.CHAT_PATH
import com.pack.colorchat.service.Constants.CHAT_VIEW
import com.pack.colorchat.service.Constants.COLOR_PARAM
import com.pack.colorchat.service.Constants.INDEX_VIEW
import com.pack.colorchat.service.Constants.KOTLIN_USER_SERVICE
import com.pack.colorchat.service.Constants.USER_ID_PARAM
import com.pack.colorchat.service.Constants.USER_ID_SESS_ATTR
import com.pack.colorchat.service.Constants.USER_NAME_PARAM
import com.pack.colorchat.service.KOTLIN_CHAT_SERVICE
import com.pack.colorchat.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.SessionAttribute
import javax.annotation.PostConstruct
import javax.servlet.http.HttpSession

@Controller
class ChatControllerKt @Autowired constructor(@Value("\${application.message:Welcome!}")
                                              val message: String,
                                              @Value("\${test.chat.kt:true}")
                                              val testChat: Boolean,
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

    @GetMapping("/")
    fun home(map: MutableMap<String, Any>): String {
        map.put("message", message)
        map.put("colors", values())
        return INDEX_VIEW
    }

    @PostMapping(ADD_USER_PATH)
    fun addUser(@RequestParam(USER_NAME_PARAM) name: String, @RequestParam(COLOR_PARAM) color: String, httpSession: HttpSession): String {
        checkUser(name)
        val id = userService.addUser(name, Companion.of(color))
        httpSession.setAttribute(USER_ID_SESS_ATTR, id)
        return "redirect:" + CHAT_PATH
    }

    @GetMapping(CHAT_PATH)
    fun chat(map: MutableMap<String, Any>, @SessionAttribute(name = USER_ID_SESS_ATTR, required = false) userId: String): String {
        checkUser(userId)
        map.put("user", userService.getUser(userId))
        map.put("users", userService.getUsers())
        map.put("messages", chatService.getMessages())
        return CHAT_VIEW
    }

    @PostMapping(ADD_MESSAGE_PATH)
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
