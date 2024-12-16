package com.zagbor.click.error;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LinkException.class)
    public String handleLinkException(LinkException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}