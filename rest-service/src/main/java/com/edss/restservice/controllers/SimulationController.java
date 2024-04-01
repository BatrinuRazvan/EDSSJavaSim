package com.edss.restservice.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edss.simulation.helperclasses.SimHelper;
import com.edss.simulation.simulation.Simulation;

@RestController
public class SimulationController {

	Simulation sim = new Simulation(8, 10000, 100);

	@PostMapping("/startSimulation")
	public String startSimulation() {

		SimHelper.initSimulatinDatabase();
		sim = new Simulation(9, 100000, 100);
		sim.runSimulation();

		return "Simulation started";
	}

	@PostMapping("/pauseSimulation")
	public String pauseSimulation() {

		SimHelper.pauseSimulation();

		return "Simulation paused";
	}

	@PostMapping("/resumeSimulation")
	public String resumeSimulation() {

		SimHelper.resumeSimulation();

		return "Simulation resumed";
	}

	@PostMapping("/resetSimulation")
	public String resetSimulation() {

		sim.resetSimulation();
		SimHelper.resetSimulationDatabase();
		SimHelper.resumeSimulation();

		return "Simulation reset.";
	}

}
