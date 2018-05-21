package com.electrolux.appliancecontrol.controller;

import com.electrolux.appliancecontrol.api.CommandRequestResponse;
import com.electrolux.appliancecontrol.service.CommandService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class CommandController {

    private final CommandService commandService;

    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping("/appliance/{applianceId}/command")
    public void addNewCommand(@PathVariable UUID applianceId, @RequestBody @Valid CommandRequestResponse commandRequest) {
        commandService.addNewCommand(applianceId, commandRequest.getCommand());
    }

    @GetMapping("/appliance/{applianceId}/command")
    public CommandRequestResponse fetchCommand(@PathVariable UUID applianceId) {
        return new CommandRequestResponse(commandService.fetchCommand(applianceId));
    }
}
