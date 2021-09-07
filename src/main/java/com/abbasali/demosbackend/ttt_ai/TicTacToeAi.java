package com.abbasali.demosbackend.ttt_ai;

import com.abbasali.demosbackend.model.BoxState;
import com.abbasali.demosbackend.model.GameState;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public abstract class TicTacToeAi {
    int MAX_DEPTH = 4;
    public abstract boolean isGameOver(GameState gameState);

    public AiResponse getBestMove(GameState gameState){
        boolean pCards[] = _getPCards(gameState,1);
        boolean aiCards[] = _getPCards(gameState,2);
        return maximize(gameState,pCards,aiCards,0);
    }

    private AiResponse maximize(GameState gameState, boolean[] pCards, boolean[] aiCards,int depth) {
        AiResponse ans = null;
        for(int i =0;i<9;i++){
           BoxState boxState = gameState.getBoxes().get(i);
           for(int j =0;j<6;j++){
               if(!aiCards[j])
                   continue;
               int cardSize = (j+2)/2;
               if(boxState.isEmpty() || boxState.calculateTopCardSize() < cardSize){
                   aiCards[j] = false;
                   _putCard(boxState,cardSize,2);
                   if(isGameOver(gameState)){
                       aiCards[j] = true;
                       _removeCard(boxState);
                       return AiResponse.builder().cardSize(cardSize).index(i).score(1).build();
                   }
                   else if(depth < MAX_DEPTH) {
                       AiResponse aiResponse = minimize(gameState,pCards,aiCards,depth+1);
                       if(aiResponse!=null){
                           if(ans==null)
                               ans = aiResponse;
                           else {
                               if(ans.score < aiResponse.score)
                                   ans = aiResponse;
                           }
                       }
                   }
                   aiCards[j] = true;
                   _removeCard(boxState);
               }
           }
        }
        return ans;
    }
    private AiResponse minimize(GameState gameState, boolean[] pCards, boolean[] aiCards,int depth) {
        AiResponse ans = null;
        for(int i =0;i<9;i++){
           BoxState boxState = gameState.getBoxes().get(i);
           for(int j =0;j<6;j++){
               if(!pCards[j])
                   continue;
               int cardSize = (j+2)/2;
               if(boxState.isEmpty() || boxState.calculateTopCardSize() < cardSize){
                   pCards[j] = false;
                   _putCard(boxState,cardSize,1);
                   if(isGameOver(gameState)){
                       pCards[j] = true;
                       _removeCard(boxState);
                       return AiResponse.builder().cardSize(cardSize).index(i).score(-1).build();
                   }
                   else if(depth < MAX_DEPTH) {
                       AiResponse aiResponse = maximize(gameState,pCards,aiCards,depth+1);
                       if(aiResponse!=null){
                           if(ans==null)
                               ans = aiResponse;
                           else {
                               if(ans.score > aiResponse.score)
                                   ans = aiResponse;
                           }
                       }

                   }
                   pCards[j] = true;
                   _removeCard(boxState);
               }
           }
        }
        return ans;
    }
    private void _putCard(BoxState boxState, int cardSize, int player) {
        if(boxState.isEmpty()){
            boxState.setEmpty(false);
            boxState.setCardSize(new ArrayList<>());
            boxState.setPlayer(new ArrayList<>());
        }
        boxState.getCardSize().add(cardSize);
        boxState.getPlayer().add(player);
    }
    private void _removeCard(BoxState boxState) {
        int n = boxState.getCardSize().size();
        boxState.getCardSize().remove(n-1);
        boxState.getPlayer().remove(n-1);
        if(n == 1)
            boxState.setEmpty(true);
    }
    private boolean[] _getPCards(GameState gameState,int player) {
        boolean[] pCards = new boolean[6];
        Arrays.fill(pCards,true);
        for (int i = 0; i < 9; i++) {
            BoxState boxState = gameState.getBoxes().get(i);
            if(boxState.isEmpty())
                continue;
            for (int j = 0; j < boxState.getPlayer().size(); j++) {
                if(boxState.getPlayer().get(j)==player)
                    markCardFalse(pCards,boxState.getCardSize().get(j));
            }
        }
        return pCards;
    }
    private void markCardFalse(boolean[] cards, int size) {
        int first = size*2 - 2;
        if(!cards[first])
            cards[first+1] = false;
        else
            cards[first] = false;
    }
}
