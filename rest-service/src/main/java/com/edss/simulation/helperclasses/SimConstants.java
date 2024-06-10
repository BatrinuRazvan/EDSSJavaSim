package com.edss.simulation.helperclasses;

import com.edss.models.ModifyableParameters;
import com.edss.models.helperclasses.HelperMethods;

public class SimConstants {

	public static final String NAME_OF_DISEASE = "EPIDEMIC";

	public static int standardIncubationTimeDisease = 6;
	public static double chanceToTransmitDisease = Double.valueOf("20");
	public static int healingTimeDisease = 15;
	public static double initialChanceToHeal = Double.valueOf("0");
	public static double initialChanceToKill = Double.valueOf("0");
	public static int chanceForAsymptomatic = 5;

	public static final String ADD_SUSCEPTIBLE = "ADD_SUSCEPTIBLE";
	public static final String ADD_DEAD = "ADD_DEAD";
	public static final String ADD_SICK = "ADD_SICK";
	public static final String ADD_RECOVERED = "ADD_RECOVERED";

	public static final String REMOVE_SUSCEPTIBLE = "REMOVE_SUSCEPTIBLE";
	public static final String REMOVE_DEAD = "REMOVE_DEAD";

	// Disease
	public static final String CHANCE_TO_KILL_NORMAL_BED = "CHANCE_TO_KILL_NORMAL_BED";
	public static final String CHANCE_TO_KILL_ICU_BED = "CHANCE_TO_KILL_ICU_BED";
	public static final String CHANCE_TO_KILL_NO_ICU_AVAILABLE = "CHANCE_TO_KILL_NO_ICU_AVAILABLE";
	public static final String CHANCE_TO_KILL_NO_NORMAL_AVAILABLE = "CHANCE_TO_KILL_NO_NORMAL_AVAILABLE";

	public static final String CHANCE_TO_HEAL = "CHANCE_TO_HEAL";
	public static final String CHANCE_TO_KILL = "CHANCE_TO_KILL";

	public static final String CHANCE_TO_AGGRAVATE = "CHANCE_TO_AGGRAVATE";

	public static boolean isMaskUseEnforced = false;
	public static boolean isMaskUseDisabled = false;
	public static boolean isVaccineEnforced = false;

	public static int chanceToGoOut = 40;

	public static double childAggravationChance = Double.valueOf("0.001");
	public static double adultAggravationChance = Double.valueOf("0.005");
	public static double elderAggravationChance = Double.valueOf("0.008");

	public static int chanceToSelfQuarantine = 95; // 95% chance to self quarantine

	public static int agentsAtCentralLocation_atSameTime = 100;

	// Mask
	public static double maskHelpingPercent = Double.valueOf("5");
	public static int maskDistributionTime = 45;
	public static int maskCooldownTime = 25;

	// Vaccine
	public static double vaccineImmunityBuffer = Double.valueOf("25");
	public static int vaccineDistributionTime = 50;

	public static void updateModifyableValues(ModifyableParameters params) {

		standardIncubationTimeDisease = HelperMethods.isZeroValue(standardIncubationTimeDisease,
				params.getStandardIncubationTimeDiseaseParam());
		standardIncubationTimeDisease = HelperMethods.isZeroValue(standardIncubationTimeDisease,
				params.getStandardIncubationTimeDiseaseParam());
		chanceToTransmitDisease = HelperMethods.isZeroValue(chanceForAsymptomatic,
				params.getChanceToTransmitDiseaseParam());
		healingTimeDisease = HelperMethods.isZeroValue(healingTimeDisease, params.getHealingTimeDiseaseParam());
		initialChanceToHeal = HelperMethods.isZeroValue(initialChanceToHeal, params.getInitialChanceToHealParam());
		initialChanceToKill = HelperMethods.isZeroValue(initialChanceToKill, params.getInitialChanceToKillParam());
		chanceForAsymptomatic = HelperMethods.isZeroValue(chanceForAsymptomatic,
				params.getChanceForAsymptomaticParam());
		chanceToGoOut = HelperMethods.isZeroValue(chanceToGoOut, params.getChanceToGoOutParam());
		chanceToSelfQuarantine = HelperMethods.isZeroValue(chanceToSelfQuarantine,
				params.getChanceToSelfQuarantineParam());
		agentsAtCentralLocation_atSameTime = HelperMethods.isZeroValue(agentsAtCentralLocation_atSameTime,
				params.getAgentsAtCentralLocation_atSameTimeParam());
		maskDistributionTime = HelperMethods.isZeroValue(maskDistributionTime, params.getMaskDistributionTimeParam());
		maskCooldownTime = HelperMethods.isZeroValue(maskCooldownTime, params.getMaskCooldownTimeParam());
		vaccineDistributionTime = HelperMethods.isZeroValue(vaccineDistributionTime,
				params.getVaccineDistributionTimeParam());
		isMaskUseEnforced = params.isMaskUseEnforcedParam();
		isMaskUseDisabled = params.isMaskUseDisabledParam();
		isVaccineEnforced = params.isVaccineEnforcedParam();

	}
}
