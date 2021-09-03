package com.abbasali.demosbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Card {
    int size ;//1,2,3
    boolean isLeft;
}
