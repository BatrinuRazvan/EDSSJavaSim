package com.edss.models;

public class ModifyableParameters {

	private int simPeriodParam;
	private int numberOfSickAtStartParam;
	private int numberOfAgentsParam;
	private int standardIncubationTimeDiseaseParam;
	private double chanceToTransmitDiseaseParam;
	private int healingTimeDiseaseParam;
	private double initialChanceToHealParam;
	private double initialChanceToKillParam;
	private int chanceForAsymptomaticParam;
	private int chanceToGoOutParam;
	private int chanceToSelfQuarantineParam;
	private int agentsAtCentralLocation_atSameTimeParam;
	private int maskDistributionTimeParam;
	private int maskCooldownTimeParam;
	private int vaccineDistributionTimeParam;
	private boolean isMaskUseEnforcedParam;
	private boolean isMaskUseDisabledParam;
	private boolean isVaccineEnforcedParam;

	public ModifyableParameters(int numberOfAgentsParam, int numberOfSickAtStartParam, int simPeriodParam,
			int standardIncubationTimeDiseaseParam, double chanceToTransmitDiseaseParam, int healingTimeDiseaseParam,
			double initialChanceToHealParam, double initialChanceToKillParam, int chanceForAsymptomaticParam,
			int chanceToGoOutParam, int chanceToSelfQuarantineParam, int agentsAtCentralLocation_atSameTimeParam,
			int maskDistributionTimeParam, int maskCooldownTimeParam, int vaccineDistributionTimeParam, boolean maskUse,
			boolean vaccineEnforced) {
		this.simPeriodParam = simPeriodParam;
		this.numberOfSickAtStartParam = numberOfSickAtStartParam;
		this.numberOfAgentsParam = numberOfAgentsParam;
		this.standardIncubationTimeDiseaseParam = standardIncubationTimeDiseaseParam;
		this.chanceToTransmitDiseaseParam = chanceToTransmitDiseaseParam;
		this.healingTimeDiseaseParam = healingTimeDiseaseParam;
		this.initialChanceToHealParam = initialChanceToHealParam;
		this.initialChanceToKillParam = initialChanceToKillParam;
		this.chanceForAsymptomaticParam = chanceForAsymptomaticParam;
		this.chanceToGoOutParam = chanceToGoOutParam;
		this.chanceToSelfQuarantineParam = chanceToSelfQuarantineParam;
		this.agentsAtCentralLocation_atSameTimeParam = agentsAtCentralLocation_atSameTimeParam;
		this.maskDistributionTimeParam = maskDistributionTimeParam;
		this.maskCooldownTimeParam = maskCooldownTimeParam;
		this.vaccineDistributionTimeParam = vaccineDistributionTimeParam;
		this.isMaskUseEnforcedParam = maskUse;
		this.isMaskUseDisabledParam = !maskUse;
		this.isVaccineEnforcedParam = vaccineEnforced;
	}

	public int getNumberOfAgentsParam() {
		return numberOfAgentsParam;
	}

	public int getStandardIncubationTimeDiseaseParam() {
		return standardIncubationTimeDiseaseParam;
	}

	public double getChanceToTransmitDiseaseParam() {
		return chanceToTransmitDiseaseParam;
	}

	public int getHealingTimeDiseaseParam() {
		return healingTimeDiseaseParam;
	}

	public double getInitialChanceToHealParam() {
		return initialChanceToHealParam;
	}

	public double getInitialChanceToKillParam() {
		return initialChanceToKillParam;
	}

	public int getChanceForAsymptomaticParam() {
		return chanceForAsymptomaticParam;
	}

	public int getChanceToGoOutParam() {
		return chanceToGoOutParam;
	}

	public int getChanceToSelfQuarantineParam() {
		return chanceToSelfQuarantineParam;
	}

	public int getAgentsAtCentralLocation_atSameTimeParam() {
		return agentsAtCentralLocation_atSameTimeParam;
	}

	public int getMaskDistributionTimeParam() {
		return maskDistributionTimeParam;
	}

	public int getMaskCooldownTimeParam() {
		return maskCooldownTimeParam;
	}

	public int getVaccineDistributionTimeParam() {
		return vaccineDistributionTimeParam;
	}

	public int getSimPeriodParam() {
		return simPeriodParam;
	}

	public int getNumberOfSickAtStartParam() {
		return numberOfSickAtStartParam;
	}

	public boolean isMaskUseEnforcedParam() {
		return isMaskUseEnforcedParam;
	}

	public boolean isMaskUseDisabledParam() {
		return isMaskUseDisabledParam;
	}

	public boolean isVaccineEnforcedParam() {
		return isVaccineEnforcedParam;
	}
}
