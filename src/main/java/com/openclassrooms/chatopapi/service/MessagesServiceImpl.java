package com.openclassrooms.chatopapi.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.chatopapi.model.Message;
import com.openclassrooms.chatopapi.repository.MessageRepository;

@Service
public class MessagesServiceImpl implements MessagesService {

	@Autowired
	private MessageRepository messageRepository;

	public void createMessage(Message message) {

		if (message.getMessage().isBlank()) {
			throw new IllegalArgumentException("Invalid message");
		}

		if (Double.isNaN(message.getUser_id()) || message.getUser_id() < 0 || Double.isNaN(message.getRental_id())
				|| message.getRental_id() < 0) {
			throw new IllegalArgumentException("Invalid id values");
		}
		
		message.setCreated_at(LocalDateTime.now());
		message.setUpdated_at(LocalDateTime.now());

		messageRepository.save(message);
	}
}
