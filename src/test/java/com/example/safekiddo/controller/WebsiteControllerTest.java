package com.example.safekiddo.controller;

import com.example.safekiddo.domain.SocialMedia;
import com.example.safekiddo.domain.Website;
import com.example.safekiddo.domain.WebsiteCategory;
import com.example.safekiddo.domain.dto.WebsiteDto;
import com.example.safekiddo.exception.ObjectNotFoundException;
import com.example.safekiddo.service.WebsiteService;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Objects;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WebsiteController.class)
public class WebsiteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WebsiteService websiteService;

    private final String TEST_URL = "https://test.pl";
    private final String TEST_LOGO_URL = "https://logourl.pl";
    private final String TEST_STRING = "TestValue";
    private final double TEST_CONFIDENCE = 0.55;

    private Website website;
    private SocialMedia socialMedia;
    private WebsiteCategory websiteCategory;
    private WebsiteDto websiteDto;

    @Before
    public void init() {
        socialMedia = new SocialMedia(TEST_URL, TEST_URL, TEST_URL, TEST_URL, null, null, null, null);
        socialMedia.setId(1L);
        websiteCategory = new WebsiteCategory(TEST_CONFIDENCE, TEST_STRING);
        websiteCategory.setId(1L);
        website = new Website(1L, TEST_URL, TEST_LOGO_URL, socialMedia, Collections.singletonList(websiteCategory));
        websiteDto = new WebsiteDto(1L, TEST_URL, TEST_LOGO_URL, null, null);
    }

    @Test
    public void shouldAddWebsite() throws Exception {
        //Given
        when(websiteService.addWebsite(anyString())).thenReturn(website);
        //When & Then
        mockMvc.perform(post("/v1/websites")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("url", "url"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.url", is(TEST_URL)))
                .andExpect(jsonPath("$.logoUrl", is(TEST_LOGO_URL)))
                .andExpect(jsonPath("$.socialMedia.id", is(1)))
                .andExpect(jsonPath("$.socialMedia.facebookUrl", is(TEST_URL)))
                .andExpect(jsonPath("$.websiteCategories", hasSize(1)))
                .andExpect(jsonPath("$.websiteCategories[0].id", is(1)))
                .andExpect(jsonPath("$.websiteCategories[0].confidence", is(TEST_CONFIDENCE)))
                .andExpect(jsonPath("$.websiteCategories[0].name", is(TEST_STRING)));
    }

    @Test
    public void shouldGetWebsite() throws Exception {
        //Given
        when(websiteService.getWebsite(anyString())).thenReturn(website);
        //When & Then
        mockMvc.perform(get("/v1/websites")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("url", "url"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.url", is(TEST_URL)))
                .andExpect(jsonPath("$.logoUrl", is(TEST_LOGO_URL)))
                .andExpect(jsonPath("$.socialMedia.id", is(1)))
                .andExpect(jsonPath("$.socialMedia.facebookUrl", is(TEST_URL)))
                .andExpect(jsonPath("$.websiteCategories", hasSize(1)))
                .andExpect(jsonPath("$.websiteCategories[0].id", is(1)))
                .andExpect(jsonPath("$.websiteCategories[0].confidence", is(TEST_CONFIDENCE)))
                .andExpect(jsonPath("$.websiteCategories[0].name", is(TEST_STRING)));
    }

    @Test
    public void shouldPatchWebsite() throws Exception {
        //Given
        String jsonContent = new Gson().toJson(websiteDto);
        when(websiteService.updateWebsite(anyString(), any())).thenReturn(website);
        //When & Then
        mockMvc.perform(patch("/v1/websites")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("url", "url")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.url", is(TEST_URL)))
                .andExpect(jsonPath("$.logoUrl", is(TEST_LOGO_URL)))
                .andExpect(jsonPath("$.socialMedia.id", is(1)))
                .andExpect(jsonPath("$.socialMedia.facebookUrl", is(TEST_URL)))
                .andExpect(jsonPath("$.websiteCategories", hasSize(1)))
                .andExpect(jsonPath("$.websiteCategories[0].id", is(1)))
                .andExpect(jsonPath("$.websiteCategories[0].confidence", is(TEST_CONFIDENCE)))
                .andExpect(jsonPath("$.websiteCategories[0].name", is(TEST_STRING)));
    }

    @Test
    public void shouldDeleteWebsite() throws Exception {
        //Given
        WebsiteService websiteService = mock(WebsiteService.class);
        doNothing().when(websiteService).deleteWebsite(anyString());
        //When & Given
        mockMvc.perform(delete("/v1/websites")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("url", "url"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldThrowdObjectNotFoundExceptionWhenPostWebsite() throws Exception {
        //Given
        when(websiteService.addWebsite(anyString())).thenThrow(new ObjectNotFoundException(TEST_STRING));
        //When & Then
        mockMvc.perform(post("/v1/websites")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("url", "url"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ObjectNotFoundException))
                .andExpect(result -> assertEquals(TEST_STRING, Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void shouldThrowObjectNotFoundExceptionWhenGetWebsite() throws Exception {
        //Given
        when(websiteService.getWebsite(anyString())).thenThrow(new ObjectNotFoundException(TEST_STRING));
        //When & Then
        mockMvc.perform(get("/v1/websites")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("url", "url"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ObjectNotFoundException))
                .andExpect(result -> assertEquals(TEST_STRING, Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void shouldThrowObjectNotFoundExceptionWhenPatchWebsite() throws Exception {
        //Given
        String jsonContent = new Gson().toJson(websiteDto);
        when(websiteService.updateWebsite(anyString(), any())).thenThrow(new ObjectNotFoundException(TEST_STRING));
        //When & Then
        mockMvc.perform(patch("/v1/websites")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("url", "url")
                .content(jsonContent))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ObjectNotFoundException))
                .andExpect(result -> assertEquals(TEST_STRING, Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

}
