package com.pack.colorchat

import groovy.util.logging.Slf4j
import org.mockito.InjectMocks
import org.mockito.Spy
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.mockito.MockitoAnnotations.initMocks
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

@Slf4j
@ContextConfiguration(classes = [ColorChatApplication.class])
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ChatControllerSpec extends Specification {

    @InjectMocks
    def controller = new ChatController()
    @Spy
    UserService userService = new UserService()
    MockMvc mvc = standaloneSetup(controller).build()

    void setup() {
        log.info("Setup")
        initMocks(this)
    }

    def "Home"() {
        when:
        def response = mvc.perform(get("/")).andReturn().response

        then:
        response.status == OK.value()
        response.forwardedUrl == "index"
    }
}
