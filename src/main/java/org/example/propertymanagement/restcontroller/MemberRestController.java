package org.example.propertymanagement.restcontroller;

import lombok.RequiredArgsConstructor;
import org.example.propertymanagement.dto.request.ChangePasswordRequest;
import org.example.propertymanagement.dto.response.MemberResponse;
import org.example.propertymanagement.entity.Member;
import org.example.propertymanagement.service.AuthService;
import org.example.propertymanagement.service.MemberService;
import org.example.propertymanagement.util.Util;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.security.Principal;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;
    private final AuthService authService;

    @GetMapping("/profile")
    public MemberResponse profile() {
        Member member = authService.getAuthenticatedUser();
        return Util.mapObj(member, MemberResponse.class);

     }
     @PutMapping("/profile")
     public Member updateProfile(@RequestBody Member member) {
         Member m = authService.getAuthenticatedUser();
         return memberService.update(m.getId(), member);
     }

     @PutMapping("/change-password")
        public String changePassword(@RequestBody ChangePasswordRequest passwordRequest, Principal principal) {
            return memberService.changeMyPassword(passwordRequest, principal.getName());
        }
}
