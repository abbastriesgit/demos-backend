package com.abbasali.demosbackend.model;

import com.abbasali.demosbackend.model.BoxState;
import com.abbasali.demosbackend.model.Card;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TicTacToeStateResponse {
    String gameId;
    List<BoxStateResponse> boxStateList;
    List<Card> myCards;
    List<Card> otherPlayersCards;
    boolean isYourTurn;
    boolean gameStarted;
    boolean gameFinished;
    boolean iWon;
    int player;
}
