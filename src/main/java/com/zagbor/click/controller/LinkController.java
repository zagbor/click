package com.zagbor.click.controller;

import com.zagbor.click.dto.LinkDto;
import com.zagbor.click.mapper.LinkMapper;
import com.zagbor.click.service.LinkService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/links")
@RestController
public class LinkController {

    private static final Logger logger = LoggerFactory.getLogger(LinkController.class);

    private final LinkService linkService;

    private final LinkMapper linkMapper;

    @GetMapping("/all")
    public List<LinkDto> getLinks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("get links for user {}", authentication.getName());
        return linkService.getLinksByUser(authentication.getName()).stream()
                .map(linkMapper::toDto).toList();
    }

    @PostMapping("/add")
    public void addLink(@RequestBody LinkDto linkDto, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        linkService.createLink(linkMapper.toEntity(linkDto), request.getHeader("host"), authentication.getName());
    }

    @PostMapping("/delete")
    public void deleteLink(@RequestBody LinkDto linkDto) {
        linkService.deleteLink(linkDto.shortUrl());
    }

}

