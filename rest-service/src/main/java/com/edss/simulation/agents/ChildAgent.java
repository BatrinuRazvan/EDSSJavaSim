package com.edss.simulation.agents;

import java.util.Random;

import com.edss.simulation.helperclasses.AgeGroup;
import com.edss.simulation.helperclasses.SimConstants;
import com.edss.simulation.simulation.Disease;

public class ChildAgent extends Agent {

	public ChildAgent(boolean isInfectious) {
		super(isInfectious);
		this.ageGroup = AgeGroup.CHILD;
		this.initImmunity();
	}

	@Override
	public void updateStateOfDisease() {
		if (disease != null && immunity < 100) {
			// disease normal flow; no hospital
			if (disease.hasIncubated() && !isHospitalized) {
				if (disease.getPeriod() == disease.getHealingTime()) {
					disease.updateVariable(SimConstants.CHANCE_TO_HEAL, Double.valueOf("50"));
					disease.updateVariable(SimConstants.CHANCE_TO_KILL, Double.valueOf("0.001"));
				}
				if (disease.getPeriod() > disease.getHealingTime()) {
					disease.updateVariable(SimConstants.CHANCE_TO_HEAL, Double.valueOf("10"));
				}
				selfQuarantine();
			}
			// disease flow while in hospital
			if (disease.hasIncubated() && isHospitalized) {
				if (disease.getPeriod() == disease.getHealingTime()) {
					disease.updateVariable(SimConstants.CHANCE_TO_HEAL, Double.valueOf("50"));
					disease.updateVariable(SimConstants.CHANCE_TO_KILL, Double.valueOf("0.001"));
				}
				if (disease.getPeriod() > disease.getHealingTime()) {
					disease.updateVariable(SimConstants.CHANCE_TO_HEAL, Double.valueOf("5"));
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
		this.immunity = immuneSystem.nextDouble(40 - 10) + 10;
	}

	@Override
	public void initDisease(Disease disease) {
		this.disease = disease;
		this.disease.setChanceToAggravate(SimConstants.childAggravationChance);
	}

}
