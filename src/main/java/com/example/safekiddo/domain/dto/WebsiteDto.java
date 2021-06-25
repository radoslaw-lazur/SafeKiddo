package com.example.safekiddo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WebsiteDto {
    Long id;
    private String url;
    private String logoUrl;
    private SocialMediaDto socialMedia;
    private List<WebsiteCategoryDto> websiteCategories;
}
