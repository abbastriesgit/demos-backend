package com.abbasali.demosbackend.adv_tic_tac_toe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoxState {
    boolean isEmpty;
    List<Integer> cardSize;//1,2,3
    List<Integer> player;//1,2

    public int calculateTopCardSize(){
        if(isEmpty || cardSize.isEmpty())
            return -1;
        return cardSize.get(cardSize.size()-1);
    }
    public int calculateTopCardPlayer(){
        if(isEmpty || player.isEmpty())
            return -1;
        return player.get(player.size()-1);
    }
    public void putCard(int size,int playerNumber){
        cardSize.add(size);
        player.add(playerNumber);
    }
}