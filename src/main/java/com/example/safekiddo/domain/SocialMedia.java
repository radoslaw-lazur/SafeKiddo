package com.example.safekiddo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "MEDIAS")
public class SocialMedia {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String facebookUrl;
    private String twitterUrl;
    private String instagramUrl;
    private String mediumUrl;
    private String youtubeUrl;
    private String pinterestUrl;
    private String linkedinUrl;
    private String githubUrl;
    @OneToOne
    @JsonIgnore
    private Website website;

    public SocialMedia(String facebookUrl, String twitterUrl, String instagramUrl, String mediumUrl, String youtubeUrl,
                       String pinterestUrl, String linkedinUrl, String githubUrl) {
        this.facebookUrl = facebookUrl;
        this.twitterUrl = twitterUrl;
        this.instagramUrl = instagramUrl;
        this.mediumUrl = mediumUrl;
        this.youtubeUrl = youtubeUrl;
        this.pinterestUrl = pinterestUrl;
        this.linkedinUrl = linkedinUrl;
        this.githubUrl = githubUrl;
    }
}
