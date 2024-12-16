package com.zagbor.click.repository;

import com.zagbor.click.model.Link;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    List<Link> findByUserId(UUID userId);

    Link findByShortUrl(String shortLink);

    boolean existsByShortUrl(String shortLink);

    void deleteByShortUrl(String shortLink);
}
