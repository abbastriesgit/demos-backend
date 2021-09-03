package com.abbasali.demosbackend;

import com.abbasali.demosbackend.model.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameLogic {
    public boolean makeMove(GameState gameState, MoveRequest request){
        Map<Integer,Integer> cardsAvailable = _getCardsAvailable(gameState.getBoxes(),request.getPlayer());
        if(!isInvalid(gameState,request,cardsAvailable))
            return false;
        BoxState boxState = gameState.getBoxes().get(request.getBoxIndex());
        boxState.setEmpty(false);
        boxState.putCard(request.getBoxSize(),request.getPlayer());
        gameState.setStatus(getStatusFromPlayer(request.getPlayer()==1?2:1));
        isGameOver(gameState);
        return true;
    }

    private Map<Integer, Integer> _getCardsAvailable(List<BoxState> boxes, int player) {
        Map<Integer, Integer> boxSizeToCountMap = new HashMap<>();
        boxSizeToCountMap.put(1,2);
        boxSizeToCountMap.put(2,2);
        boxSizeToCountMap.put(3,2);
        boxes.forEach(box->{
            if(box.calculateTopCardPlayer()==player)
                boxSizeToCountMap.put(box.calculateTopCardSize(),boxSizeToCountMap.get(box.calculateTopCardSize())-1);
        });
        return boxSizeToCountMap;
    }

    private boolean isInvalid(GameState gameState, MoveRequest moveRequest, Map<Integer, Integer> cardsAvailable) {
        //not your turn
        if(!(getPlayerFromStatus(gameState.getStatus())==moveRequest.getPlayer()))
            return false;
        //checking boxes are available
        if(!cardsAvailable.containsKey(moveRequest.getBoxSize()))
            return false;
        if(cardsAvailable.get(moveRequest.getBoxSize())==0)
            return false;
        //box size comparison
        BoxState boxState = gameState.getBoxes().get(moveRequest.getBoxIndex());
        return boxState.isEmpty() || boxState.calculateTopCardSize() < moveRequest.getBoxSize();
    }

    public boolean isGameOver(GameState gameState){
        List<BoxState> boxStates = gameState.getBoxes();
        if(checkAndUpdateState(gameState,boxStates.get(0),boxStates.get(1),boxStates.get(2)))
            return true;
        if(checkAndUpdateState(gameState,boxStates.get(3),boxStates.get(4),boxStates.get(5)))
            return true;
        if(checkAndUpdateState(gameState,boxStates.get(6),boxStates.get(7),boxStates.get(8)))
            return true;
        if(checkAndUpdateState(gameState,boxStates.get(0),boxStates.get(3),boxStates.get(6)))
            return true;
        if(checkAndUpdateState(gameState,boxStates.get(1),boxStates.get(4),boxStates.get(7)))
            return true;
        if(checkAndUpdateState(gameState,boxStates.get(2),boxStates.get(5),boxStates.get(8)))
            return true;
        if(checkAndUpdateState(gameState,boxStates.get(0),boxStates.get(4),boxStates.get(8)))
            return true;
        return checkAndUpdateState(gameState, boxStates.get(2), boxStates.get(4), boxStates.get(6));
    }

    private boolean checkAndUpdateState(GameState gameState, BoxState boxState, BoxState boxState1, BoxState boxState2) {
        if(!boxState.isEmpty() && !boxState1.isEmpty() && !boxState2.isEmpty()){
            if(boxState.calculateTopCardPlayer() == boxState1.calculateTopCardPlayer() && boxState.calculateTopCardPlayer() == boxState2.calculateTopCardPlayer()){
                gameState.setStatus(boxState.calculateTopCardPlayer()==1?GameStatus.P1_WON:GameStatus.P2_WON);
                return true;
            }
        }
        return false;
    }

    public GameStatus getStatusFromPlayer(int player) {
        if(player ==1)
            return GameStatus.P1;
        return GameStatus.P2;
    }
    public int getPlayerFromStatus(GameStatus status) {
        if(status.equals(GameStatus.P1))
            return 1;
        return 2;
    }

}
