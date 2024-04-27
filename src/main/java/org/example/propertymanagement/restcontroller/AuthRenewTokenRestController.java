package org.example.propertymanagement.restcontroller;


import lombok.RequiredArgsConstructor;
import org.example.propertymanagement.dto.response.TokenResponse;
import org.example.propertymanagement.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@RequestMapping("api/v1/token/refresh")
@RequiredArgsConstructor
public class AuthRenewTokenRestController {

    private final AuthService authService;

    @PostMapping
    public TokenResponse refresh(Principal principal) {

        return authService.issueAccessToken(principal);
    }

}
