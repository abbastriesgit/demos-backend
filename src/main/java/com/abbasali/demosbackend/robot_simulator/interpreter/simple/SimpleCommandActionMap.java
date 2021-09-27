package com.abbasali.demosbackend.robot_simulator.interpreter.simple;

import com.abbasali.demosbackend.robot_simulator.interpreter.simple.actions.RobotAction;
import com.abbasali.demosbackend.robot_simulator.interpreter.simple.actions.RobotActionName;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SimpleCommandActionMap {

    @Bean("commandActions")
    public Map<String, RobotAction> commandActions(RobotActionFactory factory){
        Map<String, RobotAction> commandActionMap;
            commandActionMap = new HashMap<>();
            commandActionMap.put("FORWARD",factory.getAction(RobotActionName.MOVE));
            commandActionMap.put("RIGHT",factory.getAction(RobotActionName.TURN_RIGHT));
            commandActionMap.put("POSITION",factory.getAction(RobotActionName.POSITION));
            commandActionMap.put("TURNAROUND",factory.getAction(RobotActionName.TURN_AROUND));
            commandActionMap.put("WAIT",factory.getAction(RobotActionName.WAIT));
        return commandActionMap;
    }
}
