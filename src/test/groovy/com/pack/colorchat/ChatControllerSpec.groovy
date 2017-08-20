package com.pack.colorchat

import com.pack.colorchat.app.ColorChatApplication
import com.pack.colorchat.model.User
import com.pack.colorchat.service.ChatService
import com.pack.colorchat.service.UserService
import com.pack.colorchat.web.ChatControllerKt
import groovy.util.logging.Slf4j
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static com.pack.colorchat.model.Color.GREEN
import static com.pack.colorchat.service.Constants.*
import static java.util.Collections.emptyList
import static java.util.Collections.singletonList
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

//@CompileStatic
@Slf4j
@ContextConfiguration(classes = [ColorChatApplication.class])
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ChatControllerSpec extends Specification {

    def TEST_USER_ID = "12"
    def controller
    MockMvc mvc

    void setup() {
        log.info("Setup")
        UserService userService = Spy(UserService)
        ChatService chatService = Spy(ChatService)
        def user = new User(TEST_USER_ID, "name1", GREEN)
        userService.getUser(TEST_USER_ID) >> user
        userService.getUsers() >> singletonList(user)
        chatService.getMessages() >> emptyList()
//        controller = new ChatControllerJava(null, null, userService, chatService)
        controller = new ChatControllerKt("", true, userService, chatService)
        mvc = standaloneSetup(controller).build()
    }

    def "Home"() {
        when:
        def response = mvc.perform(get("/")).andReturn().response

        then:
        response.status == OK.value()
        response.forwardedUrl == INDEX_VIEW
    }

    def "Chat"() {
        when:
        def response = mvc.perform(get(CHAT_PATH).sessionAttr(USER_ID_SESS_ATTR, TEST_USER_ID)).andReturn().response

        then:
        response.status == OK.value()
        response.forwardedUrl == CHAT_VIEW
    }
}
