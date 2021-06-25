package com.example.safekiddo.domain.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SocialMediaApiDto {
    @JsonProperty("facebook_url")
    private String facebookSocialUrl;
    @JsonProperty("twitter_url")
    private String twitterSocialUrl;
    @JsonProperty("instagram_url")
    private String instagramSocialUrl;
    @JsonProperty("medium_url")
    private String mediumSocialUrl;
    @JsonProperty("youtube_url")
    private String youtubeSocialUrl;
    @JsonProperty("pinterest_url")
    private String pinterestSocialUrl;
    @JsonProperty("linkedin_url")
    private String linkedinSocialUrl;
    @JsonProperty("github_url")
    private String githubSocialUrl;
}
