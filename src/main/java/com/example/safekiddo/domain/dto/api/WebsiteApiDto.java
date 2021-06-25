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
public class WebsiteApiDto {
    @JsonProperty("domain")
    private DomainApiDto domainApiDto;
    @JsonProperty("success")
    private boolean success;
}
