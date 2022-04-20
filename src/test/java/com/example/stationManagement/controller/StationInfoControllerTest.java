package com.example.stationManagement.controller;

import com.example.stationManagement.model.StationInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext
class StationInfoControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Order(1)
    @Test
    public void getStations() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/station");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andReturn();
    }

    @Order(2)
    @Test
    public void getStation() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/station/1");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("test")))
                .andReturn();
    }

    @Order(3)
    @Test
    public void getStation_id_less_than_0() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/station/-1");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andReturn();
    }

    @Order(4)
    @Test
    public void getStation_id_not_exist() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/station/999");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message", equalTo("No value present")))
                .andReturn();
    }

    @Order(5)
    @Test
    public void getStation_id_not_num() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/station/a");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andReturn();
    }

    @Order(6)
    @Test
    public void createStation() throws Exception {
        StationInfo info = new StationInfo();
        info.setName("test2");
        mockMvc.perform(post("/station")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(info)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", equalTo(2)))
                .andExpect(jsonPath("$.name", equalTo("test2")))
                .andReturn();
    }

    @Order(7)
    @Test
    public void createStation_dup() throws Exception {
        StationInfo info = new StationInfo();
        info.setName("test");

        mockMvc.perform(post("/station")
                    .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(info)))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message", equalTo("station名稱重複")))
                .andReturn();
    }

    @Order(8)
    @Test
    public void updateStation() throws Exception {
        StationInfo info = new StationInfo();
        info.setName("test");
        mockMvc.perform(put("/station/1")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(info)))
                .andExpect(status().is(400))
                .andReturn();
    }

    @Order(9)
    @Test
    public void updateStation_success() throws Exception {
        StationInfo info = new StationInfo();
        info.setName("station1");
        mockMvc.perform(put("/station/1")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(info)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("station1")))
                .andExpect(jsonPath("$.createTime", notNullValue()))
                .andExpect(jsonPath("$.updateTime", notNullValue()))
                .andReturn();
    }

    @Order(10)
    @Test
    public void findNurseInfoByStationId() throws Exception {
        mockMvc.perform(get("/station/1/nurses"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[0].id", equalTo(21103)))
                .andExpect(jsonPath("$[0].name", equalTo("sion")))
                .andExpect(jsonPath("$[0].joinTime", notNullValue()))
                .andReturn();
    }

    @Order(11)
    @Test
    public void deleteStation_less_than_0() throws Exception {
        mockMvc.perform(delete("/station/-1"))
                .andExpect(status().is(400))
                .andReturn();
    }

    @Order(12)
    @Test
    public void deleteStation_id_not_num() throws Exception {
        mockMvc.perform(delete("/station/a"))
                .andExpect(status().is(400))
                .andReturn();
    }

    @Order(13)
    @Test
    public void deleteStation() throws Exception {
        mockMvc.perform(delete("/station/1"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.message", equalTo("success")))
                .andReturn();
    }


}