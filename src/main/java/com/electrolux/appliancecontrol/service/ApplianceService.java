package com.electrolux.appliancecontrol.service;

import com.electrolux.appliancecontrol.api.ApplianceType;
import com.electrolux.appliancecontrol.api.Status;
import com.electrolux.appliancecontrol.dao.ApplianceDao;
import com.electrolux.appliancecontrol.entity.Appliance;

import java.util.UUID;

/**
 * Appliance service
 * <p>
 * Used to register, read and update status of the appliance
 *
 * @author valeryyakovlev
 */
public class ApplianceService {

    private final ApplianceDao applianceDao;

    public ApplianceService(ApplianceDao applianceDao) {
        this.applianceDao = applianceDao;
    }

    public UUID registerAppliance(ApplianceType type) {
        return applianceDao.insert(type);
    }

    /**
     * Retrieve status of appliance
     *
     * @param applianceId identification of appliance
     * @return Status of appliance (e.g. WASH, READY)
     */
    public Status getStatus(UUID applianceId) {
        return findAppliance(applianceId).getStatus();
    }

    /**
     * Update status of appliance by id
     *
     * @param applianceId identification of appliance
     * @param status      new status of appliance
     */
    public void updateStatus(UUID applianceId, Status status) {
        findAppliance(applianceId);
        applianceDao.update(applianceId, status);
    }

    /**
     * Find appliance by appliance id or throw IllegalArgumentException
     *
     * @param applianceId identification of appliance
     * @return Appliance entity
     */
    public Appliance findAppliance(UUID applianceId) {
        return applianceDao.findByApplianceId(applianceId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Appliance with id %s not found", applianceId)));
    }
}
