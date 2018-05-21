package com.electrolux.appliancecontrol.dao;

import com.electrolux.appliancecontrol.api.ApplianceType;
import com.electrolux.appliancecontrol.api.Status;
import com.electrolux.appliancecontrol.entity.Appliance;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

/**
 * Appliance Dao
 * <p>
 * Responsible for creating, searching and updating entries in appliance registry
 *
 * @author valeryyakovlev
 */
public class ApplianceDao {

    private static final String INSERT = "insert into appliance (id, type, status) values (:id, :type, :status)";
    private static final String SELECT = "select * from appliance where id=:id";
    private static final String UPDATE = "update appliance set status=:status";

    private final ApplianceMapper MAPPER = new ApplianceMapper();

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ApplianceDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UUID insert(ApplianceType type) {
        UUID id = UUID.randomUUID();

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("type", type.toString())
                .addValue("status", Status.READY.toString());

        jdbcTemplate.update(INSERT, params);
        return id;
    }

    public Optional<Appliance> findByApplianceId(UUID applianceId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SELECT, Collections.singletonMap("id", applianceId), MAPPER));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void update(UUID applianceId, Status status) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", applianceId)
                .addValue("status", status.toString());

        jdbcTemplate.update(UPDATE, params);
    }

    private class ApplianceMapper implements RowMapper<Appliance> {

        @Override
        public Appliance mapRow(ResultSet resultSet, int i) throws SQLException {

            return new Appliance(resultSet.getObject("id", UUID.class),
                                 ApplianceType.valueOf(resultSet.getString("type")),
                                 Status.valueOf(resultSet.getString("status")));
        }
    }
}
