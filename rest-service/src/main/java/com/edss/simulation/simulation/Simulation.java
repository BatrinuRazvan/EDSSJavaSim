package com.edss.simulation.simulation;

import java.util.List;
import java.util.Random;

import com.edss.simulation.helperclasses.AgeGroup;
import com.edss.simulation.helperclasses.SimHelper;

public class Simulation {

	private int dayCounter;
	private int simulationPeriodDays;
	private int numberOfAgents;
	private int childrenCount;
	private int adultsCount;
	private int elderlyCount;
	private int sickAgents;
	private int deadAgents;
	private int recoveredAgents;
	private int numberOfSickAtStart;
	private List<Agent> agents = null;

	public Simulation(int simulationPeriodMonths, int numberOfAgents, int numberOfSickAtStart) {
		this.dayCounter = 1;
		this.simulationPeriodDays = SimHelper.initMonthsToDays(simulationPeriodMonths);
		this.numberOfAgents = numberOfAgents;
		this.childrenCount = numberOfAgents * 187 / 1000;
		this.elderlyCount = numberOfAgents * 198 / 1000;
		this.adultsCount = numberOfAgents - childrenCount - elderlyCount;
		this.numberOfSickAtStart = numberOfSickAtStart;

	}

	public void runSimulation() {

		initializeAgents();

		while (dayCounter != simulationPeriodDays) {

			for (Agent agent : agents) {
				if (checkIfAgentDies(agent)) {
					continue;
				}
				if (agent.willHeal()) {
					sickAgents -= 1;
					recoveredAgents += 1;
				}
				agent.checkIfInfectious();
				agent.checkIfAbleToMeet();
				if (agent.ableToMeet()) {

				}
			}

			dayCounter += 1;
		}

	}

	private boolean checkIfAgentDies(Agent agent) {
		if (agent.isSick()) {
			Random kill = new Random();
			if (kill.nextInt(0, 100) < agent.getDisease().getChanceToKill()) {
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
				agent = new Agent(true, AgeGroup.CHILD, immunity);
			} else {
				agent = new Agent(false, AgeGroup.CHILD, immunity);
			}
			agents.add(agent);
		}

		for (int i = 0; i < adultsCount; i++) {
			Agent agent;
			int immunity = rand.nextInt(70 - 35) + 35;
			if (i < sickAdults) {
				agent = new Agent(true, AgeGroup.ADULT, immunity);
			} else {
				agent = new Agent(false, AgeGroup.ADULT, immunity);
			}
			agents.add(agent);
		}

		for (int i = 0; i < elderlyCount; i++) {
			Agent agent;
			int immunity = rand.nextInt(40 - 5) + 5;
			if (i < sickElderly) {
				agent = new Agent(true, AgeGroup.ELDER, immunity);
			} else {
				agent = new Agent(false, AgeGroup.ELDER, immunity);
			}
			agents.add(agent);
		}
	}

}
