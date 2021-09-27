package com.abbasali.demosbackend;

import com.abbasali.demosbackend.robot_simulator.interpreter.models.ValidationResult;
import com.abbasali.demosbackend.robot_simulator.interpreter.simple.actions.MoveForward;
import com.abbasali.demosbackend.robot_simulator.models.RobotDirection;
import com.abbasali.demosbackend.robot_simulator.models.RobotState;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SimpleInterpreterForwardCommandTests {

    @Test
    public void testMoveCommandValidationSuccess(){
        MoveForward moveForward = new MoveForward();
        ValidationResult result = moveForward
                .validate(ValidationResult.builder()
                        .command("FORWARD 1")
                        .build()
                );
        assertThat(result.isValid()).isEqualTo(true);
    }
    @Test
    public void testMoveCommandValidationNoArgs(){
        MoveForward moveForward = new MoveForward();
        ValidationResult result = moveForward
                .validate(ValidationResult.builder()
                        .command("FORWARD")
                        .build()
                );
        assertThat(result.isValid()).isEqualTo(false);
    }
    @Test
    public void testMoveCommandValidationWrongArgs(){
        MoveForward moveForward = new MoveForward();
        ValidationResult result = moveForward
                .validate(ValidationResult.builder()
                        .command("FORWARD wwrong")
                        .build()
                );
        assertThat(result.isValid()).isEqualTo(false);
    }
    @Test
    public void testMoveCommandSimulate(){
        MoveForward moveForward = new MoveForward();
        RobotState state = RobotState.builder().x(1).y(2).direction(RobotDirection.EAST).build();
        RobotState nState = moveForward
                .performAction(state,"FORWARD","2");
        assertThat(nState.getX()).isEqualTo(3);
        assertThat(nState.getY()).isEqualTo(2);
    }
    @Test
    public void testInvalidMoveCommandSimulate(){
        MoveForward moveForward = new MoveForward();
        RobotState state = RobotState.builder().x(1).y(2).direction(RobotDirection.EAST).build();

        assertThrows(RuntimeException.class, () -> {
            moveForward.performAction(state,"FORWARD","X");
        });
        assertThrows(RuntimeException.class, () -> {
            moveForward.performAction(state,"FORWARD");
        });
    }
}
