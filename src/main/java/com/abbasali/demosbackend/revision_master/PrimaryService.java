package com.abbasali.demosbackend.revision_master;

import com.abbasali.demosbackend.revision_master.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrimaryService {
    NotesRepository repository;

    public PrimaryService(NotesRepository repository) {
        this.repository = repository;
    }
    public List<Note> getNotes(int id, int userId){
        return repository.get(id,userId);
    }
    public List<Note> getChildren(int parentId, int userId,int start,int limit){
        return repository.getChildren(parentId,userId,start,limit);
    }
    public List<Note> insert(Note note){
        repository.insert(note);
        return repository.getChildren(note.getParentId(),note.getUserId(),0,10);
    }
    public List<Note> update(Note note){
        repository.update(note);
        return repository.getChildren(note.getParentId(),note.getUserId(),0,10);
    }

    public int delete(int id, int userId) {
        return repository.delete(id,userId);
    }
}
