package com.abbasali.demosbackend.robot_simulator.interpreter.simple;

import com.abbasali.demosbackend.robot_simulator.interpreter.simple.actions.*;
import org.springframework.stereotype.Component;

@Component
public class RobotActionFactory {
    MoveForward moveForward;
    TurnRight turnRight;
    Position position;

    public RobotActionFactory(MoveForward moveForward, TurnRight turnRight, Position position) {
        this.moveForward = moveForward;
        this.turnRight = turnRight;
        this.position = position;
    }

    public RobotAction getAction(RobotActionName name) throws IllegalArgumentException{
        switch (name){
            case MOVE:
                return moveForward;
            case TURN_RIGHT:
                return turnRight;
            case POSITION:
                return position;
        }
        throw new IllegalArgumentException("No such Move");
    }
}
