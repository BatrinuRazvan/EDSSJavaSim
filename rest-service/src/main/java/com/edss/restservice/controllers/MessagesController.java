package com.edss.restservice.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edss.models.MessageNotification;
import com.edss.models.UserLocation;
import com.edss.models.UserResponse;
import com.edss.models.helperclasses.DbHelper;

@RestController
@RequestMapping("/messages")
public class MessagesController {

	@PostMapping("/addNotification")
	public ResponseEntity<?> addNotificaton(@RequestBody MessageNotification notification) {

		DbHelper.addMessageNotification(notification.getCity(), notification.getTitle(), notification.getColor(),
				notification.getSeverity(), notification.getRange(), notification.getDescription());

		return ResponseEntity.ok().build();
	}

	@GetMapping("/getMessages")
	public List<MessageNotification> getMessages() {
		return DbHelper.getMessageNotifications();
	}

	@GetMapping("/getLocalMessages")
	public List<MessageNotification> getLocalMessages(@RequestBody UserLocation userLocation) {
		return DbHelper.getLocalMessageNotifications(userLocation);
	}

	@PostMapping("/saveResponse")
	public ResponseEntity<?> saveResponse(@RequestBody UserResponse userResponse) {
		// Assuming you have a DataSource or JdbcTemplate configured

//		DbHelper.addUserRespons(userResponse.getEmail(), userResponse.getQuestion(), userResponse.getResponse());

		return ResponseEntity.ok().build();
	}

}
