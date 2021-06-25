package com.example.safekiddo.controller;

import com.example.safekiddo.domain.Website;
import com.example.safekiddo.domain.dto.WebsiteDto;
import com.example.safekiddo.service.WebsiteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/websites")
public class WebsiteController {

    private final WebsiteService websiteService;

    public WebsiteController(WebsiteService websiteService) {
        this.websiteService = websiteService;
    }

    @PostMapping
    public ResponseEntity<Website> addWebsite(@RequestParam String url) {
        return ResponseEntity.ok().body(websiteService.addWebsite(url));
    }

    @GetMapping
    public ResponseEntity<Website> getWebsite(@RequestParam String url) {
        return ResponseEntity.ok().body(websiteService.getWebsite(url));
    }

    @PatchMapping
    public ResponseEntity<Website> patchWebsite(@RequestParam String url, @RequestBody @Valid WebsiteDto websiteDto) {
        return ResponseEntity.ok().body(websiteService.updateWebsite(url, websiteDto));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteWebsite(@RequestParam String url) {
       websiteService.deleteWebsite(url);
       return ResponseEntity.ok().build();
    }

}
