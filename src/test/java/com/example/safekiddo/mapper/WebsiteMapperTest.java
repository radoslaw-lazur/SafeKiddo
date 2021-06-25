package com.example.safekiddo.mapper;

import com.example.safekiddo.domain.SocialMedia;
import com.example.safekiddo.domain.Website;
import com.example.safekiddo.domain.WebsiteCategory;
import com.example.safekiddo.domain.dto.SocialMediaDto;
import com.example.safekiddo.domain.dto.WebsiteCategoryDto;
import com.example.safekiddo.domain.dto.api.CategoryApiDto;
import com.example.safekiddo.domain.dto.api.DomainApiDto;
import com.example.safekiddo.domain.dto.api.SocialMediaApiDto;
import com.example.safekiddo.domain.dto.api.WebsiteApiDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebsiteMapperTest {

    @Autowired
    private WebsiteMapper websiteMapper;

    private final String TEST_STRING = "TestValue";
    private final double TEST_DOUBLE = 0.55;

    private WebsiteApiDto websiteApiDto;
    private SocialMediaApiDto socialMediaApiDto;
    private CategoryApiDto categoryApiDto;
    private DomainApiDto domainApiDto;
    private SocialMediaDto socialMediaDto;
    private WebsiteCategoryDto websiteCategoryDto;

    @Before
    public void init() {
        categoryApiDto = new CategoryApiDto(TEST_DOUBLE, TEST_STRING);
        socialMediaApiDto = new SocialMediaApiDto(TEST_STRING, TEST_STRING, TEST_STRING, TEST_STRING, TEST_STRING, TEST_STRING, TEST_STRING, TEST_STRING);
        domainApiDto = new DomainApiDto(Collections.singletonList(categoryApiDto), TEST_STRING, TEST_STRING, socialMediaApiDto);
        websiteApiDto = new WebsiteApiDto(domainApiDto, Boolean.TRUE);
        socialMediaDto = new SocialMediaDto(1L, TEST_STRING, TEST_STRING, TEST_STRING, TEST_STRING, TEST_STRING, TEST_STRING, TEST_STRING, TEST_STRING);
        websiteCategoryDto = new WebsiteCategoryDto(1L, TEST_DOUBLE, TEST_STRING);
    }

    @Test
    public void shouldMapToWebSiteCategoryList() {
        //When
        List<WebsiteCategory> websiteCategories = websiteMapper.mapToWebSiteCategoryList(Collections.singletonList(categoryApiDto));
        //Then
        assertEquals(TEST_DOUBLE, websiteCategories.get(0).getConfidence(), 0);
        assertEquals(TEST_STRING, websiteCategories.get(0).getName());
    }

    @Test
    public void shouldMapToSocialMedia() {
        //When
        SocialMedia socialMedia = websiteMapper.mapToSocialMedia(socialMediaApiDto);
        //Then
        assertEquals(TEST_STRING, socialMedia.getFacebookUrl());
        assertEquals(TEST_STRING, socialMedia.getGithubUrl());
    }

    @Test
    public void shouldReturnNullWhenSocialMediaApiDtoEqualsNull() {
        //When
        SocialMedia socialMedia = websiteMapper.mapToSocialMedia(null);
        //Then
        assertTrue(Objects.isNull(socialMedia));
    }

    @Test
    public void shouldMapToWebsite() {
        //When
        Website website = websiteMapper.mapToWebsite(websiteApiDto);
        //Then
        assertEquals(1, website.getWebsiteCategories().size());
        assertEquals(TEST_DOUBLE, website.getWebsiteCategories().get(0).getConfidence(), 0);
        assertEquals(TEST_STRING, website.getUrl());
    }

    @Test
    public void shouldMapToSocialMediaUpdate() {
        //When
        SocialMedia socialMedia = websiteMapper.mapToSocialMediaUpdate(socialMediaDto);
        //Then
        assertEquals(TEST_STRING, socialMedia.getGithubUrl());
        assertEquals(TEST_STRING, socialMedia.getFacebookUrl());
    }

    @Test
    public void shouldReturnNullWhenSocialMediaDtoEqualsNull() {
        //When
        SocialMedia socialMedia = websiteMapper.mapToSocialMediaUpdate(null);
        //Then
        assertTrue(Objects.isNull(socialMedia));
    }

    @Test
    public void shouldMapToWebsiteCategoryListToUpdate() {
        //When
        List<WebsiteCategory> websiteCategories = websiteMapper.mapToWebsiteCategoryListToUpdate(Collections.singletonList(websiteCategoryDto));
        //Then
        assertEquals(1, websiteCategories.size());
        assertEquals(TEST_DOUBLE, websiteCategories.get(0).getConfidence(), 0);
        assertEquals(TEST_STRING, websiteCategories.get(0).getName());
    }

}
