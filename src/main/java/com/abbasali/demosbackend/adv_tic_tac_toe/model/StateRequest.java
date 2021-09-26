package com.abbasali.demosbackend.adv_tic_tac_toe.model;

import lombok.Data;

@Data
public class StateRequest {
    private String gameId;
    private int player;
}
