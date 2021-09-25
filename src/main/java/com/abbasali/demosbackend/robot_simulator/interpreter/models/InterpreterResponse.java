package com.abbasali.demosbackend.robot_simulator.interpreter.models;

import com.abbasali.demosbackend.robot_simulator.models.RobotState;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class InterpreterResponse {
    List<RobotState> states;
    String error;
    boolean isSuccess;
}
