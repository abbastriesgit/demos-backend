package com.abbasali.demosbackend.robot_simulator.interpreter.simple;

import com.abbasali.demosbackend.robot_simulator.interpreter.Interpreter;
import com.abbasali.demosbackend.robot_simulator.interpreter.models.InterpreterResponse;
import com.abbasali.demosbackend.robot_simulator.interpreter.models.ValidationResponse;
import com.abbasali.demosbackend.robot_simulator.interpreter.models.ValidationResult;
import com.abbasali.demosbackend.robot_simulator.interpreter.simple.actions.RobotAction;
import com.abbasali.demosbackend.robot_simulator.interpreter.simple.actions.RobotActionName;
import com.abbasali.demosbackend.robot_simulator.models.Mode;
import com.abbasali.demosbackend.robot_simulator.models.RobotSimulatorResponse;
import com.abbasali.demosbackend.robot_simulator.models.RobotState;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SimpleInterpreter implements Interpreter {

    Map<String, RobotAction> commandActions;
    public SimpleInterpreter(@Qualifier("commandActions") Map<String, RobotAction> commandActions) {
        this.commandActions = commandActions;
    }

    @Override
    public InterpreterResponse simulate(List<String> commands, int rows, int columns, Mode mode) throws IllegalArgumentException {
       List<RobotState> response = new ArrayList<>();
        for (String command : commands) {
            if(command.isBlank()) //ignore empty commands
                continue;
            String[] exp = command.split(" ");
            if(exp.length == 0)//ignore empty commands
                continue;
            if(!commandActions.containsKey(exp[0]))
                throw new IllegalArgumentException("Invalid command : " + exp[0]);
            RobotAction action = commandActions.get(exp[0]);
            RobotState newState;
            if(response.size()==0)//first command initializes robot state
                newState =action.performAction(null,exp);
            else                //
                newState = action.performAction(response.get(response.size()-1),exp);
            _applyMode(newState,rows,columns,mode);
            response.add(newState);
        }
        return InterpreterResponse.builder()
                .states(response)
                .build();
    }

    private void _applyMode(RobotState newState, int rows, int columns, Mode mode) {
        //todo:: more modes can be added later
        switch (mode){
            case BOUNDED:
            default:
                if(newState.getX()>=columns)
                    newState.setX(columns-1);
                if(newState.getY()>=rows)
                    newState.setY(rows-1);
                if(newState.getX()<0)
                    newState.setX(0);
                if(newState.getY()<0)
                    newState.setY(0);
        }
    }

    @Override
    public ValidationResponse validate(List<ValidationResult> requests) throws IllegalArgumentException {
        List<ValidationResult> response = new ArrayList<>();
        boolean allValid = true;
        if(requests.size()==0)
            return ValidationResponse.builder()
                    .validationResults(response)
                    .allValid(true)
                    .build();
        ValidationResult firstCommand = commandActions.get("POSITION").validate(requests.get(0));
        if(!firstCommand.isValid()){
            firstCommand.setError("First Command should be POSITION");
            response.add(firstCommand);
            return ValidationResponse.builder()
                    .validationResults(response)
                    .allValid(false)
                    .build();
        }
        for (ValidationResult request : requests) {
            String[] exp = request.getCommand().split(" ");
            if(exp.length == 0 || request.getCommand().trim().isBlank()){
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
