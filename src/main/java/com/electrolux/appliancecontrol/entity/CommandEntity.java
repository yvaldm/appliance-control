package com.electrolux.appliancecontrol.entity;

import com.electrolux.appliancecontrol.api.Command;

import java.util.UUID;

/**
 * Entity representing command sent by user to the appliance
 *
 * @author valeryyakovlev
 */
public class CommandEntity {

    private final long id;
    private final UUID applianceId;
    private final Command command;

    public CommandEntity(long id, UUID applianceId, Command command) {
        this.id = id;
        this.applianceId = applianceId;
        this.command = command;
    }

    public long getId() {
        return id;
    }

    public UUID getApplianceId() {
        return applianceId;
    }

    public Command getCommand() {
        return command;
    }
}
