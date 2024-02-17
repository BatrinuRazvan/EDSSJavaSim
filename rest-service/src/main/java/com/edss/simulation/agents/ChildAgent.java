package com.edss.simulation.agents;

import java.util.Random;

import com.edss.simulation.helperclasses.AgeGroup;
import com.edss.simulation.helperclasses.SimConstants;

public class ChildAgent extends Agent {

	public ChildAgent(boolean isInfectious) {
		super(isInfectious);
		this.ageGroup = AgeGroup.CHILD;
		this.initImmunity();
	}

	@Override
	public void updateStateOfDisease() {
		if (disease.hasIncubated()) {
			if (disease.getPeriod() == disease.getHealingTime()) {
				disease.updateVariable(SimConstants.CHANCE_TO_HEAL, 50.0f);
				disease.updateVariable(SimConstants.CHANCE_TO_KILL, 0.001f);
			}
			if (disease.getPeriod() > disease.getHealingTime()) {
				disease.updateVariable(SimConstants.CHANCE_TO_HEAL, 2.0f);
			}
		}
	}

	@Override
	public void initImmunity() {
		Random immuneSystem = new Random();
		this.immunity = immuneSystem.nextInt(40 - 10) + 10;
	}

}
