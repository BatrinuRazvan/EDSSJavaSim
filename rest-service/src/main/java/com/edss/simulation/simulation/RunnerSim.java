package com.edss.simulation.simulation;

import com.edss.models.helperclasses.DbHelper;

public class RunnerSim {

	public static void main(String[] args) {
//		Simulation sim = new Simulation(10, 500000, 100);
//		SimHelper.initSimulatinDatabase();
//		sim.runSimulation();
//		DbHelper.initMarkersDatabase();
		DbHelper.initNotificationsDatabase();
	}

}
