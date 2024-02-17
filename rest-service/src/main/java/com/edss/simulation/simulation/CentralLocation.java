package com.edss.simulation.simulation;

import java.util.ArrayList;
import java.util.List;

import com.edss.simulation.agents.Agent;
import com.edss.simulation.helperclasses.SimConstants;

public class CentralLocation {

	private static CentralLocation location = null;

	public CentralLocation() {
	}

	public static void initCentralLocation() {
		location = new CentralLocation();
	}

	public static synchronized CentralLocation getCentralLocation() {
		return location;
	}

	public void meetAgentsAtCentralLocation(List<Agent> outsideAgents) {

		while (outsideAgents.size() >= SimConstants.agentsAtCentralLocation_atSameTime) {
			List<Agent> agentsAtCurrentLocation = new ArrayList<Agent>();
			int totalInfectiousAtLocation = 0;
			for (int increment = 0; increment < SimConstants.agentsAtCentralLocation_atSameTime; increment++) {
				Agent current = outsideAgents.get(0);
				agentsAtCurrentLocation.add(current);
				outsideAgents.remove(current);
				if (current.isInfectious()) {
					totalInfectiousAtLocation += 1;
				}
			}
			for (int i = 0; i < totalInfectiousAtLocation; i++) {
				agentsAtCurrentLocation.forEach(agent -> agent.checkIfGetsSickAtCentralLocation());
			}
		}
	}

}
