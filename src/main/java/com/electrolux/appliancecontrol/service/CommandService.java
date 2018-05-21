package com.electrolux.appliancecontrol.service;

import com.electrolux.appliancecontrol.api.Command;
import com.electrolux.appliancecontrol.dao.CommandDao;
import com.electrolux.appliancecontrol.entity.CommandEntity;
import com.electrolux.appliancecontrol.statemachine.StateMachine;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * Command service
 * <p>
 * Service is capable of 1) adding commands to appliances by users
 * 2) fetching commands by appliances
 *
 * @author valeryyakovlev
 */
public class CommandService {

    private final CommandDao commandDao;

    private final ApplianceService applianceService;

    private final StateMachine stateMachine;

    public CommandService(CommandDao commandDao, ApplianceService applianceService, StateMachine stateMachine) {
        this.commandDao = commandDao;
        this.applianceService = applianceService;
        this.stateMachine = stateMachine;
    }

    /**
     * Add new command for given appliance
     * Invoked by user
     *
     * @param applianceId identification of appliance (UUID)
     * @param command     for the appliance to perform
     */
    public void addNewCommand(UUID applianceId, Command command) {
        commandDao.insert(applianceId, command);
    }

    /**
     * Find available command for given appliance, retrieve it and delete
     * Maximum one command per appliance
     * <p>
     * Invoked by appliance
     *
     * @param applianceId identification of appliance
     * @return command to execute
     */
    @Transactional
    public Command fetchCommand(UUID applianceId) {
        Optional<CommandEntity> entity = commandDao.findByApplianceId(applianceId);

        if (entity.isPresent()) {
            commandDao.delete(applianceId);
            Command command = entity.get().getCommand();
            applianceService.updateStatus(applianceId, stateMachine.calculateStatus(command));
            return command;
        }

        return Command.NO_ACTION;
    }
}
