package com.electrolux.appliancecontrol.dao;

import com.electrolux.appliancecontrol.api.Command;
import com.electrolux.appliancecontrol.entity.CommandEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.singletonMap;

/**
 * Command Dao
 * <p>
 * Used to store and fetch commands for appliance
 * <p>
 * Commands are created by users and fetch by devices
 *
 * @author valeryyakovlev
 */
public class CommandDao {

    private static final String INSERT = "insert into command (appliance_id, command) values (:applianceId, :command)";
    private static final String SELECT = "select * from command where appliance_id=:applianceId limit 1";
    private static final String DELETE = "delete from command where appliance_id=:applianceId";

    private final CommandMapper COMMAND_MAPPER = new CommandMapper();

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CommandDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(UUID applianceId, Command command) {

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("applianceId", applianceId)
                .addValue("command", command.toString());

        jdbcTemplate.update(INSERT, params);
    }

    /**
     * Find command for given appliance
     * (invoked by appliance)
     *
     * @param applianceId identification of appliance
     * @return optional of command
     */
    public Optional<CommandEntity> findByApplianceId(UUID applianceId) {

        try {
            return Optional.of(jdbcTemplate.queryForObject(SELECT, singletonMap("applianceId", applianceId), COMMAND_MAPPER));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * Delete appliance by id
     *
     * @param applianceId identification of appliance
     */
    public void delete(UUID applianceId) {
        jdbcTemplate.update(DELETE, singletonMap("applianceId", applianceId));
    }

    private class CommandMapper implements RowMapper<CommandEntity> {

        @Override
        public CommandEntity mapRow(ResultSet resultSet, int i) throws SQLException {

            return new CommandEntity(resultSet.getLong("id"),
                                     resultSet.getObject("appliance_id", UUID.class),
                                     Command.valueOf(resultSet.getString("command"))
            );
        }
    }
}
