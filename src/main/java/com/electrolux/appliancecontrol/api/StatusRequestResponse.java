package com.electrolux.appliancecontrol.api;

import javax.validation.constraints.NotNull;

public class StatusRequestResponse {

    @NotNull
    private Status status;

    private StatusRequestResponse() {
    }

    public StatusRequestResponse(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
