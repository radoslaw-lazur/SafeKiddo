package com.example.safekiddo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class CoreConfiguration implements WebMvcConfigurer {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(getClientHttpRequestFactory());
    }

    private SimpleClientHttpRequestFactory getClientHttpRequestFactory() {
        RestTemplateConfig restTemplateConfig = new RestTemplateConfig();
        SimpleClientHttpRequestFactory clientHttpRequestFactory
                = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(restTemplateConfig.getConnectTimeRestTemplateTimeout());
        clientHttpRequestFactory.setReadTimeout(restTemplateConfig.getReadTimeRestTemplateTimeout());
        return clientHttpRequestFactory;
    }

    @Bean
    public Docket employeeApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

}
