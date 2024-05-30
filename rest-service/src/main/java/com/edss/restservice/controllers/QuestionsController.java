package com.edss.restservice.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edss.models.helperclasses.DbHelper;
import com.edss.models.helperclasses.HelperMethods;

@RestController
@RequestMapping("/questions")
public class QuestionsController {

	@GetMapping("/getAllStoredDiagnostics")
	public List<String> getAllStoredDiagnostics() {
		return DbHelper.getAllStoredDiagnostics();
	}

	@PostMapping("/saveDiagnostic")
	public ResponseEntity<?> saveDiagnostic(@RequestBody String diagnostic) {

		DbHelper.saveDiagnostic(diagnostic);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/incrementDiagnosticNumberByUser")
	public ResponseEntity<?> incrementDiagnosticNumberByUser(@RequestBody String diagnostic) {

		DbHelper.incrementDiagnosticNumber(diagnostic, 1);
		DbHelper.addDiagnosticTimestamp(diagnostic, 1);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/incrementDiagnosticNumberByMedic")
	public ResponseEntity<?> incrementDiagnosticNumberByMedic(@RequestParam String diagnostic,
			@RequestParam String numberOfDiagnostics) {

		DbHelper.incrementDiagnosticNumber(diagnostic, Integer.parseInt(numberOfDiagnostics));
		DbHelper.addDiagnosticTimestamp(diagnostic, Integer.parseInt(numberOfDiagnostics));

		return ResponseEntity.ok().build();
	}

	@GetMapping("/getAllStoredSymptoms")
	public List<String> getAllStoredSymptoms(@RequestParam String diagnostic) {
		return DbHelper.getAllStoredSymptoms(diagnostic);
	}

	@PostMapping("/saveSymptoms")
	public ResponseEntity<?> saveSymptoms(@RequestParam String diagnostic, @RequestParam String symptoms) {

		DbHelper.saveSymptoms(diagnostic, HelperMethods.parseSymptoms(symptoms));

		return ResponseEntity.ok().build();
	}

}
