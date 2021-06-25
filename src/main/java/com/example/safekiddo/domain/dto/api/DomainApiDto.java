package com.example.safekiddo.domain.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DomainApiDto {
    @JsonProperty("categories")
    private List<CategoryApiDto> categories;
    @JsonProperty("logo_url")
    private String urlLogo;
    @JsonProperty("domain_url")
    private String url;
    @JsonProperty("social_media")
    private SocialMediaApiDto socialMediaApiDto;
}
