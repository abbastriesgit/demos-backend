package com.abbasali.demosbackend.robot_simulator.models;

import lombok.Data;

import java.util.List;

@Data
public class RobotSimulationRequest {
    List<String> commands;
    boolean getIntermediateStates;

}
