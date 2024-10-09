package edu.du.chap17.dao;

import edu.du.chap17.service.IdGenerationFailedException;
import org.apache.catalina.valves.JDBCAccessLogValve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public class IdGenerator {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public int generateNextId(@Nullable String sequenceName) throws IdGenerationFailedException {
        int id = jdbcTemplate.queryForObject("select next_value from id_sequence where sequence_name = ? for update", Integer.class, sequenceName);

        id++;

        jdbcTemplate.update("update id_sequence set next_value = ? where sequence_name = ?", id, sequenceName);

        return id;
    }

}
