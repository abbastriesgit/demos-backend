package com.abbasali.demosbackend.robot_simulator;

import com.abbasali.demosbackend.robot_simulator.interpreter.models.ValidationResponse;
import com.abbasali.demosbackend.robot_simulator.models.RobotSimulationRequest;
import com.abbasali.demosbackend.robot_simulator.models.RobotSimulatorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("robot")
@CrossOrigin(origins = "*")
public class RobotController {

    RobotSimulatorService service;

    public RobotController(RobotSimulatorService service) {
        this.service = service;
    }

    @PostMapping("simulate")
    public ResponseEntity<RobotSimulatorResponse> simulate(@RequestBody RobotSimulationRequest request){
        return ResponseEntity.ok(service.simulateRobot(request));
    }
    @PostMapping("validate")
    public ResponseEntity<ValidationResponse> validate(@RequestBody RobotSimulationRequest request){
        return ResponseEntity.ok(service.validate(request));
    }
}
