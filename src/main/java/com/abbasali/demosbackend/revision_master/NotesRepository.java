package com.abbasali.demosbackend.revision_master;

import com.abbasali.demosbackend.revision_master.model.Note;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NotesRepository {
    JdbcTemplate jdbcTemplate;
    String GET_Q = "Select * from notes where id = ? and user_id = ? ;";
    String CHILDREN_Q = "Select * from notes where p_id = ? and user_id = ? limit ? , ? ;";
    String INSERT_Q = "INSERT INTO notes (p_id, user_id, title, body) VALUES (?, ?, ?, ?);";
    String UPDATE_Q = "UPDATE notes SET p_id = ?, title = ? , body = ? where id = ? and user_id = ? ;";
    String DELETE_Q = "DELETE from notes where user_id = ? and (id = ? );";

    public NotesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Note> get(int id, int userId){
        return jdbcTemplate.query(GET_Q,new NotesRowMapper(),id,userId);
    }
    public List<Note> getChildren(int id, int userId,int start,int limit){
        return jdbcTemplate.query(CHILDREN_Q,new NotesRowMapper(),id,userId,start,limit);
    }
    public int insert(Note note){
        return jdbcTemplate.update(INSERT_Q,
                note.getParentId(),
                note.getUserId(),
                note.getTitle(),
                note.getBody()
        );
    }
    public int update(Note note){
        return jdbcTemplate.update(UPDATE_Q,
                note.getParentId(),
                note.getTitle(),
                note.getBody(),
                note.getId(),
                note.getUserId()
        );
    }

    public int delete(int id, int userId) {
        return jdbcTemplate.update(DELETE_Q,
                userId,id
        );
    }
}
