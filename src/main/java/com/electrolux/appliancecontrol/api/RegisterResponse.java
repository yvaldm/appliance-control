package com.electrolux.appliancecontrol.api;

import java.util.UUID;

/**
 * Appliance registration response
 *
 * @author valeryyakovlev
 */
public class RegisterResponse {

    private UUID applianceId;

    private RegisterResponse() {
    }

    public RegisterResponse(UUID applianceId) {
        this.applianceId = applianceId;
    }

    public UUID getApplianceId() {
        return applianceId;
    }
}
