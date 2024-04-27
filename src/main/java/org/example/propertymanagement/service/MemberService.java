package org.example.propertymanagement.service;


import org.example.propertymanagement.dto.request.ChangePasswordRequest;
import org.example.propertymanagement.dto.request.ForgotPasswordRequest;
import org.example.propertymanagement.entity.Member;

public interface MemberService {
    Member findByEmail(String email);

    Member profile(long id);

    Member findById(Long id);

    Member update(Long id, Member member);

    String changePassword(ChangePasswordRequest changePasswordRequest);

    String changeMyPassword(ChangePasswordRequest changePasswordRequest, String email);
    String forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

    String resetPassword(String email, String password);

}
