package com.zagbor.click.controller;

import com.zagbor.click.service.LinkService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class RedirectController {

    private final LinkService linkService;

    @GetMapping("/{postfix}")
    public String redirect(@PathVariable("postfix") String postfix, HttpServletRequest request) {
        return "redirect:" + linkService.getLinkByShortLink(postfix, request.getHeader("host")).getOriginalUrl();
    }
}
