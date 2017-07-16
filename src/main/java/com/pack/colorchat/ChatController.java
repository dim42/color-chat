package com.pack.colorchat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.pack.colorchat.Color.*;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@SessionAttributes("user")
public class ChatController {

    private static final String CHAT = "chat";
    private static final String CHAT_VIEW = "chat-view";
    private static final String INDEX = "index";

    @Value("${application.message:Welcome!}")
    private String message;
    @Value("${test.chat:true}")
    private Boolean testChat;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;

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
    public String home(Map map, HttpSession httpSession) {
        map.put("message", message);
        map.put("colors", Color.values());
        return INDEX;
    }

    @RequestMapping("/add-user")
    public String addUser(@RequestParam("name") String name, @RequestParam("color") String color, HttpSession httpSession) {
        checkUser(name);
        String id = userService.addUser(name, of(color));
        httpSession.setAttribute("user_id", id);
        return "redirect:" + CHAT;
    }

    @RequestMapping("/" + CHAT)
    public String chat(Map map, @SessionAttribute("user_id") String userId) {
        checkUser(userId);
        map.put("user", userService.getUser(userId));
        map.put("users", userService.getUsers());
        map.put("messages", chatService.getMessages());
        return CHAT_VIEW;
    }

    @RequestMapping(value = "/add-message", method = POST)
    public String addMessage(Map map, @RequestParam("user-id") String userId, @RequestParam("message") String message) {
        checkUser(userId);
        chatService.addMessage(userService.getUser(userId), message);
        return "redirect:" + CHAT;
    }

    private void checkUser(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User is empty!");
        }
    }
}
