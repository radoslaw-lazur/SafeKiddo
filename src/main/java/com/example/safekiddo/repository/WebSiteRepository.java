package com.example.safekiddo.repository;

import com.example.safekiddo.domain.Website;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebSiteRepository extends JpaRepository<Website, Long> {

    @Override
    Website save(Website website);

    Optional<Website> getWebsiteByUrl(String url);

    void deleteByUrl(String url);

    boolean existsByUrl(String url);

}
