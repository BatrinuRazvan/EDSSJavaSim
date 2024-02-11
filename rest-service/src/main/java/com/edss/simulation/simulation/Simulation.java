package com.edss.simulation.simulation;

import java.util.ArrayList;
import java.util.Collections;
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
	private int susceptibleAgents;
	private int sickAgents;
	private int deadAgents;
	private int recoveredAgents;
	private int numberOfSickAtStart;
	private List<Agent> agents = new ArrayList<>();
	private List<Agent> outsideAgents = new ArrayList<>();
	private Hospital hospital;

	public Simulation(int simulationPeriodMonths, int numberOfAgents, int numberOfSickAtStart, Hospital hospital) {
		this.dayCounter = 1;
		this.simulationPeriodDays = SimHelper.initMonthsToDays(simulationPeriodMonths);
		this.numberOfAgents = numberOfAgents;
		this.childrenCount = numberOfAgents * 187 / 1000;
		this.elderlyCount = numberOfAgents * 198 / 1000;
		this.adultsCount = numberOfAgents - childrenCount - elderlyCount;
		this.numberOfSickAtStart = numberOfSickAtStart;
		this.hospital = hospital;
		this.susceptibleAgents = numberOfAgents - numberOfSickAtStart;
	}

	public void runSimulation() {

		initializeAgents();

		while (dayCounter != simulationPeriodDays) {

			for (Agent agent : agents) {
				if (checkIfAgentDies(agent)) {
					hospital.removeAgent(agent);
					deadAgents += 1;
					sickAgents -= 1;
					continue;
				}
				if (agent.willHeal()) {
					sickAgents -= 1;
					recoveredAgents += 1;
				}
				if (agent.checkNeedsHospitalization()) {
					hospital.getNormalBedAgents().add(agent);
				}
				if (agent.checkNeedsIcu()) {
					hospital.getIcuBedAgents().add(agent);
					hospital.getNormalBedAgents().remove(agent);
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
			Random infection = new Random();
			if (!SimHelper.isOneOfAgentsSick(agent1, agent2)) {
				continue;
			}
			if (agent1.isInfectious() && infection.nextFloat(0, 100) <= agent1.getDisease().getChanceToTransmit()) {
				Disease disease = new Disease();
				agent2.setDisease(disease);
			}
			if (agent2.isInfectious() && infection.nextFloat(0, 100) <= agent2.getDisease().getChanceToTransmit()) {
				Disease disease = new Disease();
				agent1.setDisease(disease);
			}

		}
	}

	private boolean checkIfAgentDies(Agent agent) {
		if (agent.isSick()) { // check added for faster processing
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
