package com.my.springgatewayserver.controller;

import com.my.springgatewayserver.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user")
    public User getUser(@AuthenticationPrincipal OidcUser user) {
        log.info(user.toString());
        return new User(user.getPreferredUsername(),
                user.getGivenName(),
                user.getFamilyName(),
                user.getClaimAsStringList("roles"));
    }

}
