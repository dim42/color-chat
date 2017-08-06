package com.pack.colorchat;

import com.pack.colorchat.app.ColorChatApplication;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.pack.colorchat.rest.ChatController.CHAT;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ColorChatApplication.class)
@AutoConfigureMockMvc
public class ColorChatApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void index() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/"))
                .andDo(print()).andDo(log()).andExpect(status().isOk()).andReturn().getResponse();

        assertTrue(response.getContentAsString().contains("add-user"));
    }

    @Ignore
    @Test
    public void chat() throws Exception {
        String json = "";
        mockMvc.perform(post(CHAT).contentType(APPLICATION_JSON).content(json))
                .andDo(print()).andDo(log()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("2222"));
    }
}
