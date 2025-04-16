package com.springrest.rest.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

@Controller
public class UserPageController {

    @GetMapping("/userpage")
    public String getUserPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("roles", userDetails.getAuthorities());

        return "userPage";
    }
}
