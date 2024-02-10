package com.edss.simulation.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimulationController {
	
	@PostMapping("/startSimulation")
    public String startSimulation() {
        // Logic to start the simulation
        // ...

        return "Simulation started";
    }

}
