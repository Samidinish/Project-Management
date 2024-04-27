package org.example.propertymanagement.service;


import jakarta.transaction.Transactional;

import org.example.propertymanagement.entity.Member;
import org.example.propertymanagement.dto.request.MemberRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface AdminService {
    Page<Member> findAll(String role, Pageable pageable, Principal principal);

    @Transactional
    void approve(long id);

    Member update(long id , MemberRequest member);
}
