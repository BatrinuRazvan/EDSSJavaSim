package com.edss.restservice.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.edss.models.CityMarker;
import com.edss.models.helperclasses.DbHelper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

@RestController
public class GeneralController {

	@GetMapping("/cities")
	public List<CityMarker> getCities() {
		return DbHelper.getCities();
	}

// POST endpoint to receive the ID token from the frontend
	@PostMapping("/auth/google")
	public ResponseEntity<?> authenticateUser(@RequestBody String idTokenString) {
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
				.setAudience(Collections.singletonList("YOUR_CLIENT_ID.apps.googleusercontent.com")).build();

		try {
			GoogleIdToken idToken = verifier.verify(idTokenString);
			if (idToken != null) {
				GoogleIdToken.Payload payload = idToken.getPayload();
				// Get profile information from payload
				String email = payload.getEmail();
				String name = (String) payload.get("name");

				// Here, you would save or update the user details in your database

				return ResponseEntity.ok().body("User authenticated");
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid ID token.");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication failed.");
		}
	}

	@PostMapping("/startDaemons")
	public void startDaemons() {

	}
}
