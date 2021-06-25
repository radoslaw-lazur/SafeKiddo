package com.example.safekiddo.api;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class KlazifyConfig {
    @Value("${klazify.endpoint}")
    private String klazifyApiEndpoint;
    @Value("${klazify.key}")
    private String klazifyApiKey;
}
