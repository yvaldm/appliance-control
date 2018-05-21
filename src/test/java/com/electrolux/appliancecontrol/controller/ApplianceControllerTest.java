package com.electrolux.appliancecontrol.controller;

import com.electrolux.appliancecontrol.api.ApplianceType;
import com.electrolux.appliancecontrol.api.RegisterResponse;
import com.electrolux.appliancecontrol.api.Status;
import com.electrolux.appliancecontrol.config.DataAccessConfig;
import com.electrolux.appliancecontrol.config.ServiceConfig;
import com.electrolux.appliancecontrol.service.ApplianceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@Import({ServiceConfig.class, DataAccessConfig.class})
@SpringBootTest
@AutoConfigureMockMvc
public class ApplianceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ApplianceService applianceService;

    @Test
    public void shouldRegisterAppliance() throws Exception {

        // act
        String content = mockMvc.perform(post("/appliance")
                                                 .content("{\"type\":\"WASH_MACHINE\"}")
                                                 .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.applianceId").isNotEmpty())
                .andReturn().getResponse().getContentAsString();

        // assert
        RegisterResponse response = objectMapper.readValue(content, RegisterResponse.class);
        assertEquals(Status.READY, applianceService.getStatus(response.getApplianceId()));
    }

    @Test
    public void shouldUpdateStatusToReadyWhenFinished() throws Exception {

        // arrange
        UUID applianceId = applianceService.registerAppliance(ApplianceType.WASH_MACHINE);

        // act
        mockMvc.perform(put("/appliance/" + applianceId + "/status")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content("{\"status\":\"READY\"}"))
                .andExpect(status().isOk());

        // assert
        assertEquals(Status.READY, applianceService.getStatus(applianceId));
    }

}
