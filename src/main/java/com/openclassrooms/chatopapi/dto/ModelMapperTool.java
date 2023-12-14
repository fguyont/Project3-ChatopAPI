package com.openclassrooms.chatopapi.dto;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperTool {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
