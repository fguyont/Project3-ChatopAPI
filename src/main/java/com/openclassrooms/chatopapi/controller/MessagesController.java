package com.openclassrooms.chatopapi.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.chatopapi.dto.MessageRequest;
import com.openclassrooms.chatopapi.dto.MessageResponse;
import com.openclassrooms.chatopapi.model.Message;
import com.openclassrooms.chatopapi.service.MessagesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/messages")
public class MessagesController {

	@Autowired
	private MessagesService messagesService;

	@Autowired
	private ModelMapper modelMapper;

	@Operation(summary = "Creates a new message")
	@ApiResponse(responseCode = "200", description = "Message created")
	@ApiResponse(responseCode = "400", description = "Invalid message value or invalid id value")
	@ApiResponse(responseCode = "503", description = "Service unavailable")
	@PostMapping("")
	public ResponseEntity<?> createMessage(@RequestBody MessageRequest messageRequest) {

		Message message = modelMapper.map(messageRequest, Message.class);

		try {
			messagesService.createMessage(message);
		} catch (IllegalArgumentException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("400 error: Invalid message value or invalid id value");
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("503 error: Service unavailable");
		}

		MessageResponse messageResponse = new MessageResponse();
		messageResponse.setMessage("Message created");
		
		return ResponseEntity.ok(messageResponse);
	}
}
