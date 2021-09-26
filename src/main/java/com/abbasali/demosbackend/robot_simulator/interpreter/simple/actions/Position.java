package com.abbasali.demosbackend.robot_simulator.interpreter.simple.actions;

import com.abbasali.demosbackend.robot_simulator.interpreter.models.ValidationResult;
import com.abbasali.demosbackend.robot_simulator.models.RobotDirection;
import com.abbasali.demosbackend.robot_simulator.models.RobotState;
import org.springframework.stereotype.Component;

@Component
public class Position implements RobotAction{
    @Override
    public RobotState performAction(RobotState initState, Object... args) throws IllegalArgumentException {
        try {
            int x = Integer.parseInt((String) args[1]);
            int y = Integer.parseInt((String) args[2]);
            RobotDirection direction = RobotDirection.valueOf((String) args[3]);
            return RobotState.builder()
                    .x(x)
                    .y(y)
                    .direction(direction)
                    .build();
        }
        catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
    @Override
    public ValidationResult validate(ValidationResult request) throws IllegalArgumentException {
        String[] exp = request.getCommand().split(" ");
        try {
            if(exp.length == 4 && exp[0].equals("POSITION")){
                Integer.parseInt(exp[1]);
                Integer.parseInt(exp[2]);
                RobotDirection.valueOf(exp[3]);
                request.setValid(true);
                return request;
            }
        }catch (Exception e){
            request.setError(e.getMessage());
        }
        request.setValid(false);
        request.setError("Invalid Position command");
        return request;
    }
}
