package com.zagbor.click.repository;

import com.zagbor.click.model.Link;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    List<Link> findAllByUser_Username(String username);

    Link findByShortUrl(String shortLink);

    boolean existsByShortUrl(String shortLink);

    void deleteByShortUrl(String shortLink);
}
