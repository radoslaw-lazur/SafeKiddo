package com.example.safekiddo.client;

import com.example.safekiddo.api.KlazifyClient;
import com.example.safekiddo.api.KlazifyConfig;
import com.example.safekiddo.domain.dto.api.DomainApiDto;
import com.example.safekiddo.domain.dto.api.WebsiteApiDto;
import com.example.safekiddo.exception.KlazifyClientException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KlazifyClientTest {

    @MockBean
    RestTemplate restTemplate;

    @MockBean
    KlazifyConfig klazifyConfig;

    private String TEST_KEY = "KEY";
    private String TEST_URL = "http://test.pl";
    private WebsiteApiDto websiteApiDto;

    private KlazifyClient underTest;

    @Before
    public void init() {
        underTest = new KlazifyClient(restTemplate, klazifyConfig);
        websiteApiDto = new WebsiteApiDto(new DomainApiDto(), Boolean.TRUE);
    }

    @Test
    public void shouldWebsiteApiDtoObject() {
        //Given
        when(klazifyConfig.getKlazifyApiKey()).thenReturn(TEST_KEY);
        when(klazifyConfig.getKlazifyApiEndpoint()).thenReturn(TEST_URL);
        when(restTemplate.exchange(any(), any(), any(), (Class<Object>) any())).thenReturn(ResponseEntity.of(Optional.of(websiteApiDto)));
        //When
        ResponseEntity<WebsiteApiDto> websiteApiDtoResponse = underTest.getWebsiteData(TEST_URL);
        //Then
        assertTrue(Objects.requireNonNull(websiteApiDtoResponse.getBody()).isSuccess());
        assertEquals(websiteApiDtoResponse.getBody().getDomainApiDto(), websiteApiDto.getDomainApiDto());
    }

    @Test(expected = KlazifyClientException.class)
    public void shouldThrowKlazifyClientException() {
        //Given
        when(klazifyConfig.getKlazifyApiKey()).thenReturn(TEST_KEY);
        when(klazifyConfig.getKlazifyApiEndpoint()).thenReturn(TEST_URL);
        when(restTemplate.exchange(any(), any(), any(), (Class<Object>) any())).thenThrow(new RestClientException("TEST"));
        //When & Then
        underTest.getWebsiteData(TEST_URL);
    }
}
