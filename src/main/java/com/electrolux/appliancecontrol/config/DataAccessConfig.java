package com.electrolux.appliancecontrol.config;

import com.electrolux.appliancecontrol.dao.ApplianceDao;
import com.electrolux.appliancecontrol.dao.CommandDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Data access layer configuration
 *
 * @author valeryyakovlev
 */
@Configuration
public class DataAccessConfig {

    @Bean
    public ApplianceDao applianceDao(NamedParameterJdbcTemplate jdbcTemplate) {
        return new ApplianceDao(jdbcTemplate);
    }

    @Bean
    public CommandDao commandDao(NamedParameterJdbcTemplate jdbcTemplate) {
        return new CommandDao(jdbcTemplate);
    }
}
