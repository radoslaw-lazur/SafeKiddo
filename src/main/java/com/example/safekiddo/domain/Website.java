package com.example.safekiddo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "WEBSITES")
public class Website {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @URL
    private String url;
    @URL
    private String logoUrl;
    @OneToOne (
            targetEntity = SocialMedia.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private SocialMedia socialMedia;
    @OneToMany(
            targetEntity = WebsiteCategory.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<WebsiteCategory> websiteCategories;

    public Website(String url, String logoUrl, SocialMedia socialMedia, List<WebsiteCategory> websiteCategories) {
        this.url = url;
        this.logoUrl = logoUrl;
        this.socialMedia = socialMedia;
        this.websiteCategories = websiteCategories;
    }
}
