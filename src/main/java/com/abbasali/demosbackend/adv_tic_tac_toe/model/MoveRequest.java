package com.abbasali.demosbackend.adv_tic_tac_toe.model;

import com.abbasali.demosbackend.adv_tic_tac_toe.ai.Algorithm;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MoveRequest {
    String gameId;
    int player;
    int boxIndex;
    int boxSize;
    Algorithm algorithm;
}
