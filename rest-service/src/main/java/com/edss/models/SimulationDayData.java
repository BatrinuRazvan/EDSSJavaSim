package com.edss.models;

public class SimulationDayData {

	private int dayIncrement;
	private int totalDeaths;
	private int dailyDeaths;
	private int totalCases;
	private int dailyCases;
	private int totalRecovered;
	private int dailyRecovered;
	private int totalHospitalizations;
	private int dailyHospitalizations;
	private float maskUse;
	private int totalVaccinations;
	private int dailyVaccinations;
	private int currentHospitalOccupancy;
	private int currentIcuOccupancy;
	private int infectionFatality;

	public SimulationDayData(int dayIncrement, int totalDeaths, int dailyDeaths, int totalCases, int dailyCases,
			int totalRecovered, int dailyRecovered, int totalHospitalizations, int dailyHospitalizations, float maskUse,
			int totalVaccinations, int dailyVaccinations, int currentHospitalOccupancy, int currentIcuOccupancy,
			int infectionFatality) {
		this.dayIncrement = dayIncrement;
		this.totalDeaths = totalDeaths;
		this.dailyDeaths = dailyDeaths;
		this.totalCases = totalCases;
		this.dailyCases = dailyCases;
		this.totalRecovered = totalRecovered;
		this.dailyRecovered = dailyRecovered;
		this.totalHospitalizations = totalHospitalizations;
		this.dailyHospitalizations = dailyHospitalizations;
		this.maskUse = maskUse;
		this.totalVaccinations = totalVaccinations;
		this.dailyVaccinations = dailyVaccinations;
		this.currentHospitalOccupancy = currentHospitalOccupancy;
		this.currentIcuOccupancy = currentIcuOccupancy;
		this.infectionFatality = infectionFatality;
	}

	public int getDayIncrement() {
		return dayIncrement;
	}

	public void setDayIncrement(int dayIncrement) {
		this.dayIncrement = dayIncrement;
	}

	public int getTotalDeaths() {
		return totalDeaths;
	}

	public void setTotalDeaths(int totalDeaths) {
		this.totalDeaths = totalDeaths;
	}

	public int getDailyDeaths() {
		return dailyDeaths;
	}

	public void setDailyDeaths(int dailyDeaths) {
		this.dailyDeaths = dailyDeaths;
	}

	public int getTotalCases() {
		return totalCases;
	}

	public void setTotalCases(int totalCases) {
		this.totalCases = totalCases;
	}

	public int getDailyCases() {
		return dailyCases;
	}

	public void setDailyCases(int dailyCases) {
		this.dailyCases = dailyCases;
	}

	public int getTotalRecovered() {
		return totalRecovered;
	}

	public void setTotalRecovered(int totalRecovered) {
		this.totalRecovered = totalRecovered;
	}

	public int getDailyRecovered() {
		return dailyRecovered;
	}

	public void setDailyRecovered(int dailyRecovered) {
		this.dailyRecovered = dailyRecovered;
	}

	public int getTotalHospitalizations() {
		return totalHospitalizations;
	}

	public void setTotalHospitalizations(int totalHospitalizations) {
		this.totalHospitalizations = totalHospitalizations;
	}

	public int getDailyHospitalizations() {
		return dailyHospitalizations;
	}

	public void setDailyHospitalizations(int dailyHospitalizations) {
		this.dailyHospitalizations = dailyHospitalizations;
	}

	public float getMaskUse() {
		return maskUse;
	}

	public void setMaskUse(float maskUse) {
		this.maskUse = maskUse;
	}

	public int getTotalVaccinations() {
		return totalVaccinations;
	}

	public void setTotalVaccinations(int totalVaccinations) {
		this.totalVaccinations = totalVaccinations;
	}

	public int getDailyVaccinations() {
		return dailyVaccinations;
	}

	public void setDailyVaccinations(int dailyVaccinations) {
		this.dailyVaccinations = dailyVaccinations;
	}

	public int getCurrentHospitalOccupancy() {
		return currentHospitalOccupancy;
	}

	public void setCurrentHospitalOccupancy(int currentHospitalOccupancy) {
		this.currentHospitalOccupancy = currentHospitalOccupancy;
	}

	public int getCurrentIcuOccupancy() {
		return currentIcuOccupancy;
	}

	public void setCurrentIcuOccupancy(int currentIcuOccupancy) {
		this.currentIcuOccupancy = currentIcuOccupancy;
	}

	public int getInfectionFatality() {
		return infectionFatality;
	}

	public void setInfectionFatality(int infectionFatality) {
		this.infectionFatality = infectionFatality;
	}

}
