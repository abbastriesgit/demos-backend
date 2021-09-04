package com.abbasali.demosbackend.model;

import com.abbasali.demosbackend.ttt_ai.Algorithm;
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
