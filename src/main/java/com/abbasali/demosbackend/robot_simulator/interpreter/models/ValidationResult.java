package com.abbasali.demosbackend.robot_simulator.interpreter.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationResult {
    String command;
    int index ;
    String error;
    boolean isValid;
}
