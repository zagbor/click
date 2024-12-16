package com.zagbor.click.controller;

import com.zagbor.click.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Controller
@AllArgsConstructor
public class AccountController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/account")
    public String account() {
        return "account";
    }

    @RequestMapping("/loginSuccess")
    public String loginSuccess() {
        return "redirect:/";
    }

    @RequestMapping("/registration")
    public String registration() {
        return "registration";
    }

    @RequestMapping("/logoutSuccess")
    public String logoutSuccess() {
        return "redirect:/login?logout";
    }

    @PostMapping("/register")
    public RedirectView register(@RequestParam String username,
                                 @RequestParam String password) {
        try {
            String response = userService.registerUser(username, password);
            return new RedirectView("/login");
        } catch (IllegalArgumentException ex) {
            return new RedirectView("api/v1/register?error=" + ex.getMessage());
        }
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        // Создаём токен аутентификации
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username,
                password,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        // Пытаемся аутентифицировать пользователя
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        if (authentication.isAuthenticated()) {
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);
            HttpSession session = request.getSession(true);
            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, context);

            return "redirect:/account";
        } else {
            return "Неверный логин или пароль!";
        }
    }
}
