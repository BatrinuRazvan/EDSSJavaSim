package com.edss.restservice.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edss.models.helperclasses.DbHelper;

@RestController
@RequestMapping("/questions")
public class QuestionsController {

	@GetMapping("/getAllStoredDiagnostics")
	public List<String> getAllStoredDiagnostics() {
		return DbHelper.getAllStoredDiagnostics();
	}

	@PostMapping("/saveNewDiagnostic")
	public ResponseEntity<?> saveNewDiagnostic(@RequestBody String diagnostic) {

		DbHelper.saveNewDiagnostic(diagnostic);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/incrementDiagnosticNumber")
	public ResponseEntity<?> incrementDiagnosticNumberByUser(@RequestBody String diagnostic) {

		DbHelper.incrementDiagnosticNumber(diagnostic, 1);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/incrementDiagnosticNumber")
	public ResponseEntity<?> incrementDiagnosticNumberByMedic(@RequestBody String diagnostic,
			@RequestBody int numberOfDiagnostics) {

		DbHelper.incrementDiagnosticNumber(diagnostic, numberOfDiagnostics);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/getAllStoredSymptoms")
	public List<String> getAllStoredSymptoms(@RequestBody String diagnosticName) {
		return DbHelper.getAllStoredSymptoms(diagnosticName);
	}

}
