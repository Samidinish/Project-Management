package org.example.propertymanagement.dto.request;

import lombok.Data;

@Data
public class OfferSubmissionRequest {
    private Long propertyId;
    private String remark;
    private Double price;
}
