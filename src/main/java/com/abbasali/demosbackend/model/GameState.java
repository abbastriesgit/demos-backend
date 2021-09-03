package com.abbasali.demosbackend.model;

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
