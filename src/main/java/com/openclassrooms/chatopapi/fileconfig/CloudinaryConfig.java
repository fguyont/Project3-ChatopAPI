package com.openclassrooms.chatopapi.fileconfig;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;
@Configuration
public class CloudinaryConfig {
	//Insert your Cloud name
    private final String CLOUD_NAME = "";
    //Insert your API key
    private final String API_KEY = "";
    //Insert your API secret
    private final String API_SECRET = "";
    @Bean
    public Cloudinary cloudinary(){
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name",CLOUD_NAME);
        config.put("api_key",API_KEY);
        config.put("api_secret",API_SECRET);
        return new Cloudinary(config);
    }
}
