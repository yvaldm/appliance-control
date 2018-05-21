package com.electrolux.appliancecontrol.api;

import javax.validation.constraints.NotNull;

/**
 * Appliance registration request
 *
 * @author valeryyakovlev
 */
public class RegisterRequest {

    @NotNull
    private ApplianceType type;

    private RegisterRequest() {
    }

    public RegisterRequest(ApplianceType type) {
        this.type = type;
    }

    public ApplianceType getType() {
        return type;
    }
}
