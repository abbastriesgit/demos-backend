package com.abbasali.demosbackend.robot_simulator.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RobotState {
    private int x;
    private int y;
    private RobotDirection direction;
}
