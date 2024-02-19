package com.edss.simulation.simulation;

import com.edss.simulation.helperclasses.SimHelper;

public class RunnerSim {

	public static void main(String[] args) {
		Simulation sim = new Simulation(10, 100000, 100);
		SimHelper.initDatabase();
		sim.runSimulation();
	}

}
