package com.abbasali.demosbackend.revision_master;

import com.abbasali.demosbackend.revision_master.model.Note;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotesRowMapper implements RowMapper<Note> {
    @Override
    public Note mapRow(ResultSet resultSet, int i) throws SQLException {
        int pId  = -1;
        if(resultSet.getString("p_id") != null)
            pId = resultSet.getInt("p_id");
        return Note.builder()
                .id(resultSet.getInt("id"))
                .parentId(pId)
                .body(resultSet.getString("body"))
                .title(resultSet.getString("title"))
                .userId(resultSet.getInt("user_id"))
                .build();
    }
}
