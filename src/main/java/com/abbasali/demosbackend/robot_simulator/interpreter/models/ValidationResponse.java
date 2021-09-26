package com.abbasali.demosbackend.robot_simulator.interpreter.models;


import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationResponse {
    List<ValidationResult> validationResults;
    boolean allValid;
}

