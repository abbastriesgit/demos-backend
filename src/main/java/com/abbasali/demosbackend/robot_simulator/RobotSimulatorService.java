package com.abbasali.demosbackend.robot_simulator;

import com.abbasali.demosbackend.robot_simulator.interpreter.Interpreter;
import com.abbasali.demosbackend.robot_simulator.interpreter.models.InterpreterResponse;
import com.abbasali.demosbackend.robot_simulator.interpreter.models.ValidationResponse;
import com.abbasali.demosbackend.robot_simulator.interpreter.models.ValidationResult;
import com.abbasali.demosbackend.robot_simulator.models.RobotSimulationRequest;
import com.abbasali.demosbackend.robot_simulator.models.RobotSimulatorResponse;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class RobotSimulatorService {

    Interpreter interpreter;

    public RobotSimulatorService(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    public RobotSimulatorResponse simulateRobot(RobotSimulationRequest request){
        ValidationResponse validationResponse = validate(request);
        if(!validationResponse.isAllValid())
            return RobotSimulatorResponse.builder()
                    .validationResponse(validationResponse)
                    .isSuccess(false)
                    .message("Compilation Error")
                    .build();

        InterpreterResponse response = interpreter.simulate(request.getCommands());
        return RobotSimulatorResponse.builder()
                .initState(response.getStates().get(0))
                .intermediateStates(response.getStates())
                .build();
    }

    public ValidationResponse validate(RobotSimulationRequest request) {
        AtomicInteger index = new AtomicInteger();
        return interpreter.validate(
                request.getCommands().stream().map(
                        command->
                                ValidationResult.builder()
                                        .command(command)
                                        .index(index.getAndIncrement())
                                        .build())
                        .collect(Collectors.toList())
        );
    }
}
