package com.edss.simulation.simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.edss.simulation.agents.AdultAgent;
import com.edss.simulation.agents.Agent;
import com.edss.simulation.agents.ChildAgent;
import com.edss.simulation.agents.ElderAgent;
import com.edss.simulation.helperclasses.SimConstants;
import com.edss.simulation.helperclasses.SimHelper;

public class Simulation {

	private int dayCounter;
	private int simulationPeriodDays;
	private int numberOfAgents;
	private int childrenCount;
	private int adultsCount;
	private int elderlyCount;
	private int susceptibleAgents;
	private int sickAgents;
	private int deadAgents;
	private int recoveredAgents;
	private int numberOfSickAtStart;
	private List<Agent> agents = new ArrayList<>();
	private List<Agent> outsideAgents = new ArrayList<>();

	public Simulation(int simulationPeriodMonths, int numberOfAgents, int numberOfSickAtStart) {
		this.dayCounter = 1;
		this.simulationPeriodDays = SimHelper.initMonthsToDays(simulationPeriodMonths);
		this.numberOfAgents = numberOfAgents;
		this.childrenCount = numberOfAgents * 187 / 1000;
		this.elderlyCount = numberOfAgents * 198 / 1000;
		this.adultsCount = numberOfAgents - childrenCount - elderlyCount;
		this.numberOfSickAtStart = numberOfSickAtStart;
		this.susceptibleAgents = numberOfAgents - numberOfSickAtStart;
	}

	public void runSimulation() {

		initializeAgents();
		SimHelper.initalizeHospital(numberOfAgents);

		while (dayCounter != simulationPeriodDays) {

			for (Agent agent : agents) {
				if (checkIfAgentDies(agent)) {
					Hospital.getHospital().removeAgent(agent);
					updateGlobalVariables(SimConstants.ADD_DEAD, SimConstants.REMOVE_SICK);
					continue;
				}
				if (agent.willHeal()) {
					updateGlobalVariables(SimConstants.REMOVE_SICK, SimConstants.ADD_RECOVERED);
				}

				agent.updateStateOfDisease();
				agent.checkNeedsHospitalization();

				if (agent.checkNeedsIcu()) {
					Hospital.getHospital().removeAgent(agent);
					Hospital.getHospital().addIcuBedAgent(agent);
				}
				agent.checkIfInfectious();
				agent.checkIfAbleToMeet();
				if (agent.ableToMeet()) {
					Random goOut = new Random();
					if (agent.getChanceToGoOut() >= goOut.nextInt(0, 100)) {
						outsideAgents.add(agent);
					}
				}
			}

			meetAgents();

			dayCounter += 1;
		}

	}

	private void meetAgents() {
		Collections.shuffle(outsideAgents);
		for (int iterator = 0; iterator < outsideAgents.size() / 2; iterator++) {

			Agent agent1 = outsideAgents.get(0);
			Agent agent2 = outsideAgents.get(1);
			Random infectionAndImmunity = new Random();

			if (!SimHelper.isOneOfAgentsSick(agent1, agent2)) {
				continue;
			}
			if (agent1.isInfectious() && infectionAndImmunity.nextFloat(0, 100) < agent1.getChanceToTransmitDisease()) {
				if (infectionAndImmunity.nextFloat(0, 100) > agent2.getImmunity()) {
					Disease disease = new Disease();
					agent2.setDisease(disease);
					updateGlobalVariables(SimConstants.REMOVE_SUSCEPTIBLE, SimConstants.ADD_SICK);
				}
			}

			if (agent2.isInfectious() && infectionAndImmunity.nextFloat(0, 100) < agent2.getChanceToTransmitDisease()) {
				if (infectionAndImmunity.nextFloat(0, 100) > agent2.getImmunity()) {
					Disease disease = new Disease();
					agent1.setDisease(disease);
					updateGlobalVariables(SimConstants.REMOVE_SUSCEPTIBLE, SimConstants.ADD_SICK);
				}
			}

		}
	}

	private boolean checkIfAgentDies(Agent agent) {
		if (agent.isSick()) { // check added for faster processing
			Random kill = new Random();
			if (kill.nextInt(0, 100) < agent.getChanceToBeKilled()) {
				agents.remove(agent);
				sickAgents -= 1;
				deadAgents += 1;
				return true;
			}
		}
		return false;
	}

	private void initializeAgents() {

		int sickChildren = numberOfSickAtStart * 187 / 1000;
		int sickElderly = numberOfSickAtStart * 198 / 1000;
		int sickAdults = numberOfSickAtStart - sickChildren - sickElderly;
		Random rand = new Random();

		for (int i = 0; i < childrenCount; i++) {
			Agent agent;
			int immunity = rand.nextInt(40 - 10) + 10;
			if (i < sickChildren) {
				agent = new ChildAgent(true);
			} else {
				agent = new ChildAgent(false);
			}
			agents.add(agent);
		}

		for (int i = 0; i < adultsCount; i++) {
			Agent agent;
			int immunity = rand.nextInt(70 - 35) + 35;
			if (i < sickAdults) {
				agent = new AdultAgent(true);
			} else {
				agent = new AdultAgent(false);
			}
			agents.add(agent);
		}

		for (int i = 0; i < elderlyCount; i++) {
			Agent agent;
			int immunity = rand.nextInt(40 - 5) + 5;
			if (i < sickElderly) {
				agent = new ElderAgent(true);
			} else {
				agent = new ElderAgent(false);
			}
			agents.add(agent);
		}
	}

	private void updateGlobalVariables(String... updates) {
		for (String updateGlobal : updates) {
			switch (updateGlobal) {
			case "ADD_SUSCEPTIBLE":
				susceptibleAgents += 1;
				break;
			case "ADD_DEAD":
				deadAgents += 1;
				break;
			case "ADD_SICK":
				sickAgents += 1;
				break;
			case "ADD_RECOVERED":
				recoveredAgents += 1;
			case "REMOVE_SUSCEPTIBLE":
				susceptibleAgents -= 1;
				break;
			case "REMOVE_DEAD":
				deadAgents -= 1;
				break;
			case "REMOVE_SICK":
				sickAgents -= 1;
				break;

			default:
				throw new IllegalArgumentException("Unexpected value: " + updateGlobal);
			}
		}

	}
}
