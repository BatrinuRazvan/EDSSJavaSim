package com.edss.restservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edss.models.EdssSubscription;
import com.edss.models.User;
import com.edss.models.UserLocation;
import com.edss.models.helperclasses.DbHelper;
import com.edss.models.helperclasses.HelperMethods;

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
			@RequestParam double latitude, @RequestParam double longitude) {

		User newUser = new User(userId, email, new UserLocation(latitude, longitude));
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

}
