package com.openclassrooms.chatopapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.chatopapi.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{

}
