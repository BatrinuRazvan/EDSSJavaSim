package com.edss.restservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.edss.models.MessageNotification;
import com.edss.models.helperclasses.DbHelper;

@RestController
public class AdminAppController {

	@PostMapping("/messages/addNotification")
	public ResponseEntity<?> addNotificaton(@RequestBody MessageNotification notification) {

		DbHelper.addMessageNotification(notification.getCity(), notification.getTitle(), notification.getSeverity(),
				notification.getRange(), notification.getDescription());

		return ResponseEntity.ok().build();
	}

	@GetMapping("/messages/getMessages")
	public ResponseEntity<?> getMessages(@RequestBody MessageNotification notification) {

//		DbHelper.addMessageNotification(notification.getCity(), notification.getTitle(), notification.getSeverity(),
//				notification.getRange(), notification.getDescription());

		return ResponseEntity.ok().build();
	}

}
