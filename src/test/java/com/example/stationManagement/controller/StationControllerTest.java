package com.example.stationManagement.controller;

import com.example.stationManagement.database.entity.Station;
import com.example.stationManagement.database.repository.StationRepository;
import com.example.stationManagement.model.StationInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class StationControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StationRepository stationRepository;

    @BeforeEach
    public void before() {
        Station station = new Station();
        station.setId(1L);
        station.setName("test");
        station.setCreateTime(LocalDateTime.now());
        station.setUpdateTime(LocalDateTime.now());

        Mockito.when(stationRepository.findById(1L)).thenReturn(Optional.of(station));
        Mockito.when(stationRepository.findById(2L)).thenReturn(Optional.empty());
        Mockito.when(stationRepository.findByName("test")).thenReturn(Optional.of(station));
        Mockito.when(stationRepository.findByName("test2")).thenReturn(Optional.empty());
        Mockito.when(stationRepository.save(Mockito.any())).thenReturn(station);
    }

    @Test
    public void getStations() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/station");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    public void getStation() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/station/1");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("test")))
                .andReturn();
    }

    @Test
    public void getStation_id_less_than_0() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/station/-1");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andReturn();
    }

    @Test
    public void getStation_id_not_exist() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/station/2");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andExpect(content().string("No value present"))
                .andReturn();
    }

    @Test
    public void getStation_id_not_num() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/station/a");
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andReturn();
    }

    @Test
    public void createStation() throws Exception {
        StationInfo info = new StationInfo();
        info.setName("test2");
        mockMvc.perform(post("/station")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(info)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("test")))
                .andReturn();
    }

    @Test
    public void createStation_dup() throws Exception {
        StationInfo info = new StationInfo();
        info.setName("test");

        mockMvc.perform(post("/station")
                    .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(info)))
                .andExpect(status().is(400))
                .andExpect(content().string("station名稱重複"))
                .andReturn();
    }


}