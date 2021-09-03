package com.abbasali.demosbackend;

import com.abbasali.demosbackend.model.GameState;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameStateRowMapper implements RowMapper<GameState> {
    @SneakyThrows
    @Override
    public GameState mapRow(ResultSet resultSet, int i) throws SQLException {
        return new ObjectMapper().readValue(resultSet.getString("state"),GameState.class);
    }
}
