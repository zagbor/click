package com.zagbor.click.service;

import com.zagbor.click.error.LinkException;
import com.zagbor.click.model.Link;
import com.zagbor.click.model.User;
import com.zagbor.click.repository.LinkRepository;
import com.zagbor.click.utils.LinkGenerator;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class LinkService {

    private static final Logger logger = LoggerFactory.getLogger(LinkService.class);

    private final LinkRepository linkRepository;

    private final UserService userService;

    public void getLink(Long id) {
        clickLink(linkRepository.findById(id).get().getShortUrl());
        linkRepository.findById(id);
    }

    public List<Link> getLinksByUser(String userName) {
        return linkRepository.findAllByUser_Username(userName);
    }

    public Link getLinkByShortLink(String shortLink, String host) {
        String fullShortLink = host + "/" + shortLink;
        clickLink(fullShortLink);
        return linkRepository.findByShortUrl(fullShortLink);
    }

    public Link createLink(Link link, String host, String userName) {
        String generatedLink;
        boolean exists;

        do {
            generatedLink = host + "/" + LinkGenerator.generateLink();
            exists = linkRepository.existsByShortUrl(generatedLink);
        } while (exists);
        User user = userService.getUserByUsername(userName);
        link.setUser(user);
        link.setShortUrl(generatedLink);
        link.setCreationDate(LocalDateTime.now());
        return linkRepository.save(link);
    }

    public void deleteLink(String shortLink) {
        linkRepository.deleteByShortUrl(shortLink);
    }

    private void clickLink(String link) {
        Link linkByShortLink = linkRepository.findByShortUrl(link);
        if (linkByShortLink == null) {
            logger.error("Link not found");
            throw new LinkException("Link not found");
        }

        if (linkByShortLink.getExpirationDate().isBefore(LocalDateTime.now())) {
            logger.error("Link expired");
            throw new LinkException("Link expired");
        }

        if (linkByShortLink.getNumberOfClicks() + 1 > linkByShortLink.getLimitOfClicks()) {
            logger.error("Link limit of clicks exceeded");
            throw new LinkException("Link limit of clicks exceeded");
        }

        linkByShortLink.setNumberOfClicks(linkByShortLink.getNumberOfClicks() + 1);
        linkRepository.save(linkByShortLink).getOriginalUrl();
    }

    public void deleteExpiredLinks() {
        linkRepository.findAll().stream()
                .filter(link -> link.getExpirationDate().isBefore(LocalDateTime.now()))
                .forEach(linkRepository::delete);
    }

}
