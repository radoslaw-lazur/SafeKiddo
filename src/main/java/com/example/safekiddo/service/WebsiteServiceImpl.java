package com.example.safekiddo.service;

import com.example.safekiddo.api.KlazifyClient;
import com.example.safekiddo.domain.Website;
import com.example.safekiddo.domain.dto.WebsiteDto;
import com.example.safekiddo.domain.dto.api.WebsiteApiDto;
import com.example.safekiddo.exception.ObjectNotFoundException;
import com.example.safekiddo.mapper.WebsiteMapper;
import com.example.safekiddo.repository.WebSiteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

@Slf4j
@Service
public class WebsiteServiceImpl implements WebsiteService {

    private final KlazifyClient klazifyClient;
    private final WebsiteMapper websiteMapper;
    private final WebSiteRepository webSiteRepository;

    private final String WEBSITE_NOT_FOUND = "The website not found: ";

    public WebsiteServiceImpl(KlazifyClient klazifyClient, WebsiteMapper websiteMapper, WebSiteRepository webSiteRepository) {
        this.klazifyClient = klazifyClient;
        this.websiteMapper = websiteMapper;
        this.webSiteRepository = webSiteRepository;
    }

    @Override
    @Transactional
    public Website addWebsite(String url) {
        if (!webSiteRepository.existsByUrl(url)) {
            return webSiteRepository.save(getWebsiteToDb(url));
        }
        return webSiteRepository.getWebsiteByUrl(url).orElseThrow(() -> new ObjectNotFoundException(WEBSITE_NOT_FOUND + url));
    }

    private Website getWebsiteToDb(String url) {
        WebsiteApiDto websiteApiDto = Objects.requireNonNull(klazifyClient.getWebsiteData(url).getBody());
        if (websiteApiDto.isSuccess()) {
            return websiteMapper.mapToWebsite(websiteApiDto);
        }
        throw new ObjectNotFoundException(WEBSITE_NOT_FOUND + url);
    }

    @Override
    @Transactional
    public Website updateWebsite(String url, WebsiteDto websiteDto) {
        return webSiteRepository.getWebsiteByUrl(url)
                .map(websiteFromDb -> {
                    patchWebsite(websiteDto, websiteFromDb);
                    return webSiteRepository.save(websiteFromDb);
                }).orElseThrow(() -> new ObjectNotFoundException(WEBSITE_NOT_FOUND + url));
    }

    private void patchWebsite(WebsiteDto websiteDto, Website websiteFromDb) {
        if (!ObjectUtils.isEmpty(websiteDto.getUrl())) {
            websiteFromDb.setUrl(websiteDto.getUrl());
        }
        if (!ObjectUtils.isEmpty(websiteDto.getLogoUrl())) {
            websiteFromDb.setLogoUrl(websiteDto.getLogoUrl());
        }
        if (!ObjectUtils.isEmpty(websiteDto.getSocialMedia())) {
            websiteFromDb.setSocialMedia(websiteMapper.mapToSocialMediaUpdate(websiteDto.getSocialMedia()));
        }
        if (!ObjectUtils.isEmpty(websiteDto.getWebsiteCategories())) {
            websiteFromDb.setWebsiteCategories(websiteMapper.mapToWebsiteCategoryListToUpdate(websiteDto.getWebsiteCategories()));
        }
    }

    @Override
    public Website getWebsite(String url) {
        return webSiteRepository.getWebsiteByUrl(url).orElseThrow(() -> new ObjectNotFoundException(WEBSITE_NOT_FOUND + url));
    }

    @Override
    @Transactional
    public void deleteWebsite(String url) {
        webSiteRepository.deleteByUrl(url);
        log.info("Website deleted: " + url);
    }

}
