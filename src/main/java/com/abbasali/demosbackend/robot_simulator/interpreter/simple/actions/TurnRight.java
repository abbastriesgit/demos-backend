package com.abbasali.demosbackend.robot_simulator.interpreter.simple.actions;

import com.abbasali.demosbackend.robot_simulator.interpreter.models.ValidationResult;
import com.abbasali.demosbackend.robot_simulator.models.RobotDirection;
import com.abbasali.demosbackend.robot_simulator.models.RobotState;
import org.springframework.stereotype.Component;

@Component
public class TurnRight implements RobotAction{
    @Override
    public RobotState performAction(RobotState initState, Object... args) throws IllegalArgumentException {
        RobotDirection newDirection = null;
        switch (initState.getDirection()){
            case NORTH:
                newDirection = RobotDirection.EAST;
                break;
            case SOUTH:
                newDirection = RobotDirection.WEST;
                break;
            case WEST:
                newDirection = RobotDirection.NORTH;
                break;
            case EAST:
                newDirection = RobotDirection.SOUTH;
                break;
        }
        return RobotState.builder()
                .x(initState.getX())
                .y(initState.getY())
                .direction(newDirection)
                .build();
    }
    @Override
    public ValidationResult validate(ValidationResult request) throws IllegalArgumentException {
        String[] exp = request.getCommand().split(" ");
        if(exp.length == 1 && exp[0].equals("RIGHT")){
            request.setValid(true);
            return request;
        }
        request.setValid(false);
        request.setError("Invalid RIGHT command");
        return request;
    }
}
