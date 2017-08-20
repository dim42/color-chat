package com.pack.colorchat;

import com.pack.colorchat.app.ColorChatApplication;
import com.pack.colorchat.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.pack.colorchat.model.Color.YELLOW;
import static com.pack.colorchat.service.Constants.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ColorChatApplication.class)
@AutoConfigureMockMvc
public class ColorChatApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Qualifier(KOTLIN_USER_SERVICE)
    @Autowired
    private UserService userService;

    @Test
    public void testIndex() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/"))
                .andDo(print()).andDo(log())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        assertTrue(response.getContentAsString().contains(ADD_USER_PATH));
    }

    @Test
    public void testChat() throws Exception {
        String userId = userService.addUser("user3", YELLOW);

        MockHttpServletResponse response = mockMvc.perform(get(CHAT_PATH).sessionAttr(USER_ID_SESS_ATTR, userId))
                .andDo(print()).andDo(log())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        assertTrue(response.getContentAsString().contains("Conversation"));
    }

    @Test
    public void testAddMessage() throws Exception {
        String userId = userService.addUser("user4", YELLOW);

        MockHttpServletResponse response = mockMvc.perform(post(ADD_MESSAGE_PATH)
                .param(USER_ID_PARAM, userId)
                .param("message", "message1"))
                .andDo(print()).andDo(log()).andExpect(status().isFound())
                .andExpect(redirectedUrl(CHAT_PATH))
                .andReturn().getResponse();

        assertThat(response.getHeader("Location"), equalTo(CHAT_PATH));
    }
}
