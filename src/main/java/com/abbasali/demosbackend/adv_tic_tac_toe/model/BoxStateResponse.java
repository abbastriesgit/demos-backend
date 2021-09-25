package com.abbasali.demosbackend.adv_tic_tac_toe.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoxStateResponse {
    boolean isEmpty;
    int size;
    int player;
}
