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
       int steps =  Integer.parseInt((String) args[1]);
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
               .x(initState.getX() + xStep * steps)
               .y(initState.getY() + yStep * steps)
               .direction(initState.getDirection())
               .build();
    }

    @Override
    public ValidationResult validate(ValidationResult request) throws IllegalArgumentException {
        String[] exp = request.getCommand().split(" ");
        if(exp.length == 2 && exp[0].equals("FORWARD")){
            try {
                Integer.parseInt(exp[1]);
                request.setValid(true);
                return request;
            }
            catch (Exception ignored){
            }
        }
        request.setValid(false);
        request.setError("Invalid FORWARD command");
        return request;
    }
}
