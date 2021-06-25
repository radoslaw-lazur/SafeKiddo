package com.example.safekiddo.service;

import com.example.safekiddo.api.KlazifyClient;
import com.example.safekiddo.domain.SocialMedia;
import com.example.safekiddo.domain.Website;
import com.example.safekiddo.domain.WebsiteCategory;
import com.example.safekiddo.exception.ObjectNotFoundException;
import com.example.safekiddo.mapper.WebsiteMapper;
import com.example.safekiddo.repository.WebSiteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebsiteServiceTest {

    @MockBean
    KlazifyClient klazifyClient;

    @MockBean
    WebsiteMapper websiteMapper;

    @MockBean
    WebSiteRepository webSiteRepository;

    private WebsiteService underTest;

    private final String TEST_URL = "http://test.pl";
    private final String TEST_LOGO_URL = "https://logourl.pl";
    private final String TEST_STRING = "TestValue";
    private final double TEST_CONFIDENCE = 0.55;

    private Website website;
    private SocialMedia socialMedia;
    private WebsiteCategory websiteCategory;

    @Before
    public void init() {
        underTest = new WebsiteServiceImpl(klazifyClient, websiteMapper, webSiteRepository);
        socialMedia = new SocialMedia(TEST_URL, TEST_URL, TEST_URL, TEST_URL, null, null, null, null);
        socialMedia.setId(1L);
        websiteCategory = new WebsiteCategory(TEST_CONFIDENCE, TEST_STRING);
        websiteCategory.setId(1L);
        website = new Website(1L, TEST_URL, TEST_LOGO_URL, socialMedia, Collections.singletonList(websiteCategory));
    }

    @Test
    public void shouldAddWebsiteButIfExistsInDatabaseItHasToBeReturned() {
        //Given
        when(webSiteRepository.existsByUrl(anyString())).thenReturn(Boolean.TRUE);
        when(webSiteRepository.getWebsiteByUrl(anyString())).thenReturn(Optional.of(website));
        //When
        Optional<Website> websiteFromDb = Optional.ofNullable(underTest.addWebsite(TEST_URL));
        //Then
        verify(webSiteRepository).existsByUrl(anyString());
        assertTrue(websiteFromDb.isPresent());
        assertEquals(TEST_URL, websiteFromDb.get().getUrl());
        assertEquals(TEST_CONFIDENCE, websiteFromDb.get().getWebsiteCategories().get(0).getConfidence(), 0);
        assertEquals(TEST_URL, websiteFromDb.get().getSocialMedia().getFacebookUrl());
        assertEquals(TEST_LOGO_URL, websiteFromDb.get().getLogoUrl());
    }

    @Test
    public void shouldGetWebsite() {
        //Given
        when(webSiteRepository.getWebsiteByUrl(anyString())).thenReturn(Optional.of(website));
        //When
        Optional<Website> websiteFromDb = Optional.ofNullable(underTest.getWebsite(TEST_URL));
        //Then
        verify(webSiteRepository).getWebsiteByUrl(anyString());
        assertTrue(websiteFromDb.isPresent());
        assertEquals(TEST_URL, websiteFromDb.get().getUrl());
        assertEquals(TEST_CONFIDENCE, websiteFromDb.get().getWebsiteCategories().get(0).getConfidence(), 0);
        assertEquals(TEST_URL, websiteFromDb.get().getSocialMedia().getFacebookUrl());
        assertEquals(TEST_LOGO_URL, websiteFromDb.get().getLogoUrl());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void shouldThrowObjectNotFoundExceptionWhenGettingWebsite() {
        //Given
        when(webSiteRepository.getWebsiteByUrl(anyString())).thenThrow(new ObjectNotFoundException(TEST_STRING));
        //When
        Optional<Website> websiteFromDb = Optional.ofNullable(underTest.getWebsite(TEST_URL));
        //Then
        assertFalse(websiteFromDb.isPresent());
    }

    @Test
    public void shouldDeleteWebsite() {
        //When & Then
        underTest.deleteWebsite(TEST_URL);
        verify(webSiteRepository).deleteByUrl(anyString());
    }

}
