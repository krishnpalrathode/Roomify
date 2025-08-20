package com.hotelmanagement.roomify.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map URL path /images/** → to local folder uploads/
        registry.addResourceHandler("/Images/**")
                .addResourceLocations("file:C:/Users/Lenovo/Desktop/hotel management/roomify/uploads/Images/");
    }
}
