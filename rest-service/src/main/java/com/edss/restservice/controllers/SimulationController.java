package com.edss.restservice.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edss.models.SimulationDayData;
import com.edss.models.helperclasses.DbHelper;
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

	@GetMapping("/getSimulationData")
	public List<SimulationDayData> getDecisionResponses() {
		return DbHelper.getSimulationData();
	}

}
