package com.edss.simulation.agents;

import java.util.Random;

import com.edss.simulation.helperclasses.AgeGroup;
import com.edss.simulation.helperclasses.SimConstants;
import com.edss.simulation.simulation.Disease;

public class AdultAgent extends Agent {

	public AdultAgent(boolean isInfectious) {
		super(isInfectious);
		this.ageGroup = AgeGroup.ADULT;
		this.initImmunity();
	}

	@Override
	public void updateStateOfDisease() {
		if (disease != null && immunity < 100) {
			// case normal - after incubation
			if (disease.hasIncubated() && !isHospitalized) {
				if (disease.getPeriod() == disease.getHealingTime()) {
					disease.updateVariable(SimConstants.CHANCE_TO_HEAL, 50.0f);
					disease.updateVariable(SimConstants.CHANCE_TO_KILL, 0.01f);
				}
				if (disease.getPeriod() > disease.getHealingTime()) {
					disease.updateVariable(SimConstants.CHANCE_TO_HEAL, 2.0f);
				}
				selfQuarantine();
			}
			// disease flow while in hospital
			if (disease.hasIncubated() && isHospitalized) {
				if (disease.getPeriod() == disease.getHealingTime()) {
					disease.updateVariable(SimConstants.CHANCE_TO_HEAL, 25.0f);
					disease.updateVariable(SimConstants.CHANCE_TO_KILL, 0.01f);
				}
				if (disease.getPeriod() > disease.getHealingTime()) {
					disease.updateVariable(SimConstants.CHANCE_TO_HEAL, 3.0f);
				}
				if (disease.getPeriod() < disease.getHealingTime()) {
					disease.updateVariable(SimConstants.CHANCE_TO_KILL, 1.0f);
				}
			}
		}
	}

	@Override
	public void initImmunity() {
		Random immuneSystem = new Random();
		this.immunity = immuneSystem.nextInt(70 - 35) + 35;
	}

	@Override
	public void initDisease(Disease disease) {
		this.disease = disease;
		this.disease.setChanceToAggravate(SimConstants.adultAggravationChance);
	}

}
