package com.abbasali.demosbackend.adv_tic_tac_toe.model;

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
