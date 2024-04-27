package org.example.propertymanagement.service;


import org.example.propertymanagement.dto.request.PropertyRequest;
import org.example.propertymanagement.entity.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface PropertyService {

    Page<Property> findAll(Long memberId, String search, Double minPrice, Double maxPrice, String category, String type, String numberOfRoom, String location, Pageable pageable, Principal principal);

    Property findById(long id);

    Property createProperty(PropertyRequest propertyRequest, Principal principal);

    Property updateProperty(long id, PropertyRequest propertyRequest, Principal principal);
}
