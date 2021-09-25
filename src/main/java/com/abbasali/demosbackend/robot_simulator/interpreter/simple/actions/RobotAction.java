package com.abbasali.demosbackend.robot_simulator.interpreter.simple.actions;

import com.abbasali.demosbackend.robot_simulator.interpreter.models.ValidationResult;
import com.abbasali.demosbackend.robot_simulator.models.RobotState;

public interface RobotAction {
    RobotState performAction(RobotState initState, Object... args) throws IllegalArgumentException;
    ValidationResult validate(ValidationResult request) throws IllegalArgumentException;
}
