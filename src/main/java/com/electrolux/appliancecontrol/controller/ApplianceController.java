package com.electrolux.appliancecontrol.controller;

import com.electrolux.appliancecontrol.api.RegisterRequest;
import com.electrolux.appliancecontrol.api.RegisterResponse;
import com.electrolux.appliancecontrol.api.StatusRequestResponse;
import com.electrolux.appliancecontrol.service.ApplianceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class ApplianceController {

    private final ApplianceService applianceControlService;

    public ApplianceController(ApplianceService applianceControlService) {
        this.applianceControlService = applianceControlService;
    }

    @PostMapping("/appliance")
    public RegisterResponse register(@RequestBody @Valid RegisterRequest request) {
        return new RegisterResponse(applianceControlService.registerAppliance(request.getType()));
    }

    @GetMapping("/appliance/{applianceId}/status")
    public StatusRequestResponse getStatus(@PathVariable UUID applianceId) {
        return new StatusRequestResponse(applianceControlService.getStatus(applianceId));
    }

    @PutMapping("/appliance/{applianceId}/status")
    public void updateStatus(@PathVariable UUID applianceId, @RequestBody @Valid StatusRequestResponse request) {
        applianceControlService.updateStatus(applianceId, request.getStatus());
    }
}
