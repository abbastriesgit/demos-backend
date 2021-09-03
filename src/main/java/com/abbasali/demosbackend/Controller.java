package com.abbasali.demosbackend;

import com.abbasali.demosbackend.model.MoveRequest;
import com.abbasali.demosbackend.model.TicTacToeStateResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class Controller {
    TicTacToeService ticTacToeService;

    public Controller(TicTacToeService ticTacToeService) {
        this.ticTacToeService = ticTacToeService;
    }

    @GetMapping("/state")
    public ResponseEntity<TicTacToeStateResponse> getState(@RequestParam String gameId , @RequestParam int player){
        return ResponseEntity.ok(ticTacToeService.getState(gameId,player));
    }
//    @GetMapping("/restart")
//    public ResponseEntity<TicTacToeStateResponse> restart(@RequestParam String gameId ,@RequestParam int player){
//        return ResponseEntity.ok(ticTacToeService.restart(gameId,player));
//    }
    @PostMapping("/create")
    public ResponseEntity<TicTacToeStateResponse> create() throws JsonProcessingException {
        return ResponseEntity.ok(ticTacToeService.createNewGame());
    }
    @PostMapping("/join")
    public ResponseEntity<TicTacToeStateResponse> join(@RequestParam String gameId) throws JsonProcessingException {
        return ResponseEntity.ok(ticTacToeService.joinAGame(gameId));
    }
    @PostMapping("/restart")
    public ResponseEntity<TicTacToeStateResponse> restart(@RequestParam String gameId,int player) throws JsonProcessingException {
        return ResponseEntity.ok(ticTacToeService.restart(gameId,player));
    }
    @PostMapping("/move")
    public ResponseEntity<TicTacToeStateResponse> move(@RequestBody MoveRequest request) throws JsonProcessingException {
        try{
            return ResponseEntity.ok(ticTacToeService.makeAMove(request));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

}
