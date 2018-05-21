package com.electrolux.appliancecontrol.api;

import javax.validation.constraints.NotNull;

/**
 * Request and response object for command
 *
 * @author valeryyakovlev
 */
public class CommandRequestResponse {

    @NotNull
    private Command command;

    private CommandRequestResponse() {
    }

    public CommandRequestResponse(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
