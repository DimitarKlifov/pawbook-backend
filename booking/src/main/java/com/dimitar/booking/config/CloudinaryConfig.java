package com.dimitar.booking.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.validation.Valid;
import jakarta.validation.executable.ValidateOnExecution;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary(){

            Cloudinary cloudinary = new Cloudinary();
            cloudinary.config.cloudName = cloudName;
            cloudinary.config.apiKey = apiKey;
            cloudinary.config.apiSecret = apiSecret;
            cloudinary.config.secure = true;

            return cloudinary;
        }
    }

