package org.example.propertymanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.Data;
import org.example.propertymanagement.entity.Role;

import java.util.List;
import java.util.Map;


@Data
public class MemberResponse {
    private long id;
    private String email;
    private String name;
    private List<Role> roles;
    private String city;
    private String address;
    private String state;
    private String zip;
    private String status;
    private String phone;

    @JsonGetter("roles")
    public List<Map<String, String>> getRoles() {
        return roles.stream().map(m -> Map.of("role", m.getName())).toList();
    }

}
