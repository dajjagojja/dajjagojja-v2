package com.multi.travel.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${image.review.add-resource-handler}")
    private String reviewHandler;

    @Value("${image.review.add-resource-locations}")
    private String reviewLocation;



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(reviewHandler)
                .addResourceLocations(reviewLocation);
    }
}
