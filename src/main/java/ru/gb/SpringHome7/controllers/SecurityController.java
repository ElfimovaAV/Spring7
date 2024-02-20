package ru.gb.SpringHome7.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class SecurityController {

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    /** Перехват запроса на доступ любого зарегистрированного пользователя к публичной странице
     *
     * @return public-data.html
     */
    @GetMapping("/public-data")
    public String publicPage(){
        return "public-data";
    }

    /**Перехват запроса на доступ админа к приватной странице
     * (обычному пользователю доступа нет)
     * @return private-data.html
     */
    @GetMapping("/private-data")
    public String privatePage(){
        return "private-data";
    }

    /** Перехват запроса на вход в систему под своим логином и паролем
     *
     * @return index.html
     */
    @GetMapping("/login")
    public String login(){
        if (isAuthenticated()) {
            return "redirect:index";
        }
        return "login";
    }

}
