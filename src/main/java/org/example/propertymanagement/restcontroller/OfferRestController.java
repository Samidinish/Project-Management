package org.example.propertymanagement.restcontroller;


import jakarta.validation.Valid;
import org.example.propertymanagement.dto.request.OfferSubmissionRequest;
import org.example.propertymanagement.dto.response.OfferResponse;
import org.example.propertymanagement.dto.response.PageResponse;
import org.example.propertymanagement.entity.Member;
import org.example.propertymanagement.entity.Offer;
import org.example.propertymanagement.entity.Property;
import org.example.propertymanagement.entity.type.OfferStatus;
import org.example.propertymanagement.service.MemberService;
import org.example.propertymanagement.service.OfferService;
import org.example.propertymanagement.service.PropertyService;
import org.example.propertymanagement.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/v1/offers")
public class OfferRestController {
    private final OfferService offerService;
    private final MemberService memberService;
    private final PropertyService propertyService;

    @Autowired
    public OfferRestController
            (OfferService offerService, MemberService memberService, PropertyService propertyService)
    {
        this.offerService = offerService;
        this.memberService = memberService;
        this.propertyService = propertyService;
    }

    @GetMapping
    public ResponseEntity<PageResponse> getAllOffers(
            @PageableDefault(
                    size = 50,
                    direction = Sort.Direction.DESC,
                    sort = {"createdAt"}
            )
            Pageable pageable
    ) {
        Page<Offer> offers = offerService.getAllOffers(pageable);
        return new ResponseEntity<>(new PageResponse(offers, OfferResponse.class), HttpStatus.OK);
    }

    @PostMapping("/submit")
    public ResponseEntity<OfferResponse> submitOffer
            (@Valid @RequestBody OfferSubmissionRequest request, Principal principal)
    {
        try {
            Member customer = memberService.findByEmail(principal.getName());
            Property property = propertyService.findById(request.getPropertyId());
            String remark = request.getRemark();
            double price = request.getPrice();
            Offer submittedOffer = offerService.submitOffer(customer, property, price, remark);
            return new ResponseEntity<>(Util.mapObj(submittedOffer, OfferResponse.class), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/customer")
    public ResponseEntity<PageResponse> getAllOffersByCustomer(

            @PageableDefault(
                    size = 50,
                    direction = Sort.Direction.DESC,
                    sort = {"createdAt"}
            )
            Pageable pageable,
            Principal principal
    ) {
        try {
            Member customer = memberService.findByEmail(principal.getName());
            Page<Offer> offers = offerService.getAllOffersByCustomer(customer, pageable);
            return new ResponseEntity<>(new PageResponse(offers, OfferResponse.class), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<PageResponse> getAllOffersForProperty(
            @PathVariable long propertyId,
            @PageableDefault(
                    size = 50,
                    direction = Sort.Direction.DESC,
                    sort = {"createdAt"}
            )
            Pageable pageable
    ) {
        try {
            Property property = propertyService.findById(propertyId);
            Page<Offer> offers = offerService.getAllOffersForProperty(property, pageable);
            return new ResponseEntity<>(new PageResponse(offers, OfferResponse.class), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/property/{propertyId}/status/{status}")
    public ResponseEntity<PageResponse> getOffersForPropertyByStatus(
            @PathVariable long propertyId,
            @PathVariable OfferStatus status,
            @PageableDefault(
                    size = 50,
                    direction = Sort.Direction.DESC,
                    sort = {"createdAt"}
            )
            Pageable pageable
    ) {
        try {
            Property property = propertyService.findById(propertyId);
            Page<Offer> offers = offerService.getOffersForPropertyByStatus(property, status, pageable);
            return new ResponseEntity<>(new PageResponse(offers, OfferResponse.class), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/accept/{offerId}")
    public ResponseEntity<?> acceptOffer(@PathVariable long offerId) {
        try {
            Offer offer = offerService.findById(offerId);
            offerService.acceptOffer(offer);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/reject/{offerId}")
    public ResponseEntity<?> rejectOffer(@PathVariable long offerId) {
        try {
            Offer offer = offerService.findById(offerId);
            offerService.rejectOffer(offer);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/cancel/{offerId}")
    public ResponseEntity<?> cancelOffer(@PathVariable long offerId) {
        try {
            Offer offer = offerService.findById(offerId);
            offerService.cancelOffer(offer);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
