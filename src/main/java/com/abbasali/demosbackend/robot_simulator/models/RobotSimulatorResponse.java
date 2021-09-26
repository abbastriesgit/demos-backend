package com.abbasali.demosbackend.robot_simulator.models;

import com.abbasali.demosbackend.robot_simulator.interpreter.models.ValidationResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class RobotSimulatorResponse  {
    List<RobotState> positions;
    String message;
    ValidationResponse validationResponse;
    boolean isSuccess;
}
