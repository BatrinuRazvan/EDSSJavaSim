package com.edss.restservice.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.edss.models.ModifyableParameters;
import com.edss.models.SimulationDayData;
import com.edss.models.helperclasses.DbHelper;
import com.edss.models.helperclasses.HelperMethods;
import com.edss.simulation.helperclasses.SimConstants;
import com.edss.simulation.helperclasses.SimHelper;
import com.edss.simulation.simulation.Simulation;

@RestController
public class SimulationController {

	private Simulation sim;
	private int simPeriod = 9;
	private int numberOfAgents = 100000;
	private int numberOfSickAtStart = 100;

	@PostMapping("/startSimulation")
	public String startSimulation() {

		SimHelper.initSimulatinDatabase();
		sim = new Simulation(simPeriod, numberOfAgents, numberOfSickAtStart);
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

	@PostMapping("/updateParameters")
	public ResponseEntity<Object> updateParameters(@RequestBody ModifyableParameters params) {

		SimConstants.updateModifyableValues(params);
		simPeriod = HelperMethods.isZeroValue(simPeriod, params.getSimPeriodParam());
		numberOfAgents = HelperMethods.isZeroValue(numberOfAgents, params.getNumberOfAgentsParam());
		numberOfSickAtStart = HelperMethods.isZeroValue(numberOfSickAtStart, params.getNumberOfSickAtStartParam());
		System.out.println("sent");

		return ResponseEntity.ok().build();
	}

}
