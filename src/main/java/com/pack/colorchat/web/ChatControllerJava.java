package com.pack.colorchat.web;

import com.pack.colorchat.model.Color;
import com.pack.colorchat.service.ChatService;
import com.pack.colorchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.pack.colorchat.model.Color.*;
import static com.pack.colorchat.service.ChatServiceKt.KOTLIN_CHAT_SERVICE;
import static com.pack.colorchat.service.Constants.*;

//@Controller
public class ChatControllerJava {

    private final String message;
    private final Boolean testChat;
    private final UserService userService;
    private final ChatService chatService;

    @Autowired
    public ChatControllerJava(@Value("${application.message:Welcome!}") String message, @Value("${test.chat.java:true}") Boolean testChat,
                              @Qualifier(KOTLIN_USER_SERVICE) UserService userService, @Qualifier(KOTLIN_CHAT_SERVICE) ChatService chatService) {
        this.message = message;
        this.testChat = testChat;
        this.userService = userService;
        this.chatService = chatService;
    }

    @PostConstruct
    public void init() {
        if (testChat) {
            String id1 = userService.addUser("test user1", GREEN);
            String id2 = userService.addUser("test user2", RED);
            chatService.addMessage(userService.getUser(id1), "t1 fdsf dsfvse<br/>2fsdf");
            chatService.addMessage(userService.getUser(id2), "test2 fvdsd");
        }
    }

    @RequestMapping("/")
    public String home(Map<String, Object> map) {
        map.put("message", message);
        map.put("colors", Color.values());
        return INDEX_VIEW;
    }

    @PostMapping(ADD_USER_PATH)
    public String addUser(@RequestParam(USER_NAME_PARAM) String name, @RequestParam(COLOR_PARAM) String color, HttpSession httpSession) {
        checkUser(name);
        String id = userService.addUser(name, Companion.of(color));
        httpSession.setAttribute(USER_ID_SESS_ATTR, id);
        return "redirect:" + CHAT_PATH;
    }

    @GetMapping(CHAT_PATH)
    public String chat(Map<String, Object> map, @SessionAttribute(name = USER_ID_SESS_ATTR, required = false) String userId) {
        checkUser(userId);
        map.put("user", userService.getUser(userId));
        map.put("users", userService.getUsers());
        map.put("messages", chatService.getMessages());
        return CHAT_VIEW;
    }

    @PostMapping(ADD_MESSAGE_PATH)
    public String addMessage(@RequestParam(USER_ID_PARAM) String userId, @RequestParam("message") String message) {
        checkUser(userId);
        chatService.addMessage(userService.getUser(userId), message);
        return "redirect:" + CHAT_PATH;
    }

    private void checkUser(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User is empty!");
        }
    }
}
