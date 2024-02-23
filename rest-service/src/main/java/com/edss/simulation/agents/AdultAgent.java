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
					disease.updateVariable(SimConstants.CHANCE_TO_HEAL, Double.valueOf("50"));
					disease.updateVariable(SimConstants.CHANCE_TO_KILL, Double.valueOf("0.01"));
				}
				if (disease.getPeriod() > disease.getHealingTime()) {
					disease.updateVariable(SimConstants.CHANCE_TO_HEAL, Double.valueOf("13"));
				}
				selfQuarantine();
			}
			// disease flow while in hospital
			if (disease.hasIncubated() && isHospitalized) {
				if (disease.getPeriod() == disease.getHealingTime()) {
					disease.updateVariable(SimConstants.CHANCE_TO_HEAL, Double.valueOf("25"));
					disease.updateVariable(SimConstants.CHANCE_TO_KILL, Double.valueOf("0.01"));
				}
				if (disease.getPeriod() > disease.getHealingTime()) {
					disease.updateVariable(SimConstants.CHANCE_TO_HEAL, Double.valueOf("3"));
				}
				if (disease.getPeriod() < disease.getHealingTime()) {
					disease.updateVariable(SimConstants.CHANCE_TO_KILL, Double.valueOf("1"));
				}
			}
		}
	}

	@Override
	public void initImmunity() {
		Random immuneSystem = new Random();
		this.immunity = immuneSystem.nextInt(75 - 35) + 35;
	}

	@Override
	public void initDisease(Disease disease) {
		if (this.disease == null) {
			this.disease = disease;
			this.disease.setChanceToAggravate(SimConstants.adultAggravationChance);
		}
	}

}
