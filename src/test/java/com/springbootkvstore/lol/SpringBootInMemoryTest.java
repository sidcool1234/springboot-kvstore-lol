package com.springbootkvstore.lol;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class SpringBootInMemoryTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private KVStore<String, String> kvStore;


    @Test
    void testSetGetAndGetAll() throws Exception {

        performAdd("key", "value")
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Success"));

        performAdd("key1", "value1")
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Success"));

        mvc.perform(MockMvcRequestBuilders.get("/set")
                        .param("key", "key"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        mvc.perform(MockMvcRequestBuilders.get("/set")
                        .param("val", "value"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());


        mvc.perform(MockMvcRequestBuilders.get("/get")
                        .param("key", "key"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("value"));


        mvc.perform(MockMvcRequestBuilders.get("/get-all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("key1=value1, key=value"));
    }

    private ResultActions performAdd(String key, String value) throws Exception {
        return mvc.perform(MockMvcRequestBuilders.get("/set")
                .param("key", key)
                .param("val", value));
    }
}