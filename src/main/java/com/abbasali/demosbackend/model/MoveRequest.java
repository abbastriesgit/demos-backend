package com.abbasali.demosbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MoveRequest {
    String gameId;
    int player;
    int boxIndex;
    int boxSize;
}
