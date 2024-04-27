package org.example.propertymanagement.dto.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String token;
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
}
