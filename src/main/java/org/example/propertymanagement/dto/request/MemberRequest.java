package org.example.propertymanagement.dto.request;


import lombok.Data;
import org.example.propertymanagement.entity.type.MemberStatus;

@Data
public class MemberRequest {
    private String email;
    private String name;
    private String city;
    private String address;
    private String state;
    private String zip;
    private String phone;
    private MemberStatus status;
}
