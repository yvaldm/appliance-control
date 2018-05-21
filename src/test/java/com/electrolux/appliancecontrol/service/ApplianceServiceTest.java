package com.electrolux.appliancecontrol.service;

import com.electrolux.appliancecontrol.api.ApplianceType;
import com.electrolux.appliancecontrol.api.Status;
import com.electrolux.appliancecontrol.dao.ApplianceDao;
import com.electrolux.appliancecontrol.entity.Appliance;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ApplianceServiceTest {

    private ApplianceDao applianceDao;

    @Before
    public void setup() {
        applianceDao = mock(ApplianceDao.class);
    }

    @Test
    public void shouldRegisterAppliance() {
        // arrange
        when(applianceDao.insert(any())).thenReturn(new UUID(0, 1));
        ApplianceService applianceService = new ApplianceService(applianceDao);

        // act
        UUID uuid = applianceService.registerAppliance(ApplianceType.WASH_MACHINE);

        // assert
        Assert.assertEquals(new UUID(0, 1), uuid);
    }

    @Test
    public void shouldGetStatusOfAppliance() {
        // arrange
        when(applianceDao.findByApplianceId(new UUID(0, 1)))
                .thenReturn(Optional.of(new Appliance(new UUID(0, 1), ApplianceType.WASH_MACHINE, Status.READY)));
        ApplianceService applianceService = new ApplianceService(applianceDao);

        // act
        Status status = applianceService.getStatus(new UUID(0, 1));

        // assert
        Assert.assertEquals(Status.READY, status);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailIfGettingStatusForNonExistentAppliance() {

        // arrange
        when(applianceDao.findByApplianceId(any())).thenReturn(Optional.empty());
        ApplianceService applianceService = new ApplianceService(applianceDao);

        // act
        applianceService.getStatus(new UUID(0, 1));
    }

    @Test
    public void shouldUpdateStatus() {

        // arrange
        when(applianceDao.findByApplianceId(any())).thenReturn(Optional.of(new Appliance(new UUID(0, 1),
                                                                                         ApplianceType.WASH_MACHINE,
                                                                                         Status.READY)));
        ApplianceService applianceService = new ApplianceService(applianceDao);

        // act
        applianceService.updateStatus(new UUID(0, 1), Status.WASHING);

        // assert
        verify(applianceDao, times(1)).update(new UUID(0, 1), Status.WASHING);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailIfUpdatingStatusForNonExistentAppliance() {

        // arrange
        when(applianceDao.findByApplianceId(new UUID(0, 1))).thenReturn(Optional.empty());
        ApplianceService applianceService = new ApplianceService(applianceDao);

        // act
        applianceService.updateStatus(new UUID(0, 1), Status.WASHING);

    }
}