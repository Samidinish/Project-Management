package org.example.propertymanagement.service.impl;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.propertymanagement.customexception.PlatformException;
import org.example.propertymanagement.dto.request.MemberRequest;
import org.example.propertymanagement.entity.Member;
import org.example.propertymanagement.entity.type.MemberStatus;
import org.example.propertymanagement.repo.MemberRepo;
import org.example.propertymanagement.service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final MemberRepo memberRepo;

    @Override
    public Page<Member> findAll(String role, Pageable pageable, Principal principal) {

        Specification<Member> spec = Specification.allOf();

        if (Objects.nonNull(role)) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.join("roles").get("name"), role));
        }

        return memberRepo.findAll(spec, pageable);
    }

    public Member findById(long id) {
        return memberRepo.findById(id)
                .orElseThrow(() -> new PlatformException("User not found", HttpStatus.NOT_FOUND));
    }

    @Transactional
    @Override
    public void approve(long id) {
        var member = findById(id);

        if (MemberStatus.ACTIVE.equals(member.getStatus())) {
            throw new PlatformException("Member is already activated", HttpStatus.BAD_REQUEST);
        }

        member.setStatus(MemberStatus.ACTIVE);
        memberRepo.save(member);
    }

    @Override
    public Member update(long id, MemberRequest memberRequest) {
        Member member = findById(id);
        member.setName(memberRequest.getName());
        member.setEmail(memberRequest.getEmail());
        member.setPhone(memberRequest.getPhone());
        member.setStatus(memberRequest.getStatus());
        member.setCity(memberRequest.getCity());
        member.setAddress(memberRequest.getAddress());
        member.setState(memberRequest.getState());
        member.setZip(memberRequest.getZip());
        return memberRepo.save(member);
    }

}
