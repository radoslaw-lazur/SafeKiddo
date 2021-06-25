package com.example.safekiddo.service;

import com.example.safekiddo.domain.Website;
import com.example.safekiddo.domain.dto.WebsiteDto;

public interface WebsiteService {

    Website addWebsite(String url);

    Website updateWebsite(String url, WebsiteDto websiteDto);

    Website getWebsite(String url);

    void deleteWebsite(String url);

}
