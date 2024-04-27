package org.example.propertymanagement.restcontroller;


import lombok.RequiredArgsConstructor;
import org.example.propertymanagement.dto.request.PropertyRequest;
import org.example.propertymanagement.dto.response.PageResponse;
import org.example.propertymanagement.dto.response.PropertyResponse;
import org.example.propertymanagement.entity.Property;
import org.example.propertymanagement.service.PropertyService;
import org.example.propertymanagement.util.Util;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/v1/properties")
@RequiredArgsConstructor
public class PropertyRestController {

    private final PropertyService propService;

    @GetMapping
    public ResponseEntity<PageResponse> findAllProps(
            @RequestParam(value = "memberId", required = false) Long memberId,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "numberOfRoom", required = false) String numberOfRoom,
            @RequestParam(value = "location", required = false) String location,

            @PageableDefault(
                    size = 50,
                    direction = Sort.Direction.DESC,
                    sort = {"createdAt"}
            )
            Pageable pageable,
            Principal principal
    ) {
        var props = propService.findAll(
                memberId,
                search,
                minPrice,
                maxPrice,
                category,
                type,
                numberOfRoom,
                location,
                pageable,
                principal
        );

        return ResponseEntity.ok(new PageResponse(props, PropertyResponse.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponse> findById(@PathVariable("id") Long id) {
        Property prop = propService.findById(id);
        return ResponseEntity.ok(Util.mapObj(prop, PropertyResponse.class));
    }

    @PostMapping
    public ResponseEntity<PropertyResponse> createProperty(
            @RequestBody PropertyRequest propertyRequest,
            Principal principal
    ) {
        return ResponseEntity.ok(
                Util.mapObj(propService.createProperty(propertyRequest, principal), PropertyResponse.class)
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<PropertyResponse> updateProperty(
            @PathVariable long id,
            @RequestBody PropertyRequest propertyRequest,
            Principal principal
    ) {
        return ResponseEntity.ok(
                Util.mapObj(propService.updateProperty(id, propertyRequest, principal), PropertyResponse.class)
        );
    }

}
