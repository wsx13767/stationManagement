package com.example.stationManagement.controller;

import com.example.stationManagement.model.NurseInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@SpringBootTest
class NurseInfoControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Order(1)
    @Test
    public void findAllNurse() throws Exception {
        mockMvc.perform(get("/nurse"))
                .andExpect(status().is(200))
                .andReturn();
    }

    @Order(2)
    @Test
    public void getNurse() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/nurse/21103");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", equalTo(21103)))
                .andExpect(jsonPath("$.name", equalTo("sion")))
                .andReturn();
    }

    @Order(3)
    @Test
    public void getNurse_id_less_than_0() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/nurse/-1");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andReturn();
    }

    @Order(4)
    @Test
    public void getNurse_id_not_exist() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/nurse/999");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message", equalTo("No value present")))
                .andReturn();
    }

    @Order(5)
    @Test
    public void getNurse_id_not_num() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/nurse/a");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andReturn();
    }

    @Order(6)
    @Test
    public void createNurse() throws Exception {
        NurseInfo info = new NurseInfo();
        info.setId(999L);
        info.setName("test2");
        Set<Long> set = new HashSet<>();
        set.add(1L);
        info.setStationIds(set);
        mockMvc.perform(post("/nurse")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(info)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", equalTo(999)))
                .andExpect(jsonPath("$.name", equalTo("test2")))
                .andReturn();
    }

    @Order(7)
    @Test
    public void createStation_dup() throws Exception {
        NurseInfo info = new NurseInfo();
        info.setId(21103L);
        info.setName("test2");
        mockMvc.perform(post("/nurse")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(info)))
                .andExpect(status().is(400))
                .andReturn();
    }

    @Order(8)
    @Test
    public void updateNurse() throws Exception {
        NurseInfo info = new NurseInfo();
        info.setId(888L);
        info.setName("teseee");
        mockMvc.perform(put("/nurse/777")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(info)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", equalTo(888)))
                .andExpect(jsonPath("$.name", equalTo("teseee")))
                .andReturn();
    }

    @Order(9)
    @Test
    public void findStationInfoByNurseId() throws Exception {
        mockMvc.perform(get("/nurse/21103/stations"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].name", equalTo("test")))
                .andReturn();
    }

    @Order(10)
    @Test
    public void deleteNurse() throws Exception {
        mockMvc.perform(delete("/nurse/21103"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.message", equalTo("success")))
                .andReturn();
    }






}