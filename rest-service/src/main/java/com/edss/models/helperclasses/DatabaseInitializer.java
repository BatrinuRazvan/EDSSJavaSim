package com.edss.models.helperclasses;

import com.edss.simulation.helperclasses.SimHelper;

public class DatabaseInitializer {

	public static void main(String[] args) {
		SimHelper.initSimulatinDatabase();
		InitTablesHelper.initMarkersDatabase();
		InitTablesHelper.initNotificationsDatabase();
		InitTablesHelper.initUsersTable();
		InitTablesHelper.initUsersTable();
		InitTablesHelper.initUserResponsesTable();
		InitTablesHelper.initDecisionResponsesTable();
		InitTablesHelper.initExitPointsTable();
		InitTablesHelper.initDiagnosticsTable();
		InitTablesHelper.initSymptomsTable();
		InitTablesHelper.initCovidTimisoaraDatabase();
		InitTablesHelper.initDiagnosticsTimeStampTable();
	}

}
