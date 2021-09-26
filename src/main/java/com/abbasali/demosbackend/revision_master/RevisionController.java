package com.abbasali.demosbackend.revision_master;

import com.abbasali.demosbackend.revision_master.model.Note;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("notes")
public class RevisionController {
    PrimaryService service;

    public RevisionController(PrimaryService service) {
        this.service = service;
    }

    @GetMapping("get")
    public ResponseEntity<List<Note>> get(@RequestParam int id, @RequestParam int userId){
        return ResponseEntity.ok(service.getNotes(id,userId));
    }
    @GetMapping("get/children")
    public ResponseEntity<List<Note>> getChildren(@RequestParam int parentId, @RequestParam int userId,@RequestParam int start,@RequestParam int limit){
        return ResponseEntity.ok(service.getChildren(parentId,userId,start,limit));
    }
    @PostMapping("add")
    public ResponseEntity<List<Note>> add(@RequestBody Note note){
        return ResponseEntity.ok(service.insert(note));
    }
    @PostMapping("update")
    public ResponseEntity<List<Note>> update(@RequestBody Note note){
        return ResponseEntity.ok(service.update(note));
    }
    @DeleteMapping("delete")
    public ResponseEntity<Integer> delete(@RequestParam int id, @RequestParam int userId){
        if(!service.getChildren(id,userId,0,2).isEmpty()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        return ResponseEntity.ok(service.delete(id,userId));
    }
}
