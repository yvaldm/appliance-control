package com.electrolux.appliancecontrol.entity;

import com.electrolux.appliancecontrol.api.ApplianceType;
import com.electrolux.appliancecontrol.api.Status;

import java.util.UUID;

/**
 * Appliance entity
 *
 * @author valeryyakovlev
 */
public class Appliance {

    private UUID id;
    private ApplianceType type;
    private Status status;

    public Appliance(UUID id, ApplianceType type, Status status) {
        this.id = id;
        this.type = type;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public ApplianceType getType() {
        return type;
    }

    public Status getStatus() {
        return status;
    }
}
