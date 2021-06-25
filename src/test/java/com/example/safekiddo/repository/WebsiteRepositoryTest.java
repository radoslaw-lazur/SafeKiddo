package com.example.safekiddo.repository;

import com.example.safekiddo.domain.SocialMedia;
import com.example.safekiddo.domain.Website;
import com.example.safekiddo.domain.WebsiteCategory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class WebsiteRepositoryTest {

    @Autowired
    private WebSiteRepository webSiteRepository;

    private final String WEBSITE_URL = "http://test.pl";
    private final String LOGO_URL = "http://logourl.pl";
    private final String CATEGORY_NAME = "CategoryName";
    private final double CONFIDENCE = 0.55;
    private Website website;

    @Before
    public void init() {
        website = new Website(WEBSITE_URL, LOGO_URL, null, null);
    }

    @Test
    public void shouldSaveWebsite() {
        //Given
        webSiteRepository.save(website);
        String websiteUrl = website.getUrl();
        //When
        Optional<Website> websiteDb = webSiteRepository.getWebsiteByUrl(websiteUrl);
        //Then
        assertTrue(websiteDb.isPresent());
    }

    @Test
    public void shouldGetWebsiteByUrl() {
        //Given
        webSiteRepository.save(website);
        String websiteUrl = website.getUrl();
        //When
        Optional<Website> websiteDb = webSiteRepository.getWebsiteByUrl(websiteUrl);
        //Then
        assertTrue(websiteDb.isPresent());
        assertEquals(WEBSITE_URL, websiteDb.get().getUrl());
        assertEquals(LOGO_URL, websiteDb.get().getLogoUrl());
    }

    @Test
    public void shouldSaveSocialMediaToWebsite() {
        //Given
        SocialMedia socialMedia = new SocialMedia(
                "facebookUrl",
                "twitterUrl",
                "instagramUrl",
                "mediumUrl",
                null,
                null,
                null,
                null
        );
        website.setSocialMedia(socialMedia);
        socialMedia.setWebsite(website);
        //When
        webSiteRepository.save(website);
        String websiteUrl = website.getUrl();
        Optional<Website> websiteDb = webSiteRepository.getWebsiteByUrl(websiteUrl);
        //Then
        assertTrue(websiteDb.isPresent());
        assertEquals(WEBSITE_URL, websiteDb.get().getUrl());
        assertEquals("facebookUrl", websiteDb.get().getSocialMedia().getFacebookUrl());
        assertTrue(Objects.isNull(websiteDb.get().getSocialMedia().getGithubUrl()));
    }

    @Test
    public void shouldSaveWebsiteCategoryListToWebsite() {
        //Given
        WebsiteCategory websiteCategory = new WebsiteCategory(CONFIDENCE, CATEGORY_NAME);
        website.setWebsiteCategories(Collections.singletonList(websiteCategory));
        websiteCategory.setWebsite(website);
        //When
        webSiteRepository.save(website);
        String websiteUrl = website.getUrl();
        Optional<Website> websiteDb = webSiteRepository.getWebsiteByUrl(websiteUrl);
        //Then
        assertTrue(websiteDb.isPresent());
        assertEquals(WEBSITE_URL, websiteDb.get().getUrl());
        assertEquals(CATEGORY_NAME, websiteDb.get().getWebsiteCategories().get(0).getName());
        assertEquals(CONFIDENCE, websiteDb.get().getWebsiteCategories().get(0).getConfidence(), 0);
        assertEquals(1, websiteDb.get().getWebsiteCategories().size());
    }

    @Test
    public void shouldCheckIfWebsiteExistsInDatabase() {
        //Given
        webSiteRepository.save(website);
        String websiteUrl = website.getUrl();
        //When
        boolean isWebsite = webSiteRepository.existsByUrl(websiteUrl);
        //Then
        assertTrue(isWebsite);
    }

    @Test
    public void shouldDeleteWebsiteByUrl() {
        //Given
        webSiteRepository.save(website);
        String websiteUrl = website.getUrl();
        //When
        webSiteRepository.deleteByUrl(websiteUrl);
        //Then
        assertFalse(webSiteRepository.existsByUrl(websiteUrl));
    }

    @After
    public void cleanUp() {
        webSiteRepository.deleteAll();
    }
}
