package com.abbasali.demosbackend.adv_tic_tac_toe.ai;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AiResponse {
    int score;
    int index;
    int cardSize;
}
