package org.example.propertymanagement.restcontroller;


import lombok.RequiredArgsConstructor;
import org.example.propertymanagement.dto.request.MemberRequest;
import org.example.propertymanagement.dto.response.MemberResponse;
import org.example.propertymanagement.dto.response.PageResponse;
import org.example.propertymanagement.service.AdminService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/v1/admins")
@RequiredArgsConstructor
public class AdminRestController {

    private final AdminService adminService;

    @GetMapping("users")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse findAll(
            @RequestParam(value = "role", required = false) String role,

            @PageableDefault(
                    size = 50,
                    direction = Sort.Direction.DESC,
                    sort = {"createdAt"}
            )
            Pageable pageable,
            Principal principal
    ) {
        var members = adminService.findAll(role, pageable, principal);
        return new PageResponse(members, MemberResponse.class);
    }

    @PutMapping("users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> update(@PathVariable("id") long id, @RequestBody MemberRequest memberRequest) {
        adminService.update(id, memberRequest);
        return new ResponseEntity<>("User approved", HttpStatus.NO_CONTENT);
    }



    @PostMapping("users/{id}/approve")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void approve(@PathVariable("id") long id) {
        adminService.approve(id);
    }

}
