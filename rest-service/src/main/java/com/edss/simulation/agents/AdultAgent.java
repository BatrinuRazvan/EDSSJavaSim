package com.edss.simulation.agents;

import java.util.Random;

import com.edss.simulation.helperclasses.AgeGroup;
import com.edss.simulation.helperclasses.SimConstants;

public class AdultAgent extends Agent {

	public AdultAgent(boolean isInfectious) {
		super(isInfectious);
		this.ageGroup = AgeGroup.ADULT;
		this.initImmunity();
	}

	@Override
	public void updateStateOfDisease() {
		// case normal - after incubation
		if (disease.hasIncubated()) {
			if (disease.getPeriod() == disease.getHealingTime()) {
				disease.updateVariable(SimConstants.CHANCE_TO_HEAL, 50.0f);
				disease.updateVariable(SimConstants.CHANCE_TO_KILL, 0.01f);
			}
			if (disease.getPeriod() > disease.getHealingTime()) {
				disease.updateVariable(SimConstants.CHANCE_TO_HEAL, 2.0f);
			}
		}
	}

	@Override
	public void initImmunity() {
		Random immuneSystem = new Random();
		this.immunity = immuneSystem.nextInt(70 - 35) + 35;
	}

}
