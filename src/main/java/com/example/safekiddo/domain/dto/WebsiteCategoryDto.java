package com.example.safekiddo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WebsiteCategoryDto {
    private Long id;
    private double confidence;
    private String name;
}
