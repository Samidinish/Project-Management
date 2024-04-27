package org.example.propertymanagement.dto.request;

import lombok.Data;
import org.example.propertymanagement.entity.type.MemberStatus;

@Data
public class RegisterRequest {
     private String name;
     private String email;
     private String password;
     private String role;
     private MemberStatus status;
}
