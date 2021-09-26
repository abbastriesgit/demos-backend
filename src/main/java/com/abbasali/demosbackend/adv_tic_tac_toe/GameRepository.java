package com.abbasali.demosbackend.adv_tic_tac_toe;

import com.abbasali.demosbackend.adv_tic_tac_toe.model.GameState;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GameRepository {
    JdbcTemplate jdbcTemplate;
    String SELECT = "Select * from tic_tac_toe where id = ?;";
    String UPDATE = "UPDATE tic_tac_toe SET state = ? where id = ?;";
    String INSERT = "INSERT into tic_tac_toe (id,state) values (?,?);";
    public GameRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public GameState get(String id){
        List<GameState> gameStateList = jdbcTemplate.query(SELECT,new GameStateRowMapper(),id);
        if(!gameStateList.isEmpty())
            return gameStateList.get(0);
        return null;
    }
    public boolean update(GameState state) throws JsonProcessingException {
        jdbcTemplate.update(UPDATE,new ObjectMapper().writeValueAsString(state),state.getId());
        return true;
    }
    public boolean insert(GameState state) throws JsonProcessingException {
        jdbcTemplate.update(INSERT,state.getId(),new ObjectMapper().writeValueAsString(state));
        return true;
    }
}
