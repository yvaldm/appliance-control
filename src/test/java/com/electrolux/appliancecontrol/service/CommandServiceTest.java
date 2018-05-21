package com.electrolux.appliancecontrol.service;

import com.electrolux.appliancecontrol.api.Command;
import com.electrolux.appliancecontrol.dao.CommandDao;
import com.electrolux.appliancecontrol.entity.Appliance;
import com.electrolux.appliancecontrol.entity.CommandEntity;
import com.electrolux.appliancecontrol.statemachine.StateMachine;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CommandServiceTest {

    private ApplianceService applianceService = mock(ApplianceService.class);
    private StateMachine stateMachine = new StateMachine();

    @Test
    public void shouldInsertNewCommand() {

        // arrange
        CommandDao commandDao = mock(CommandDao.class);
        CommandService commandService = new CommandService(commandDao, applianceService, stateMachine);

        // act
        commandService.addNewCommand(new UUID(0, 1), Command.WASH);

        // assert
        verify(commandDao, times(1)).insert(new UUID(0, 1), Command.WASH);
    }

    @Test
    public void shouldFetchCommand() {

        // arrange
        CommandDao commandDao = mock(CommandDao.class);
        when(commandDao.findByApplianceId(new UUID(0, 1)))
                .thenReturn(Optional.of(new CommandEntity(1L, new UUID(0, 1), Command.WASH)));
        CommandService commandService = new CommandService(commandDao, applianceService, stateMachine);

        // act
        Command command = commandService.fetchCommand(new UUID(0, 1));

        // assert
        Assert.assertEquals(Command.WASH, command);
    }

    @Test
    public void shouldReturnNoActionIfNoCommandFound() {

        // arrange
        CommandDao commandDao = mock(CommandDao.class);
        when(commandDao.findByApplianceId(any())).thenReturn(Optional.empty());
        CommandService commandService = new CommandService(commandDao, applianceService, stateMachine);

        // act
        Command command = commandService.fetchCommand(new UUID(0, 1));

        // assert
        Assert.assertEquals(Command.NO_ACTION, command);
    }

    @Test
    public void shouldDeleteCommandAfterFetch() {

        // arrange
        CommandDao commandDao = mock(CommandDao.class);
        when(commandDao.findByApplianceId(new UUID(0, 1)))
                .thenReturn(Optional.of(new CommandEntity(1L, new UUID(0, 1), Command.WASH)));
        CommandService commandService = new CommandService(commandDao, applianceService, stateMachine);

        // act
        commandService.fetchCommand(new UUID(0, 1));

        // assert
        verify(commandDao, times(1)).delete(new UUID(0, 1));
    }
}