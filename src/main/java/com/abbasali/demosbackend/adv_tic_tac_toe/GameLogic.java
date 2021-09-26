package com.abbasali.demosbackend.adv_tic_tac_toe;

import com.abbasali.demosbackend.adv_tic_tac_toe.model.*;
import com.abbasali.demosbackend.adv_tic_tac_toe.ai.AiResponse;
import com.abbasali.demosbackend.adv_tic_tac_toe.ai.Algorithm;
import com.abbasali.demosbackend.adv_tic_tac_toe.ai.TicTacToeAi;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameLogic extends TicTacToeAi {
    public int makeMove(GameState gameState, MoveRequest request){
        Map<Integer,Integer> cardsAvailable = _getCardsAvailable(gameState.getBoxes(),request.getPlayer());
        if(!isInvalid(gameState,request,cardsAvailable))
            return -1;
        boolean gameOver = makeMoveForPlayer(gameState,request);
        if(request.getAlgorithm()==null || request.getAlgorithm().equals(Algorithm.PLAYER)){
            return request.getPlayer();
        }
        if(gameOver)
            return 1;
        if(request.getAlgorithm().equals(Algorithm.MIN_MAX)){
            AiResponse aiResponse = getBestMove(gameState);
            if(aiResponse == null){
                gameState.setStatus(GameStatus.DRAW);
            }
            else{
                MoveRequest aiRequest = new MoveRequest("",2,aiResponse.getIndex(), aiResponse.getCardSize(), request.getAlgorithm());
                makeMoveForPlayer(gameState,aiRequest);
            }
            return 2;
        }
        return -1;
    }
    public boolean makeMoveForPlayer(GameState gameState,MoveRequest request){
        BoxState boxState = gameState.getBoxes().get(request.getBoxIndex());
        boxState.setEmpty(false);
        boxState.putCard(request.getBoxSize(),request.getPlayer());
        gameState.setStatus(getStatusFromPlayer(request.getPlayer()==1?2:1));
        return isGameOver(gameState);

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
