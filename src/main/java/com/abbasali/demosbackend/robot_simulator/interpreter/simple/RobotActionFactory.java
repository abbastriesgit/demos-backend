package com.abbasali.demosbackend.robot_simulator.interpreter.simple;

import com.abbasali.demosbackend.robot_simulator.interpreter.simple.actions.*;
import org.springframework.stereotype.Component;

@Component
public class RobotActionFactory {
    MoveForward moveForward;
    TurnRight turnRight;
    Position position;
    TurnAround turnAround;
    Wait wait;

    public RobotActionFactory(MoveForward moveForward, TurnRight turnRight, Position position, TurnAround turnAround, Wait wait) {
        this.moveForward = moveForward;
        this.turnRight = turnRight;
        this.position = position;
        this.turnAround = turnAround;
        this.wait = wait;
    }

    public RobotAction getAction(RobotActionName name) throws IllegalArgumentException{
        switch (name){
            case MOVE:
                return moveForward;
            case TURN_RIGHT:
                return turnRight;
            case POSITION:
                return position;
            case TURN_AROUND:
                return turnAround;
            case WAIT:
                return wait;
        }
        throw new IllegalArgumentException("No such Move");
    }
}
