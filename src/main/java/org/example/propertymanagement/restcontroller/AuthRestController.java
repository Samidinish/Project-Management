package org.example.propertymanagement.restcontroller;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.propertymanagement.dto.request.AuthRequest;
import org.example.propertymanagement.dto.request.ChangePasswordRequest;
import org.example.propertymanagement.dto.request.ForgotPasswordRequest;
import org.example.propertymanagement.dto.request.RegisterRequest;
import org.example.propertymanagement.dto.response.MemberResponse;
import org.example.propertymanagement.dto.response.TokenResponse;
import org.example.propertymanagement.entity.Member;
import org.example.propertymanagement.service.AuthService;
import org.example.propertymanagement.service.MemberService;
import org.example.propertymanagement.util.Util;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthService authService;
    private final MemberService memberService;
    @PostMapping("token")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse token(@RequestBody AuthRequest authRequest) {
        return authService.issueAccessToken(authRequest);
    }

    @PostMapping("owner/register")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponse registerOwner(@RequestBody RegisterRequest registerRequest) {
        return Util.mapObj(authService.registerOwner(registerRequest), MemberResponse.class);
    }

    @PostMapping("customer/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Member registerCustomer(@RequestBody RegisterRequest registerRequest) {
       return authService.registerCustomer(registerRequest);
    }
    @PutMapping("change-password")
    @Transactional
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest passwordRequest) {
        var s = memberService.changePassword(passwordRequest);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @PostMapping("forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        return new ResponseEntity<>(memberService.forgotPassword(forgotPasswordRequest), HttpStatus.OK);
    }
}
