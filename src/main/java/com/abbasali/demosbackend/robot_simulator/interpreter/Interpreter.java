package com.abbasali.demosbackend.robot_simulator.interpreter;

import com.abbasali.demosbackend.robot_simulator.interpreter.models.InterpreterResponse;
import com.abbasali.demosbackend.robot_simulator.interpreter.models.ValidationResponse;
import com.abbasali.demosbackend.robot_simulator.interpreter.models.ValidationResult;
import com.abbasali.demosbackend.robot_simulator.models.Mode;

import java.util.List;

public interface Interpreter {
    InterpreterResponse simulate(List<String> command, int rows, int columns, Mode mode) throws IllegalArgumentException;
    ValidationResponse validate(List<ValidationResult> requests) throws IllegalArgumentException;
}
