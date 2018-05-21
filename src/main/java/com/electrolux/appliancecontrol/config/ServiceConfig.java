package com.electrolux.appliancecontrol.config;

import com.electrolux.appliancecontrol.dao.ApplianceDao;
import com.electrolux.appliancecontrol.dao.CommandDao;
import com.electrolux.appliancecontrol.service.ApplianceService;
import com.electrolux.appliancecontrol.service.CommandService;
import com.electrolux.appliancecontrol.statemachine.StateMachine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Service layer configuration
 *
 * @author valeryyakovlev
 */
@Configuration
public class ServiceConfig {

    @Bean
    public StateMachine stateMachine() {
        return new StateMachine();
    }

    @Bean
    public ApplianceService applianceControlService(ApplianceDao applianceDAo) {
        return new ApplianceService(applianceDAo);
    }

    @Bean
    public CommandService commandService(CommandDao commandDao, ApplianceService applianceService, StateMachine stateMachine) {
        return new CommandService(commandDao, applianceService, stateMachine);
    }
}
