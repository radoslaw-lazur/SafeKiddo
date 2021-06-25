package com.example.safekiddo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SocialMediaDto {
    private Long id;
    private String facebookUrl;
    private String twitterUrl;
    private String instagramUrl;
    private String mediumUrl;
    private String youtubeUrl;
    private String pinterestUrl;
    private String linkedinUrl;
    private String githubUrl;
}
