package com.abbasali.demosbackend.adv_tic_tac_toe.model;

import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameState {
    String id;
    List<BoxState> boxes;
    GameStatus status;
}
