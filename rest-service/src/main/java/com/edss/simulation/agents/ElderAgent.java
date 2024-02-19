package com.edss.simulation.agents;

import java.util.Random;

import com.edss.simulation.helperclasses.AgeGroup;
import com.edss.simulation.helperclasses.SimConstants;
import com.edss.simulation.simulation.Disease;

public class ElderAgent extends Agent {

	public ElderAgent(boolean isInfectious) {
		super(isInfectious);
		this.ageGroup = AgeGroup.ELDER;
		this.initImmunity();
	}

	@Override
	public void updateStateOfDisease() {
		if (disease != null && immunity < 100) {
			if (disease.hasIncubated() && !isHospitalized) {
				if (disease.getPeriod() == disease.getHealingTime()) {
					disease.updateVariable(SimConstants.CHANCE_TO_HEAL, Double.valueOf("50"));
					disease.updateVariable(SimConstants.CHANCE_TO_KILL, Double.valueOf("0.08"));
				}
				if (disease.getPeriod() > disease.getHealingTime()) {
					disease.updateVariable(SimConstants.CHANCE_TO_HEAL, Double.valueOf("2"));
				}
				selfQuarantine();
			}
			if (disease.hasIncubated() && isHospitalized) {
				if (disease.getPeriod() == disease.getHealingTime()) {
					disease.updateVariable(SimConstants.CHANCE_TO_HEAL, Double.valueOf("25"));
					disease.updateVariable(SimConstants.CHANCE_TO_KILL, Double.valueOf("0.08"));
				}
				if (disease.getPeriod() > disease.getHealingTime()) {
					disease.updateVariable(SimConstants.CHANCE_TO_HEAL, Double.valueOf("3"));
				}
				if (disease.getPeriod() < disease.getHealingTime()) {
					disease.updateVariable(SimConstants.CHANCE_TO_KILL, Double.valueOf("1"));
				}
			}
			disease.incrementPeriod();
		}
	}

	@Override
	public void initImmunity() {
		Random immuneSystem = new Random();
		this.immunity = immuneSystem.nextInt(40 - 5) + 5;
	}

	@Override
	public void initDisease(Disease disease) {
		this.disease = disease;
		this.disease.setChanceToAggravate(SimConstants.elderAggravationChance);
	}

}
