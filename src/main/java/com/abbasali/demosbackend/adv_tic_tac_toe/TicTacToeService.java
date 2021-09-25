package com.abbasali.demosbackend.adv_tic_tac_toe;

import com.abbasali.demosbackend.adv_tic_tac_toe.model.*;
import com.abbasali.demosbackend.adv_tic_tac_toe.ai.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TicTacToeService {
    GameRepository repository;
    GameLogic gameLogic;

    public TicTacToeService(GameRepository repository, GameLogic gameLogic) {
        this.repository = repository;
        this.gameLogic = gameLogic;
    }

    public TicTacToeStateResponse getState(String id,int player){
        GameState gameState = repository.get(id);
        return mapToResponse(gameState,player);
    }
    public TicTacToeStateResponse makeAMove(MoveRequest request) throws JsonProcessingException {
        GameState gameState= repository.get(request.getGameId());
        int player = gameLogic.makeMove(gameState,request);
        if(player!=-1){
            repository.update(gameState);
            return mapToResponse(gameState,request.getPlayer());
        }
        throw new IllegalArgumentException("Invalid Move");
    }
    public TicTacToeStateResponse createNewGame(Algorithm algorithm) throws JsonProcessingException {
        GameStatus gameStatus = GameStatus.CREATED;
        if(algorithm!=null && algorithm.equals(Algorithm.MIN_MAX))
            gameStatus = GameStatus.P1;
        GameState gameState = GameState.builder()
                .id(UUID.randomUUID().toString().substring(1,10))
                .status(gameStatus)
                .boxes(_initializeListOfBoxes())
                .build();
        repository.insert(gameState);
        return mapToResponse(gameState,1);
    }

    public TicTacToeStateResponse mapToResponse(GameState gameState, int player){
        int otherPlayer = player == 1?2:1;
        GameStatus playerInStatus = gameLogic.getStatusFromPlayer(player);
        GameStatus playerWinStatus = playerInStatus.equals(GameStatus.P1)?GameStatus.P1_WON:GameStatus.P2_WON;

        List<Card> myCards = _initCards();
        _calculateCards(gameState,myCards,player);

        List<Card> otherPlayersCards = _initCards();
        _calculateCards(gameState,otherPlayersCards,otherPlayer);
        List<BoxStateResponse> boxStateResponses = gameState.getBoxes()
                .stream().map(boxState ->
                     BoxStateResponse.builder()
                            .isEmpty(boxState.isEmpty())
                             .size(boxState.calculateTopCardSize())
                             .player(boxState.calculateTopCardPlayer())
                             .build()
                ).collect(Collectors.toList());
        return TicTacToeStateResponse.builder()
                .myCards(myCards)
                .otherPlayersCards(otherPlayersCards)
                .boxStateList(boxStateResponses)
                .gameId(gameState.getId())
                .iWon(gameState.getStatus().equals(playerWinStatus))
                .isYourTurn(gameState.getStatus().equals(playerInStatus))
                .gameStarted(!gameState.getStatus().equals(GameStatus.CREATED))
                .gameFinished(
                        gameState.getStatus().equals(GameStatus.DRAW) ||
                        gameState.getStatus().equals(GameStatus.P1_WON) ||
                        gameState.getStatus().equals(GameStatus.P2_WON)
                )
                .player(player)
                .build();
    }

    private void _calculateCards(GameState gameState,
                                 List<Card> cards, int player) {
        for (BoxState boxState : gameState.getBoxes()) {
            if(boxState.isEmpty())
               continue;
            for(int i =0;i<boxState.getPlayer().size();i++){
                if (boxState.getPlayer().get(i) == player) {
                    _markCardSize(cards, boxState.getCardSize().get(i));
                }
            }
        }
    }

    private void _markCardSize(List<Card> cards, int cardSize) {
        for (Card card : cards) {
            if (card.isLeft() && card.getSize() == cardSize) {
                card.setLeft(false);
                return;
            }
        }
    }

    private List<Card> _initCards() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(1,true));
        cards.add(new Card(1,true));
        cards.add(new Card(2,true));
        cards.add(new Card(2,true));
        cards.add(new Card(3,true));
        cards.add(new Card(3,true));
        return cards;
    }

    public TicTacToeStateResponse joinAGame(String id) throws JsonProcessingException {
        GameState gameInfo = repository.get(id);
        gameInfo.setStatus(GameStatus.P1);
        repository.update(gameInfo);
        return mapToResponse(gameInfo,2);
    }

    private List<BoxState> _initializeListOfBoxes() {
        List<BoxState> boxStates = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            boxStates.add(new BoxState(true,new ArrayList<>(),new ArrayList<>()));
        }
        return boxStates;
    }

    public TicTacToeStateResponse restart(String gameId, int player) throws JsonProcessingException {
        GameState gameInfo = repository.get(gameId);
        gameInfo.setBoxes(_initializeListOfBoxes());
        if(!(gameInfo.getStatus().equals(GameStatus.P1) || gameInfo.getStatus().equals(GameStatus.P2))){
            gameInfo.setStatus(gameLogic.getStatusFromPlayer(player));
        }
        repository.update(gameInfo);
        return mapToResponse(gameInfo,player);
    }
}
