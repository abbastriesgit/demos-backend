package com.abbasali.demosbackend.robot_simulator.interpreter.simple;

import com.abbasali.demosbackend.robot_simulator.interpreter.Interpreter;
import com.abbasali.demosbackend.robot_simulator.interpreter.models.InterpreterResponse;
import com.abbasali.demosbackend.robot_simulator.interpreter.models.ValidationResponse;
import com.abbasali.demosbackend.robot_simulator.interpreter.models.ValidationResult;
import com.abbasali.demosbackend.robot_simulator.interpreter.simple.actions.RobotAction;
import com.abbasali.demosbackend.robot_simulator.interpreter.simple.actions.RobotActionName;
import com.abbasali.demosbackend.robot_simulator.models.RobotSimulatorResponse;
import com.abbasali.demosbackend.robot_simulator.models.RobotState;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SimpleInterpreter implements Interpreter {

    Map<String, RobotAction> commandActions;
    public SimpleInterpreter(RobotActionFactory factory) {
        commandActions = new HashMap<>();
        commandActions.put("FORWARD",factory.getAction(RobotActionName.MOVE));
        commandActions.put("RIGHT",factory.getAction(RobotActionName.TURN_RIGHT));
        commandActions.put("POSITION",factory.getAction(RobotActionName.POSITION));
    }

    @Override
    public InterpreterResponse simulate(List<String> commands) throws IllegalArgumentException {
       List<RobotState> response = new ArrayList<>();
        for (String command : commands) {
            String[] exp = command.split(" ");
            if(exp.length == 0)
                continue;
            if(!commandActions.containsKey(exp[0]))
                throw new IllegalArgumentException("Invalid command : " + exp[0]);
            RobotAction action = commandActions.get(exp[0]);
            if(response.size()==0)
                response.add(action.performAction(null,exp));
            else
                response.add(action.performAction(response.get(response.size()-1),exp));
        }
        return InterpreterResponse.builder()
                .states(response)
                .build();
    }

    @Override
    public ValidationResponse validate(List<ValidationResult> requests) throws IllegalArgumentException {
        List<ValidationResult> response = new ArrayList<>();
        boolean allValid = true;
        for (ValidationResult request : requests) {
            String[] exp = request.getCommand().split(" ");
            if(exp.length == 0){
                response.add(request);
                continue;
            }
            if(!commandActions.containsKey(exp[0]))
            {
                request.setValid(false);
                request.setError("Unknown command");
                response.add(request);
                allValid = false;
                continue;
            }
            RobotAction action = commandActions.get(exp[0]);
            ValidationResult result = action.validate(request);
            allValid = result.isValid() && allValid;
            response.add(result);
        }
        return ValidationResponse.builder()
                .validationResults(response)
                .allValid(allValid)
                .build();
    }

//    POSITION 1 3 EAST //sets the initial position for the robot
//    FORWARD 3 //lets the robot do 3 steps forward
//    WAIT //lets the robot do nothing
//    TURNAROUND         //lets the robot turn around
//    RIGHT //lets the robot turn right
}
