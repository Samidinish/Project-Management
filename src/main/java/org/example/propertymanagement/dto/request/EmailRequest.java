package org.example.propertymanagement.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmailRequest {

    private String title;
    private String content;

    @NotNull
    private String recipient;

}
