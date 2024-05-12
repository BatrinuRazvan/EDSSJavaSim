package com.edss.restservice.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edss.models.CityMarker;
import com.edss.models.EdssSubscription;
import com.edss.models.Response;
import com.edss.models.User;
import com.edss.models.UserLocation;
import com.edss.models.UserResponse;
import com.edss.models.helperclasses.DbHelper;
import com.edss.models.helperclasses.HelperMethods;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/user")
public class UserController {

	@PostMapping("/updateLocation")
	public ResponseEntity<?> updateLocation(@RequestParam String userId, @RequestParam double latitude,
			@RequestParam double longitude) {

		return ResponseEntity.ok().build();
	}

	@PostMapping("/saveUser")
	public ResponseEntity<?> saveUser(@RequestParam String userId, @RequestParam String email,
			@RequestParam double latitude, @RequestParam double longitude, @RequestParam String userType) {

		User newUser = new User(userId, email, new UserLocation(latitude, longitude), userType);
		if (!HelperMethods.userAlreadyExists(newUser)) {
			DbHelper.saveUser(newUser);
		}

		return ResponseEntity.ok().build();
	}

	@PostMapping("/saveSubscription")
	public ResponseEntity<?> saveSubscription(@RequestBody EdssSubscription subscription) {

		DbHelper.saveSubscription(subscription);

		return ResponseEntity.ok().build();
	}

	@Autowired
	private ObjectMapper objectMapper;

	@PostMapping("/saveResponse")
	public ResponseEntity<?> saveResponse(@RequestBody String rawJson) {
		try {
			JsonNode jsonNode = objectMapper.readTree(rawJson);
			String userId = jsonNode.get("userId").asText();
			List<Response> responses = objectMapper.convertValue(jsonNode.get("responses"),
					new TypeReference<List<Response>>() {
					});

			UserResponse userResponse = new UserResponse(userId, responses);
			DbHelper.saveUserResponse(userResponse);

			// Now you have a UserResponse object populated with userId and responses
			System.out.println("Received UserResponse: " + userResponse);

			// Process the userResponse as needed
			// For example, save it to the database

			return ResponseEntity.ok().build();
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("Error processing JSON");
		}
	}

	@GetMapping("/getNearestExit")
	public CityMarker getNearestExit(@RequestParam double latitude, @RequestParam double longitude) {
		return DbHelper.getNearestCityExit(latitude, longitude);
	}

	@GetMapping("/getUserType")
	public String getUserType(@RequestParam String userId) {
		return DbHelper.getUserType(userId);
	}

}
