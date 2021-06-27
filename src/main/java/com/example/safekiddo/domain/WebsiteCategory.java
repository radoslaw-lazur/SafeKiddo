package com.example.safekiddo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "CATEGORIES")
public class WebsiteCategory {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private double confidence;
    @NotNull
    private String name;
    @ManyToOne
    @JoinColumn(name = "website_id")
    @JsonIgnore
    private Website website;

    public WebsiteCategory(double confidence, String name) {
        this.confidence = confidence;
        this.name = name;
    }
}
