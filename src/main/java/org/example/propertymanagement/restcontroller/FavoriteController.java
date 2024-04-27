package org.example.propertymanagement.restcontroller;


import org.example.propertymanagement.dto.response.PropertyResponse;
import org.example.propertymanagement.entity.Member;
import org.example.propertymanagement.entity.Property;
import org.example.propertymanagement.service.FavoriteService;
import org.example.propertymanagement.service.MemberService;
import org.example.propertymanagement.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private MemberService memberService;

        @PostMapping("/{propertyId}")
        public ResponseEntity<String> addFavorite(@PathVariable long propertyId, Principal principal) {
            favoriteService.addFavorite(propertyId, memberService.findByEmail(principal.getName()).getId());
            return ResponseEntity.ok("Favorite added");
        }

        @DeleteMapping("/{propertyId}")
        public void removeFavorite(@PathVariable long propertyId , Principal principal) {
            favoriteService.removeFavorite(propertyId, memberService.findByEmail(principal.getName()).getId());
        }

        @PostMapping("/isFavorite")
        public boolean isFavorite(long propertyId) {
            return favoriteService.isFavorite(propertyId);
        }

        @PostMapping("/getFavoriteByMemberId")
        public void getFavoriteByMemberId(long memberId) {
            favoriteService.getFavoriteByMemberId(memberId);
        }

        @PostMapping("/getFavoritePropertyId")

        public void getFavoritePropertyId(long propertyId) {
            favoriteService.getFavoritePropertyId(propertyId);
        }
        @GetMapping
        public List<PropertyResponse> listFavoritesByMemberId(Principal principal) {
            Member member = memberService.findByEmail(principal.getName());
            List<Property> property = favoriteService.findFavoritesByMemberId(member.getId());
            return Util.mapList(property, PropertyResponse.class);
        }

        // @PostMapping("/unFavorite")
        // public void unFavorite(long favoriteId) {
        //     favoriteService.unFavorite(favoriteId);
        // }
    
}
