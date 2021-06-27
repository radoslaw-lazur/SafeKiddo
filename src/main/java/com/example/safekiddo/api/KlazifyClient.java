package com.example.safekiddo.api;

import com.example.safekiddo.domain.dto.api.WebsiteApiDto;
import com.example.safekiddo.exception.KlazifyClientException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@Slf4j
public class KlazifyClient {

    private final RestTemplate restTemplate;
    private final KlazifyConfig klazifyConfig;

    public KlazifyClient(RestTemplate restTemplate, KlazifyConfig klazifyConfig) {
        this.restTemplate = restTemplate;
        this.klazifyConfig = klazifyConfig;
    }

    public ResponseEntity<WebsiteApiDto> getWebsiteData(String url) {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        restTemplate.setRequestFactory(requestFactory);
        HttpHeaders httpHeaders = getHttpHeaders();
        try {
            return restTemplate.exchange(getKlazifyURL(url), HttpMethod.POST, new HttpEntity<>(httpHeaders), WebsiteApiDto.class);
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
            throw new KlazifyClientException("Klazify API Response Issue");
        }
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, klazifyConfig.getKlazifyApiKey());
        return httpHeaders;
    }

    private URI getKlazifyURL(String url) {
        return UriComponentsBuilder.fromHttpUrl(klazifyConfig.getKlazifyApiEndpoint()).queryParam("url", url).build().encode().toUri();
    }

}
