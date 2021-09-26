package com.abbasali.demosbackend.robot_simulator.interpreter.simple.actions;

import com.abbasali.demosbackend.robot_simulator.interpreter.models.ValidationResult;
import com.abbasali.demosbackend.robot_simulator.models.RobotState;
import org.springframework.stereotype.Component;

@Component
public class Wait implements RobotAction {
    @Override
    public RobotState performAction(RobotState initState, Object... args) throws IllegalArgumentException {
        return initState;
    }

    @Override
    public ValidationResult validate(ValidationResult request) throws IllegalArgumentException {
        String[] exp = request.getCommand().split(" ");
        if(exp.length == 1 && exp[0].equals("WAIT")){
            request.setValid(true);
            return request;
        }
        request.setValid(false);
        request.setError("Invalid WAIT command");
        return request;
    }
}
