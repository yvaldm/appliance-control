package com.electrolux.appliancecontrol.controller;

import com.electrolux.appliancecontrol.api.ApplianceType;
import com.electrolux.appliancecontrol.api.Command;
import com.electrolux.appliancecontrol.config.DataAccessConfig;
import com.electrolux.appliancecontrol.config.ServiceConfig;
import com.electrolux.appliancecontrol.dao.CommandDao;
import com.electrolux.appliancecontrol.entity.CommandEntity;
import com.electrolux.appliancecontrol.service.ApplianceService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@Import({ServiceConfig.class, DataAccessConfig.class})
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplianceService applianceService;

    @Autowired
    private CommandDao commandDao;

    private UUID applianceId;

    @Before
    public void setup() {
        applianceId = applianceService.registerAppliance(ApplianceType.WASH_MACHINE);
    }

    @Test
    public void shouldCreateCommandForAppliance() throws Exception {

        // act
        mockMvc.perform(post("/appliance/" + applianceId + "/command")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content("{\"command\":\"WASH\"}"))
                .andExpect(status().isOk());

        // assert
        Optional<CommandEntity> command = commandDao.findByApplianceId(applianceId);
        Assert.assertTrue(command.isPresent());
        Assert.assertEquals(Command.WASH, command.get().getCommand());
    }

    @Test
    public void shouldFetchCommand() throws Exception {

        // arrange
        commandDao.insert(applianceId, Command.WASH);

        // act
        mockMvc.perform(get("/appliance/" + applianceId + "/command")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content("{\"command\":\"WASH\"}"));

    }
}