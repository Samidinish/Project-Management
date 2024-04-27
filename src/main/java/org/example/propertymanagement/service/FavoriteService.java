package org.example.propertymanagement.service;


import org.example.propertymanagement.entity.Member;
import org.example.propertymanagement.entity.Property;

import java.util.List;

public interface FavoriteService {
//    void addFavorite(long propertyId);
    void addFavorite(long propertyId, long memberId);
    void removeFavorite(long propertyId, long memberId);
    boolean isFavorite(long propertyId);
    List<Property> getFavoriteByMemberId(long memberId);
    List<Member> getFavoritePropertyId(long propertyId);
    List<Property> findFavoritesByMemberId(long memberId);

    //void unFavorite(long favoriteId);
    
}
