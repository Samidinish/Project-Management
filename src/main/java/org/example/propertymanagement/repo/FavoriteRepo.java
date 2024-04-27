package org.example.propertymanagement.repo;

import org.example.propertymanagement.entity.Favorite;
import org.example.propertymanagement.entity.Member;
import org.example.propertymanagement.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepo extends JpaRepository<Favorite,Long> {
    @Query("SELECT p FROM Favorite f JOIN f.member u JOIN f.property p WHERE u.id = :memberId")
    List<Property> findFavoritePropertyByMemberId(@Param("memberId") long memberId);

    @Query("SELECT u FROM Favorite f JOIN f.member u JOIN f.property p WHERE p.id = :propertyId")
    List<Member> findFavoritesUsersByPropertyId(@Param("propertyId") long propertyId);

    boolean existsByMemberAndProperty(Member member, Property property);

    void deleteByMemberAndProperty(Member member, Property property);

    List<Favorite> findByMemberId(long memberId);

    Optional<Favorite> findByMemberAndProperty(Member member, Property property);

   
}