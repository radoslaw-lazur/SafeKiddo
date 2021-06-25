package com.example.safekiddo.mapper;

import com.example.safekiddo.domain.SocialMedia;
import com.example.safekiddo.domain.Website;
import com.example.safekiddo.domain.WebsiteCategory;
import com.example.safekiddo.domain.dto.SocialMediaDto;
import com.example.safekiddo.domain.dto.WebsiteCategoryDto;
import com.example.safekiddo.domain.dto.api.CategoryApiDto;
import com.example.safekiddo.domain.dto.api.SocialMediaApiDto;
import com.example.safekiddo.domain.dto.api.WebsiteApiDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class WebsiteMapper {

    public Website mapToWebsite(final WebsiteApiDto websiteApiDto) {
        return new Website(
                websiteApiDto.getDomainApiDto().getUrl(),
                Optional.ofNullable(websiteApiDto.getDomainApiDto().getUrlLogo()).orElse(null),
                mapToSocialMedia(websiteApiDto.getDomainApiDto().getSocialMediaApiDto()),
                mapToWebSiteCategoryList(websiteApiDto.getDomainApiDto().getCategories())
        );
    }

    public SocialMedia mapToSocialMedia(final SocialMediaApiDto socialMediaApiDto) {
        if (Objects.isNull(socialMediaApiDto)) {
            return null;
        }
        return new SocialMedia(
                socialMediaApiDto.getFacebookSocialUrl(),
                socialMediaApiDto.getTwitterSocialUrl(),
                socialMediaApiDto.getInstagramSocialUrl(),
                socialMediaApiDto.getMediumSocialUrl(),
                socialMediaApiDto.getYoutubeSocialUrl(),
                socialMediaApiDto.getPinterestSocialUrl(),
                socialMediaApiDto.getLinkedinSocialUrl(),
                socialMediaApiDto.getGithubSocialUrl()
        );
    }

    public List<WebsiteCategory> mapToWebSiteCategoryList(final List<CategoryApiDto> categoriesDto) {
        return categoriesDto.stream()
                .map(categoryApiDto -> new WebsiteCategory(categoryApiDto.getConfidence(), categoryApiDto.getName()))
                .collect(Collectors.toList());
    }

    public SocialMedia mapToSocialMediaUpdate(final SocialMediaDto socialMediaDto) {
        if (Objects.isNull(socialMediaDto)) {
            return null;
        }
        return new SocialMedia(
                socialMediaDto.getFacebookUrl(),
                socialMediaDto.getTwitterUrl(),
                socialMediaDto.getInstagramUrl(),
                socialMediaDto.getMediumUrl(),
                socialMediaDto.getYoutubeUrl(),
                socialMediaDto.getPinterestUrl(),
                socialMediaDto.getLinkedinUrl(),
                socialMediaDto.getGithubUrl()
        );
    }

    public List<WebsiteCategory> mapToWebsiteCategoryListToUpdate(final List<WebsiteCategoryDto> categoriesDto) {
        return categoriesDto.stream()
                .map(categoryDto -> new WebsiteCategory(categoryDto.getConfidence(), categoryDto.getName()))
                .collect(Collectors.toList());
    }

}
