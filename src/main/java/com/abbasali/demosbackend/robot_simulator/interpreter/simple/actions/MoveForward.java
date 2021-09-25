package com.abbasali.demosbackend.robot_simulator.interpreter.simple.actions;

import com.abbasali.demosbackend.robot_simulator.interpreter.models.ValidationResult;
import com.abbasali.demosbackend.robot_simulator.models.RobotState;
import org.springframework.stereotype.Component;

@Component
public class MoveForward implements RobotAction {
    @Override
    public RobotState performAction(RobotState initState, Object... args) throws IllegalArgumentException {
       int xStep = 0;
       int yStep = 0;
       switch (initState.getDirection()){
           case NORTH:
               yStep = -1;
               break;
           case SOUTH:
               yStep = 1;
               break;
           case WEST:
               xStep = -1;
               break;
           case EAST:
               xStep = 1;
               break;
       }
       return RobotState.builder()
               .x(initState.getX() + xStep)
               .y(initState.getY() + yStep)
               .direction(initState.getDirection())
               .build();
    }

    @Override
    public ValidationResult validate(ValidationResult request) throws IllegalArgumentException {
        String[] exp = request.getCommand().split(" ");
        if(exp.length == 1 && exp[0].equals("FORWARD")){
            request.setValid(true);
            return request;
        }
        request.setValid(false);
        request.setError("Invalid forward command");
        return request;
    }
}
