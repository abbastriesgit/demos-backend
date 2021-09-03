package com.abbasali.demosbackend.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoxStateResponse {
    boolean isEmpty;
    int size;
    int player;
}
