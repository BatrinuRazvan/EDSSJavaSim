package com.edss.simulation.simulation;

public class Disease {

	private String name;
	private int periodOfDisease;
	private int incubationTime;
	private float chanceToTransmit;
	private int healingTime;
	private float chanceToHeal;
	private float chanceToKill;

	public Disease(String name, int periodOfDisease, int incubationTime, float chanceToTransmit, int healingTime,
			float chanceToHeal, float chanceToKill) {
		this.name = name;
		this.periodOfDisease = periodOfDisease;
		this.incubationTime = incubationTime;
		this.chanceToTransmit = chanceToTransmit;
		this.healingTime = healingTime;
		this.chanceToHeal = chanceToHeal;
		this.chanceToKill = chanceToKill;
	}

	public float getChanceToKill() {
		return chanceToKill;
	}

	public float getChanceToHeal() {
		return chanceToHeal;
	}

	public float getChanceToTransmit() {
		return chanceToTransmit;
	}

	public boolean hasIncubated() {
		return periodOfDisease >= incubationTime ? true : false;
	}

	public int getPeriod() {
		return periodOfDisease;
	}

	public int getIncubationTime() {
		return incubationTime;
	}
}
