package com.abbasali.demosbackend.ttt_ai;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AiResponse {
    int score;
    int index;
    int cardSize;
}
