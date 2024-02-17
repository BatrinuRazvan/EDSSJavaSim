package com.edss.simulation.agents;

import java.util.Random;

import com.edss.simulation.helperclasses.AgeGroup;
import com.edss.simulation.helperclasses.SimConstants;

public class ElderAgent extends Agent {

	public ElderAgent(boolean isInfectious) {
		super(isInfectious);
		this.ageGroup = AgeGroup.ELDER;
		this.initImmunity();
	}

	@Override
	public void updateStateOfDisease() {
		if (disease.hasIncubated()) {
			if (disease.getPeriod() == disease.getHealingTime()) {
				disease.updateVariable(SimConstants.CHANCE_TO_HEAL, 50.0f);
				disease.updateVariable(SimConstants.CHANCE_TO_KILL, 0.08f);
			}
			if (disease.getPeriod() > disease.getHealingTime()) {
				disease.updateVariable(SimConstants.CHANCE_TO_HEAL, 2.0f);
			}
		}
	}

	@Override
	public void initImmunity() {
		Random immuneSystem = new Random();
		this.immunity = immuneSystem.nextInt(40 - 5) + 5;
	}

}
