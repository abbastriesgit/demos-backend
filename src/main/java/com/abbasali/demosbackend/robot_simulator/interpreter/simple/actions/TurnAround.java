package com.abbasali.demosbackend.robot_simulator.interpreter.simple.actions;

import com.abbasali.demosbackend.robot_simulator.interpreter.models.ValidationResult;
import com.abbasali.demosbackend.robot_simulator.models.RobotDirection;
import com.abbasali.demosbackend.robot_simulator.models.RobotState;
import org.springframework.stereotype.Component;

@Component
public class TurnAround implements RobotAction{

    @Override
    public RobotState performAction(RobotState initState, Object... args) throws IllegalArgumentException {
        RobotDirection direction = initState.getDirection();
        switch (initState.getDirection()){
            case EAST:
                direction = RobotDirection.WEST;
                break;
            case WEST:
                direction = RobotDirection.EAST;
                break;
            case NORTH:
                direction = RobotDirection.SOUTH;
                break;
            case SOUTH:
                direction = RobotDirection.NORTH;
                break;
        }
        return RobotState.builder()
                .x(initState.getX())
                .y(initState.getY())
                .direction(direction)
                .build();
    }

    @Override
    public ValidationResult validate(ValidationResult request) throws IllegalArgumentException {
        String[] exp = request.getCommand().split(" ");
        if(exp.length == 1 && exp[0].equals("TURNAROUND")){
            request.setValid(true);
            return request;
        }
        request.setValid(false);
        request.setError("Invalid TURNOVER command");
        return request;
    }
}
