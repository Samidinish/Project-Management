package org.example.propertymanagement.service;



import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.example.propertymanagement.entity.Member;
import org.example.propertymanagement.entity.type.TokenType;
import org.example.propertymanagement.dto.request.AuthRequest;
import org.example.propertymanagement.dto.request.RegisterRequest;
import org.example.propertymanagement.dto.response.TokenResponse;

import java.security.Principal;

public interface AuthService {
    Member getAuthenticatedUser();
    org.springframework.security.core.Authentication authenticate(String email, String password);

   // String createToken(Authentication auth, String email, TokenType tokenType, long expired);

    String createToken(org.springframework.security.core.Authentication auth, String email, TokenType tokenType, long expired);

    TokenResponse issueAccessToken(AuthRequest authRequest);

    TokenResponse issueAccessToken(Principal principal);

    Member registerCustomer(RegisterRequest authRequest);

    Member registerOwner(RegisterRequest authRequest);

    Member registerAdmin(RegisterRequest authRequest);
}
