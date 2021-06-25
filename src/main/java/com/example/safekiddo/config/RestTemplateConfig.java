package com.example.safekiddo.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class RestTemplateConfig {
    @Value("${resttemplate.readtime}")
    private int readTimeRestTemplateTimeout;
    @Value("${resttemplate.connecttime}")
    private int connectTimeRestTemplateTimeout;
}
