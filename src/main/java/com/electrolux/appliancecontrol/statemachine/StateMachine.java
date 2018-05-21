package com.electrolux.appliancecontrol.statemachine;

import com.electrolux.appliancecontrol.api.Command;
import com.electrolux.appliancecontrol.api.Status;

/**
 * Mini state machine for calculating statuses corresponding to the commands
 *
 * @author valeryyakovlev
 */
public class StateMachine {
    public Status calculateStatus(Command command) {

        if (Command.WASH.equals(command)) {
            return Status.WASHING;
        }

        throw new IllegalStateException(String.format("Can't calculate Status for command %s", command));
    }
}
